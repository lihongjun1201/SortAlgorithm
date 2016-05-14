package com.newtalent.www.hfmtree;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.CancellationException;

import javax.xml.soap.Node;

import com.newtalent.www.bstree.Tree;

public class HFMEncode {
	
	
	public static void main(String[] args) {
		
		String text = "Welcome";
		int[] counts = getCharsFrequence(text);
		
		System.out.printf("%-15s%-15s%-15s%-15s\n","ASCII Code","Character","Frequence","HFMCode");
		
		
		HFMTree hfmTree = createHfmTree(counts);
		String[] codes = getCode(hfmTree.root);
		
		for (int i = 0; i < codes.length; i++) {
			if (counts[i] != 0) {
				System.out.printf("%-15s%-15s%-15s%-15s\n",i,(char)i + "",counts[i],codes[i] );
			}
			
		}
				
	}
	
	
	/**
	 * 获取各字符的最终赫夫曼编码
	 * @param root
	 * @return
	 */
	public static String[] getCode(HFMTree.HFMNode root) {
		if (root == null) {
			return null;
		}
		
		String[] codes = new String[2 * 128];
		encode(root, codes);
		return codes;
	}
	
	/**
	 * * 从树根开始编码，递归访问各节点
	 * 当访问到叶子节点时，将完整的编码赋予code[]数组
	 * 
	 * @param root
	 * @param codes
	 */
	public static void encode(HFMTree.HFMNode root , String[] codes) {
		if (root.left != null) {
			root.left.hfmcode =  root.hfmcode + "0";
			encode(root.left, codes);
			
			root.right.hfmcode = root.hfmcode + "1";
			encode(root.right, codes);
		}
		else {
			codes[(int) root.element] = root.hfmcode;
		}
	}
	
	
	/**
	 * 统计文本各个字符的出现次数
	 * @param text
	 * @return
	 */
	public static int[] getCharsFrequence(String text) {
		//256个ASCII码的计数器
		int[] charCount = new int[256];
		
		for (int i = 0; i < text.length(); i++) {
				charCount[(int)text.charAt(i)]++;
		}
		
		return charCount;
		
	}
	
	/**
	 * 创建最终的一颗赫夫曼树（通过优先队列，即堆维护赫夫曼森林）
	 * @param counts  字符词频数组
	 * @return
	 */
	public static HFMTree createHfmTree(int[] counts) {
		//优先队列是默认是降序
		PriorityQueue<HFMTree> hfmTreesHeap = new PriorityQueue<>();
		
		//将字符词频数组中 所有出现了的字符 及其频率 创建 赫夫曼树森林 （ 每棵赫夫曼都只有单个节点）
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				hfmTreesHeap.offer(new HFMTree(counts[i], (char) i));
			}
		}
		
		while (hfmTreesHeap.size() > 1) {
			//获取森林中树根权重最小的两颗子树
			HFMTree tree1 = hfmTreesHeap.poll();
			HFMTree tree2 = hfmTreesHeap.poll();
			
			HFMTree conbinedTree = new HFMTree(tree1, tree2);
			hfmTreesHeap.add(conbinedTree);
		}
		
		//获取最后建成的一棵赫夫曼树
		return hfmTreesHeap.poll();
	}
	
}
