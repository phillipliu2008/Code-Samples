#ifndef MENU1_H
#define MENU1_H

#include <QApplication>
#include <QPushButton>
#include <QLayout>
#include <QLabel>
#include <QLineEdit>

class Menu1 : public QWidget
{
	Q_OBJECT

public:
	Menu1();

	~Menu1();

	QWidget* window;

	QLabel* welcome_msg;
	QLabel* login_msg;

	QPushButton* login;
	QPushButton* new_user;
	QPushButton* quit;

	QLineEdit* input_text;

	QHBoxLayout* first;
	QGridLayout* second;
	QHBoxLayout* third;
	QVBoxLayout* whole;

};


#endif
