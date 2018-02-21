#include "DynamicWall.h"
#include "EmptyWall.h"
#include "GetOut.h"
#include <iostream>
DynamicWall::DynamicWall(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("mur.png"))
	{
		throw TextureLoadingException();
	}
	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);
}


bool DynamicWall::PlayerAction(Player  &player,sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	return true;
}


void DynamicWall::ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned int &count_elems)
{
	if(--count_elems == 0)
		elem = std::make_unique<GetOut>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	else
		elem = std::make_unique<EmptyWall>(elem->GetElem().getSize(), elem->GetElem().getPosition());
}