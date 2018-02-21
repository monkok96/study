#ifndef BOARDELEM_H
#define BOARDELEM_H

#include <memory>
#include <iostream>
#include "ExceptionClass.h"
#include "Player.h"
#include <chrono>
class BoardElem 
{
protected:
	sf::RectangleShape elem;
	sf::Texture elem_texture;

public:
	/* zwraca true, jezeli gracz nie moze wejsc na ten rodzaj sciany */
	virtual bool PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) = 0;
	/* Zwraca true, jesli na danym rodzaju sciany da sie postawic bombe; wtedy tez zmienia typ sciany na PlantedBomb. Dzieje sie tak tylko w wypadku sciany typu EmptyWall*/
	virtual bool PutABomb(std::unique_ptr<BoardElem> &elem) { return false; }
	/* Jezeli dany rodzaj elementu sciany po zetknieciu z bomba powinien zamienic sie na inny rodzaj, zmienna count_elems jest dekrementowana za kazdym razem, gdy wybucha sciana dynamiczna; w przypadku, gdy bedzie ona rowna 0 oznaczac to bedzie, ze rozbity zostal ostatni fragment muru, w zwiazku z czym nalezy zamienic go na "drzwi*, stanowiace wyjscie z gry*/
	virtual void ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned int &count_elems) {}
	virtual void DrawOnBoard(sf::RenderWindow &window)
	{
		window.draw(elem);
	}
	/* zwraca aktualny element sciany - a konkretnie, jego parametr opisany przez RectangleShape*/
	sf::RectangleShape GetElem() const { return elem; }
	virtual ~BoardElem() = default;
};

#endif