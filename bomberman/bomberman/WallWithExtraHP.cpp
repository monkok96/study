#include "WallWithExtraHP.h"
#include "ExtraHP.h"
#include "GetOut.h"

WallWithExtraHP::WallWithExtraHP(const sf::Vector2f& elem_size, const sf::Vector2f& position) :DynamicWall(elem_size,position)
{ }

bool WallWithExtraHP::PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	return true;
}

void WallWithExtraHP::ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned int &count_elems)
{

	if(--count_elems == 0)
		elem = std::make_unique<GetOut>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	else
		elem = std::make_unique<ExtraHP>(elem->GetElem().getSize(), elem->GetElem().getPosition());
}