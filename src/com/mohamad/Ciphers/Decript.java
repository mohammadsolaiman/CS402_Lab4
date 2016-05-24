package com.mohamad.Ciphers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

public class Decript {

	public static int asciiA = 65, asciiZ = 90;

	public String VeginereDecryption(Key key, String CText) {

		String out = "";
		String KEY = key.getVig_key();
		int keyLength = KEY.length();
		char[] keychars = KEY.toCharArray();
		int[] keyArr = new int[keychars.length];
		for (int i = 0; i < keyArr.length; i++) {
			keyArr[i] = keychars[i] - asciiA;
		}

		CText = CText.toUpperCase();
		CText = CText.replaceAll(" ", "");

		String remTxt = CText;

		while (remTxt.length() > 0) {
			String curText = "";
			if (remTxt.length() < KEY.length()) {
				curText = remTxt;
				remTxt = "";
			} else {
				curText = remTxt.substring(0, keyLength);
				remTxt = remTxt.substring(keyLength);
			}

			for (int i = 0; i < curText.length(); i++) {
				char ch = curText.charAt(i);
				int ch_int = ch - asciiA;

				ch_int -= keyArr[i];
				while (ch_int < 0)
					ch_int += 26;
				ch_int = ch_int % 26;

				ch_int += asciiA;
				ch = (char) ch_int;
				out += ch;

			}
		}

		return out;
	}

	public String OneTimePadDecryption(Key key, String CText) {
		String out = "";
		String KEY = key.getOne_timePadKey();
		int keyLength = KEY.length();
		char[] keychars = KEY.toCharArray();
		int[] keyArr = new int[keychars.length];
		for (int i = 0; i < keyArr.length; i++) {
			keyArr[i] = keychars[i] - asciiA;
		}

		CText = CText.toUpperCase();
		CText = CText.replaceAll(" ", "");

		String remTxt = CText;

		while (remTxt.length() > 0) {
			String curText = "";
			if (remTxt.length() < KEY.length()) {
				curText = remTxt;
				remTxt = "";
			} else {
				curText = remTxt.substring(0, keyLength);
				remTxt = remTxt.substring(keyLength);
			}

			for (int i = 0; i < curText.length(); i++) {
				char ch = curText.charAt(i);
				int ch_int = ch - asciiA;

				ch_int -= keyArr[i];
				while (ch_int < 0)
					ch_int += 26;
				ch_int = ch_int % 26;

				ch_int += asciiA;
				ch = (char) ch_int;
				out += ch;

			}
		}

		return out;
	}

	public String RailFanceDecryption(Key key, String CText) {
		String out = "";

		CText = CText.toUpperCase();
		CText = CText.replaceAll(" ", "");

		int KEY = key.getRailFanceKey();
		
		int[][] digMatrix = new int[KEY][CText.length()];

		for (int i = 0; i < digMatrix.length; i++) {
			for (int j = 0; j < digMatrix[0].length; j++)
				digMatrix[i][j] = -1;
		}

		int ctr = 0;
		boolean up = false;
		for (int j = 0; j < digMatrix[0].length; j++) {
			if (ctr >= CText.length())
				break;

			if (j == 0) {
				for (int i = 0; i < KEY; i++) {
					if (ctr >= CText.length())
						break;
					digMatrix[i][j] = ctr;
					ctr++;
				}
				up = true;
			} else if (up) {
				for (int i = KEY - 2; i >= 0; i--) {
					if (ctr >= CText.length())
						break;

					digMatrix[i][j] = ctr;
					ctr++;

				}
				up = !up;
			} else {
				for (int i = 1; i < KEY; i++) {
					if (ctr >= CText.length())
						break;

					digMatrix[i][j] = ctr;
					ctr++;

				}
				up = !up;

			}

		}
		char[] CHR = new char[CText.length()];
		ctr = 0;
		for(int i=0;i<digMatrix.length;i++){
			for(int j=0;j<digMatrix[0].length;j++){
				if(digMatrix[i][j] != -1){
					CHR[digMatrix[i][j]] = CText.charAt(ctr);
					ctr++;
				}
			}
		}

		for(char c:CHR){
			out += c;
		}
		return out;
	}

	public String RowTranspositionDecryption(Key key, String CText) {
		String out = "";

		CText = CText.toUpperCase();
		CText = CText.replaceAll(" ", "");

		int[] KEY = key.getRowTranspositionKey();

		int rows = CText.length() / KEY.length;

		String[] columns = new String[KEY.length];

		String rem = CText;
		int ctr = 0;
		while (rem.length() > 0) {

			columns[ctr] = rem.substring(0, rows);
			rem = rem.substring(rows);
			ctr++;
		}

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < KEY.length; i++) {
				int ii = KEY[i];

				out += columns[ii].charAt(j);
			}

		}
		return out;
	}
}