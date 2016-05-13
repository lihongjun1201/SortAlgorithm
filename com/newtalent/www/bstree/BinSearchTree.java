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
	 * Ĭ�Ϲ�����
	 */
	public BinSearchTree() {};
	
	/**
	 * ��һ��ڵ㹹�����������
	 * @param nodes
	 */
	public BinSearchTree(E[] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			//�������������
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

			//��¼��ǰ�ڵ�ĸ��ڵ�
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
	 * �����½ڵ�
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
			//�����ѯ�ڵ��ֵ�ϵ�ǰС�����ѯ����������Ҫ�ݹ飬ֱ�Ӳ������ü��ɣ�
			if ( e.compareTo(current.element) < 0 ) {
				current = current.left;
			}//����������������� 
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
		//��λҪɾ���ڵ㣬�ͼ�¼�丸�ڵ�
		TreeNode<E> parent = null;
		TreeNode<E> current = root;		
		
		while (current != null) {
			if (dNode.compareTo(current.element) < 0) { //��������Ѱ��
				parent = current;
				current = current.left;
			}
			else if (dNode.compareTo(current.element) > 0) {//��������Ѱ��
				parent = current;
				current = current.right;
			}
			else {//�ҵ���Ҫɾ���Ľڵ�
				break;
			}
		}
		
		//û�иýڵ�
		if (current == null) {
			return false;
		}
		
		
		//ɾ�������������еĽڵ㣬���������Ϊ�������
		//���һ�� Ҫɾ���Ľڵ� û�����ӣ� ��Ҫɾ���Ľڵ�ΪҶ�ӽڵ�Ҳ�����������
		//��ʱֻ��Ҫ�ҵ��ұ����������ֵ��Ȼ��parentָ��ɾ���ڵ��ֱ���Һ��ӣ���ɾ���ڵ�ΪҶ�ӽڵ㣬���Һ���Ϊnull��
		//���������ֵ �� parent ��������
		if (current.left == null) {
			
			//���Ҫɾ���Ľڵ�Ϊ���ڵ�
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
		//������� Ҫɾ���Ľڵ� ������,�ϸ���
		//����Ҫɾ���ڵ���������е����ֵ
		else {
			TreeNode<E> parentOfmaxright = current;
			TreeNode<E> maxRightofChilds = current.left;
			
			while (maxRightofChilds.right != null) {
				parentOfmaxright = maxRightofChilds;
				maxRightofChilds = maxRightofChilds.right;
			}
			
			//����
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
			System.out.println("�����������ֵ��");
			return null;
		}
		
		while (root.right != null) {
			root = root.right;
		}
		
		return root.element;
	}
	
	public E getMin(TreeNode<E> root) {
		if (root == null) {
			System.out.println("����������Сֵ��");
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

	/** �������� */
	public TreeNode<E> getRoot() {
		return root;
	}
	
	
	
	/** ��ն��������� */
	public void clear() {
		root = null;
		size = 0;
	}
	
	
	/**
	 * ��̬�ڲ��� ���ڵ�
	 * @author AllenLee
	 *
	 * @param <E>
	 */
	public static class TreeNode<E extends Comparable<E>> {
		
		E element;
		TreeNode<E> left;
		TreeNode<E> right;
		
		/** ���캯�� */
		public TreeNode(E e) {
			element = e;
		}
	}
	
	/**
	 * �ڲ��� ----�������������
	 *  
	 * @author AllenLee
	 *
	 */
	class InorderIterator implements Iterator {

		ArrayList<E> nodesArray = new ArrayList<E>();
		
		private int currentIndex = 0;
		
		//�������������---���캯��
		public InorderIterator(){
			//�������������ʱ��������������ӿڣ���ö����������������������
			inorderTraverse();
		}
		
		private void inorderTraverse() {
			inorder(root);
		}
		
		private void inorder(TreeNode<E> root) {
			if (root == null) {
				return;
			}
			
			//�ݹ��������
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
			//���ݲ�ͬ���ɾ�����еĽڵ�
			delete(nodesArray.get(currentIndex));
			
			//��սڵ㼯��
			nodesArray.clear();
			
			//���»�ȡɾ����������������
			inorderTraverse();
		}
		
	}
		
		
		
}
