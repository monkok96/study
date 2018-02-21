#ifndef EMPTYWALL_H
#define EMPTYWALL_H
#include "BoardElem.h"
class EmptyWall :public BoardElem
{
private:
	
public:
	EmptyWall(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PutABomb(std::unique_ptr<BoardElem> &elem);
	bool PlayerAction(Player  &player, sf::RenderWindow& window, std::unique_ptr<BoardElem> &elem) override;
};

#endif