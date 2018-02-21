#include "GameOver.h"
#include "ExceptionClass.h"
#include <string>
GameOver::GameOver(int width, int height, Config &config) : width(width), height(height), conf(config)
{
	if (!font.loadFromFile("times.ttf"))
	{
		throw FontLoadingException();
	}
	text.setFont(font);
	text.setFillColor(sf::Color::Magenta);
	text.setCharacterSize(28);
	text.setPosition(sf::Vector2f(width / 3, height / 3));
}
int GameOver::Run(sf::RenderWindow &window)
{
	std::string message;
	switch (conf.game_over_state)
	{
	case Config::first_player_win:
		message = "First player won!\n\n";
		break;
	case Config::sec_player_win:
		message = "Second player won!\n\n";
		break;
	case Config::one_player_loose:
		message = "You lost!\n\n";
		break;
	case Config::one_player_win:
		message = "You won!\n\n";
		break;
	case Config::plyers_draw:
		message = "Draw beetwen players!\n\n";
		break;
	}
	message += "Game over.\n\nPress enter to go to menu.";
	text.setString(message);
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
				case sf::Keyboard::Return:
					return 0;
				}

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
void GameOver::Draw(sf::RenderWindow &window)
{
	window.draw(text);
}