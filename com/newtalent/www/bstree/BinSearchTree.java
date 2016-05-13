package com.newtalent.www.bstree;

import java.text.spi.BreakIteratorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.NullType;

import org.omg.CORBA.Current;

public class BinSearchTree<E extends Comparable<E>> extends AbstractTree<E> {
	
	protected TreeNode<E> root;
	
	public int size = 0;
	
	/*
	 * 默认构造器
	 */
	public BinSearchTree() {};
	
	/**
	 * 以一组节点构造二叉排序树
	 * @param nodes
	 */
	public BinSearchTree(E[] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			//插入二叉排序树
		}
	}
	
	
	@Override
	public boolean insert(E e) {
		// TODO Auto-generated method stub
		if (root == null) {
			root = createNewNode(e);
		}
		else {
			TreeNode<E> current = root;

			//记录当前节点的父节点
			TreeNode<E> parent = null;
			
			while (current != null) {
				if (e.compareTo(current.element) < 0 ) {
					parent = current;
					current = parent.left;
				} 
				else if (e.compareTo(current.element) > 0) {
					parent = current;
					current = parent.right;
				}
				else {
					return false;
				}
			}
			
			if (e.compareTo(parent.element) < 0) {
				parent.left = createNewNode(e);
			}
			else {
				parent.right = createNewNode(e);
			}
	
			
		}
		
		size++;
		return true;
	}

	/**
	 * 创建新节点
	 * @param e
	 * @return
	 */
	private TreeNode<E> createNewNode(E e) {
		// TODO Auto-generated method stub
		return new TreeNode<E>(e);
	}

	@Override
	public boolean search(E e) {
		// TODO Auto-generated method stub
		TreeNode<E> current = root;
		
		while (current != null) {
			//如果查询节点的值较当前小，则查询左子树（不要递归，直接操作引用即可）
			if ( e.compareTo(current.element) < 0 ) {
				current = current.left;
			}//否则继续查找右子树 
			else if (e.compareTo(current.element) > 0) {
				current = current.right;
			}
			else {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean delete(E dNode) {
		//定位要删除节点，和记录其父节点
		TreeNode<E> parent = null;
		TreeNode<E> current = root;		
		
		while (current != null) {
			if (dNode.compareTo(current.element) < 0) { //向左子树寻找
				parent = current;
				current = current.left;
			}
			else if (dNode.compareTo(current.element) > 0) {//向右子树寻找
				parent = current;
				current = current.right;
			}
			else {//找到了要删除的节点
				break;
			}
		}
		
		//没有该节点
		if (current == null) {
			return false;
		}
		
		
		//删除二叉排序树中的节点，可以总体分为两种情况
		//情况一： 要删除的节点 没有左孩子（ 将要删除的节点为叶子节点也是这样情况）
		//此时只需要找到右边子树中最大值，然后将parent指向删除节点的直接右孩子（若删除节点为叶子节点，则右孩子为null）
		//右子树最大值 与 parent 交换即可
		if (current.left == null) {
			
			//如果要删除的节点为根节点
			if (parent == null  ) {
				root = current.right;
			}
			else {
				if (dNode.compareTo(parent.element) < 0) {
					parent.left = current.right;
				}
				else {
					parent.right = current.right;
				}
			}
		}
		//情况二： 要删除的节点 有左孩子,较复杂
		//先找要删除节点的左子树中的最大值
		else {
			TreeNode<E> parentOfmaxright = current;
			TreeNode<E> maxRightofChilds = current.left;
			
			while (maxRightofChilds.right != null) {
				parentOfmaxright = maxRightofChilds;
				maxRightofChilds = maxRightofChilds.right;
			}
			
			//交换
			current.element = maxRightofChilds.element;
			
			if (parentOfmaxright.right == maxRightofChilds) {
				parentOfmaxright.right = maxRightofChilds.left;
			} else {
				parentOfmaxright.left = maxRightofChilds.left;
			}
			
		}
		
		size--;
		return true;
		
	}
	
	public E getMax(TreeNode<E> root) {
		if (root == null) {
			System.out.println("空树，无最大值！");
			return null;
		}
		
		while (root.right != null) {
			root = root.right;
		}
		
		return root.element;
	}
	
	public E getMin(TreeNode<E> root) {
		if (root == null) {
			System.out.println("空树，无最小值！");
			return null;
		}
		
		while (root.left != null) {
			root = root.left;
		}
		
		return root.element;
	}
	
	
	
	@Override
	public void preorderTraverse () {
		// TODO Auto-generated method stub
		_preorder(root);
	}
	
	private void _preorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		
		System.out.print( root.element + " ");
		_preorder(root.left);
		_preorder(root.right);
	}
	
	
	
	@Override
	public void inorderTraverse() {
		// TODO Auto-generated method stub
		_inorder(root);
	}
	
	private void _inorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		_inorder(root.left);
		System.out.print( root.element + " ");
		_inorder(root.right);
	}
	
	@Override
	public void postorderTraverse() {
		// TODO Auto-generated method stub
		_postorder(root);
	}
	
	private void _postorder(TreeNode<E> root) {
		if (root == null) {
			return;
		}
		_postorder(root.left);
		_postorder(root.right);
		System.out.print( root.element + " ");
	}
	

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return size;
	}

	/** 返回树根 */
	public TreeNode<E> getRoot() {
		return root;
	}
	
	
	
	/** 清空二叉排序树 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	
	/**
	 * 静态内部类 树节点
	 * @author AllenLee
	 *
	 * @param <E>
	 */
	public static class TreeNode<E extends Comparable<E>> {
		
		E element;
		TreeNode<E> left;
		TreeNode<E> right;
		
		/** 构造函数 */
		public TreeNode(E e) {
			element = e;
		}
	}
	
	/**
	 * 内部类 ----中序遍历迭代器
	 *  
	 * @author AllenLee
	 *
	 */
	class InorderIterator implements Iterator {

		ArrayList<E> nodesArray = new ArrayList<E>();
		
		private int currentIndex = 0;
		
		//中序遍历迭代器---构造函数
		public InorderIterator(){
			//构造中序迭代器时，调用中序遍历接口，获得二叉排序树的中序遍历集合
			inorderTraverse();
		}
		
		private void inorderTraverse() {
			inorder(root);
		}
		
		private void inorder(TreeNode<E> root) {
			if (root == null) {
				return;
			}
			
			//递归中序遍历
			inorder(root.left);
			nodesArray.add(root.element);
			inorder(root.right);
		}

		@Override
		public boolean hasNext() {
			if (currentIndex < nodesArray.size()) {
				return true;
			}
			
			return false;
		}

		@Override
		public Object next() {
			return nodesArray.get(currentIndex++);
		}

		@Override
		public void remove() {
			//根据不同情况删除树中的节点
			delete(nodesArray.get(currentIndex));
			
			//清空节点集合
			nodesArray.clear();
			
			//重新获取删除后的中序遍历集合
			inorderTraverse();
		}
		
	}
		
		
		
}
