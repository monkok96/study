#ifndef BOARDFACTORY_H
#define BOARDFACTORY_H
#include <vector>
#include "BoardElem.h"
#include <memory>
class BoardFactory
{
	std::vector<std::vector<std::unique_ptr<BoardElem>>> board_elems;
	int num_of_lines, num_of_columns;
	sf::Vector2f wall_size;
	unsigned int count_elems; //liczba elementow (scian dyanmicznych) na planszy
public:
	/* Konstruuje plansze na podstawie pliku wejsciowego; odpowiednio intepretuje zawarte w nim dane, tworzac wektor wektorow o podanym rozmiarze, w ktorym pojedyncze elementy sa unikalnymi wskaznikami wskazujacymi na odpowiedni typ, bedacy klasa pochodna, dziedziczaca po klasie BoardElem */
	BoardFactory(const std::string &infile); 
	/* Zwraca referencje do wektora wektorow unikalnych wskaznikow na element planszy. Jest to referencja a nie wartosc, poniewaz unikalne wskazniki mozna jedynie przeniesc */
	std::vector<std::vector<std::unique_ptr<BoardElem>>>& GetBoardElems() { return board_elems; }
	sf::Vector2f GetWallSize() const { return wall_size; }
	unsigned int GetCountElems() const { return count_elems; }
};


#endif 
