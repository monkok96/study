#include "WallWithBombBiggerDist.h"
#include "BombBiggerDist.h"
#include "GetOut.h"

WallWithBombBiggerDist::WallWithBombBiggerDist(const sf::Vector2f& elem_size, const sf::Vector2f& position) : DynamicWall(elem_size,position)
{ }

bool WallWithBombBiggerDist::PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)
{
	return true;
}

void WallWithBombBiggerDist::ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned  int &count_elems)
{
	if (--count_elems == 0)
		elem = std::make_unique<GetOut>(elem->GetElem().getSize(), elem->GetElem().getPosition());
	else
		elem = std::make_unique<BombBiggerDist>(elem->GetElem().getSize(), elem->GetElem().getPosition());
}