#include "Menu1.h"

Menu1::Menu1(){
	window = new QWidget;
	window->setWindowTitle("104-Flix");

	welcome_msg = new QLabel("Welcome to CSCI 104-Flix");
	welcome_msg->setAlignment(Qt::AlignCenter);
	login_msg = new QLabel("Login:");

	login = new QPushButton("Login");
	new_user = new QPushButton("New User");
	quit = new QPushButton("Quit");

	input_text = new QLineEdit;

	first = new QHBoxLayout;
	second = new QGridLayout;
	third = new QHBoxLayout;
	whole = new QVBoxLayout;
	
	first->addWidget(welcome_msg);
	second->addWidget(login_msg, 0, 0);
	second->addWidget(input_text, 0, 1);
	third->addWidget(login);
	third->addWidget(new_user);
	third->addWidget(quit);
	whole->addLayout(first);
	whole->addLayout(second);
	whole->addLayout(third);

	window->setLayout(whole);
}

Menu1::~Menu1(){
	qDeleteAll(this->children());
}
