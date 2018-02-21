#include "PlayerKeys.h"


PlayerKeys::PlayerKeys(int num) : player_number(num)
{
	switch (player_number)
	{
	case 1: 
		up = sf::Keyboard::Up;
		down = sf::Keyboard::Down;
		left = sf::Keyboard::Left;
		right = sf::Keyboard::Right;
		bomb = sf::Keyboard::Space;
		break;
	case 2:
		up = sf::Keyboard::W;
		down = sf::Keyboard::S;
		left = sf::Keyboard::A;
		right = sf::Keyboard::D;
		bomb = sf::Keyboard::Z;
		break;
	}
}