#ifndef OPTIONS_H
#define OPTIONS_H
#include "IScreen.h"
#define MAX_SIZE_OPTIONS 2
class Options: public IScreen
{
	int SelectedOption;
	sf::Text text[MAX_SIZE_OPTIONS];
	double width, height;
	sf::Font font;
	int GetSelectedOption() { return SelectedOption; }
	void MoveUp();
	void MoveDown();
	Config &conf;
public:
	Options(int width, int height, Config &config);
	int Run(sf::RenderWindow &window) override;
	void Draw(sf::RenderWindow &window);
};
#endif // !OPTIONSSCREEN_H
