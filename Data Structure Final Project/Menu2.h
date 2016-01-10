#ifndef MENU2_H
#define MENU2_H

#include <QApplication>
#include <QPushButton>
#include <QLayout>
#include <QLabel>
#include <QLineEdit>

class Menu2 : public QWidget
{
	Q_OBJECT

public:
	Menu2();

	~Menu2();

	QWidget* window;

	QLabel* sign_up_msg;
	QLabel* login_msg;
	QLabel* user_name_msg;

	QPushButton* confirm_button;
	QPushButton* cancel_button;

	QLineEdit* login_text;
	QLineEdit* user_name_text;

	QHBoxLayout* first;
	QGridLayout* second;
	QGridLayout* third;
	QHBoxLayout* forth;
	QVBoxLayout* whole;

};


#endif