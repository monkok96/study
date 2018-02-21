#include "Menu.h"
#include "Game.h"
#include <iostream>
#include <string>
using namespace std;
Menu::Menu(double width, double height) : width(width), height(height)
{
	if (!font.loadFromFile("times.ttf"))
	{
		throw FontLoadingException();
	}
	text[0].setFont(font);
	text[0].setString("Play");
	text[0].setFillColor(sf::Color::Magenta);
	text[0].setCharacterSize(28);
	text[0].setPosition(sf::Vector2f(width / 2.5, height / (MAX_SIZE_MENU + 1) * 1));

	text[1].setFont(font);
	text[1].setFillColor(sf::Color::White);
	text[1].setString("Options");
	text[1].setCharacterSize(28);
	text[1].setPosition(sf::Vector2f(width / 2.5, height / (MAX_SIZE_MENU + 1) * 2));

	text[2].setFont(font);
	text[2].setFillColor(sf::Color::White);
	text[2].setString("Quit");
	text[2].setCharacterSize(28);
	text[2].setPosition(sf::Vector2f(width / 2.5, height / (MAX_SIZE_MENU + 1) * 3));
	
	SelectedOption = 0;
}


void Menu::MoveUp()
{
	if (SelectedOption > 0)
	{
		text[SelectedOption].setFillColor(sf::Color::White);
		SelectedOption--;
		text[SelectedOption].setFillColor(sf::Color::Magenta);
	}
}
void Menu::MoveDown()
{
	if (SelectedOption + 1< MAX_SIZE_MENU)
	{
		text[SelectedOption].setFillColor(sf::Color::White);
		SelectedOption++;
		text[SelectedOption].setFillColor(sf::Color::Magenta);
	}
}

void Menu::Draw(sf::RenderWindow &window)
{
	for (int i = 0; i < MAX_SIZE_MENU; ++i)
	{
		window.draw(text[i]);
	}
}

int Menu::Run(sf::RenderWindow &window)
{
	sf::Event event;
	while (window.isOpen())
	{
		while (window.pollEvent(event))
		{
			switch (event.type)
			{
			case sf::Event::KeyPressed:
				switch (event.key.code)
				{
				case sf::Keyboard::Up:
					MoveUp();
					break;
				case sf::Keyboard::Down:
					MoveDown();
					break;
				case sf::Keyboard::Return:
					switch (GetSelectedOption())
					{
					case 0:
						return 1;
					case 1:
						return 2;
					case 2:
						return -1;
					}
					break;
				}
				break;

			case sf::Event::Closed:
				window.close();
				break;
			}
		}
		window.clear();
		Draw(window);
		window.display();
	}
	return -1;
}