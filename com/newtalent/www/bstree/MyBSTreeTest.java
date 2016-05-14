package com.newtalent.www.bstree;

import java.util.Iterator;

public class MyBSTreeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinSearchTree<String> nameTree = new BinSearchTree<String>();
		nameTree.insert("G");
		nameTree.insert("M");
		nameTree.insert("T");
		nameTree.insert("A");
		nameTree.insert("J");
		nameTree.insert("P");
		nameTree.insert("D");
		
		
		//²âÊÔÖĞĞòµü´úÆ÷
		Iterator iterator = nameTree.iterator();
		while (iterator.hasNext()) {
			System.out.println(  ((String)(iterator.next())).toLowerCase() );
			
		}
		

		
		
//		System.out.println( nameTree.getMax(nameTree.getRoot()) );
//		System.out.println( nameTree.getMin(nameTree.getRoot()) );
		nameTree.delete("G");
		nameTree.delete("A");
	
		

		//ÖĞĞòÔò°´×ÖÄ¸ÉıĞòÊä³ö
		nameTree.inorderTraverse();
		System.out.println();
		nameTree.postorderTraverse();
		System.out.println();
		nameTree.preorderTraverse();
		System.out.println();
		System.out.println();
		

		
	
		
//		System.out.println(nameTree.search("D"));
		//System.out.println(nameTree.getSize());

		
		
//		BinSearchTree<Integer> intTree = new BinSearchTree<Integer>();
//		intTree.insert(2);
//		intTree.insert(1);
//		intTree.insert(4);
//		intTree.insert(3);
//		intTree.insert(8);
//		intTree.insert(5);
//		intTree.insert(6);
//		intTree.insert(7);
//		intTree.inorderTraverse();
//		System.out.println( intTree.getMax(intTree.getRoot()) );
//		System.out.println( intTree.getMin(intTree.getRoot()) );
		
	}

}
