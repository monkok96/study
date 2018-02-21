#ifndef CONFIG_H
#define CONFIG_H
struct Config
{
	int players_num = 1;
	enum GameOverState {one_player_win, one_player_loose, first_player_win, sec_player_win, plyers_draw} game_over_state;
};

#endif // !CONFIG_H
