#ifndef STATICBOARD_H_
#define STATICBOARD_H
#include "BoardElem.h"

class StaticWall : public BoardElem
{
	
public:
	StaticWall(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
};

#endif