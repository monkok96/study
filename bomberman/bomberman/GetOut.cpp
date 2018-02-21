#include "GetOut.h"

GetOut::GetOut(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	elem.setSize(elem_size);
	elem.setPosition(position);
	elem.setFillColor(sf::Color::Red);
}

bool GetOut::PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	player.is_winner = true;
	return false;
}
