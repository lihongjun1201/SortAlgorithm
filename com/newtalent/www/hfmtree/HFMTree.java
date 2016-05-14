package com.newtalent.www.hfmtree;

import java.util.Comparator;

public class HFMTree  implements Comparable<HFMTree> {

	HFMNode root;
	
	/**
	 * ͨ��Ȩ�غ�Ԫ�ش��� ���ڵ�շ�����
	 * @param weight
	 * @param element
	 */
	public HFMTree(int weight,char element){
		root = new HFMNode(weight,element);
	}
	
	
	/** ͨ�����úշ����� ������һ���µĺշ����� */
	public HFMTree(HFMTree treeOne,HFMTree treeTwo) {
		root = new HFMNode();
		root.left = treeOne.root;
		root.right = treeTwo.root;
		
		//�������Ľڵ�Ȩ��Ϊ���������ڵ�Ȩ��֮��
		root.weight = treeOne.root.weight + treeTwo.root.weight;
	}

//
//	@Override
//	public int compare(HFMTree t1, HFMTree t2) {
//		if (t1.root.weight < t2.root.weight) {
//			return 1;
//		}
//		else if (t1.root.weight > t2.root.weight) {
//			return -1;
//		}
//		else {
//			return 0;
//		}
//	}
	


	


	@Override
	public int compareTo(HFMTree t) {
		if (root.weight < t.root.weight) {
			return -1;
		}
		else if (root.weight == t.root.weight) {
			return 0;
		}
		else {
			return 1;
		}
		
	}


	public class HFMNode {
		
		//���ַ���Ȩ��
		int weight;
		
		//�洢�ַ�Ԫ��
		char element;
		
		//��ʽ�ṹ�����Һ��ӽڵ�
		HFMNode left;
		HFMNode right;
		
		//�ڵ��ַ����յĺշ������룬���� 01 001��
		String hfmcode = "";
		
		public HFMNode(){};
		
		//�ڵ㹹�캯��
		public HFMNode(int weight,char element) {
			super();
			this.weight = weight;
			this.element = element;
		}
		
	}

}
