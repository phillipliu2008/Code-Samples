#ifndef QUEUE_H
#define QUEUE_H

#include <iostream>
#include <exception>

struct EmptyQueueException : public std::exception
{
  const char* what () const throw ()
  {
    return "Queue is Empty";
  }

  std::string custom(std::string msg){	// allow user to input custom message
	return msg;
  }
};

template <class T>
struct Node{
	T value;
	Node<T>* next;
};

template <class T>
class Queue {
  public:
	 
	Queue(); // constructor

    void enqueue (const T & item);
      /* adds the item to the end of the queue.
         The same item may be added many times. */

    void dequeue (); 
      /* removes the front element of the queue. 
         Throws an EmptyQueueException when called on an empty queue.
         EmptyQueueException should inherit from std::exception. */

    const T & peekFront ();
      /* returns the front element of the queue. 
         Throws an EmptyQueueException when called on an empty queue.
         EmptyQueueException should inherit from std::exception. */

    bool isEmpty ();
      /* returns whether the queue is empty. */

private:
	Node<T>* head;
	int sizes;
};

template <class T>
Queue<T>::Queue(){
	head = NULL;
	sizes = 0;
}

template <class T>
void Queue<T>::enqueue(const T& item){
	Node<T>* temp = new Node<T>();
	Node<T>* temp2;
	temp->value = item;
	temp->next = NULL;
	temp2 = head;

	while(true){
		if(head == NULL){
			head = temp;
			sizes++;
			break;
		}
		else if(temp2->next == NULL){
			temp2->next = temp;
			sizes++;
			break;
		}
		temp2 = temp2->next;
	}
}

template <class T>
void Queue<T>::dequeue(){
	if(sizes != 0){
		if(sizes == 1){
			delete head;
			head = NULL;
		}
		else{
			Node<T>* temp = head;
			head = head->next;
			delete temp;
		}
		sizes--;
	}
	else if(isEmpty()){
		throw EmptyQueueException();
	}
}

template <class T>
const T& Queue<T>::peekFront(){
	if(isEmpty()){
		throw EmptyQueueException();
	}
	return head->value;
}

template <class T>
bool Queue<T>::isEmpty(){
	return sizes == 0;
}

#endif // !QUEUE_H
