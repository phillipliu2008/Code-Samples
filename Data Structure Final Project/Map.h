#ifndef MAP_H
#define MAP_H

#include <iostream>
#include <string>
#include <sstream>
#include <fstream>
#include <exception>

struct NoSuchElementException : public std::exception
{
  const char* what () const throw ()
  {
    return "Element does not exist!";
  }

  std::string custom(std::string msg){	// allow user to input custom message
	return msg;
  }
};


template <class keyType, class valueType>
struct MapItem
{
  keyType key;
  valueType value;
  MapItem<keyType, valueType> *prev, *next;
};


template <class FirstType, class SecondType> 
struct Pair {
   FirstType first;
   SecondType second;

   Pair (FirstType first, SecondType second)
      { this->first = first; this->second = second; }
};


template <class keyType, class valueType>
class Map
{
  public:
    Map ();  // constructor for a new empty map

    ~Map (); // destructor

	Map (const Map<keyType, valueType> & other); // copy constructor 

	Map<keyType, valueType>& operator= (const Map<keyType, valueType>& other); // assignment operator

    int size () const; // returns the number of key-value pairs

    void add (const keyType key, valueType value); 
      /* Adds a new association between the given key and the given value.
         If the key already has an association, it should do nothing.
      */

    void remove (const keyType key);
     /* Removes the association for the given key.
        If the key has no association, it should do nothing. */

    valueType get (const keyType key) const;
     /* Returns the value associated with the given key.
        If the key existed in the map, success should be set to true.
        If the key has no association, it should set success to false. */

    void merge (const Map<keyType, valueType> & other);
     /* Adds all the key-value associations of other to the current map.
        If both maps (this and other) contain an association for the same
        key, then the one of this is used. */


	/***************************************************************************/

	public:
     class Iterator {
         /* add any constructors that you feel will be helpful,
            and choose whether to make them public or private. */

		 const Map<keyType, valueType>* whoIBelongTo;

		 MapItem<keyType, valueType>* currentItem;

		 Iterator(const Map<keyType, valueType>* m,  MapItem<keyType, valueType>* t);


         public:

		   friend class Map<keyType, valueType>;

		   Iterator();	// default constructor

           Pair<keyType, valueType> operator* () const;
              // return the current (key, value) pair the iterator is at

           typename Map<keyType,valueType>::Iterator operator++ ();
              // advances the iterator (pre-increment)

           typename Map<keyType,valueType>::Iterator operator= (const typename Map<keyType,valueType>::Iterator & other);
              // assigns the other iterator to this iterator and returns this

           bool operator== (const typename Map<keyType,valueType>::Iterator & other) const;
              // returns whether this iterator is equal to the other iterator

           bool operator!= (const typename Map<keyType,valueType>::Iterator & other) const;
              // returns whether this iterator is not equal to the other iterator


     };

	typename Map<keyType,valueType>::Iterator begin () const;
	// returns an iterator initialized to the first element

	typename Map<keyType,valueType>::Iterator end () const;
	/* returns an iterator initialized past the last element,
	to designate that the end of the map has been reached. */

	/**************************************************************************/

  private:
     /* The head and tail of the linked list you're using to store
        all the associations. */

     MapItem <keyType, valueType> *head, *tail, *travel;
	 int sizes;
	 bool success;

     /* If you like, you can add further data fields and private
        helper methods. */
};

template <class keyType, class valueType>
	 Map<keyType,valueType>::Iterator::Iterator(const Map<keyType, valueType>* m,  MapItem<keyType, valueType>* t){
		whoIBelongTo = m;
		currentItem = t;
	 }

template <class keyType, class valueType>
	 Map<keyType,valueType>::Iterator::Iterator(){
	 }

template <class keyType, class valueType>
	 Pair<keyType, valueType> Map<keyType,valueType>::Iterator::operator* () const{
		 Pair<keyType, valueType> currentPair(currentItem->key, currentItem->value);
		 return currentPair;
	 }

template <class keyType, class valueType>
	 typename Map<keyType,valueType>::Iterator Map<keyType,valueType>::Iterator::operator++ (){
		currentItem = currentItem->next;
		return *this;
	 }

template <class keyType, class valueType>
	 typename Map<keyType,valueType>::Iterator Map<keyType,valueType>::Iterator::operator= (const typename Map<keyType,valueType>::Iterator& other){

		this->whoIBelongTo = other.whoIBelongTo;
		this->currentItem = other.currentItem;
		return *this;
	 }

template <class keyType, class valueType>
	 bool Map<keyType,valueType>::Iterator::operator== (const typename Map<keyType,valueType>::Iterator & other) const{
		return currentItem == other.currentItem && whoIBelongTo == other.whoIBelongTo;
	 }

