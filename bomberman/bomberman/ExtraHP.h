#ifndef EXTRAHP_H
#define EXTRAHP_H
#include "BoardElem.h"


class ExtraHP : public BoardElem
{

public:
	ExtraHP(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem) override;
};

#endif