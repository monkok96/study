#include "Options.h"
#include "ExceptionClass.h"
#include <iostream>
Options::Options(int width, int height, Config &config) : width(width), height(height), conf(config)
{
	if (!font.loadFromFile("times.ttf"))
	{
		throw FontLoadingException();
	}
	text[0].setFont(font);
	text[0].setString("1 player");
	text[0].setFillColor(sf::Color::Magenta);
	text[0].setCharacterSize(28);
	text[0].setPosition(sf::Vector2f(width / 2.5, height / (MAX_SIZE_OPTIONS + 1) * 1));

	text[1].setFont(font);
	text[1].setFillColor(sf::Color::White);
	text[1].setString("2 players");
	text[1].setCharacterSize(28);
	text[1].setPosition(sf::Vector2f(width / 2.5, height / (MAX_SIZE_OPTIONS + 1) * 2));

	SelectedOption = 0;
}

void Options::MoveUp()
{
	if (SelectedOption > 0)
	{
		text[SelectedOption].setFillColor(sf::Color::White);
		SelectedOption--;
		text[SelectedOption].setFillColor(sf::Color::Magenta);
	}
}
void Options::MoveDown()
{
	if (SelectedOption + 1< MAX_SIZE_OPTIONS)
	{
		text[SelectedOption].setFillColor(sf::Color::White);
		SelectedOption++;
		text[SelectedOption].setFillColor(sf::Color::Magenta);
	}
}

void Options::Draw(sf::RenderWindow &window)
{
	for (int i = 0; i < MAX_SIZE_OPTIONS; ++i)
	{
		window.draw(text[i]);
	}
}

int Options::Run(sf::RenderWindow &window)
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
						conf.players_num = 1;
						return 0;
					case 1:
						conf.players_num = 2;
						return 0;
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
