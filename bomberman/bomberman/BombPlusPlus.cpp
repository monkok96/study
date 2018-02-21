#include "BombPlusPlus.h"
#include "EmptyWall.h"
#include <iostream>
BombPlusPlus::BombPlusPlus(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("plus_plus.png"))
	{
		throw TextureLoadingException();
	}
	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);
}

bool BombPlusPlus::PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	elem = std::make_unique<EmptyWall>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	player.IncNumOfBomb();
	return false;
}
