#ifndef USER_H
#define USER_H

#include <string>
#include "Queue.h"
#include "Movie.h"

class User {
  public:
	User (); // default constructor 

    User (std::string ID, std::string name);  
    /* constructor that generates a user with the given name and ID.
       While multiple users may have the same name, the ID should be unique
       (e.g., an e-mail address), so that users can log in with it. */

    User (const User & other); // copy constructor

    ~User ();            // destructor

    std::string getID () const;    // returns the ID associated with this user

    std::string getName () const;  // returns the name associated with this user

	Queue<Movie*> * movieQueue (); 
      /* returns a pointer to the user's movie queue.
         This is the easiest way to ensure that you can modify the queue.
         (If you return by reference, that's also possible, but you'd need
         to be very careful not to invoke any deep copy constructors or
         assignments. A pointer is thus safer.) */

    void rentMovie (Movie *m);
      /* sets the user's currently rented movie to m.
         If the user already has a movie checked out, then it does nothing.
         (So no overwriting the current movie.) */

    void returnMovie ();
      /* returns the movie that the user currently has checked out.
         Does nothing if the user doesn't currently have a movie. */

    Movie* currentMovie ();
      /* returns the user's current checked out movie.
         Returns NULL if the user has no movie checked out. */

    void setRating (std::string movie_name, int rating);
      /* set the user's rating to specific movie */

    int returnRating (std::string movie_name);
      /* returns the user's rating for specific movie */

    int ratingSize ();
      /* returns the size of the userRating map */

    Map<std::string, int>* ratingMap();
      /* returns a pointer of userRating map */

    void similarity(Map<std::string, User*>& userMap, Map<std::string, Movie*>& movieMap);
      /* calculate the similarity among other users */

    void recommandations(Map<std::string, User*>& userMap, Map<std::string, Movie*>& movieMap);
      /* calculate a list of movie recommandations */

    void remove_recommand(std::string name);
      /* remove the movie inside recommandation list */

    int recommandation_size();
      /* return the size of recommandation list */

    Map<std::string, double>* interestMap();

  private:
	  std::string userID;
	  std::string userName;
	  Queue<Movie*> movie_queue;
	  Movie* checked_out;
    Map<std::string, int> userRating;
    Map<std::string, double> similarMap;
    Map<std::string, double> interesting;
};
#endif // !USER_H
