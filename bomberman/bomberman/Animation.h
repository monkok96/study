#ifndef ANIMATION_H
#define ANIMATION_H
#include <SFML/Graphics.hpp>

class Animation
{
	static const int num_of_frames = 3, num_of_death_frames = 9;
	double hold_time = 0.1;
	sf::IntRect frames[num_of_frames];
	sf::IntRect death_frames[num_of_death_frames];
	int cur_frame = 0;
	double time;

public:
	/* konstruktor dla animacji ruchu */
	Animation(int x, int y, sf::Vector2f &char_size, sf::Vector2f &elem_size);
	/* konstruktor dla animacji smierci */
	Animation(int x, int y, sf::Vector2f &char_size, sf::Vector2f &elem_size, int death);
	Animation() = default;
	/* Ustawia sprite'owi uprzednio wyciêty fragment obrazka, stanowiacy jego aktualna animacje */
	void ApplyToSprite(sf::Sprite & ch);
	/* Jak w/w metoda, ale w przypadku animacji smierci */
	void ApplyDeathToSprite(sf::Sprite & ch);
	/* Kontroluje, jaka aktualnie powinna byc wyswietlona animacja - dzieki czasowi delta_time, obliczonemu w funkcji glownej gry, animacje sa plynne */
	void Update(double delta_time, bool is_death_animation);
	/* zwraca numer aktualnie ustawionej animacji */
	int GetCurFram() const { return cur_frame; }
	/* zwraca liczbe animacji potrzebnych, by przedstawic cala smierc */
	int GetNumOfDeathFrames() const { return num_of_death_frames; }
private:
	/* Kontroluje, by w przypadku gdy numer animacji bedzie wiekszy niz ich liczba, ponownie wyswietlalo sie wszystko od pierwszej animacji */
	void ControlFrames(int num_of_anim_frames)
	{
		if (++cur_frame >= num_of_anim_frames)
			cur_frame = 0;
	}
};
#endif