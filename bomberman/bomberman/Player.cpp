#include "Player.h"
#include "ExceptionClass.h"
#include <chrono>
#include <iostream>

Player::Player(const sf::Vector2f &board_el_size, const sf::Vector2f &char_pos, int player_num, sf::Texture &playerTexture) : board_elem_size(board_el_size), num_of_bomb(1), hp(1), num_of_bomb_bigger_dist(0), num_of_planted_bomb(0), player_key(player_num)
{
	if (!playerTexture.loadFromFile("bomb2.png"))
	{
		throw TextureLoadingException();
	}
	character.setTexture(playerTexture);
	character.setPosition(char_pos);
	this->char_pos = char_pos;
	this->player_num = player_num;
	character_size.x = (double)playerTexture.getSize().x /25;
	character_size.y = (double)playerTexture.getSize().y / 8;
	elem_size.x = ((double)playerTexture.getSize().x - (double)playerTexture.getSize().x / 25 )/ 14;
	elem_size.y = ((double)playerTexture.getSize().y- (double)playerTexture.getSize().y / 8) / 5;
	character.setTextureRect(sf::IntRect(0,0,elem_size.x,elem_size.y));
	if (player_num == 1)
	{
		animations[int(AnimationIndex::WalkingDown)] = Animation(0, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingUp)] = Animation(2, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingLeft)] = Animation(1, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingRight)] = Animation(3, 0, character_size, elem_size);
		animations[int(AnimationIndex::Death)] = Animation(4, 0, character_size, elem_size, 1);
	
	}
	else if (player_num == 2)
	{
		animations[int(AnimationIndex::WalkingDown)] = Animation(7, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingUp)] = Animation(9, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingLeft)] = Animation(8, 0, character_size, elem_size);
		animations[int(AnimationIndex::WalkingRight)] = Animation(10, 0, character_size, elem_size);
		animations[int(AnimationIndex::Death)] = Animation(11, 0, character_size, elem_size, 1);
	}
	cur_animation = AnimationIndex::WalkingDown;
}

void Player::Action(sf::RenderWindow &window, sf::Vector2f &player_pos)
{
	sf::Vector2f direction = { 0,0 };
	player_pos = character.getPosition();
	if (sf::Keyboard::isKeyPressed(player_key.up))
	{
		cur_animation = AnimationIndex::WalkingUp;
		MoveUp();
		animations[int(cur_animation)].Update(delta_t,0);
	}
	if (sf::Keyboard::isKeyPressed(player_key.down))
	{
		cur_animation = AnimationIndex::WalkingDown;
		MoveDown(window);
		animations[int(cur_animation)].Update(delta_t,0);
	}
	if (sf::Keyboard::isKeyPressed(player_key.left))
	{
		cur_animation = AnimationIndex::WalkingLeft;
		MoveLeft();
		animations[int(cur_animation)].Update(delta_t,0);
	}
	if (sf::Keyboard::isKeyPressed(player_key.right))
	{
		cur_animation = AnimationIndex::WalkingRight;
		MoveRight(window);
		animations[int(cur_animation)].Update(delta_t,0);
	}
	animations[int(cur_animation)].ApplyToSprite(character);
}

void Player::DeathAnimation()
{
	cur_animation = AnimationIndex::Death;
	animations[int(cur_animation)].Update(delta_t, 1);
	animations[int(cur_animation)].ApplyDeathToSprite(character);
	if (animations[int(cur_animation)].GetCurFram() == animations[int(cur_animation)].GetNumOfDeathFrames()-1) is_dead = true;
}

bool Player::BombAction() 
{ 
	if (sf::Keyboard::isKeyPressed(player_key.bomb))
		return true; 
	return false; 
}

void Player::MoveUp()
{
	if (character.getPosition().y>0)
		character.move(0, -1);
}
void  Player::MoveDown(sf::RenderWindow &window)
{
	if (character.getPosition().y + character_size.y <window.getSize().y)
		character.move(0, 1);
}
void  Player::MoveLeft()
{
	if (character.getPosition().x>0)
		character.move(-1, 0);
}
void  Player::MoveRight(sf::RenderWindow &window)
{
	if (character.getPosition().x + character_size.x <window.getSize().x)
		character.move(1, 0);
}

bool Player::IsKilledByABomb(int bmb_pos_x, int bmb_pos_y)
{
	//indeksy elementu w tablicy planszy - dla gracza 
	int i = (int)(GetMiddlePos().x / board_elem_size.x); //zeby zabijalo jesli player bedzie chociazby troszke na kwadraciku, do ktorego siega bomba, trzeba zrobic GetPos()
	int j = (int)(GetMiddlePos().y / board_elem_size.y);
	if (i == bmb_pos_x && j == bmb_pos_y) return true;
	return false;
}

bool Player::IsCollision(const sf::RectangleShape &elem)
{
	if (character.getGlobalBounds().intersects(elem.getGlobalBounds())) return true;
	return false;
}