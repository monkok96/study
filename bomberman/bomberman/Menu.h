#ifndef MENU_H
#define MENU_H
#include <SFML/Graphics.hpp>
#include "IScreen.h"
#define MAX_SIZE_MENU 3

class Menu : public IScreen
{
	int SelectedOption;
	sf::Text text[MAX_SIZE_MENU];
	double width, height;
	sf::Font font;
	int GetSelectedOption() { return SelectedOption; }
	void MoveUp();
	void MoveDown();

public:
	Menu() {}
	Menu(double width, double height);
	~Menu() {}
	int Run(sf::RenderWindow &window) override;
	void Draw(sf::RenderWindow &window);
};


#endif
