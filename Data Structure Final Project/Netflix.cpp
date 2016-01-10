#include "Netflix.h"

Netflix::Netflix(std::string u_file, std::string m_file, Menu1* fm, Menu2* sm, Menu3* tm, Menu4* fom, Menu5* fif){
	user_file = u_file;
	movie_file = m_file;
	current_user = NULL;
	buffer_movie = NULL;
	first_menu = fm;
	second_menu = sm;
	third_menu = tm;
	forth_menu = fom;
	fifth_menu = fif;

	Read_file(user_file, movie_file);
	makeKeymap();
	makeActormap();
}

Netflix::~Netflix(){

	delete_vector();

	for(Map<std::string, User*>::Iterator it = userMap.begin(); it != userMap.end(); ++it){
		createUser((*it).second, user_file);
		delete (*it).second;
	}

	for(Map<std::string, Movie*>::Iterator it = movieMap.begin(); it != movieMap.end(); ++it){
		delete (*it).second;
	}
}


void Netflix::Read_file(std::string user_file, std::string movie_file){

	std::ifstream inputFile2(movie_file.c_str());									// read in list of movies
	std::string temp, whole_name, whole_key, single_word;
	Set<std::string> keywords;
	Set<std::string> actors;

	while(getline(inputFile2, temp)){
		std::stringstream sss(temp);
		sss >> temp;

		if(temp == "BEGIN"){
			whole_name = "";
			while(sss >> single_word){
				whole_name = whole_name + single_word + " ";
			}
			whole_name = whole_name.substr(0, whole_name.length()-1);	// remove the white space at the end
		}
		else if(temp == "KEYWORD:"){
			whole_key = "";
			while(sss >> single_word){
				whole_key = whole_key + single_word + " ";
			}
			whole_key = whole_key.substr(0, whole_key.length()-1);
			keywords.add(whole_key);
		}
		else if(temp == "ACTOR:"){
			whole_key = "";
			while(sss >> single_word){
				whole_key = whole_key + single_word + " ";
			}
			whole_key = whole_key.substr(0, whole_key.length()-1);
			actors.add(whole_key);
		}
		else if(temp == "END"){
			Movie temp_movie(whole_name);

			for(Set<std::string>::Iterator it = keywords.begin(); it != keywords.end(); ++it){
				temp_movie.addKeyword(*it);
			}
			for(Set<std::string>::Iterator it = actors.begin(); it != actors.end(); ++it){
				temp_movie.addActor(*it);
			}

			allKeywords.merge(keywords);
			allActors.merge(actors);
			movieMap.add(whole_name, new Movie(temp_movie));			// combine movie name and keyword list
			keywords = Set<std::string>();
			actors = Set<std::string>();
		}
	}

	std::ifstream inputFile(user_file.c_str());										// read in list of user infos
	std::string id, first_name, last_name, movie_name, movie_word;
	User* temp_user;
	Queue<Movie*>* user_queue;

	while(getline(inputFile, temp)){
		std::stringstream ss(temp);
		ss >> temp;

		if(temp == "BEGIN"){
			ss >> temp;
			if(temp == "QUEUE"){
				temp_user = userMap.get(id);
				user_queue = temp_user->movieQueue();

				while(true){
					getline(inputFile, temp);
					std::stringstream ss(temp);
					ss >> temp;
					if(temp == "END"){
						break;
					}
					else{
						movie_name = temp + " ";
						while(ss >> movie_word){
							movie_name = movie_name + movie_word + " ";
						}
						movie_name = movie_name.substr(0, movie_name.length()-1);
						user_queue->enqueue(return_movie_by_name(movie_name));	// add movies into the queue
					}
				}
			}
			else if(temp == "RATINGS"){
				temp_user = userMap.get(id);

				while(true){
					getline(inputFile, temp);
					std::stringstream ss(temp);
					ss >> temp;
					if(temp == "END"){
						break;
					}
					else{
						int rating;
						std::stringstream sss(temp);
						sss >> rating;
						ss >> temp;
						movie_name = temp + " ";
						while(ss >> movie_word){
							movie_name = movie_name + movie_word + " ";
						}
						movie_name = movie_name.substr(0, movie_name.length()-1);
						temp_user->setRating(movie_name, rating);							// store movie name with corresponding rating
					}
				}
			}
			else{
				id = temp;
			}
		}
		else if(temp == "NAME:"){
			ss >> first_name >> last_name;
			userMap.add(id, new User(id, first_name + " " + last_name));	// combine the name and put it into user map
		}
		else if(temp == "MOVIE:"){
			movie_name = "";
			while(ss >> movie_word){
				movie_name = movie_name + movie_word + " ";
			}
			movie_name = movie_name.substr(0, movie_name.length()-1);
			temp_user = userMap.get(id);
			temp_user->rentMovie(return_movie_by_name(movie_name));	// checked out a movie
		}
	}

	inputFile.close();
	inputFile2.close();
}


