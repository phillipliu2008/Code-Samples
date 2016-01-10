#ifndef NETFLIX_H
#define NETFLIX_H

#include "Set.h"
#include "Movie.h"
#include "User.h"
#include "Queue.h"
#include "Menu1.h"
#include "Menu2.h"
#include "Menu3.h"
#include "Menu4.h"
#include "Menu5.h"
#include <QString>
#include <QLayoutItem>
#include <QMessageBox>
#include <algorithm>
#include <vector>
#include <set>
#include <map>
#include <queue>

class Netflix : public QWidget
{
	Q_OBJECT

public:

	Netflix (std::string u_file, std::string m_file, Menu1* fm, Menu2* sm, Menu3* tm, Menu4* fom, Menu5* fif);

	~Netflix();

	Movie* return_movie_by_name(std::string title);

	std::string capitalize(std::string word);

	bool find_movie_by_name();

	bool find_movie_by_key();

	bool find_movie_by_actor();

	void Read_file (std::string user_file, std::string movie_file);

	void makeKeymap();

	void makeActormap();

	void createUser(User* current_user, std::string fileName);

	void printMovie(Movie movie);

	void delete_vector();

	void menu3(std::string cmds);

	void menu2(std::string cmds);

	void menu1(std::string cmds);


private:

	Map<std::string, User*> userMap;
	Map<std::string, Movie*> movieMap;
	Map<std::string, Set<std::string> > keywordMap;
	Map<std::string, Set<std::string> > actorMap;
	Set<std::string> allKeywords;
	Set<std::string> allActors;
	std::string user_file, movie_file, title, keyword, actor_name;
	User* current_user;
	Movie* buffer_movie;
	Movie* recommand_movie;
	std::vector<QLabel*> label_vec;
	std::vector<Movie*> movie_vec;
	std::vector<Movie*>::iterator current_movie;

	Menu1* first_menu;
	Menu2* second_menu;
	Menu3* third_menu;
	Menu4* forth_menu;
	Menu5* fifth_menu;

public slots:

	void start_login();

	void start_new_user();

	void first_to_second_menu();

	void second_to_first_menu();

	void update_third_menu();

	void search_movie_name();

	void search_movie_key();

	void search_movie_actor();

	void update_forth_menu();

	void add_to_movie_queue();

	void return_movie();

	void forth_to_third_menu();

	void third_to_first_menu();

	void third_to_forth_menu();

	void rent_a_movie();

	void delete_front_queue();

	void move_back_queue();

	void rate_movie();

	void skip_movie();

	void add_recommand_movie();

	void update_recommandation();

	void create_graph();

	void play_bacon_game(std::map<std::string, std::set<std::string> >& graph);

};

#endif
