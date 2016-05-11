package com.newtalent.www;

public class HeapSort {

	/**
	 * ∂—≈≈–Ú
	 * @param elments
	 */
	public static <E extends Comparable> void heapSort(E[] elments) {
		Heap<E> heap = new Heap(elments);
		
		for (int i = elments.length-1 ; i >= 0 ; --i) {
			elments[i] = heap.removeTop();
		}
	}
	
	
	public static void main(String[] args) {
		Integer[] unsortedArray = {5,4,3,2,1};
		heapSort(unsortedArray);
		for (int i = 0; i < unsortedArray.length; i++) {
			System.out.print(unsortedArray[i] + " ");
		}
		
	}

}