std::string Netflix::capitalize(std::string word){
	std::transform(word.begin(), word.end(), word.begin(), toupper);	// capitalize string using iterators
	return word;
}


Movie* Netflix::return_movie_by_name(std::string title){

	for(Map<std::string, Movie*>::Iterator it = movieMap.begin(); it != movieMap.end(); ++it){
		if(capitalize((*it).first) == capitalize(title)){
			return (*it).second;
		}
	}
	return NULL;
}


void Netflix::makeKeymap(){
	
	for(Set<std::string>::Iterator it = allKeywords.begin(); it != allKeywords.end(); ++it){
		std::string tempKey = *it;											// get each keyword from the set
		Set<std::string> tempSet;

		for(Map<std::string, Movie*>::Iterator it2 = movieMap.begin(); it2 != movieMap.end(); ++it2){			
			Movie tempMov(*((*it2).second));								// get a copy of the movie object
			Set<std::string> movieKeywords = tempMov.getAllKeywords();		// get all the keywords from the movie object
			if(movieKeywords.contains(tempKey)){							// if this movie contains this keyword
				tempSet.add(tempMov.getTitle());							// add movie name to the temporary set
			}
		}
		keywordMap.add(tempKey, tempSet);
	}
}

void Netflix::makeActormap(){

	for(Set<std::string>::Iterator it = allActors.begin(); it != allActors.end(); ++it){
		std::string tempActor = *it;										// get each actor from the set
		Set<std::string> tempSet;

		for(Map<std::string, Movie*>::Iterator it2 = movieMap.begin(); it2 != movieMap.end(); ++it2){			
			Movie tempMov(*((*it2).second));								// get a copy of the movie object
			Set<std::string> movieActors = tempMov.getAllActors();			// get all the actors from the movie object
			if(movieActors.contains(tempActor)){							// if this movie contains this actor
				tempSet.add(tempMov.getTitle());							// add movie name to the temporary set
			}
		}
		actorMap.add(tempActor, tempSet);
	}	
}

void Netflix::createUser(User* current_user, std::string fileName){
	std::ofstream fileOutput;
	fileOutput.open(fileName.c_str(), std::fstream::app);
	fileOutput << "BEGIN " << current_user->getID() << "\n";		// output user info to the file
	fileOutput << "NAME: " << current_user->getName() << "\n";
	if(current_user->currentMovie() != NULL){
		fileOutput << "MOVIE: " << current_user->currentMovie()->getTitle() << "\n";
	}
	if(!current_user->movieQueue()->isEmpty()){
		fileOutput << "BEGIN QUEUE\n";
		while(!current_user->movieQueue()->isEmpty()){
			fileOutput << current_user->movieQueue()->peekFront()->getTitle() << "\n";
			current_user->movieQueue()->dequeue();
		}
		fileOutput << "END QUEUE\n";
	}
	if(current_user->ratingSize() != 0){
		fileOutput << "BEGIN RATINGS\n";
		for(Map<std::string, int>::Iterator it = current_user->ratingMap()->begin(); it != current_user->ratingMap()->end(); ++it){
			fileOutput << (*it).second << " " << (*it).first << "\n";
		}
		fileOutput << "END RATINGS\n";
	}
	fileOutput << "END\n";
	fileOutput.close();
}

void Netflix::delete_vector(){
	unsigned int vec_size = label_vec.size();
	for(unsigned int i = 0; i < vec_size; i++){
		QLabel* temp = label_vec[i];
		forth_menu->key_words->removeWidget(temp); 	// delete existing labels inside the movie info layout
		delete temp;
	}
	for(unsigned int i = 0; i < vec_size; i++){
		label_vec.pop_back();
	}
}

void Netflix::printMovie(Movie movie){

	forth_menu->movie_name->setText(QString::fromStdString(movie.getTitle()));
	Set<std::string> temp_set = movie.getAllKeywords();

	delete_vector();

	for(Set<std::string>::Iterator it = temp_set.begin(); it != temp_set.end(); ++it){	
		QLabel* temp = new QLabel(QString::fromStdString(*it));
		forth_menu->key_words->addWidget(temp);		// add new labels into the movie info layout
		label_vec.push_back(temp);
	}
}

