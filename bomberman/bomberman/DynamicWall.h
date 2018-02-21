#ifndef DYNAMICBOARD_H
#define DYNAMICBOARD_H
#include "BoardElem.h"

class DynamicWall : public BoardElem
{
public:
	DynamicWall(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
	void ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned  int &count_elems);
};

#endif