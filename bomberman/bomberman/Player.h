#ifndef PLAYER_H
#define PLAYER_H
#include "PlayerKeys.h"
#include <SFML\Graphics.hpp>
#include <chrono>
#include <queue>
#include <tuple>
#include "Animation.h"

class Player
{
	PlayerKeys player_key;
	enum class AnimationIndex
	{
		WalkingUp, WalkingDown, WalkingLeft, WalkingRight, Death, Count
	};
	Animation animations[int(AnimationIndex::Count)];
	AnimationIndex cur_animation;
	sf::Vector2f elem_size; //wielksoc elelemtnu we wczytywanej teksturze (sa nieptorzebne przerwy miedzy psozczegolnymi psotaciami)
	sf::Sprite character;
	sf::Vector2f character_size, board_elem_size;
	int hp;
	int num_of_bomb;
	int num_of_bomb_bigger_dist;
	int num_of_planted_bomb;
	int player_num;
	sf::Vector2f char_pos;

public:
	std::chrono::time_point<std::chrono::system_clock> before, after;
	double delta_t;
	bool is_dying = false, is_dead = false, is_save = true, is_winner = false;
	//kolejka FIFO bomb, ktore zostana postawione; skladowe: wspolrzedne elementu planszy, na ktorym stoi bomba, czas postawienia bomby, dystans bomby
	std::queue<std::tuple<sf::Vector2f, std::chrono::time_point<std::chrono::system_clock>, int>> bomb_queue;

private:

	/* Ponizsze metody wywolywane sa jedynie przez metode Action; przesuwaja gracza w kierunku, w ktorym sie on porusza*/
	void MoveUp();
	void MoveDown(sf::RenderWindow &window);
	void MoveLeft();
	void MoveRight(sf::RenderWindow &window);

public:
	Player(const sf::Vector2f &board_elem_size, const sf::Vector2f &char_pos, int player_num, sf::Texture &playerTexture);
	sf::Vector2f GetPos() const { return character.getPosition(); }
	sf::Vector2f GetMiddlePos() const { return sf::Vector2f(character.getPosition().x + character_size.x / 2.0, character.getPosition().y + character_size.y / 2.0); }
	/* Na podstawie wcisnietego przez gracza klawisza, zostaje ustawiona odpowiednia animacja i wywo³ana zostaje odpowiednia metoda dotycz¹ca ruchu gracza*/
	void Action(sf::RenderWindow &window, sf::Vector2f &player_pos);
	void DeathAnimation();
	/* Zwraca true, jezeli gracz nacisnal przycisk swiadczacy o checi postawienia bomby */
	bool BombAction();
	void IncNumOfBomb() { ++num_of_bomb; }
	void IncNumOfBombBiggerDist() { ++num_of_bomb_bigger_dist; }
	void IncHP() { ++hp; }
	void IncNumOfPlantedBomb() { ++num_of_planted_bomb; }
	void DecNumOfBomb() { --num_of_bomb; }
	void DecNumOfBombBiggerDist() { --num_of_bomb_bigger_dist; }
	void DecHP() { --hp; }
	void DecNumOfPlantedBomb() { --num_of_planted_bomb; }
	int GetNumOfPlantedBomb() const { return num_of_planted_bomb; }
	int GetHP() const { return hp; }
	int GetNumOfBomb() const { return num_of_bomb; }
	int GetNumOfBombBiggerDist() const { return num_of_bomb_bigger_dist; }
	int GetNumOfPlantedBomb() { return num_of_planted_bomb; }
	/*jesli kolizja - wracamy do poprzedniej pozycji */
	void ActionIfCollision(sf::Vector2f player_position)
	{
		character.setPosition(player_position);
	}

	void DrawPlayer(sf::RenderWindow &window)
	{
		window.draw(character);
	}
	/* sprawdzenie kolizji funkcja z sfml'a */
	bool IsCollision(const sf::RectangleShape &elem);
	/* Zwraca true, jezeli gracz znajduje sie na elemencie planszy, ktory zostanie objety przez wybuch bomby */
	bool IsKilledByABomb(int bmb_pos_x, int bmb_pos_y);

};


#endif
