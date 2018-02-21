#include "EmptyWall.h"
#include <memory>
#include "PlantedBomb.h"
EmptyWall::EmptyWall(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("empty.png"))
	{
		throw TextureLoadingException();
	}

	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);
}

bool EmptyWall::PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{ 
	return false; 
}

bool EmptyWall::PutABomb(std::unique_ptr<BoardElem> &elem)
{
	elem = std::make_unique<PlantedBomb>(elem->GetElem().getSize() ,elem->GetElem().getPosition());
	return true;
}