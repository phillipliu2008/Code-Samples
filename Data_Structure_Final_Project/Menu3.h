#ifndef MENU3_H
#define MENU3_H

#include <QApplication>
#include <QPushButton>
#include <QLayout>
#include <QLabel>
#include <QLineEdit>
#include <QGroupBox>

class Menu3 : public QWidget
{
	Q_OBJECT

public:
	Menu3();

	~Menu3();

	QWidget* window;

	QLabel* title;
	QLabel* movie_name;
	QLabel* queue_name;
	QLabel* search_word;
	QLabel* recommandation;
	QLabel* enter_actor;

	QPushButton* return_movie;
	QPushButton* rent_movie;
	QPushButton* delete_queue;
	QPushButton* move_back;
	QPushButton* search_title;
	QPushButton* search_key;
	QPushButton* logout;
	QPushButton* add_to_queue;
	QPushButton* search_actor;
	QPushButton* play;

	QLineEdit* search_result;
	QLineEdit* actor1;
	QLineEdit* actor2;

	QGroupBox* group1;
	QGroupBox* group2;
	QGroupBox* group3;
	QGroupBox* group4;
	QGroupBox* group5;


	QVBoxLayout* first;
	QVBoxLayout* second;
	QHBoxLayout* second2;
	QVBoxLayout* third;
	QHBoxLayout* third1;
	QHBoxLayout* third2;
	QVBoxLayout* forth;
	QHBoxLayout* fifth;
	QVBoxLayout* fifth1;
	QVBoxLayout* whole;


};


#endif 