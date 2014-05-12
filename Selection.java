/*********************************************************
* Selection.java (Project 3: Selection Problem)
* Author: Robert Payne
* Date: 11/09/2012
* Class: ITCS 2215

* Purpose: To implement two decrease and conquer algorithms
to solve the selection problem, seeking the k-th smallest
element in an unsorted list using a recursive and a non-
recursive algorithm. This program reads in 32 integers from
a file to put into the array and then the program reads in
the 9 k values that we are looking for. Then the program
copies the data array into two seperate arrays which the
recursive and non-recursive algorithms seek the k-th 
element. 

The recursive algorithm is a modified version
of quicksort, which does not sort the whole array, instead
it only acts on the part of the array containing the k-th
position until the proper element is place into k.

The non-recursive algorithm is a modified version of
selection sort. Instead of sorting the whole array, it
sorts from the closest end (left/right) until it reaches
k and then returns the k-th value. If it is either trivial
case of the smallest or largest value, it simply does a
max or min search. 

/*********************************************************/
import java.util.Scanner;
import java.io.*;

public class Selection {


/*	main()	 
===========================================================

---------------------------------------------------------*/ 
	public static void main(String[] args)throws IOException {	
		
	   /////////////////////////////////////////////////////
		//Declarations
		/////////////////////////////////////////////////////
		//constants for the array sizes  
		final int S_ARRAY_SIZE = 32;
      final int K_ARRAY_SIZE = 9;	 
	 	//array objects
		//array containing the input from the file; doesn't change
		int[] inputArray = new int[S_ARRAY_SIZE];
		//array containing the elements; used by non-recursive algorithm
		int[] selectionArray = new int[S_ARRAY_SIZE]; 
		//array containing the elements; used by the recursive algorithm													
		int[] recursiveSelectionArray = new int[S_ARRAY_SIZE];
		//array containing the k values to be found
		int[] kValues = new int[K_ARRAY_SIZE];
		//string used for converting the file contents to integers
		String temp = null;
		//file objects for I/O
		File openFile = new File("input.txt");
		Scanner inFile = new Scanner(openFile);
		
		
		//read in the selection array inputs from "input.txt"
		for(int i = 0; i < S_ARRAY_SIZE; i++) {
			
			temp = inFile.nextLine();
		   inputArray[i] = Integer.parseInt(temp);
		}
		//read in the k values that will be searched for
		for(int i = 0; i < K_ARRAY_SIZE; i++) {
		
			temp = inFile.nextLine();
			kValues[i] = Integer.parseInt(temp);
		}
		//close the file
		inFile.close();

		//loop for finding the k-th value of each array
		for(int j = 0; j < K_ARRAY_SIZE; j++){
		
			//copying the array for consistency
			for(int i = 0; i< S_ARRAY_SIZE; i++) {
		  		selectionArray[i] = inputArray[i];
				recursiveSelectionArray[i] = selectionArray[i];
			}
			
			//output containing the results for the recursive algorithm
		   System.out.println("Recursive Version: k = " +
			kValues[j] + ",\t\t Val = " +
			SelectionRecursive(recursiveSelectionArray, kValues[j], 
			S_ARRAY_SIZE)); 
		   //output containing the results for the non-recursive algorithm  
			System.out.println("Non-recursive Version: k = " +
			kValues[j] + ",\t Val = " + 
			SelectionNonRecursive(selectionArray, kValues[j], 
			S_ARRAY_SIZE));
		}	
	}
	
/*	int MedianOfThree(int, int, int, int[])	 
===========================================================
This function takes the left, middle, right and the array
as input arguments and checks the elements for the median
value. This returns a 1, 2, 3 corresponding to middle,
right, and left respectively. Then the partition function
will use that value to set up the pivot value to be used. 
---------------------------------------------------------*/
	public static int MedianOfThree(int a, int m, int b, int array[]){
		
		//middle is median case
		if((array[m] <= array[a] && array[m] >= array[b])
			|| (array[m] <= array[b] && array[m] >= array[a])){

			return 1;
		}
		
		//right is median case
		else if((array[b] <= array[a] && array[b] >= array[m])
			|| (array[b] <= array[m] && array[b] >= array[a])){
			
			return 2;
		}
		
		//left is median case	
		else {
		

			return 3;
		}
	}

/*	Partition()	 
===========================================================
This function uses the partition element to put the lesser
values on the left side and greater on the right side. It
then sends the index of the partion element, which is now
in the correct place, back to the quicksort function.
---------------------------------------------------------*/	
	public static int Partition(int left, int right, int array[]){
					
		int pivot = MedianOfThree(left, (left + right)/2, right, array); //setting up
																							//to get the pivot
		int pivotIndex;				//used to keep track of the pivot value
		int i = left, j = right;	//setting i to left and j to right
		
		//check to see what the pivot and pivot index should be set to based
		//on the result of MedianOfThree
		if(pivot == 1){
			

			pivot = array[(left + right)/2];
			pivotIndex = (left+right)/2;
		}
		
		else if(pivot == 2){
			

			pivot = array[right];
			pivotIndex = right;
		}
		
		else{
			

			pivot = array[left];
			pivotIndex = left;
		}
		
		//i and j move from the left and right towards each other until
		//they get to an element that is on the wrong side of the pivot.
		//then they swap elements
		while(i<j){
						
			//increment i to the right until i passes j or the element
			//is larger than the pivot
			while(array[i]<= pivot && i < j)
				i++;
			
			//incremeant j to the left until the element is smaller than
			//the pivot
			while(array[j]> pivot)
				j--;
			
			if(i < j){
				
				//change the pivot's index if needed	
				if(j == pivotIndex){
				
					pivotIndex = i; 
				}
				
				else if(i == pivotIndex){
				

					pivotIndex = j;
				}

				Swap(i,j,array); 	//swap the elements in i and j

			}		
		}
		
		//swaps the pivot's position with j
		Swap(pivotIndex,j,array);
		return j;

	}
/* void SelectionRecursive(int [], int, int)	 
===========================================================
Calls the modified quicksort function which will quicksort
until k is found to be in the right position.
---------------------------------------------------------*/	
	public static int SelectionRecursive(int array[], int k, int sizeOf){

	QuickSort(0, sizeOf-1, array, k);	
	return array[k];
}
/* void QuickSort(int, int, int[], int)	 
===========================================================
This function is a modified quicksort which only sorts the
part of the array that k is on until k is found to contain
the right element.
---------------------------------------------------------*/	
	public static void QuickSort(int left, int right, int array[], int k){

		//calls partition and quicksort(left/right) if the size
		//of the current partition is greater than 1
		if(right - left > 1){
		

			int i = Partition(left, right, array);
			if(i > k)
				QuickSort(left, i-1, array, k);
			else if(i < k)
				QuickSort(i+1, right, array, k);
			else
				return;
			
		}
		//swaps elements in size 2 partition
		//if it is needed
		else if(right - left == 1) {
		

			if(array[left] > array[right])
				Swap(left,right,array);
		}
		
	}
/* int SelectionNonRecursive(int [], int, int)
===========================================================
This function is a modified selection sort which has 4 cases

1. k is the largest element in the array, does a max search
2. k is the smallest element in the array, does a min search
3. k is less than half of the array size, selection sorts
  from the left until it reaches k then returns k's value
4. k is greater than half of the array size, selection sorts
  from the right until it reaches k then returns k's value
---------------------------------------------------------*/
	public static int SelectionNonRecursive(int array [], int k, int sizeOf) {
	//looking for the largest element
	if(k == sizeOf -1)
	{
		int maxPosition = 0;
		
		for(int i = 0; i < sizeOf; i++)
			if(array[i] > array[maxPosition])
				maxPosition = i;
		
		return array[maxPosition];
	}
	//looking for the smallest element
	else if(k == 0)
	{
		int minPosition = 0;
		
		for(int i = 0; i < sizeOf; i++)
			if(array[i] < array[minPosition])
				minPosition = i;
		
		return array[minPosition];
	}
	//looking for an element in the first half
	else if(k < sizeOf/2)
	{
		int min;
		int minPosition = 0;
		
		for(int i = 0; i <= k; i++) {
		
			min = array[i];
			minPosition = i;
			
			for(int j = i + 1; j < sizeOf; j++) {
				if(array[j] < min) {
					
					min = array[j];
					minPosition = j;
				}
			}
			
			Swap(minPosition, i, array);
		}
	
		return array[k];
	}	
	//looking for an element in the larger half of the array	
	else
	{
		int max;
		int maxPosition = sizeOf -1;
	
		for(int i = sizeOf -1; i >= k; i--) {
				
			max = array[i];
			maxPosition = i;
				
			for(int j = i -1; j >= 0; j--) {
				if(array[j] > max) {
					
					max = array[j];
					maxPosition = j;
				}
			}
				
			Swap(maxPosition, i, array);

		}
	
		return array[k];
	}
}		
	
/*	void Swap(int, int, int[])	 
===========================================================
This function takes two indexes and an array and swaps
the element values.
---------------------------------------------------------*/
	public static void Swap(int i, int j, int array[])
	{
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}