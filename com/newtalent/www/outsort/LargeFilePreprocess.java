package com.newtalent.www.outsort;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.print.attribute.standard.OutputDeviceAssigned;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * ���ɴ��ļ�
 * @author AllenLee
 *
 */
public class LargeFilePreprocess {

	
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
			
			System.out.println("creating...");
			for (int i = 0; i < 84; i++) {
				output.writeInt((int) (Math.random() * 1000000));
			}
			
			output.close();
			System.out.println("done��");
			
			DataInputStream input = new DataInputStream(
					new FileInputStream(filename));
			for (int i = 0; i < 20; i++) {
				System.out.print(input.readInt() + " ");
			}
			
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
		

		System.out.println("�������һ��smallSegment"+outSegFileCount);
		
		}
		
		//�ر���
		inputStream.close();
		outputStream.close();
		
		return outSegFileCount;
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
	
//	public static int getSmallSortedSegments(int segSize,
//											String LargeFilename) throws IOException {
//		//�ܹ������ڴ�����ƴ�С����
//		int[] list = new int[segSize];
//		
//		//��ȡ���ļ�
//		//װ����ģʽ		
//		DataInputStream inputStream	= new DataInputStream(
//										new BufferedInputStream(
//											new FileInputStream(LargeFilename)));
//		
//		
//		
//		
//		int outSegFileCount = 0;
//		
//		while(inputStream.available() > 0){
//			outSegFileCount++;
//			
//			//��ȡ���ƴ�С��Ԫ�ص�����
//			int i = 0;
//			for (; inputStream.available() > 0 && i < segSize; i++) {
//				list[i] = inputStream.readInt();
//			}
//			
//			//����
//			Arrays.sort(list);
//			
//			//����ļ���
//			DataOutputStream outputStream = new DataOutputStream(
//												new BufferedOutputStream(
//													new FileOutputStream("segment"+outSegFileCount + ".dat")));
//			
//			
//			//�����С���ļ�
//			for (int j = 0; j < i; j++) {
//				outputStream.writeInt(list[j]);
//			}
//
//			outputStream.close();
//			System.out.println("���smallSegment"+outSegFileCount);
//		}
//		
//		//�ر���
//		inputStream.close();
//		
//	
//		return outSegFileCount;
//	}

	public static void main(String[] args) throws IOException {
		
		LargeFilePreprocess.createLargeFile("BigFile.dat");
		LargeFilePreprocess.getSmallSortedSegments(10, "BigFile.dat","f1.dat");
		
	}
}
