#include <iostream>
#include "Game.h"
#include "BoardFactory.h"
#include "ExceptionClass.h"
#include "GameScreen.h"
#include "Options.h"
#include "Config.h"
#include "GameOver.h"

using namespace std;
const int SCREEN_MENU = 0;
const int SCREEN_GAME = 1;
const int SCREEN_OPTIONS = 2;
const int SCREEN_GAMEOVER = 3;
/* INFO 
MOZLIWOSC GRANIA W JEDNA LUB DWIE OSOBY. BONUSY: DODATKOWA BOMBA, DODATKOWE ZYCIE, WIEKSZY ZASIEG BOMBY. 
OSTATNI KAWALEK MURY KTORY ZOSTANIE ROZBITY JEST DRZWIAMI DO WYJSCIA Z GRY - TEN GRACZ, KTORY JAKO PIERWSZY DOTKNIE TEGO ELEMENTU, WYGRYWA
*/
int main()
{
	try
	{
		vector<IScreen*> screens;
		int screen_num = SCREEN_MENU;

		sf::RenderWindow window(sf::VideoMode(680, 680), "BOMBERMAN");
		window.setFramerateLimit(60);
		window.setVerticalSyncEnabled(true);
		window.clear(sf::Color::White);
		Config config;
		Menu screen_menu(680, 680);
		screens.push_back(&screen_menu);
		GameScreen screen_game(config);
		screens.push_back(&screen_game);
		Options screen_options(680, 680, config);
		screens.push_back(&screen_options);
		GameOver screen_gameover(680, 680, config);
		screens.push_back(&screen_gameover);
		while (screen_num >= 0)
		{
			screen_num = screens[screen_num]->Run(window);
		}

	}
	catch (BoardLoadingException &ex)
	{
		cout << "Cannot open a board input file.\n";
	}
	catch (TextureLoadingException &ex)
	{
		cout << "Cannot open a texture file.\n";
	}
	catch (FontLoadingException &ex)
	{
		cout << "Cannot open a font file.\n";
	}
	catch (exception &ex)
	{
		cout << ex.what();
	}
	catch (...)
	{
		cout << "Unknown exception cought.\n";
	}

}
