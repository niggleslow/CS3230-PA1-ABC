/**
 * Name: Nicholas Low
 * Matric No. : A0110574N
 **/

import java.util.*;
import java.lang.*;
import java.io.*;

class PA1_ABC {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out))); // use this (a much faster output routine) instead of Java System.out.println (slow)
        
        int T, B; // T = no. of test cases; B = radix base
		String V, M, answer; // V = first operand; M = second operand
		int radixpoint_V, radixpoint_M, radixpoint = 0;
        T = sc.nextInt();
		
        for (int i = 1; i <= T; ++i) {
            B = sc.nextInt(); 
			sc.nextLine();
			V = sc.nextLine(); M = sc.nextLine();

			if(V.contains(".") && !M.contains(".")) {
				M = M + ".0";
			}
			if(!V.contains(".") && M.contains(".")) {
				V = V + ".0";
			}
			//checking position of radix points
			radixpoint_V = V.indexOf(".");
			radixpoint_M = M.indexOf(".");
			
			if(radixpoint_V != -1 && radixpoint_M != -1) {
				radixpoint = radixpoint_V + radixpoint_M - 1;
			}
			V = V.replace(".", "");
			M = M.replace(".", "");

			answer = multiply(V, M, B);
			if(radixpoint != 0) {
                answer = answer.substring(0, radixpoint + 1) + "." + answer.substring(radixpoint + 1, answer.length());
			}
			pw.write(trimZeros(answer));
			pw.write("\n");
        }
        pw.close(); // do not forget to use this
	}

	//Long multiplication O(n^2)
	private static String multiply(String a, String b, int base) {
		int[] first = stringToArray(a);
		int[] second = stringToArray(b);
		int sizeOfIntermediate = first.length + second.length;
		int positionCounter = sizeOfIntermediate - 1;
		int secondPositionCounter = sizeOfIntermediate -1;
		int[] intermediate = new int[sizeOfIntermediate];
		for(int firstCount = first.length - 1; firstCount >= 0; firstCount--) {
			for(int secondCount = second.length - 1; secondCount >= 0; secondCount--) {
				intermediate[positionCounter] += first[firstCount] * second[secondCount];
				if(intermediate[positionCounter] > base-1) {
					intermediate[positionCounter - 1] += intermediate[positionCounter] / base;
					intermediate[positionCounter] = intermediate[positionCounter] % base;
				}  
				positionCounter--;
			}
			secondPositionCounter--;
			positionCounter = secondPositionCounter;
		}
		return arrayToString(intermediate);
	}

	private static String arrayToString(int[] a) {
		int length = a.length;
		String stringArray = "";
		for(int i = 0; i < length; i++) {
			stringArray = stringArray + toDigit(a[i]);
		}
		return stringArray;
	}

	private static int[] stringToArray(String a) {
		int sizeOfArray = a.length();
		int[] array = new int[sizeOfArray];
		for(int i = 0; i < a.length(); i++) {
			array[i] = parseDigit(a.charAt(i));
		}
		return array;
	}

	private static char toDigit(int digit) {
		if (digit <= 9) {
			return (char)(digit + '0');
		} 
		return (char)(digit - 10 + 'A');
	}

	private static String trimZeros(String input) {
		int left = 0;
		int right = input.length()-1;
		int fp = input.indexOf('.');
		if (fp == -1) {
			fp = input.length();
		}
		
		while(left < fp-1) {
			if (input.charAt(left) != '0')
				break;
			left++;
		}
		
		while (right >= fp) {
			if (input.charAt(right) != '0') {
				if (input.charAt(right) == '.')
					right--;
				break;
			}
			right--;
		}
		
		if (left >= fp)
			return "0" + input.substring(left,right+1);
		return input.substring(left,right+1);
	}

	private static int parseDigit(char c) {
		if (c <= '9') {
			return c - '0';
		} 
		return c - 'A' + 10;
	}
}