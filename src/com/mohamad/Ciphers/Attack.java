package com.mohamad.Ciphers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Attack {

	public String VisenereAttack(File output, String PlainText, String CipherText) {
		PlainText = PlainText.toUpperCase();
		CipherText = CipherText.toUpperCase();
		PlainText = PlainText.replaceAll(" ", "");
		CipherText = CipherText.replaceAll(" ", "");
		String out = "\t\tVisenereAttack\n\nPlainText:\t" + PlainText + "\n\nCipherText:\t" + CipherText + "\n\nKey:\t";

		String keyCh="";
		char[] plainChars = PlainText.toCharArray();
		char[] cipherChars = CipherText.toCharArray();

		int[] plainArr = new int[plainChars.length];
		int[] cipherArr = new int[cipherChars.length];
		for (int i = 0; i < plainArr.length; i++) {
			plainArr[i] = plainChars[i] - Decript.asciiA;
			cipherArr[i] = cipherChars[i] - Decript.asciiA;
		}

		for (int i = 0; i < plainChars.length; i++) {
			char keyChar = ' ';
			for (int j = 0; j < 26; j++) {
				if ((plainArr[i] + j) % 26 == cipherArr[i]) {
					keyChar = (char) (j + Decript.asciiA);
					break;
				}

			}
			keyCh += keyChar;
		}

		String KEY="";
		for(int i = 0;i<Math.floor(keyCh.length()/2) ;i++){
			String sub1 = keyCh.substring(0, i);
			String rem = keyCh.substring(i);
			String sub2 = rem.substring(0, sub1.length());
			if(sub1.equals(sub2))
			{
				KEY = sub1;
			}
		}
		
		if(KEY.length() == 0)
			KEY = keyCh;
		out += KEY;
		try {
			FileWriter fw = new FileWriter(output);
			fw.write(out);

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out;
	}

	private String IntToAlphabet(int num) {

		String result = "";
		while (num > 0) {
			num--; // 1 => a, not 0 => a
			int remainder = num % 26;
			char digit = (char) (remainder + Decript.asciiA);
			result = digit + result;
			num = (num - remainder) / 26;
		}

		return result;
	}

	public void OneTimePadAttack(File output, String PlainText, String CipherText) {
		PlainText = PlainText.toUpperCase();
		CipherText = CipherText.toUpperCase();
		PlainText = PlainText.replaceAll(" ", "");
		CipherText = CipherText.replaceAll(" ", "");
		String out = "\t\tOneTimePadAttack\n\nPlainText:\t" + PlainText + "\n\nCipherText:\t" + CipherText
				+ "\n\nKey:\t";

		Decript dec = new Decript();
		Key key = new Key();
		int ctr = 1;
		String decText = "";
		String est_key = "";
		while (est_key.length() <= CipherText.length()) {
			est_key = IntToAlphabet(ctr);
			// System.out.println(ctr + "\t" + est_key);
			key.SET_ONETIMEPAD_KEY(est_key);
			decText = dec.OneTimePadDecryption(key, CipherText);
			if (PlainText.equals(decText)) {
				out += est_key;
				break;
			}
			ctr++;
		}

		try {
			FileWriter fw = new FileWriter(output);
			fw.write(out);

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String RailFanceAttack(File output, String PlainText, String CipherText) {
		PlainText = PlainText.toUpperCase();
		CipherText = CipherText.toUpperCase();
		PlainText = PlainText.replaceAll(" ", "");
		CipherText = CipherText.replaceAll(" ", "");
		String out = "\t\tRailFanceAttack\n\nPlainText:\t" + PlainText + "\n\nCipherText:\t" + CipherText
				+ "\n\nKey:\t";

		String est_text = "";
		Decript dec = new Decript();
		Key k = new Key();
		for (int key = 1; key < CipherText.length(); key++) {
			if (CipherText.length() % key == 0) {
				k.setRailFanceKey(key);
				est_text = dec.RailFanceDecryption(k, CipherText);

				if (est_text.equals(PlainText)) {
					out += key;
					break;
				}
			}
		}

		try {
			FileWriter fw = new FileWriter(output);
			fw.write(out);

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out;
	}

	private List<String> permutation(String str) {
		List<String> perms = new ArrayList<String>();
		permutation("", str, perms);
		return perms;
	}

	private void permutation(String prefix, String str, List<String> allPerms) {
		int n = str.length();
		if (n == 0)
			allPerms.add(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), allPerms);
		}
	}

	public String RowTranspositionAttack(File output, String PlainText, String CipherText) {

		PlainText = PlainText.toUpperCase();
		CipherText = CipherText.toUpperCase();
		PlainText = PlainText.replaceAll(" ", "");
		CipherText = CipherText.replaceAll(" ", "");
		
		if(PlainText.length() < CipherText.length()){
			for(int i = PlainText.length() ; i<CipherText.length();i++){
				PlainText +='X';
			}
		}
		PlainText = PlainText.substring(0, CipherText.length());
		
		String out = "\t\tRowTranspositionAttack\n\nPlainText:\t" + PlainText + "\n\nCipherText:\t" + CipherText
				+ "\n\nKey:\t";
		String est_text = "",row_keyStr="";
		Decript dec = new Decript();
		Key key = new Key();
		for (int len = 1; len <= CipherText.length(); len++) {

			if (PlainText.equals(est_text)) {
				break;
			}

			if(CipherText.length()% len != 0)
				continue;
			String keyStr = "";
			for (int i = 0; i < len; i++)
				keyStr += i;

			List<String> allKeys = permutation(keyStr);

			for (String k : allKeys) {
				key.setRowTranspositionKey(k);
				est_text = dec.RowTranspositionDecryption(key, CipherText);
				row_keyStr = k;
				if (PlainText.equals(est_text)) {
					break;
				}
			}

		}
		
		out += row_keyStr;

		try {
			FileWriter fw = new FileWriter(output);
			fw.write(out);

			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return out;
	}

}