package com.newtalent.www.hfmtree;

import java.util.Comparator;

public class HFMTree  implements Comparable<HFMTree> {

	HFMNode root;
	
	/**
	 * 通过权重和元素创建 单节点赫夫曼树
	 * @param weight
	 * @param element
	 */
	public HFMTree(int weight,char element){
		root = new HFMNode(weight,element);
	}
	
	
	/** 通过两棵赫夫曼树 ，构造一颗新的赫夫曼树 */
	public HFMTree(HFMTree treeOne,HFMTree treeTwo) {
		root = new HFMNode();
		root.left = treeOne.root;
		root.right = treeTwo.root;
		
		//新树根的节点权重为两颗子树节点权重之和
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
		
		//该字符的权重
		int weight;
		
		//存储字符元素
		char element;
		
		//链式结构，左右孩子节点
		HFMNode left;
		HFMNode right;
		
		//节点字符最终的赫夫曼编码，形如 01 001等
		String hfmcode = "";
		
		public HFMNode(){};
		
		//节点构造函数
		public HFMNode(int weight,char element) {
			super();
			this.weight = weight;
			this.element = element;
		}
		
	}

}
