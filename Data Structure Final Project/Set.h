#ifndef SET_H
#define SET_H

#include "Map.h"
#include "MergeSort.h"
#include <algorithm>

template <class T>
class Set
{
  public:
    Set ();  // constructor for a new empty set

    ~Set (); // destructor

	Set (const Set<T> & other); // copy constructor

	Set<T>& operator= (const Set<T> & other); // assignment operator

    int size () const; // returns the number of elements in the set

    void add (T item); 
      /* Adds the item to the set.
         If the item is already in the set, it should do nothing.
      */

    void remove (const T item);
     /* Removes the item from the set.
        If the item was not in the set, it should do nothing. */

    bool contains (const T item) const;
     /* Returns whether the item is in the set. */

    void merge (const Set<T> & other);
     /* Adds all elements of other to this set. 
        However, will not create duplicates, i.e., it ignores elements
        already in the set. */

	Set<T> setUnion (const Set<T> & other) const;

	Set<T> setIntersection (const Set<T> & other) const;

	/***************************************************************************/

	public:
     class Iterator {
         /* add any constructors that you feel will be helpful,
            and choose whether to make them public or private. */

		 const Set<T>* whoIBelongTo;

		 typename Map<T, int>::Iterator it;

		 Iterator(const Set<T>* s,   typename Map<T, int>::Iterator m);

         public:

		   friend class Set<T>;
		   friend class Map<T, int>;

           T operator* () const;
              // return the current value the iterator is at

           typename Set<T>::Iterator operator++ ();
              // advances the iterator (pre-increment)

           typename Set<T>::Iterator operator= (const typename Set<T>::Iterator & other);
              // assigns the other iterator to this iterator and returns this

           bool operator== (const typename Set<T>::Iterator & other) const;
              // returns whether this iterator is equal to the other iterator

           bool operator!= (const typename Set<T>::Iterator & other) const;
              // returns whether this iterator is not equal to the other iterator
     };

	typename Set<T>::Iterator begin () const;
	// returns an iterator initialized to the first element

	typename Set<T>::Iterator end () const;
	/* returns an iterator initialized past the last element,
	to designate that the end of the set has been reached. */

	/**************************************************************************/

  private:
    Map<T, int> internalStorage;

	std::vector<T> merge_vec(const std::vector<T>& first, const std::vector<T>& second) const;

	std::vector<T> intersect_vec(const std::vector<T>& first, const std::vector<T>& second) const;
     /* You should use a Map (your own implementation) to store your set.
        It is part of your assignment to figure out what types you 
        need for the keyType and valueType. */

     /* If you like, you can add further data fields and private
        helper methods. */
};


template <class T>
Set<T>::Iterator::Iterator(const Set<T>* s, typename Map<T, int>::Iterator m){
	whoIBelongTo = s;
	it = m;
}

template <class T>
T Set<T>::Iterator::operator* () const{
	return (*it).first;
}

template <class T>
typename Set<T>::Iterator Set<T>::Iterator::operator++ (){
	++it;
	return *this;
}

template <class T>
typename Set<T>::Iterator Set<T>::Iterator::operator= (const typename Set<T>::Iterator & other){
	whoIBelongTo = other.whoIBelongTo;
	it = other.it;
	return *this;
}

template <class T>
bool Set<T>::Iterator::operator== (const typename Set<T>::Iterator & other) const{
	return it == other.it && whoIBelongTo == other.whoIBelongTo;
}

template <class T>
bool Set<T>::Iterator::operator!= (const typename Set<T>::Iterator & other) const{
	return !(*this == other);
}

template <class T>
typename Set<T>::Iterator Set<T>::begin () const{
	return Set<T>::Iterator(this, internalStorage.begin());
}

template <class T>
typename Set<T>::Iterator Set<T>::end () const{
	return Set<T>::Iterator(this, internalStorage.end());
}

template <class T>
Set<T>::Set(){}

template <class T>
Set<T>::~Set(){}

template <class T>
Set<T>::Set(const Set<T>& other){
	this->internalStorage = other.internalStorage;
}

template <class T>
Set<T>& Set<T>::operator= (const Set<T> & other){
	this->internalStorage = other.internalStorage;
	return *this;
}

template <class T>
int Set<T>::size() const{
	return internalStorage.size();
}

template <class T>
void Set<T>::add(T item){
	internalStorage.add(item, 0);
}

template <class T>
void Set<T>::remove(const T item){
	internalStorage.remove(item);
}

template <class T>
bool Set<T>::contains (const T item) const{
	try{
		internalStorage.get(item);
	}catch(NoSuchElementException& e){
		e.what();
		return false;
	}
	return true;
}

template <class T>
void Set<T>::merge (const Set<T> & other){
	internalStorage.merge(other.internalStorage);
}

template <class T>
std::vector<T> Set<T>::merge_vec(const std::vector<T>& first, const std::vector<T>& second) const{
	std::vector<T> new_vec;
	unsigned int f_index = 0, s_index = 0;

	while(f_index < first.size() || s_index < second.size()){
		if(f_index < first.size() && (s_index >= second.size() || first[f_index] < second[s_index])){
			new_vec.push_back(first[f_index]);
			f_index++;
		}
		else{
			new_vec.push_back(second[s_index]);
			s_index++;
		}
	}
	return new_vec;
}

template <class T>
std::vector<T> Set<T>::intersect_vec(const std::vector<T>& first, const std::vector<T>& second) const{
	std::vector<T> new_vec, new_vec2;
	new_vec = merge_vec(first, second);

	for(unsigned int i = 0; i < new_vec.size(); i++){
		if(std::find(first.begin(), first.end(), new_vec[i]) != first.end()){
			if(std::find(second.begin(), second.end(), new_vec[i]) != second.end()){
				new_vec2.push_back(new_vec[i]);
			}
		}
	}
	return new_vec2;
}

template <class T>
Set<T> Set<T>::setUnion(const Set<T>& other) const{
	Set<T> newSet;
	std::vector<T> vec1, vec2, union_vec;

	for(Set<T>::Iterator it = this->begin(); it != this->end(); ++it){
		vec1.push_back(*it);
	}
	for(Set<T>::Iterator it = other.begin(); it != other.end(); ++it){
		vec2.push_back(*it);
	}
	vec1 = MergeSort<T>::sort(vec1);
	vec2 = MergeSort<T>::sort(vec2);
	union_vec = merge_vec(vec1, vec2);

	for(unsigned int i = 0; i < union_vec.size(); i++){
		newSet.add(union_vec[i]);
	}
	return newSet;
}

template <class T>
Set<T> Set<T>::setIntersection(const Set<T> & other) const{
	Set<T> newSet;
	std::vector<T> vec1, vec2, inter_vec;

	for(Set<T>::Iterator it = this->begin(); it != this->end(); ++it){
		vec1.push_back(*it);
	}
	for(Set<T>::Iterator it = other.begin(); it != other.end(); ++it){
		vec2.push_back(*it);
	}
	vec1 = MergeSort<T>::sort(vec1);
	vec2 = MergeSort<T>::sort(vec2);
	inter_vec = intersect_vec(vec1, vec2);

	for(unsigned int i = 0; i < inter_vec.size(); i++){
		newSet.add(inter_vec[i]);
	}
	return newSet;
}

#endif // !SET_H
