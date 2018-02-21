#include "BombBiggerDist.h"
#include "EmptyWall.h"

BombBiggerDist::BombBiggerDist(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("bigger_dist.png"))
	{
		throw TextureLoadingException();
	}
	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);

}

bool BombBiggerDist::PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	elem = std::make_unique<EmptyWall>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	player.IncNumOfBombBiggerDist();
	return false;
}
