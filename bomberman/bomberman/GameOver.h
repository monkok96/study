#ifndef GAMEOVER_H
#define GAMEOVER_H
#include "IScreen.h"

class GameOver : public IScreen
{
	sf::Text text;
	double width, height;
	sf::Font font;
	Config &conf;

public:
	GameOver(int width, int height, Config &config);
	int Run(sf::RenderWindow &window) override;
	void Draw(sf::RenderWindow &window);
};

#endif