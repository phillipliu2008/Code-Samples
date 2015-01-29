#ifndef MOVIE_H
#define MOVIE_H


#include "Set.h"

class Movie {
  public: 
	Movie (); // default constructor 

    Movie (std::string title);			// constructor for a movie with the given title

    Movie (const Movie & other);		// copy constructor

    ~Movie ();							// destructor

    std::string getTitle () const;		// returns the title of the movie

    void addKeyword (std::string keyword); 
      /* Adds the (free-form) keyword to this movie.
         If the exact same keyword (up to capitalization) was already
         associated with the movie, then the keyword is not added again. */

    Set<std::string> getAllKeywords () const;
      /* Returns a set of all keywords associated with the movie. */

    void addActor (std::string actor);
      /* Adds actors to this movie */

    Set<std::string> getAllActors () const;
      /* Returns a set of all actors associated with the movie. */

  private:
	  std::string title;
	  Set<std::string> keywords;
    Set<std::string> actors;
};

#endif // !MOVIE_H