import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Pass2 {

	public static void main(String[] args) {


		FileWriter fw = null;
		BufferedWriter mcode_file = null;
		try {
			String filename1 = "/home/himanshu/eclipse-workspace/Macro Pass 2/OUTPUT/MACHINE_CODE.txt";
			fw = new FileWriter(filename1);
			mcode_file = new BufferedWriter(fw);

			String filename2 = "/home/himanshu/eclipse-workspace/Macro Pass 2/OUTPUT/APTAB.txt";
			FileWriter fw1 = new FileWriter(filename2);
			BufferedWriter aptab_file = new BufferedWriter(fw1);

			String filename3 = "/home/himanshu/eclipse-workspace/Macro Pass 2/OUTPUT/EVTAB.txt";
			FileWriter fw2 = new FileWriter(filename3);
			BufferedWriter evtab_file = new BufferedWriter(fw2);

			String filename = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/MDT.txt";
			File fr = new File(filename);
			RandomAccessFile mdt_file = new RandomAccessFile(fr, "r");

			String f1 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/PNTAB.txt";
			FileReader fr1 = new FileReader(f1);
			BufferedReader pntab_file = new BufferedReader(fr1);

			String f2 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/EVNTAB.txt";
			FileReader fr2 = new FileReader(f2);
			BufferedReader evntab_file = new BufferedReader(fr2);

			String f3 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/SSNTAB.txt";
			FileReader fr3 = new FileReader(f3);
			BufferedReader ssntab_file = new BufferedReader(fr3);

			String f4 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/MNT.txt";
			FileReader fr4 = new FileReader(f4);
			BufferedReader mnt_file = new BufferedReader(fr4);

			String f5 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/KPDTAB.txt";
			FileReader fr5 = new FileReader(f5);
			BufferedReader kpdtab_file = new BufferedReader(fr5);

			String f6 = "/home/himanshu/eclipse-workspace/Macro Pass 2/INPUT/SSTAB.txt";
			FileReader fr6 = new FileReader(f6);
			BufferedReader sstab_file = new BufferedReader(fr6);

			String mnt[][] = new String[7][2];
			String s;
			int i = 0;
			while ((s = mnt_file.readLine()) != null) {
				mnt[i][0] = s.split("\t")[0];
				mnt[i][1] = s.split("\t")[1];
				i++;
			}

			ArrayList<String> PNTAB = new ArrayList<String>();
			String pntab;
			while ((pntab = pntab_file.readLine()) != null) {
				PNTAB.add(pntab);
			}
			ArrayList<String> EVNTAB = new ArrayList<String>();
			String evntab;
			while ((evntab = evntab_file.readLine()) != null) {
				EVNTAB.add(evntab);
			}
			
			ArrayList<String> SSNTAB = new ArrayList<String>();
			String ssntab;
			while ((ssntab = ssntab_file.readLine()) != null) {
				SSNTAB.add(ssntab);
			}
			
			ArrayList<Integer> SSTAB = new ArrayList<Integer>();
			String sstab;
			while ((sstab = sstab_file.readLine()) != null) {
				SSTAB.add(Integer.parseInt(sstab));
			}
			

			String kpdtab;
			int kpd = 0;
			String temp[] = new String[10];
			while ((kpdtab = kpdtab_file.readLine()) != null) {
				temp[kpd] = kpdtab;
				kpd++;
			}
			String KPDTAB[][] = new String[kpd][2];
			int z = 0;
			while (z < kpd) {
				KPDTAB[z][0] = temp[z].split("\t")[0];
				KPDTAB[z][1] = temp[z].split("\t")[1];
				z++;
			}
			

			ArrayList<Integer> EVTAB = new ArrayList<Integer>();

			for (int j = 0; j < EVNTAB.size(); j++) {
				EVTAB.add(0);
			}
			

			ArrayList<String> APTAB = new ArrayList<String>();

			for (int j = 0; j < (Integer.parseInt(mnt[1][1]) + Integer.parseInt(mnt[2][1])); j++) {
				APTAB.add(null);
			}
			
			
			
			//Here.
			
			int pp = Integer.parseInt(mnt[1][1]);
			for (int j = 0; j < kpd; j++) {
				APTAB.remove(pp + j);
				APTAB.add(pp + j, KPDTAB[j][1]);
			}
			

			System.out.println("Enter Macro call");
			Scanner sc = new Scanner(System.in);
			String name = sc.nextLine();

			int MEC = Integer.parseInt(mnt[4][1]);


			String call[] = new String[name.split(" |\\,").length];
			for (int j = 1; j < name.split(" |\\,").length; j++) {
				call[j - 1] = name.split(" |\\,")[j];
			}
			
			for (int j = 0; j < Integer.parseInt(mnt[1][1]); j++) {
				APTAB.remove(j);
				APTAB.add(j, call[j]);
			}
			for (int j = 0; j < Integer.parseInt(mnt[2][1]); j++) {
				if (call[pp + j] != null) {
					APTAB.remove(pp + j);
					APTAB.add(pp + j, call[pp + j]);
				}
			}
			
			String p = null;
			
			while ((p = mdt_file.readLine()) != null) 
			{
				if (p.contains("MEND"))
				{

				} 
				
				else 
				{

					if (p.contains("SET")) 
					{
						int a = 100000, b = 100000;
						String op = null;
						
						for (int j = 4; j < p.split("\t").length; j++) 
						{

							if (p.split("\t")[j].equals("E")) 
							{
								a = EVTAB.get(Integer.parseInt(p.split("\t")[j + 1]) - 1);
								
								j++;
							}
							else 
							{
								if (p.split("\t")[j].equals("+") || p.split("\t")[j].equals("-")
										|| p.split("\t")[j].equals("*") || p.split("\t")[j].equals("/")) 
								{
									op = p.split("\t")[j];
								} 
								
								else 
								{
									b = Integer.parseInt(p.split("\t")[j]);
								}

							}
						}
						
						if (op == null) 
						{
							if (a != 100000) 
							{
								EVTAB.remove(Integer.parseInt(p.split("\t")[2]) - 1);
								EVTAB.add(Integer.parseInt(p.split("\t")[2]) - 1, a);

							}
							if (b != 100000) 
							{
								EVTAB.remove(Integer.parseInt(p.split("\t")[2]) - 1);
								EVTAB.add(Integer.parseInt(p.split("\t")[2]) - 1, b);
							}
							
						} 
						
						else 
						{
							int c = 0;
							if (op.equals("+")) 
							{
								c = a + b;
							} 
							
							else if (op.equals("-")) 
							{
								c = a - b;
							} 
							
							else if (op.equals("*")) 
							{
								c = a * b;
							} 
							
							else if (op.equals("/")) 
							{
								c = a / b;
							}
							EVTAB.remove(Integer.parseInt(p.split("\t")[2]) - 1);
							EVTAB.add(Integer.parseInt(p.split("\t")[2]) - 1, c);

						}
						

					} 
					else if (p.contains("AGO")) 
					{

						
					} 
					
					else if (p.contains("AIF")) 
					{
					

					} 
					
					else if (!p.contains("LCL")) 
					{
						for (int j = 1; j < p.split("\t").length; j++) 
						{
							String q = p.split("\t")[j];
							if (q.equals("P")) 
							{
								String q1 = APTAB.get(Integer.parseInt(p.split("\t")[j + 1]) - 1);
								mcode_file.write(q1 + "\t");
								j++;
							} 
							
							else if (q.equals("E")) 
							{
								String q1 = Integer.toString(EVTAB.get(Integer.parseInt(p.split("\t")[j + 1]) - 1));
								mcode_file.write(q1 + "\t");
								j++;
							}
							
							else 
							{
								mcode_file.write(q + "\t");
							}
						}
						mcode_file.write("\n");
					}
				}
			}

			for (int j = 0; j < EVTAB.size(); j++) {
				evtab_file.write(EVTAB.get(j) + "\n");
			}

			for (int j = 0; j < APTAB.size(); j++) {
				aptab_file.write(APTAB.get(j) + "\n");
			}

			mdt_file.close();
			mcode_file.close();
			aptab_file.close();
			evtab_file.close();
			pntab_file.close();
			evntab_file.close();
			ssntab_file.close();
			mnt_file.close();
			kpdtab_file.close();
			sstab_file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
