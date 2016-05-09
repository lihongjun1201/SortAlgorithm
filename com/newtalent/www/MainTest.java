package com.newtalent.www;

public class MainTest {

	public static void main(String[] args) {

//		int[] unsortedArray = {72,24,83,13,68,98,43,37};
		int[] unsortedArray = {4,3,2,1};
		
		int[] unsortedArray1 = {1,4,5,7,11,30};
		int[] unsortedArray2 = {2,3,6,9};
		int[] unsortedArray3 = {4,3,2,1};
		
		MergeSort.mergeSort(unsortedArray);
		
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
	}

}
