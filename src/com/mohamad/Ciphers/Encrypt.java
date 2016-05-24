package com.mohamad.Ciphers;

public class Encrypt {

	public static int asciiA = 65, asciiZ = 90;

	public String VeginereEncryption(Key key, String PText) {

		String out = "";
		String KEY = key.getVig_key();
		int keyLength = KEY.length();
		char[] keychars = KEY.toCharArray();
		int[] keyArr = new int[keychars.length];
		for (int i = 0; i < keyArr.length; i++) {
			keyArr[i] = keychars[i] - asciiA;
		}

		PText = PText.toUpperCase();
		PText = PText.replaceAll(" ", "");

		String remTxt = PText;

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

				ch_int += keyArr[i];
				ch_int = ch_int % 26;
				ch_int += asciiA;
				ch = (char) ch_int;
				out += ch;

			}
		}

		return out;
	}

	public String OneTimePadEncryption(Key key, String PText) {
		String out = "";
		String KEY = key.getOne_timePadKey();
		int keyLength = KEY.length();
		char[] keychars = KEY.toCharArray();
		int[] keyArr = new int[keychars.length];
		for (int i = 0; i < keyArr.length; i++) {
			keyArr[i] = keychars[i] - asciiA;
		}

		PText = PText.toUpperCase();
		PText = PText.replaceAll(" ", "");

		String remTxt = PText;

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

				ch_int += keyArr[i];
				ch_int = ch_int % 26;
				ch_int += asciiA;
				ch = (char) ch_int;
				out += ch;

			}
		}

		return out;
	}

	public String RailFanceEncryption(Key key, String PText) {
		String out = "";

		PText = PText.toUpperCase();
		PText = PText.replaceAll(" ", "");

		int KEY = key.getRailFanceKey();
		int subsNum = PText.length() / KEY;

		char[][] digMatrix = new char[KEY][PText.length()];

		for (int i = 0; i < digMatrix.length; i++) {
			for (int j = 0; j < digMatrix[0].length; j++)
				digMatrix[i][j] = '.';
		}

		int ctr = 0;
		boolean up = false;
		for (int j = 0; j < digMatrix[0].length; j++) {
			if (ctr >= PText.length())
				break;

			if (j == 0) {
				for (int i = 0; i < KEY; i++) {
					if (ctr >= PText.length())
						break;
					digMatrix[i][j] = PText.charAt(ctr);
					ctr++;
				}
				up = true;
			} else if (up) {
				for (int i = KEY - 2; i >= 0; i--) {
					if (ctr >= PText.length())
						break;

					digMatrix[i][j] = PText.charAt(ctr);
					ctr++;

				}
				up = !up;
			} else {
				for (int i = 1; i < KEY; i++) {
					if (ctr >= PText.length())
						break;

					digMatrix[i][j] = PText.charAt(ctr);
					ctr++;

				}
				up = !up;

			}

		}

		for (int i = 0; i < digMatrix.length; i++)

		{
			for (int j = 0; j < digMatrix[0].length; j++) {
				if (digMatrix[i][j] != '.') {
					out += digMatrix[i][j];
				}
			}
		}

		return out;

	}

	public String RowTranspositionEncryption(Key key, String PText) {
		String out = "";

		PText = PText.toUpperCase();
		PText = PText.replaceAll(" ", "");

		int[] KEY = key.getRowTranspositionKey();

		int subsNum = PText.length() / KEY.length;
		if (PText.length() % KEY.length != 0)
			subsNum++;
		String[] subs = new String[subsNum];

		String rem = PText;
		int ctr = 0;
		while (rem.length() > 0) {
			if (rem.length() < KEY.length) {
				for (int i = rem.length(); i < KEY.length; i++)
					rem += "X";
			}
			subs[ctr] = rem.substring(0, KEY.length);
			rem = rem.substring(KEY.length);
			ctr++;
		}
		int pos = 0;
		for (int i = 0; i < KEY.length; i++) {
			int ii = -1;
			for (int s = 0; s < KEY.length; s++) {
				if (pos == KEY[s]) {
					ii = s;
					break;
				}
			}
			for (int j = 0; j < subs.length; j++) {
				out += subs[j].charAt(ii);
			}
			pos++;
		}

		return out;
	}
}