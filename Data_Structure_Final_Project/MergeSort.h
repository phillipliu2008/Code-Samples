#ifndef MERGESORT_H
#define MERGESORT_H

#include<vector>
#include <cmath>
#include <iostream>

template <class T>	// This is a template class, please add typename before calling its functions
class MergeSort {
public:
    static std::vector<T> sort (std::vector<T> a);

private:
	static void start (std::vector<T>& a, int l, int r);

	static void merge (std::vector<T>& a, int l, int r, int m);
};

template <class T>
std::vector<T> MergeSort<T>::sort(std::vector<T> a){
	int l = 0;
	int r = a.size()-1;
	start(a, l, r);
	return a;
}

template <class T>
void MergeSort<T>::start (std::vector<T>& a, int l, int r){
	if(l < r){
		int m = int(std::floor((l+r)/2));
		start(a, l, m);			// divide vector into smaller parts
		start(a, m+1, r);
		merge(a, l, r, m);		// merge and sort the divided parts
	}
}

template <class T>
void MergeSort<T>::merge (std::vector<T>& a, int l, int r, int m){
	std::vector<T> temp;
	int first_half = l, second_half = m+1;
	while(first_half <= m || second_half <= r){
		if(first_half <=m && (second_half > r || a[first_half] < a[second_half])){	// if left part is smaller than right part
			temp.push_back(a[first_half]);
			first_half++;
		}
		else{
			temp.push_back(a[second_half]);		// if right part is smaller than left part
			second_half++;
		}
	}
	for (unsigned int i = 0; i < temp.size(); i++){
		a[i+l] = temp[i];
	}
}

#endif
