#include "Menu5.h"

Menu5::Menu5(){
	window = new QWidget;
	window->setWindowTitle("104-Flix");

	movie_info = new QLabel(" ");

	rate = new QPushButton("Rate");
	skip = new QPushButton("Skip");

	group = new QGroupBox("Ratings (1-5 stars)");

	star1 = new QRadioButton("1");
	star2 = new QRadioButton("2");
	star3 = new QRadioButton("3");
	star4 = new QRadioButton("4");
	star5 = new QRadioButton("5");

	star5->setChecked(true);

	radio_buttons = new QHBoxLayout;
	push_buttons = new QHBoxLayout;
	whole = new QVBoxLayout;

	radio_buttons->addWidget(star1);
	radio_buttons->addWidget(star2);
	radio_buttons->addWidget(star3);
	radio_buttons->addWidget(star4);
	radio_buttons->addWidget(star5);

	group->setLayout(radio_buttons);

	push_buttons->addWidget(rate);
	push_buttons->addWidget(skip);

	whole->addWidget(movie_info);
	whole->addWidget(group);
	whole->addLayout(push_buttons);

	window->setLayout(whole);
}

Menu5::~Menu5(){
	qDeleteAll(this->children());
}