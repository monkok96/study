#ifndef GETOUT_H
#define GETOUT_H
#include "BoardElem.h"

class GetOut : public BoardElem
{

public:
	GetOut(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
};


#endif