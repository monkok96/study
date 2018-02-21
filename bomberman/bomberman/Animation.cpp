#include "Animation.h"
#include "ExceptionClass.h"
Animation::Animation(int x, int y, sf::Vector2f &char_size, sf::Vector2f &elem_size)
{
	for (int i = 0; i < num_of_frames; ++i)
	{
		frames[i] = { x*(int)elem_size.x, (y+i)*(int)elem_size.y, (int)char_size.x, (int)char_size.y };
	}
	time = 0;
}

Animation::Animation(int x, int y, sf::Vector2f &char_size, sf::Vector2f &elem_size, int death)
{
	int k = 0;
	for (int i = y; i <y+ 3; ++i)
	{
		for (int j = x; j < x+3; ++j)
		{
			death_frames[k++] = { (int)elem_size.x*j, i*(int)elem_size.y, (int)char_size.x, (int)char_size.y };
		}
	}
	time = 0;
}

void Animation::ApplyToSprite(sf::Sprite & ch)
{
	ch.setTextureRect(frames[cur_frame]);
}

void Animation::ApplyDeathToSprite(sf::Sprite & ch)
{
	ch.setTextureRect(death_frames[cur_frame]);
}

void Animation::Update(double delta_time, bool is_death_animation)
{
	time += delta_time;
	if (time >= hold_time)
	{
		time -= hold_time;
		if(is_death_animation) ControlFrames(num_of_death_frames);
		else ControlFrames(num_of_frames);
	}
}