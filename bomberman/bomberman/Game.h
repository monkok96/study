#ifndef GAME_H
#define GAME_H
#include <SFML/Graphics.hpp>
#include <string>
#include "Menu.h"
#include "Config.h"
#include "BoardFactory.h"
#include "Player.h"

class Game
{
	Config &conf;
	double width, height;
	std::vector<std::vector<std::unique_ptr<BoardElem>>> board_elems;
	sf::Vector2f elem_size;
	sf::Vector2u num_of_elems; //tak naprawde wektor ile na ile mamy tablice elementow
	bool is_collision = false;
	unsigned int count_elems;
	sf::Texture playerTexture;
	int num_of_players;

private:
	/* Zwraca true, jezeli ktorys z graczy przegral lub wygral (gra sie zakonczyla). Dodatkowo korzystajac ze struktury Config, ustawia odpowiedni stan, mowiacy o tym, ktory z graczy wygral lub tez przegral*/
	bool ControlGameOver(const Player &player, const std::vector<Player> &charac);
	/* Zwraca true, jezeli na danym typie sciany mozna postawic bombe. Bomba ta zostanie postawiona na elemencie planszy znajdujacym sie najblizej srodka postaci gracza*/
	bool PutABomb(Player &player, sf::Vector2f &bomb_pos);
	/* Zwraca true, jezeli gracz po wybuchu bomby dalej zyje; w przeciwnym wypadku - zwraca false. Dba o reakcje wszystkich znajdujacych sie w sasiedztwie o wielkosci zasiegu bomby fragmentach muru na wybuch bomby. Jezeli po drodze napotkano sciane statyczna, trzeba dodatkowo zadbac, aby przy dluzszym niz 1 zasiegu bomby, elementy za statyczna sciana nie zostaly zniszczone*/
	bool BombExplosion(const sf::Vector2f &bomb_pos, int bomb_dist, Player &player, double delta_t); // wartosc zwracana oznacza to czy player zginal czy nie; true - zyje
public:
	/* Metoda ta zwraca numer tej planszy, ktora powinna zostac wyswietlona w wypadku, gdy skonczy sie gra (moze to byc menu glowne w sytuacji, gdy gracz sam wyjdzie z gry lub okno mowiace o koncu gry, gdy ktorys z graczy wygra lub przegra). 
	Tutaj znajduje sie petla glowna programu. W kazdym jej obiegu przechodzimy przez wektor wzystkich graczy i kolejno: jesli nastapil ruch gracza, jest on widoczny, sprawdzane jest wystapienie kolizji gracza z murum, a w takim wypadku zablokowanie mozliwosci najscia na fragmenty muru, w ktorymi gracz kolidowac nie moze, nastepnie sprawdzone zostaje,
	czy gracz nie probuje polozyc bomby - jezeli tak, a ma taka mozliwosc (np. ma wystarczajaco bomb do polozenia w danej chwili czasu), zostaje ona polozona i dodana do kolejki bobm gracza. Kolejno sprawdzane jest zaistnienie wybuchu bomby i w takim wypadku kontrola, ktore fragmenty muru powinny wybuchnac i czy nie zostal zabity ktorys z graczy. Na samym koncu 
	tej petli cala plansza wraz z graczami zostaje wyswietlona */
	int PlayAGame(sf::RenderWindow &window);
	Game(BoardFactory& board_factory, double width, double  height, Config &config);
	~Game() {}
};


#endif