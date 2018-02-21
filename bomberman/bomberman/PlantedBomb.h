#ifndef PLANTEDBOMB_H
#define PLANTEDBOMB_H
#include "BoardElem.h"
#include "EmptyWall.h"
class PlantedBomb : public BoardElem
{
	sf::Sprite bomb;
public:
	PlantedBomb(const sf::Vector2f& elem_size, const sf::Vector2f& position);
	bool PlayerAction(Player  &player, sf::RenderWindow &window, std::unique_ptr<BoardElem> &elem)  override;
	void ReactionToABomb(std::unique_ptr<BoardElem> &elem, unsigned int &count_elem);
};

#endif // !PLANTEDBOMB_H