bool Netflix::find_movie_by_name(){

	for(Map<std::string, Movie*>::Iterator it = movieMap.begin(); it != movieMap.end(); ++it){
		if(capitalize((*it).first) == capitalize(title)){
			movie_vec.push_back((*it).second);
			current_movie = movie_vec.begin();
			return true;
		}
	}
	return false;
}

bool Netflix::find_movie_by_key(){

	for(Map<std::string, Set<std::string> >::Iterator it = keywordMap.begin(); it != keywordMap.end(); ++it){

		if(capitalize((*it).first) == capitalize(keyword)){			// check if keyword exist
			Set<std::string> temp = (*it).second;					// prepare to iterate a set of movies titles

			for(Set<std::string>::Iterator it2 = temp.begin(); it2 != temp.end(); ++it2){	
				std::string movie_name = *it2;
				movie_vec.push_back(movieMap.get(movie_name)); 		// store all the matching movies into a vector<Movie*>
			}
			current_movie = movie_vec.begin(); 						// set the current movie as the first one in the movie vector
			return true;
		}
	}
	return false;
}

bool Netflix::find_movie_by_actor(){
	for(Map<std::string, Set<std::string> >::Iterator it = actorMap.begin(); it != actorMap.end(); ++it){

		if(capitalize((*it).first) == capitalize(actor_name)){		// check if actor exist
			Set<std::string> temp = (*it).second;					// prepare to iterate a set of movies titles

			for(Set<std::string>::Iterator it2 = temp.begin(); it2 != temp.end(); ++it2){	
				std::string movie_name = *it2;
				movie_vec.push_back(movieMap.get(movie_name)); 		// store all the matching movies into a vector<Movie*>
			}
			current_movie = movie_vec.begin(); 						// set the current movie as the first one in the movie vector
			return true;
		}
	}
	return false;
}


void Netflix::menu3(std::string cmds){
	std::string cmd = cmds;

		if(!current_user->movieQueue()->isEmpty()){
			third_menu->queue_name->setText("Front of Queue: " + QString::fromStdString(current_user->movieQueue()->peekFront()->getTitle())); 
		}
		else{
			third_menu->queue_name->setText("*Movie Queue is Empty");
		}

		//1. Order movie
		//2. Remove movie from queue 
		//3. Move movie to back of queue

		if(cmd == "1"){
			if(current_user->currentMovie() == NULL){		// check out a movie and remove it from the queue
				if(!current_user->movieQueue()->isEmpty()){
					current_user->rentMovie(current_user->movieQueue()->peekFront());
					current_user->movieQueue()->dequeue();
					update_third_menu();
				}
				else{
					QMessageBox::information(this, "Invalid Input", "Movie Queue is Empty, No Movie Can be Ordered");
				}
			}
			else{
				QMessageBox::information(this, "Invalid Input", "Need to Return the Current Movie First");
			}
		}
		else if(cmd == "2"){
			if(!current_user->movieQueue()->isEmpty()){
				current_user->movieQueue()->dequeue();			// remove a movie from queue
				update_third_menu();
			}
			else{
				QMessageBox::information(this, "Invalid Input", "Movie Queue is Already Empty, No Movie Can be Removed");
			}
		}
		else if(cmd == "3"){
			if(!current_user->movieQueue()->isEmpty()){
				Movie* temp = current_user->movieQueue()->peekFront();
				current_user->movieQueue()->dequeue();			// move the first movie to the back of the queue
				current_user->movieQueue()->enqueue(temp);
				update_third_menu();
			}
			else{
				QMessageBox::information(this, "Invalid Input", "Movie Queue is Already Empty, No Movie Can be Moved");
			}
		}
}


