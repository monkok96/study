#include "ExtraHP.h"
#include "EmptyWall.h"
ExtraHP::ExtraHP(const sf::Vector2f& elem_size, const sf::Vector2f& position)
{
	if (!elem_texture.loadFromFile("hp.png"))
	{
		throw TextureLoadingException();
	}
	elem.setTexture(&elem_texture);
	elem.setSize(elem_size);
	elem.setPosition(position);
}

bool ExtraHP::PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	elem = std::make_unique<EmptyWall>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	player.IncHP();
	return false;
}

