#include "Menu3.h"

Menu3::Menu3(){
	window = new QWidget;
	window->setWindowTitle("104-Flix");

	title = new QLabel("Welcome to CSCI 104-Flix");
	title->setAlignment(Qt::AlignCenter);
	movie_name = new QLabel("*No Movie Checked Out");	// variable
	queue_name = new QLabel("*Movie Queue is Empty");	// variable
	search_word = new QLabel("Search:");
	recommandation = new QLabel("*No More Movies to Recommend");
	enter_actor = new QLabel("Enter Two Actors: ");

	return_movie = new QPushButton("Return Movie");
	rent_movie = new QPushButton("Rent Movie");
	delete_queue = new QPushButton("Delete from Queue");
	move_back = new QPushButton("Move to Back of Queue");
	search_title = new QPushButton("Search by Title");
	search_key = new QPushButton("Search by Keyword");
	logout = new QPushButton("Logout");
	add_to_queue = new QPushButton("Add to Queue");
	search_actor = new QPushButton("Search by Actor");
	play = new QPushButton("Play Game");

	search_result = new QLineEdit;
	actor1 = new QLineEdit;
	actor2 = new QLineEdit;

	group1 = new QGroupBox("Your Current Movie");
	group2 = new QGroupBox("Your Movie Queue");
	group3 = new QGroupBox("Search for a Movie");
	group4 = new QGroupBox("Movie Recommandation");
	group5 = new QGroupBox("The Bacon Game");


	first = new QVBoxLayout;
	second = new QVBoxLayout;
	second2 = new QHBoxLayout;
	third = new QVBoxLayout;
	third1 = new QHBoxLayout;
	third2 = new QHBoxLayout;
	forth = new QVBoxLayout;
	fifth = new QHBoxLayout;
	fifth1 = new QVBoxLayout;
	whole = new QVBoxLayout;

	first->addWidget(movie_name);
	first->addWidget(return_movie);
	group1->setLayout(first);

	second2->addWidget(rent_movie);
	second2->addWidget(delete_queue);
	second2->addWidget(move_back);
	second->addWidget(queue_name);
	second->addLayout(second2);
	group2->setLayout(second);

	third1->addWidget(search_word);
	third1->addWidget(search_result);
	third2->addWidget(search_title);
	third2->addWidget(search_key);
	third2->addWidget(search_actor);
	third->addLayout(third1);
	third->addLayout(third2);
	group3->setLayout(third);

	forth->addWidget(recommandation);
	forth->addWidget(add_to_queue);
	group4->setLayout(forth);

	fifth->addWidget(enter_actor);
	fifth->addWidget(actor1);
	fifth->addWidget(actor2);
	fifth1->addLayout(fifth);
	fifth1->addWidget(play);
	group5->setLayout(fifth1);

	whole->addWidget(title);
	whole->addWidget(group1);
	whole->addWidget(group2);
	whole->addWidget(group3);
	whole->addWidget(group4);
	whole->addWidget(group5);
	whole->addWidget(logout);

	window->setLayout(whole);

}

Menu3::~Menu3(){
	qDeleteAll(this->children());
}