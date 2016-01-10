#include "Menu4.h"

Menu4::Menu4(){
	window = new QWidget;
	window->setWindowTitle("104-Flix");

	movie_name = new QLabel("*No Movie Selected");
	movie_name->setAlignment(Qt::AlignCenter);

	next_movie = new QPushButton("Next Movie");
	add_to_queue = new QPushButton("Add to My Queue");
	return_to_menu = new QPushButton("Return to Main Menu");

	group = new QGroupBox("Movie Info");

	key_words = new QVBoxLayout;
	buttons = new QHBoxLayout;
	whole = new QVBoxLayout;

	buttons->addWidget(next_movie);
	buttons->addWidget(add_to_queue);
	buttons->addWidget(return_to_menu);

	group->setLayout(key_words);

	whole->addWidget(movie_name);
	whole->addWidget(group);
	whole->addLayout(buttons);

	window->setLayout(whole);

}

Menu4::~Menu4(){
	qDeleteAll(this->children());
}