void Netflix::menu2(std::string cmds){
	std::string cmd = cmds;


		if(current_user->currentMovie() != NULL){
			third_menu->movie_name->setText(QString::fromStdString(current_user->currentMovie()->getTitle()));
		}
		else{
			third_menu->movie_name->setText("*No Movie Checked Out");
		}

		//1. Search for a movie by title 
		//2. Search for a movie by keyword 
		//5. Search for a movie by actor
		//3. Return the current movie
		//4. Logout

		if(cmd == "1"){
			title = third_menu->search_result->text().toStdString();

			if(!find_movie_by_name()){
				QMessageBox::information(this, "Invalid Input", "No Movie Found, Please Try Again");
			}
			else{
				third_to_forth_menu();
			}
		}		
		else if(cmd == "2"){
			keyword = third_menu->search_result->text().toStdString();
			title = keyword;

			if(!find_movie_by_name()){
				if(!find_movie_by_key()){ 			// search & print out movie by keyword
					QMessageBox::information(this, "Invalid Input", "No Movie Found, Please Try Again");
				}
				else{
					third_to_forth_menu();
				}
			}
			else{
				third_to_forth_menu();
			}
		}
		else if(cmd == "5"){
			actor_name = third_menu->search_result->text().toStdString();

			if(!find_movie_by_actor()){
				QMessageBox::information(this, "Invalid Input", "No Movie Found, Please Try Again");
			}
			else{
				third_to_forth_menu();
			}
		}

		else if(cmd == "3"){
			if(current_user->currentMovie() == NULL){
				QMessageBox::information(this, "Invalid Input", "No Movie to Return");
			}
			else if(current_user->returnRating(current_user->currentMovie()->getTitle()) == -1){
				fifth_menu->movie_info->setText("Would you like to rate " + QString::fromStdString(current_user->currentMovie()->getTitle()) + " ?");
				fifth_menu->window->show();
				third_menu->window->hide();
			}
			else{
				current_user->returnMovie();
				update_third_menu();
			}
		}
		else if(cmd == "4"){
			current_user = NULL;
			buffer_movie = NULL;
			third_menu->window->hide();
			first_menu->window->show();
		}
}


void Netflix::menu1(std::string cmds){
	std::string cmd = cmds;

	//1. Log in 
	//2. Create a new user 

	if(cmd == "1"){
		std::string id = first_menu->input_text->text().toStdString();

		if(id != ""){
			try{
				current_user = userMap.get(id);
				third_menu->title->setText("Welcome to CSCI 104-Flix, " + QString::fromStdString(current_user->getName()) + " (" 
					+ QString::fromStdString(current_user->getID()) + ")");

				current_user->similarity(userMap, movieMap);
				current_user->recommandations(userMap, movieMap);
				update_third_menu();
				update_recommandation();
				first_menu->window->hide();
				third_menu->window->show();
					
			}catch(NoSuchElementException& e){
				QMessageBox::information(this, "Invalid Input", "User ID Does Not Exist, Please Try Again");
			}
		}
		else{
			QMessageBox::information(this, "Invalid Input", "User ID Cannot be Empty, Please Try Again");
		}	
	}
	else if(cmd == "2"){

		std::string id = second_menu->login_text->text().toStdString();
		std::string names = second_menu->user_name_text->text().toStdString();

		if(id != "" && names != ""){
			try{
				userMap.get(id);
				QMessageBox::information(this, "Invalid Input", "User ID Already Exist, Please Try Again");

			}catch(NoSuchElementException& e){
				userMap.add(id, new User(id, names));
				second_to_first_menu();
				e.what();
			}	
		}
		else{
			QMessageBox::information(this, "Invalid Input", "User ID and Name Cannot be Empty, Please Try Again");
		}
	}
}


void Netflix::start_login(){
	menu1("1");
}

void Netflix::first_to_second_menu(){
	first_menu->window->hide();
	second_menu->window->show();
}

void Netflix::start_new_user(){
	menu1("2");
}

void Netflix::second_to_first_menu(){
	second_menu->window->hide();
	first_menu->window->show();
}

void Netflix::update_third_menu(){
	menu2("0");
	menu3("0");
}

void Netflix::search_movie_name(){
	menu2("1");
}

void Netflix::search_movie_key(){
	menu2("2");
}

void Netflix::search_movie_actor(){
	menu2("5");
}

void Netflix::update_forth_menu(){
	if(current_movie != movie_vec.end()){
		buffer_movie = *current_movie;
		printMovie(*(*current_movie));
		++current_movie;
	}
	else{
		QMessageBox::information(this, "Invalid Input", "No More Movies Left");
	}
}

void Netflix::add_to_movie_queue(){
	current_user->movieQueue()->enqueue(buffer_movie);
	update_third_menu();
}

void Netflix::return_movie(){
	menu2("3");
	update_third_menu();
}

void Netflix::forth_to_third_menu(){
	unsigned int temp = movie_vec.size();
	for(unsigned int i = 0; i < temp; i++){
		movie_vec.pop_back();
	}
	forth_menu->window->hide();
	third_menu->window->show();
}

void Netflix::third_to_first_menu(){
	menu2("4");
}

void Netflix::third_to_forth_menu(){
	update_forth_menu();
	third_menu->window->hide();
	forth_menu->window->show();
}

void Netflix::rent_a_movie(){
	menu3("1");
}

void Netflix::delete_front_queue(){
	menu3("2");
}

void Netflix::move_back_queue(){
	menu3("3");
}

