package main;

import java.io.*;

import exceptions.DuplicateKeyException;
import exceptions.EmptyTreeException;
import exceptions.KeyNotFoundException;
import exceptions.InvalidKeyException;
import exceptions.ThresholdExceededException;
import implementation.NodeList;

public class main {

	public static void main(String[] args) throws IOException, DuplicateKeyException, KeyNotFoundException, InvalidKeyException, ThresholdExceededException, EmptyTreeException {
		File file = new File("C:\\Users\\Nick Trinh\\Desktop\\COMP352 Assignments\\NASTA_testfiles\\NASTA_test_file0.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
				
			String studentID;
			CleverSIDC csidc = new CleverSIDC(10000);
			
			csidc.SetSIDCThreshold(1000);
			while((studentID = br.readLine()) != null) {
				int key = Integer.parseInt(studentID);
				csidc.add(csidc, key, "test value for " + key);
							
			}
	
			int newKey = csidc.generate();

			
			System.out.println("\n************* Add/Remove methods *************");
			csidc.add(csidc, newKey, "testing generate with random new key");
			csidc.allKeys(csidc);
			csidc.remove(csidc, newKey);
			csidc.remove(csidc, 11111110);
			
			System.out.println("\n************* allKeys method *************");
			csidc.allKeys(csidc);
	
			System.out.println("\n************* rangeKeys method *************");
			csidc.rangeKey(11111111, 66666666);
			
			System.out.println("\n************* Next/Previous key methods *************");
			csidc.prevKey(csidc, 22222222);
			csidc.nextKey(csidc, 44444444);
			
			System.out.println("\n************* getValue method *************");
			csidc.getValue(csidc, 11111111);

		}
		
	}

}