template <class keyType, class valueType>
	 bool Map<keyType,valueType>::Iterator::operator != (const typename Map<keyType,valueType>::Iterator & other) const{
		 return !(*this == other);
	 }

template <class keyType, class valueType>
typename Map<keyType,valueType>::Iterator Map<keyType, valueType>::begin () const{
	return Map<keyType,valueType>::Iterator(this, head);
}

template <class keyType, class valueType>
typename Map<keyType,valueType>::Iterator Map<keyType, valueType>::end () const{
	return Map<keyType,valueType>::Iterator(this, NULL);
}


template <class keyType, class valueType>
Map<keyType, valueType>::Map(){
	head = tail = travel = NULL;	// set default pointers to null
	sizes = 0;
}

template <class keyType, class valueType>
Map<keyType, valueType>::~Map(){
	MapItem<keyType, valueType>* node1 = head;
	MapItem<keyType, valueType>* node2;

	if(head != NULL){
		while(node1 != NULL){
			node2 = node1;
			node1 = node1->next;
			delete node2;
		}
	}
}

template <class keyType, class valueType>
Map<keyType, valueType>::Map(const Map<keyType, valueType>& other){
	*this = other;
}

template <class keyType, class valueType>
Map<keyType, valueType>& Map<keyType, valueType>::operator= (const Map<keyType, valueType>& other){
	if(this != &other){
		MapItem<keyType, valueType>* node1 = head;
		MapItem<keyType, valueType>* node2;

		if(head != NULL){
			while(node1 != NULL){	// deallocate the existing data first
				node2 = node1;
				node1 = node1->next;
				delete node2;
			}
		}
		sizes = 0;
		head = tail = travel = NULL;
	
		merge(other);	// add the new data in
	}
	return *this;
}

template <class keyType, class valueType>
int Map<keyType, valueType>::size () const{
	return  sizes;
}

template <class keyType, class valueType>
void Map<keyType, valueType>::add(const keyType key, valueType value){
	if(sizes == 0){
		head = new MapItem<keyType, valueType>();	// if the map is empty, create head first
		head->key = key;
		head->value = value;
		head->next = head->prev = NULL;
		travel = head;
		sizes++;
	}
	else if(sizes == 1){
		if(head->key != key){
			tail = new MapItem<keyType, valueType>();	// if only head exist, create tail next 
			tail->key = key;
			tail->value = value;
			tail->next = NULL;
			tail->prev = head;
			head->next = tail;
			sizes++;
		}
	}
	else{
		try{
			get(key);
		}catch(NoSuchElementException& e){
			MapItem<keyType, valueType>* newItem = new MapItem<keyType, valueType>();
			newItem->key = key;
			newItem->value = value;
			newItem->prev = tail;
			newItem->next = NULL;
			tail->next = newItem;		// insert the new item before tail 
			tail = newItem;
			sizes++;
			e.what();					// wrote e.what() to avoid warnings
		}
	}
}

template <class keyType, class valueType>
void Map<keyType, valueType>::remove (const keyType key){
	if(sizes > 0){
		MapItem<keyType, valueType>* temp;
		try{
			get(key);				// if key exist in map
			if(head->key == key){	// if needs to delete head, move head to the next one
				temp = head;
				head = head->next;
				if(head != NULL){
					head->prev = NULL;
					if(head == tail){
						tail = NULL;
					}
				}
			}
			else if(tail->key == key){	// if needs to delete tail, move tail to the previous one
				temp = tail;
				tail = tail->prev;
				if(tail == head){
					tail = NULL;
					head->next = NULL;
				}
				else{
					tail->next = NULL;
				}
			}
			else{
				for(temp = head; temp != NULL; temp = temp->next){ // else delete the pointers in between
					if(temp->key == key){
						temp->prev->next = temp->next;
						temp->next->prev = temp->prev;
						break;
					}
				}
			}
			delete temp;
			sizes--;
			travel = head;
		}catch(NoSuchElementException& e){ e.what();}
	}
}

template <class keyType, class valueType>
valueType Map<keyType, valueType>::get (const keyType key) const{
	if(head != NULL){
		MapItem<keyType, valueType>* node = head;

		while(node != NULL){
			if(node->key == key){
				return node->value;
			}
			node = node->next;
		}
	}
	throw NoSuchElementException();		// if key is not in map, throw exception
}

template <class keyType, class valueType>
void Map<keyType, valueType>::merge (const Map<keyType, valueType> & other){
	MapItem<keyType, valueType>* temp = other.head;

	for(int i = 0; i < other.size(); i++){
		add(temp->key, temp->value);
		temp = temp->next;
	}
}


#endif
