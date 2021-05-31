import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Pass1 {
	public static void main(String[] args) {
		BufferedReader input_file = null;
		FileReader fr = null;

		FileWriter fw = null;
		BufferedWriter mdt_file = null;

		try {
			String filename = "/home/himanshu/eclipse-workspace/Macro Pass 1/INPUT/Input.txt";
			fr = new FileReader(filename);
			input_file = new BufferedReader(fr);

			String filename1 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/MDT.txt";
			fw = new FileWriter(filename1);
			mdt_file = new BufferedWriter(fw);

			String f1 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/PNTAB.txt";
			FileWriter fw1 = new FileWriter(f1);
			BufferedWriter pn_file = new BufferedWriter(fw1);

			String f2 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/EVNTAB.txt";
			FileWriter fw2 = new FileWriter(f2);
			BufferedWriter evn_file = new BufferedWriter(fw2);

			String f3 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/SSNTAB.txt";
			FileWriter fw3 = new FileWriter(f3);
			BufferedWriter ssN_file = new BufferedWriter(fw3);

			String f4 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/MNT.txt";
			FileWriter fw4 = new FileWriter(f4);
			BufferedWriter mnt_file = new BufferedWriter(fw4);

			String f5 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/KPDTAB.txt";
			FileWriter fw5 = new FileWriter(f5);
			BufferedWriter kpd_file = new BufferedWriter(fw5);

			String f6 = "/home/himanshu/eclipse-workspace/Macro Pass 1/OUTPUT/SSTAB.txt";
			FileWriter fw6 = new FileWriter(f6);
			BufferedWriter ss_file = new BufferedWriter(fw6);

			String s;
			
			s = input_file.readLine();		//MACRO
			s = input_file.readLine();		//Macro name and params list
			
			String mnt[][] = new String[7][2];
			mnt[0][0] = "name";		
			mnt[1][0] = "#PP";
			mnt[2][0] = "#KP";
			mnt[3][0] = "#EV";			//0th column for headers
			mnt[4][0] = "#MDTP";
			mnt[5][0] = "#KPDTP";
			mnt[6][0] = "#SSTP";
			
			mnt[1][1] = Integer.toString(0);
			mnt[2][1] = Integer.toString(0);
			mnt[3][1] = Integer.toString(0);		//1st column for values
			mnt[4][1] = Integer.toString(0);
			mnt[5][1] = Integer.toString(0);
			mnt[6][1] = Integer.toString(0);

			ArrayList<String> PNTAB = new ArrayList<String>();
			ArrayList<String> EVNTAB = new ArrayList<String>();
			ArrayList<String> SSNTAB = new ArrayList<String>();
			ArrayList<Integer> SSTAB = new ArrayList<Integer>();

			Hashtable<String, String> KPDTAB = new Hashtable<String, String>();

			int pp = 0;
			int kp = 0;
			int ev = 0;
			int mdt = 1;
			
			mnt[4][1] = Integer.toString(mdt);		//only one macro. so enter the MDT loc as 1.

			mnt[0][1] = s.split(" ")[1];
			
			
			int i = s.split("&|\\,").length;
			
			
			for (int j = 1; j < i; j += 2) 			//+2, because "," and "&" contain a null string between them. :  ,<null>&
			{
				String s1;
				if ((s1 = s.split("&|\\,")[j]).contains("=")) 
				{
					kp++;
					PNTAB.add(s1.split("=")[0]);					//param name
					KPDTAB.put(s1.split("=")[0], s1.split("=")[1]);	//param default value
				} 
				else 
				{
					pp++;
					PNTAB.add(s.split("&|\\,")[j]);
				}

			}
			
			
			mnt[1][1] = Integer.toString(pp);
			mnt[2][1] = Integer.toString(kp);

			while ((s = input_file.readLine()) != null)
			{
				mdt_file.write(mdt + "\t");		//always write mdt
				
				if (s.equals("MEND")) 
				{
					mdt_file.write("MEND");
				} 
				else 
				{
					
					String s2;
					
					if ((s.split(" ")[1]).equals("LCL"))
					{
						ev++;
						s2 = s.split(" ")[2];		//LCL varname
						EVNTAB.add(s2.split("&")[1]);
						mnt[3][1]=Integer.toString(Integer.parseInt(mnt[3][1])+1);
						mdt_file.write(s.split(" ")[1] + "\tE\t" + (EVNTAB.indexOf(s2.split("&")[1]) + 1));
					} 
					
					else if ((s2 = s.split(" ")[1]).equals("SET") || (s2 = s.split(" ")[1]).equals("AIF")
							|| (s2 = s.split(" ")[1]).equals("AGO")) 
					{
						if ((s.split(" ")[1]).equals("SET")) 
						{
							s2 = s.split(" ")[0];		
							
							mdt_file.write("E\t" + (EVNTAB.indexOf(s2.split("&")[1]) + 1) + "\t" + s.split(" ")[1] + "\t");
							
							for (int z = 2; z < s.split(" ").length; z++) 	//ata mazi satakli
							{
								String a = s.split(" ")[z];
								if (a.contains("&")) 
								{
									if (EVNTAB.contains(a.split("&")[1])) 
									{

										mdt_file.write("E\t" + (EVNTAB.indexOf(a.split("&")[1]) + 1) + "\t");
									} 
									
									else 
									{
										mdt_file.write(s.split(" ")[z] + "\t");
									}
								} 
								
								else 
								{
									mdt_file.write(a + "\t");
								}
							}
						} // end of (SET) 
						
						else if ((s.split(" ")[1]).equals("AIF") || (s.split(" ")[1]).equals("AGO")) 
						{
							mdt_file.write(s.split(" ")[1] + "\t");
							
							
							for (int y = 2; y < s.split(" ").length; y++) 	//why 2? ->answer.
							{
								String s3 = s.split(" ")[y];
								if (s3.contains("&")) 
								{
									if (EVNTAB.contains(s3.split("&|\\)")[1])) 
									{
										mdt_file.write("E\t" + (EVNTAB.indexOf(s3.split("&|\\)")[1]) + 1) + "\t");
									}
									
									if (PNTAB.contains(s3.split("&|\\)")[1])) 
									{
										mdt_file.write("P\t" + (PNTAB.indexOf(s3.split("&|\\)")[1]) + 1) + "\t");
									}
									
								} 
								
								else if (s3.contains(".")) 
								{
									if (!(SSNTAB.contains(s3.split(" |\\.")[1]))) 	//if not present in ssntab, add it! (fwd ref)
									{
										SSNTAB.add(s3.split(" |\\.")[1]);

									}

									mdt_file.write("S\t" + ((SSNTAB.indexOf(s3.split(" |\\.")[1]) + 1) + "\t"));

								} 
								
								else 
								{
									mdt_file.write(s3 + "\t");
								}
								
							}//end of for loop
							
						}	//end of (AIF||AGO)
					}		//end of (SET||AIF||AGO) 
					
					else   // it is neither of LCL,SET,AIF,AGO.
					{
						String s4;
						int len;
						len = s.split(" |\\,").length;
						for (int q = 0; q < len; q++) 
						{
							s4 = s.split(" |\\,")[q];

							if (s4.contains(".")) 
							{
								if (!(SSNTAB.contains(s4.split(" |\\.")[1]))) 
								{
									SSNTAB.add(s4.split(" |\\.")[1]);

								}
							} 
							
							else if (s4.contains("&")) 
							{
								if (PNTAB.contains(s4.split("&")[1])) 
								{
									mdt_file.write("P\t" + (PNTAB.indexOf(s4.split("&")[1]) + 1) + "\t");
								} 
								
								else if (EVNTAB.contains(s4.split("&")[1])) 
								{
									mdt_file.write("E\t" + (EVNTAB.indexOf(s4.split("&")[1]) + 1) + "\t");
								}
							} 
							
							else if (s4.length() > 0) 
							{
								mdt_file.write(s4 + "\t");
							}
						} //end of for loop
					}
					
					
					String l=s.split(" ")[0];
					if((s.split(" ")[0].contains("."))){

						int index=SSNTAB.indexOf(l.split(" |\\.")[1]);

						SSTAB.add(index,mdt);		//write the value of MDT in the SSTAB, at the index of the symbol in SSNTAB.
					}
				}
				
				mdt++;
				mdt_file.write("\n");
				
				
			} //end of while loop
			
			
			
			for (int p = 0; p < PNTAB.size(); p++) {
				pn_file.write(PNTAB.get(p) + "\n");
			}
			for (int p = 0; p < EVNTAB.size(); p++) {
				evn_file.write(EVNTAB.get(p) + "\n");
			}
			for (int p = 0; p < SSNTAB.size(); p++) {
				ssN_file.write(SSNTAB.get(p) + "\n");
			}
			for (int z = 0; z < 7; z++) {
				mnt_file.write(mnt[z][0] + "\t" + mnt[z][1]+"\n");
			}
			
			for (Map.Entry m : KPDTAB.entrySet()) {
				kpd_file.write(m.getKey() + "\t" + m.getValue()+"\n");
			}
			
			for (int p = 0; p < SSTAB.size(); p++) {
				ss_file.write(SSTAB.get(p) + "\n");
			}
			
			
			mnt[3][1] = Integer.toString(ev);
			input_file.close();
			mdt_file.close();
			pn_file.close();
			evn_file.close();
			ssN_file.close();
			mnt_file.close();
			kpd_file.close();
			ss_file.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}
}
