#ifndef MENU4_H
#define MENU4_H

#include <QApplication>
#include <QPushButton>
#include <QLayout>
#include <QLabel>
#include <QLineEdit>
#include <QGroupBox>

class Menu4 : public QWidget
{
	Q_OBJECT

public:
	Menu4();

	~Menu4();

	QWidget* window;

	QLabel* movie_name;

	QPushButton* next_movie;
	QPushButton* add_to_queue;
	QPushButton* return_to_menu;

	QGroupBox* group;

	QVBoxLayout* key_words;
	QHBoxLayout* buttons;
	QVBoxLayout* whole;
};



#endif