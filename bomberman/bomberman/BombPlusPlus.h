#ifndef BOMBPLUSPLUS_H
#define BOMBPLUSPLUS_H
#include "BoardElem.h"

class BombPlusPlus : public BoardElem
{
public:
	BombPlusPlus(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
};

#endif