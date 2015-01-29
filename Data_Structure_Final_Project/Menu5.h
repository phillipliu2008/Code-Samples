#ifndef MENU5_H
#define MENU5_H

#include <QApplication>
#include <QPushButton>
#include <QLayout>
#include <QLabel>
#include <QRadioButton>
#include <QGroupBox>

class Menu5 : public QWidget
{
	Q_OBJECT

public:
	Menu5();

	~Menu5();

	QWidget* window;

	QLabel* movie_info;

	QPushButton* rate;
	QPushButton* skip;

	QGroupBox* group;

	QRadioButton* star1;
	QRadioButton* star2;
	QRadioButton* star3;
	QRadioButton* star4;
	QRadioButton* star5;

	QHBoxLayout* radio_buttons;
	QHBoxLayout* push_buttons;
	QVBoxLayout* whole;
};

#endif