#include "StaticWall.h"
#include <iostream>
StaticWall::StaticWall(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	elem.setFillColor(sf::Color::Black);
	elem.setSize(elem_size);
	elem.setPosition(position);
}

bool StaticWall::PlayerAction(Player  &player, sf::RenderWindow& window, std::unique_ptr<BoardElem> &elem)
{
	return true;
}
