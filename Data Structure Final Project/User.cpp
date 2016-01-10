#include "User.h"

User::User(){
	checked_out = NULL;
}

User::User(std::string ID, std::string Name){
	userID = ID;
	userName = Name;
	checked_out = NULL;
}

User::User(const User& other){
	userID = other.userID;
	userName = other.userName;
}

User::~User(){}

std::string User::getID () const{
	return userID;
}

std::string User::getName () const{
	return userName;
}

Queue<Movie*>* User::movieQueue(){
	return &movie_queue;
}
	
void User::rentMovie (Movie *m){
	if(checked_out == NULL){
		checked_out = m;
	}
}

void User::returnMovie (){
	if(checked_out != NULL){
		checked_out = NULL;
	}
}

Movie* User::currentMovie (){
	return checked_out;
}

void User::setRating(std::string movie_name, int rating){
	try{
		userRating.get(movie_name);
		userRating.remove(movie_name);

	}catch(NoSuchElementException& e){}

	userRating.add(movie_name, rating);
}

int User::returnRating(std::string movie_name){
	try{
		return userRating.get(movie_name);

	}catch(NoSuchElementException& e){
		return -1;
	}
}

int User::ratingSize(){
	return userRating.size();
}

Map<std::string, int>* User::ratingMap(){
	return &userRating;
}

void User::similarity(Map<std::string, User*>& userMap, Map<std::string, Movie*>& movieMap){
	Map<std::string, double> similarMap_refresh;
	similarMap = similarMap_refresh;

	for(Map<std::string, User*>::Iterator it = userMap.begin(); it != userMap.end(); ++it){
		std::vector<std::string> shared_movie;

		if((*it).first != getID()){

		for(Map<std::string, Movie*>::Iterator it2 = movieMap.begin(); it2 != movieMap.end(); ++it2){
			std::string movie_title = ((*it2).second)->getTitle();
			if(returnRating(movie_title) != -1 && ((*it).second)->returnRating(movie_title) != -1){
				shared_movie.push_back(movie_title); 				// the intersection of all the movies rated between A and B
			}
		}
		if(!shared_movie.empty()){
			double num_sum = 0.0;

			for(unsigned int i = 0; i < shared_movie.size(); i++){
				num_sum += (std::abs( (double) returnRating(shared_movie[i]) - (double) ((*it).second)->returnRating(shared_movie[i])) / 4);	// sum of all the similarities
			}
			num_sum = num_sum / (double) shared_movie.size();		// average similarity
			similarMap.add(((*it).second)->getID(), num_sum);
		}
		else{
			similarMap.add(((*it).second)->getID(), 1.0);
		}
		}
	}
}

void User::recommandations(Map<std::string, User*>& userMap, Map<std::string, Movie*>& movieMap){
	Map<std::string, double> interesting_refresh;
	interesting = interesting_refresh;

	for(Map<std::string, Movie*>::Iterator it = movieMap.begin(); it != movieMap.end(); ++it){
		Movie* movie = (*it).second;
		if(returnRating(movie->getTitle()) == -1){
			Set<User*> S;

			for(Map<std::string, User*>::Iterator it = userMap.begin(); it != userMap.end(); ++it){
				if(((*it).second)->returnRating(movie->getTitle()) != -1){
					S.add((*it).second); 									// A set of users who rated the movie
				}
			}

			double R_sum = 0.0;
			double W_sum = 0.0;
			for(Set<User*>::Iterator it = S.begin(); it != S.end(); ++it){
				double rating = (double) (*it)->returnRating(movie->getTitle());
				double sim = similarMap.get((*it)->getID());
				R_sum += ((1 - sim) * rating);
				W_sum += (1 - sim);
			}

			double interest;
			if(W_sum != 0.0){
				interest = R_sum / W_sum;
			}
			else{
				interest = 0.0;
			}
			interesting.add(movie->getTitle(), interest);
		}
	} 
}

void User::remove_recommand(std::string name){
	interesting.remove(name);
}

int User::recommandation_size(){
	return interesting.size();
}

Map<std::string, double>* User::interestMap(){
	return &interesting;
}
