#include "Movie.h"

Movie::Movie(){}

Movie::Movie(std::string title){
	this->title = title;
}

Movie::Movie(const Movie& other){
	keywords = other.keywords;
	actors = other.actors;
	title = other.title;
}

Movie::~Movie(){}


std::string Movie::getTitle() const{
	return title;
}

void Movie::addKeyword(std::string keyword){
	keywords.add(keyword);
}

Set<std::string> Movie::getAllKeywords () const{
	return keywords;
}

void Movie::addActor (std::string actor){
	actors.add(actor);
}

Set<std::string> Movie::getAllActors () const{
	return actors;
}