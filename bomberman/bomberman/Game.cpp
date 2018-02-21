#include "Game.h"
#include "Player.h"
#include "PlantedBomb.h"
#include "StaticWall.h"
#include <iostream>
#include <algorithm>
#include <typeinfo>
#include <chrono>
#include <utility>
#include <queue>
#include <tuple>
using namespace std;

Game::Game(BoardFactory& board_factory, double width, double  height, Config &config) : conf(config), width(width), height(height)
{
	count_elems = board_factory.GetCountElems();
	board_elems = move(board_factory.GetBoardElems());
	elem_size = board_factory.GetWallSize();
	num_of_elems.x = width / elem_size.x;
	num_of_elems.y = height / elem_size.y;
	num_of_players = conf.players_num;
}


int Game::PlayAGame(sf::RenderWindow &window)
{
	sf::Event event;
	vector<Player> characters;
	characters.push_back(Player(elem_size,sf::Vector2f(40,40),1, playerTexture));
	if(num_of_players == 2)
		characters.push_back(Player(elem_size, sf::Vector2f(600, 600), 2, playerTexture));
	sf::Vector2f player_pos, bomb_pos;
	chrono::time_point<chrono::system_clock> cur_time, bomb_time;
	for (auto& player : characters)
	{
		player.before = chrono::system_clock::now();
	}
	while (true)
	{
		while (window.pollEvent(event))
		{
			switch (event.type)
			{
			case sf::Event::Closed:
				window.close();
				return -1;
			}
			switch (event.key.code)
			{
			case sf::Keyboard::Escape:
				return 0;
			}
		}
		for (auto& player : characters)
		{
			is_collision = false;
			player.after = chrono::system_clock::now();
			player.delta_t = chrono::duration<double>(player.after - player.before).count();
			player.before = player.after;
			if (player.is_dying) player.DeathAnimation();
			else 
				player.Action(window, player_pos);

			/* sprawdzenie, czy nastapila kolizja; nalezy porownac polozenie postaci z polozeniem kazdego z elementow planszy oraz sprawdzic, czy dla danego elementu
			nastepuje kolizja, czy tez postac moze wejsc na ten element */
			for (int i = 0; i < num_of_elems.x; ++i)
			{
				auto colliding_elem = find_if(board_elems[i].begin(), board_elems[i].end(), [&](auto& ptr) {
					return player.IsCollision((ptr->GetElem())) && ptr->PlayerAction(player, window, ptr);
				});
				is_collision = colliding_elem != board_elems[i].end();
				if (is_collision) break;
			}
			if (is_collision) player.ActionIfCollision(player_pos);

			/* podlozenie bomby moze nastapic tylko wtedy, gdy zostanie wywolana akcja do tego potrzebna oraz postac nie podlozyla juz maksymalnej liczby bomb */
			if (player.BombAction() &&  player.GetNumOfPlantedBomb() < player.GetNumOfBomb())
			{
				if (PutABomb(player, bomb_pos))
				{
					bomb_time = chrono::system_clock::now(); // czas postawienia bomby
					player.bomb_queue.push(make_tuple(bomb_pos, bomb_time,player.GetNumOfBombBiggerDist()));
				}
			}
			/* jezeli na planszy leza bomby, nalezy sprawdzic, czy nie powinny wybuchnac */
			{
				if (!player.bomb_queue.empty())
				{
					cur_time = chrono::system_clock::now();
					double time_dif = chrono::duration<double>(cur_time - get<1>(player.bomb_queue.front())).count();
					if (time_dif > 3.0)
					{
						
						for (auto &pl : characters)						
						{							
							pl.is_save = BombExplosion(get<0>(player.bomb_queue.front()), get<2>(player.bomb_queue.front()), pl,player.delta_t); //DLA KAZDEGO PLAYERA
							if (!pl.is_save)
							{
								pl.DecHP();
								if (pl.GetHP() == 0)
									pl.is_dying = true;
							}
						}
						player.DecNumOfPlantedBomb();
						player.bomb_queue.pop();
					}
				}
			}

			if(ControlGameOver(player, characters)) return 3; //jezeli ktorys z graczy przegral lub wygral, nalezy wyswietlic informacje o tym oraz zakonczyc aktualna rozgrywke
		}
		
		window.clear(sf::Color::White);

		for (int i = 0; i < num_of_elems.x; ++i)
		{
			for (int j = 0; j < num_of_elems.y; ++j)
			{
				board_elems[i][j]->DrawOnBoard(window);
			}
		}
		for (auto& players : characters)
			players.DrawPlayer(window);
		window.display();
	}

	return -1; //nigdy nie dojdzie do tego miejsca, ale w razie czego, niech program sie zakonczy
}

