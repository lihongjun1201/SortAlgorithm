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
	 * ��ȡ���ַ������պշ�������
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
	 * * ��������ʼ���룬�ݹ���ʸ��ڵ�
	 * �����ʵ�Ҷ�ӽڵ�ʱ���������ı��븳��code[]����
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
	 * ͳ���ı������ַ��ĳ��ִ���
	 * @param text
	 * @return
	 */
	public static int[] getCharsFrequence(String text) {
		//256��ASCII��ļ�����
		int[] charCount = new int[256];
		
		for (int i = 0; i < text.length(); i++) {
				charCount[(int)text.charAt(i)]++;
		}
		
		return charCount;
		
	}
	
	/**
	 * �������յ�һ�źշ�������ͨ�����ȶ��У�����ά���շ���ɭ�֣�
	 * @param counts  �ַ���Ƶ����
	 * @return
	 */
	public static HFMTree createHfmTree(int[] counts) {
		//���ȶ�����Ĭ���ǽ���
		PriorityQueue<HFMTree> hfmTreesHeap = new PriorityQueue<>();
		
		//���ַ���Ƶ������ ���г����˵��ַ� ����Ƶ�� ���� �շ�����ɭ�� �� ÿ�úշ�����ֻ�е����ڵ㣩
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				hfmTreesHeap.offer(new HFMTree(counts[i], (char) i));
			}
		}
		
		while (hfmTreesHeap.size() > 1) {
			//��ȡɭ��������Ȩ����С����������
			HFMTree tree1 = hfmTreesHeap.poll();
			HFMTree tree2 = hfmTreesHeap.poll();
			
			HFMTree conbinedTree = new HFMTree(tree1, tree2);
			hfmTreesHeap.add(conbinedTree);
		}
		
		//��ȡ��󽨳ɵ�һ�úշ�����
		return hfmTreesHeap.poll();
	}
	
}
