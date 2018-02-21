#ifndef WALLWITHBOMBBIGGERDIST_H
#define WALLWITHBOMBBIGGERDIST_H
#include "DynamicWall.h"

class WallWithBombBiggerDist : public DynamicWall
{
	
public:
	WallWithBombBiggerDist(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
	void ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned int &count_elems);
};

#endif