bool Game::PutABomb(Player &player, sf::Vector2f &bomb_pos)
{
	int i = (int)(player.GetMiddlePos().x / elem_size.x);
	int j = (int)(player.GetMiddlePos().y / elem_size.y);
	bomb_pos = sf::Vector2f(i * elem_size.x, j * elem_size.y);
	if (board_elems[i][j]->PutABomb(board_elems[i][j]))
	{
		player.IncNumOfPlantedBomb();
		return true;
	}
	return false;
	
}

bool Game::BombExplosion(const sf::Vector2f &bomb_pos, int bomb_dist, Player &player, double delta_t)
{
	//int bomb_dist = player->GetNumOfBombBiggerDist();
	bool player_alive = true;
	int i = (int)(bomb_pos.x / elem_size.x);
	int j = (int)(bomb_pos.y / elem_size.y);
	board_elems[i][j]->ReactionToABomb(board_elems[i][j], count_elems);
	if (player.IsKilledByABomb(i, j)) player_alive = false;
	bool was_static_wall[4] = { false, false, false, false }; // 0 - element na prawo, 1 - element w dol, 2 - element w lewo, 3 - element na gore; jesli jest statyczna sciana, to nastepny element w tym kierunku nie powinien byc niszczony
	for (int m = 0; m <= bomb_dist; ++m)
	{ 
		
		if (!was_static_wall[0])
		{
			if (typeid(*board_elems[i + 1 + m][j]) != typeid(PlantedBomb)) board_elems[i + 1 + m][j]->ReactionToABomb(board_elems[i + 1 + m][j], count_elems);
			if (player.IsKilledByABomb(i+1+m, j)) player_alive = false;
		}
		if (!was_static_wall[1])
		{
			if (typeid(*board_elems[i][j+1+m]) != typeid(PlantedBomb)) board_elems[i][j + 1 + m]->ReactionToABomb(board_elems[i][j + 1 + m], count_elems);
			if (player.IsKilledByABomb(i, j+1+m)) player_alive = false;
		}
		if (!was_static_wall[2])
		{
			if (typeid(*board_elems[i - 1 - m][j]) != typeid(PlantedBomb)) board_elems[i - 1 - m][j]->ReactionToABomb(board_elems[i - 1 - m][j],count_elems);
			if (player.IsKilledByABomb(i - 1 - m, j)) player_alive = false;
		}
		if (!was_static_wall[3])
		{
			if (typeid(*board_elems[i][j-1-m]) != typeid(PlantedBomb)) board_elems[i][j - 1 - m]->ReactionToABomb(board_elems[i][j - 1 - m],count_elems);
			if (player.IsKilledByABomb(i, j - 1 - m)) player_alive = false;
		}
		if (i + 1 + m < num_of_elems.x &&typeid(*board_elems[i + 1 + m][j]) == typeid(StaticWall)) was_static_wall[0] = true;
		if (j + 1 + m < num_of_elems.y &&typeid(*board_elems[i][j+1+m]) == typeid(StaticWall)) was_static_wall[1] = true;
		if (i - 1 - m >= 0 && typeid(*board_elems[i - 1 - m][j]) == typeid(StaticWall)) was_static_wall[2] = true;
		if (j - 1 - m >= 0 && typeid(*board_elems[i][j-1-m]) == typeid(StaticWall)) was_static_wall[3] = true;
	}
	if (player_alive) return true;
	return false;
}

bool Game::ControlGameOver(const Player &player,const  vector<Player>& charac)
{
	if (player.is_dead)
	{
		if (num_of_players == 1)
			conf.game_over_state = Config::one_player_loose;
		else
		{
			if (charac[0].is_dying && charac[1].is_dying) conf.game_over_state = Config::plyers_draw;
			else if (charac[0].is_dead) conf.game_over_state = Config::sec_player_win;
			else  conf.game_over_state = Config::first_player_win;
		}
		return true;
	}
	if (player.is_winner)
	{
		if (num_of_players == 1)
			conf.game_over_state = Config::one_player_win;
		else
		{
			if (charac[0].is_winner && charac[1].is_winner) conf.game_over_state = Config::plyers_draw;
			else if (charac[0].is_winner) conf.game_over_state = Config::first_player_win;
			else  conf.game_over_state = Config::sec_player_win;
		}
		return true;
	}
	return false;
}