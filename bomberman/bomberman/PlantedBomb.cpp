#include "PlantedBomb.h"

PlantedBomb::PlantedBomb(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("planted_bomb.png"))
	{
		throw TextureLoadingException();
	}
	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);
}
void PlantedBomb::ReactionToABomb(std::unique_ptr<BoardElem> &elem,unsigned int &count_elem)
{
	elem = std::make_unique<EmptyWall>(elem->GetElem().getSize(),elem->GetElem().getPosition());
}
bool PlantedBomb::PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{ 
	return false; 
}
