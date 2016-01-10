#include "Menu2.h"

Menu2::Menu2(){
	window = new QWidget;
	window->setWindowTitle("104-Flix");

	sign_up_msg = new QLabel("     Please sign up for CSCI 104 Flix     ");
	sign_up_msg->setAlignment(Qt::AlignCenter);
	login_msg = new QLabel("Login:");	
	user_name_msg = new QLabel("Name:");

	confirm_button = new QPushButton("Confirm");
	cancel_button = new QPushButton("Cancel");

	login_text = new QLineEdit;
	user_name_text = new QLineEdit;

	first = new QHBoxLayout;
	second = new QGridLayout;
	third = new QGridLayout;
	forth = new QHBoxLayout;
	whole = new QVBoxLayout;

	first->addWidget(sign_up_msg);
	second->addWidget(login_msg, 0, 0);
	second->addWidget(login_text, 0, 1);
	third->addWidget(user_name_msg, 0, 0);
	third->addWidget(user_name_text, 0, 1);
	forth->addWidget(confirm_button);
	forth->addWidget(cancel_button);
	whole->addLayout(first);
	whole->addLayout(second);
	whole->addLayout(third);
	whole->addLayout(forth);

	window->setLayout(whole);
}

Menu2::~Menu2(){
	qDeleteAll(this->children());
}
