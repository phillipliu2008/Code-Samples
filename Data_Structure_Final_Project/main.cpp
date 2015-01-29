#include "Netflix.h"
#include <QApplication>


int main(int argc, char* argv[]){
	
	QApplication app(argc, argv);
	
	Menu1* first_menu = new Menu1;
  	Menu2* second_menu = new Menu2;
  	Menu3* third_menu = new Menu3;
  	Menu4* forth_menu = new Menu4;
  	Menu5* fifth_menu = new Menu5;

	std::ifstream inputFile(argv[argc-1]);
	std::string line1, line2;
	getline(inputFile, line1);
	getline(inputFile, line2);

	//std::stringstream ss; 				// strip the extra \r character
	//ss << line1 << line2;                   
	//ss >> line1 >> line2;

	Netflix netflix(line1, line2, first_menu, second_menu, third_menu, forth_menu, fifth_menu);

	QObject::connect(first_menu->new_user, SIGNAL(clicked()), &netflix, SLOT(first_to_second_menu()));
	QObject::connect(first_menu->login, SIGNAL(clicked()), &netflix, SLOT(start_login()));
	QObject::connect(first_menu->quit, SIGNAL(clicked()), &app, SLOT(quit()));
	QObject::connect(first_menu->input_text, SIGNAL(returnPressed()), &netflix, SLOT(start_login()));

	QObject::connect(second_menu->confirm_button, SIGNAL(clicked()), &netflix, SLOT(start_new_user()));
	QObject::connect(second_menu->cancel_button, SIGNAL(clicked()), &netflix, SLOT(second_to_first_menu()));
	QObject::connect(second_menu->user_name_text, SIGNAL(returnPressed()), &netflix, SLOT(start_new_user()));

	QObject::connect(third_menu->search_title, SIGNAL(clicked()), &netflix, SLOT(search_movie_name()));
	QObject::connect(third_menu->search_key, SIGNAL(clicked()), &netflix, SLOT(search_movie_key()));
	QObject::connect(third_menu->search_actor, SIGNAL(clicked()), &netflix, SLOT(search_movie_actor()));
	QObject::connect(third_menu->return_movie, SIGNAL(clicked()), &netflix, SLOT(return_movie()));
	QObject::connect(third_menu->logout, SIGNAL(clicked()), &netflix, SLOT(third_to_first_menu()));
	QObject::connect(third_menu->rent_movie, SIGNAL(clicked()), &netflix, SLOT(rent_a_movie()));
	QObject::connect(third_menu->delete_queue, SIGNAL(clicked()), &netflix, SLOT(delete_front_queue()));
	QObject::connect(third_menu->move_back, SIGNAL(clicked()), &netflix, SLOT(move_back_queue()));
	QObject::connect(third_menu->search_result, SIGNAL(returnPressed()), &netflix, SLOT(search_movie_name()));
	QObject::connect(third_menu->add_to_queue, SIGNAL(clicked()), &netflix, SLOT(add_recommand_movie()));
	QObject::connect(third_menu->play, SIGNAL(clicked()), &netflix, SLOT(create_graph()));
	QObject::connect(third_menu->actor1, SIGNAL(returnPressed()), &netflix, SLOT(create_graph()));
	QObject::connect(third_menu->actor2, SIGNAL(returnPressed()), &netflix, SLOT(create_graph()));

	QObject::connect(forth_menu->add_to_queue, SIGNAL(clicked()), &netflix, SLOT(add_to_movie_queue()));
	QObject::connect(forth_menu->next_movie, SIGNAL(clicked()), &netflix, SLOT(update_forth_menu()));
	QObject::connect(forth_menu->return_to_menu, SIGNAL(clicked()), &netflix, SLOT(forth_to_third_menu()));

	QObject::connect(fifth_menu->rate, SIGNAL(clicked()), &netflix, SLOT(rate_movie()));
	QObject::connect(fifth_menu->skip, SIGNAL(clicked()), &netflix, SLOT(skip_movie()));


	std::ofstream fileOutput;		
	fileOutput.open(line1.c_str());
	fileOutput << "";						// clear the file and ready to overwrite
	fileOutput.close();

	inputFile.close();
	first_menu->window->show();
	app.exec();

	delete first_menu;
	delete second_menu;
	delete third_menu;
	delete forth_menu;

	return 0;
}
