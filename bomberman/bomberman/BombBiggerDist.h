#ifndef BOMBBIGGERDIST_H
#define BOMBBIGGERDIST_H
#include "BoardElem.h"

class BombBiggerDist : public BoardElem
{
public:
	BombBiggerDist(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
};

#endif