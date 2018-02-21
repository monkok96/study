#ifndef WALLWITHBOMBPLUSPLUS_H
#define WALLWITHBOMBPLUSPLUS_H
#include "DynamicWall.h"

class WallWithBombPlusPlus : public DynamicWall 
{

public:
	WallWithBombPlusPlus(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
	void ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned  int &count_elems);
};

#endif