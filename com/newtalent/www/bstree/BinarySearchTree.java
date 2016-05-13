package com.newtalent.www.bstree;

import javax.swing.text.StyledEditorKit.BoldAction;



public class BinarySearchTree<E extends Comparable> {
	
	TreeNode<E> root ;
	
	public BinarySearchTree() {
		root = null;
	}
	
	
	public boolean searchNode(TreeNode<E> node,TreeNode<E> r) {
		if (root == null) {
			root = node;
			r = root;
			return false;
		}
		
		if (r == null) {
			return false;
		}else {
			if (root.element.compareTo(node.element) < 0) {
				searchNode(node,r.left);
			} else if (root.element.compareTo(node.element) > 0) {
				searchNode(node,r.left);
			}
			else {
				return true;
			}
		}
		
		return false;
		
	}
	
	
	
	public  void inOrderTraverse(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		else {
			System.out.println(root.element);
			inOrderTraverse(root.left);
			inOrderTraverse(root.right);
		}
			
	}
	

	
	


	public static void main(String[] args) {
		
		BinarySearchTree<Integer> bstree = new BinarySearchTree<Integer>();
	
	
		bstree.inOrderTraverse(bstree.root);
		
	}
	
	
	
	static	class TreeNode<E>  {
		E element;
		TreeNode<E> left;
		TreeNode<E> right;

		public TreeNode(E element) {
			super();
			this.element = element;
		}

	
	}
	
}






