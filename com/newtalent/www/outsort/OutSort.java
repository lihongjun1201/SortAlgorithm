package com.newtalent.www.outsort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class OutSort {
	public static final int MAX_ARRAY_SIZE = 10;
	public static final int BUFFER_SIZE = 10;
	
	
	public static void main(String[] args) throws Exception {
		
		createLargeFile("BigFile.dat");
		
		outSort("BigFile.dat","sortedFile.dat");
		
		displayFile("sortedFile.dat");
		
	}
	
	public static void outSort(String sourcefile,String targetfile) throws Exception {
		int segmentsNum = getSmallSortedSegments(MAX_ARRAY_SIZE, sourcefile, "f1.dat");
		
		merge(segmentsNum, MAX_ARRAY_SIZE, "f1.dat", "f2.dat", "f3.dat", targetfile);		
	}
	
	/**
	 * ����һ�����а�ʮ���int�����ġ����ļ������������ڴ�Ų��£�
	 * @param filename
	 * @throws IOException
	 */
	public static void createLargeFile(String filename) throws IOException {
		try {
			DataOutputStream output = new DataOutputStream(
					new BufferedOutputStream(
					new FileOutputStream(filename)));
			
			System.out.println("���ڴ������ļ�" + filename );
			System.out.println("���Ժ�...\n");
			for (int i = 0; i < 84; i++) {
				output.writeInt((int) (Math.random() * 100000));
			}
			
			output.close();
			System.out.println("������ɣ�\n");

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ļ��ڲ��ֳɶ��С�ֶ�
	 * ��С���ļ����򣬲�������Ԫ�������f1��
	 * f1�а�����������СSegment
	 * 
	 * @param segSize  �ڴ������С�ֶδ�С
	 * @param filename
	 * @param tmpSegFileName
	 * @return
	 * @throws IOException
	 */
	public static int getSmallSortedSegments(int segSize,
											 String largeFileName,
											 String f1) throws IOException {
		//�ܹ������ڴ�����ƴ�С����
		int[] list = new int[segSize];
		
		//��ȡ���ļ�
		//װ����ģʽ		
		DataInputStream inputStream	= new DataInputStream(
					new BufferedInputStream(
						new FileInputStream(largeFileName)));
		
		//����ļ���
				DataOutputStream outputStream = new DataOutputStream(
									new BufferedOutputStream(
										new FileOutputStream(f1)));
				
		
		System.out.println("��ʼ�����ļ�" + largeFileName +  "�ֳɶ��С��...");
		
		
		int outSegFileCount = 0;
		
		while(inputStream.available() > 0){
		outSegFileCount++;
		
		//��ȡ���ƴ�С��Ԫ�ص�����
		int i = 0;
		for (; inputStream.available() > 0 && i < segSize; i++) {
			list[i] = inputStream.readInt();
		}
		
		//����
		Arrays.sort(list);
		
		
		//�����С���ļ�
		for (int j = 0; j < i; j++) {
			outputStream.writeInt(list[j]);
		}
		

		System.out.println("��� ������Segment"+outSegFileCount + "���ļ� " + f1);
		
		}
		
		System.out.println("�ֶ���ɣ�׼���ݹ�鲢���ֶ�!\n");
		System.out.println("���ڹ鲢�����Ժ�...");
		
		//�ر���
		inputStream.close();
		outputStream.close();
		
		return outSegFileCount;
	}
	
	/**
	 * �ݹ���ù鲢����
	 * 
	 * @param segNums
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param targetfile
	 * @throws Exception
	 */
	public static void merge(int segNums,int segSize,
					String f1,String f2,String f3,String targetfile) throws Exception {
		System.out.println("��ʱ���鲢�θ����� " + segNums );
		
		
		if (segNums > 1) {
			//��f1 ���Ϊ���룬�ֱ��������ɰ�������ڴ������С ���ݹ�鲢
			mergeOneStep(segNums, segSize, f1, f2, f3);
			System.out.println( "�鲢 " + f1 + " �� " + f2 + ",д����ʱ�ļ�  " + f3 + "...\n");
			
			//�ݹ�鲢�����鲢�������٣�ÿ�γ������ӣ�������ʱ�ļ�
			merge( (segNums + 1)/2 , segSize * 2, f3, f1, f2, targetfile);
			
			
		}
		else {
			File sortedFile = new File(targetfile);
			//�����ڣ���ɾ��
			if (sortedFile.exists()) {
				sortedFile.delete();
			}
			//����
			new File(f1).renameTo(sortedFile);
		}

	}
	
	public static void mergeOneStep (int segNums,int segSize,
			String f1,String f2,String f3) throws Exception {
		
		//����f1������
		DataInputStream f1Input = new DataInputStream(
				new BufferedInputStream(new FileInputStream(f1),BUFFER_SIZE));
		
		//���f2�����
		DataOutputStream f2Output = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(f2),BUFFER_SIZE));
		
		//��f1 ǰ�벿�ָ��Ƶ� f2��
		System.out.println("��" + f1 + "ǰ�벿������ " + f2 + "..."  );
		copyFirstHalf2TempFile(segNums, segSize, f1Input, f2Output);
		f2Output.close();
		
		//��ȡf2������
		DataInputStream f2Input = new DataInputStream(
						new BufferedInputStream(new FileInputStream(f2),BUFFER_SIZE));
				
		//��ȡf3���������Ź鲢 f1���� f2 �������
		DataOutputStream f3Output = new DataOutputStream(
						new BufferedOutputStream(new FileOutputStream(f3),BUFFER_SIZE));
				
		//�鲢f1��ʣ��ԭ���� ��벿�� �� f2��f1ԭ����ǰ�벿��
		//�����f3��
		mergeAllSegments(segNums/2, segSize, f1Input, f2Input, f3Output);
		
		//�ر�������
		f1Input.close();
		f2Input.close();
		f3Output.close();
		
	}
	
	
	
	
	/**
	 * ��f1�ļ��е�ǰһ��ֶθ��Ƶ���ʱ�ļ�f2��
	 * 
	 * @param segmentsNum
	 * @param segmentSize
	 * @param f1
	 * @param tempf2
	 * @throws IOException
	 */
	public static void copyFirstHalf2TempFile(int segmentsNum,
											  int segmentSize,
											  DataInputStream f1,
											  DataOutputStream tempf2) throws IOException {
		
		for (int i = 0; i < (segmentsNum/2) * segmentSize; i++) {
			tempf2.writeInt(f1.readInt());
		}
	}
	
	/**
	 * �鲢�����ֶ�
	 * ���ϲ�������Ĵ�ֶ�������ļ�f3��
	 * 
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @throws IOException
	 */
	public static void mergeTwoSegments(int segSize,
										DataInputStream f1,
										DataInputStream f2,
										DataOutputStream f3) throws IOException {
		int intValueInF1 = f1.readInt();
		int intValueInF2 = f2.readInt();
		
		int f1Count = 1, f2Count = 1;
		
		while(true){
			if (intValueInF1 < intValueInF2) {
				f3.writeInt(intValueInF1);
				
				//����ļ�f1���������꣬���߶���������������ڵ��ڷֶδ�С
				//��ֱ�ӽ�f2д��f3
				if (f1.available() == 0 || f1Count++ >= segSize) {
					f3.writeInt(intValueInF2);
					break;
				}
				else {
					//���f1�������ݣ����������
					intValueInF1 = f1.readInt();
				}
			}
			else {
				f3.writeInt(intValueInF2);
				
				if (f2.available() == 0 || f2Count++ >= segSize) {
					f3.writeInt(intValueInF1);
					break;
				}
				else {
					intValueInF2 = f2.readInt();
				}		
			}
		}
		
		//��f1 �� f2 ��ʣ�������д��f3���ɣ���ֻѡһ
		while (f1.available() > 0 && f1Count++ < segSize) {
			f3.writeInt(f1.readInt());
		}
		
		while (f2.available() > 0 && f2Count++ < segSize) {
			f3.writeInt(f2.readInt());
		}
	}
	
	/**
	 * ��f1�ļ���ֻʣ�º��Σ� �� f2��f1��ǰһ�룩�����еķֶν��й鲢
	 * �����f3�ļ���
	 * 
	 * @param segNums
	 * @param segSize
	 * @param f1
	 * @param f2
	 * @param f3
	 * @throws IOException
	 */
	public static void mergeAllSegments(int segNums,
										int segSize,
										DataInputStream f1,
										DataInputStream f2,
										DataOutputStream f3) throws IOException  {
		for (int i = 0; i < segNums; i++) {
			mergeTwoSegments(segSize, f1, f2, f3);
		}
		
		//ԭ�ļ�f1�ķֶ���Ϊ�����������һ�Σ����ò�������д��f3
		while(f1.available() > 0)
			f3.writeInt(f1.readInt());
		
	}
	
	

	/**
	 * ��ӡ�ź���Ĵ��ļ� ǰ 100����
	 * @param fileName
	 */
	private static void displayFile(String fileName) {
		System.out.println("\n���ļ��ⲿ�鲢������ɣ�\n�������(ǰ50��Ԫ��)��");
		

		try {
			DataInputStream  inputStream = new DataInputStream(
					new BufferedInputStream(
							new FileInputStream(fileName)));
			
			for (int i = 0; i < 50; i++) {
				System.out.println(inputStream.readInt());
			}
			
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	
}