void Netflix::rate_movie(){
	std::string movie_title = current_user->currentMovie()->getTitle();
	if(fifth_menu->star1->isChecked()){									// options for radio buttons
		current_user->setRating(movie_title, 1);
	}
	else if(fifth_menu->star2->isChecked()){
		current_user->setRating(movie_title, 2);
	}
	else if(fifth_menu->star3->isChecked()){
		current_user->setRating(movie_title, 3);
	}
	else if(fifth_menu->star4->isChecked()){
		current_user->setRating(movie_title, 4);
	}
	else if(fifth_menu->star5->isChecked()){
		current_user->setRating(movie_title, 5);
	}
	current_user->similarity(userMap, movieMap);
	current_user->recommandations(userMap, movieMap);
	update_recommandation();
	skip_movie();
}

void Netflix::skip_movie(){
	fifth_menu->window->hide();
	third_menu->window->show();
	current_user->returnMovie();
	update_third_menu();
}

void Netflix::add_recommand_movie(){
	if(current_user->recommandation_size() > 0){
		current_user->movieQueue()->enqueue(recommand_movie);
		update_third_menu();
	}
}

void Netflix::update_recommandation(){
	double max = 0.0;
	if(current_user->recommandation_size() > 0){
		for(Map<std::string, double>::Iterator it = current_user->interestMap()->begin(); it != current_user->interestMap()->end(); ++it){
			if((*it).second >= max){
				max = (*it).second;
				recommand_movie = movieMap.get((*it).first);
			}
		}
		third_menu->recommandation->setText(QString::fromStdString(recommand_movie->getTitle())); 		// choose the one that has the highest interest value
	}
	else{
		third_menu->recommandation->setText("*No More Movies to Recommend");
	}
}

void Netflix::create_graph(){
	std::map<std::string, std::set<std::string> > graph;

	for(Set<std::string>::Iterator it = allActors.begin(); it != allActors.end(); ++it){ 				// iterate through all the actors
		std::set<std::string> temp;

		for(Map<std::string, Movie*>::Iterator it2 = movieMap.begin(); it2 != movieMap.end(); ++it2){	// iterator through all the movies
			Set<std::string> all_actors = ((*it2).second)->getAllActors();

			if((((*it2).second)->getAllActors()).contains(*it)){
				for(Set<std::string>::Iterator it12 = all_actors.begin(); it12 != all_actors.end(); ++it12){
					temp.insert(*it12);
				}
			}
		}
		temp.erase(*it); 															// remove actor himself
		graph.insert(std::pair<std::string, std::set<std::string> >(*it, temp));
	}
	play_bacon_game(graph);
}

void Netflix::play_bacon_game(std::map<std::string, std::set<std::string> >& graph){
	std::string actor1 = third_menu->actor1->text().toStdString();
	std::string actor2 = third_menu->actor2->text().toStdString();

	if(allActors.contains(actor1) && allActors.contains(actor2)){
		std::set<std::string> visited;
		std::queue<std::string> actor_queue;
		std::map<std::string, std::vector<std::string> > path1;			// the actual path to reach actor2
		std::map<std::string, int > path2;								// the number of chains
		bool reached = false;

		visited.insert(actor1);
		actor_queue.push(actor1);
		path2[actor1] = 0;

		while(!actor_queue.empty()){
			std::string curr_actor = actor_queue.front();
			actor_queue.pop(); 											// BFS with a queue

			for(std::set<std::string>::iterator it = graph[curr_actor].begin(); it != graph[curr_actor].end(); ++it){
				const bool is_in = visited.find(*it) != visited.end();
				if(!is_in){ 											// if the current actor is not visited

					path1[*it] = path1[curr_actor];
					path1[*it].push_back(curr_actor);
					path2[*it] = path2[curr_actor] + 1;

					if(*it == actor2){ 									// if the actor is reached, break out of the loop
						reached = true;
						break;
					}
					visited.insert(*it);
					actor_queue.push(*it);
				}
			}
			if(reached){
				break;
			}
		}
		if(reached){
			std::string text, number;
			std::stringstream ss;

			for(std::vector<std::string>::iterator it = path1[actor2].begin(); it != path1[actor2].end(); ++it){
				text += *it + " --> ";
			}
			ss << path2[actor2];
			ss >> number;
			text += actor2 + "\n\nWith " + number + " Chain(s) of Collaboration(s)";
			QMessageBox::information(this, "Result", QString::fromStdString(text));
		}
		else{
			QMessageBox::information(this, "Result", "Path is not available");
		}
	}
	else{
		QMessageBox::information(this, "Invalid Input", "Invalid Actor Name, Please Try Again");
	}
}








