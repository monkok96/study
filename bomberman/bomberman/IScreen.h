#ifndef ISCREEN_H
#define ISCREEN_H
#include "Config.h"
#include <SFML/Graphics.hpp>
class IScreen
{
public:
	virtual int Run(sf::RenderWindow &window) = 0;
};

#endif