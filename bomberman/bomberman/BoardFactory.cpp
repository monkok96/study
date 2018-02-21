#include "BoardFactory.h"
#include "DynamicWall.h"
#include "StaticWall.h"
#include "EmptyWall.h"
#include "WallWithBombPlusPlus.h"
#include "WallWithBombBiggerDist.h"
#include "WallWithExtraHP.h"
#include <string>
#include <fstream>
#include <iostream>
#include "ExceptionClass.h"
using namespace std;

BoardFactory::BoardFactory(const std::string &infile)
{
	count_elems = 0;
	ifstream input(infile);
	string line;
	int i = 0;
	if (input)
	{
		input >> num_of_lines;
		input >> num_of_columns;
		input >> wall_size.x;
		input >> wall_size.y;
		board_elems.resize(num_of_columns);
		for (auto& e : board_elems)
			e.resize(num_of_lines);
		while (input >> line)
		{
			for (int j = 0; j < line.length(); ++j)
			{
				switch (line[j])
				{
				case '0':
					board_elems[j][i] = std::make_unique<EmptyWall>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					break;
				case '1':
					board_elems[j][i] = std::make_unique<StaticWall>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					break;
				case '2':
					board_elems[j][i] = std::make_unique<DynamicWall>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					++count_elems;
					break;
				case '3':
					board_elems[j][i] = std::make_unique<WallWithBombPlusPlus>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					++count_elems;
					break;
				case '4':
					board_elems[j][i] = std::make_unique<WallWithBombBiggerDist>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					++count_elems;
					break;
				case '5':
					board_elems[j][i] = std::make_unique<WallWithExtraHP>(wall_size, sf::Vector2f(wall_size.x * j, wall_size.y * i));
					++count_elems;
					break;
				}
			}
			++i;
		}
	}
	else throw(BoardLoadingException());
	input.close();
}