#ifndef PLAYERKEYS_H
#define PLAYERKEYS_H
#include <SFML\Graphics.hpp>
class PlayerKeys
{
	friend class Player;
	int player_number;
	sf::Keyboard::Key up, down, left, right, bomb;
	PlayerKeys(int num);
};

#endif