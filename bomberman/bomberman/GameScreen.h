#ifndef GAMESCREEN_H
#define GAMESCREEN_H
#include "IScreen.h"
#include "Game.h"
class GameScreen: public IScreen
{
	Config &conf;
public:
	GameScreen(Config &config) : conf(config) {}
	int Run(sf::RenderWindow &window) override
	{
		BoardFactory factory("input.txt");
		Game game(factory, 680, 680, conf);
		return game.PlayAGame(window);
	}
};

#endif // !GAMESCREEN_H
