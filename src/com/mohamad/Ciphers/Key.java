package com.mohamad.Ciphers;

import java.util.*;

public class Key {

	private String vig_key;
	private String one_timePadKey;
	private int railFanceKey;
	private int[] rowTranspositionKey;
	
	public int[] getRowTranspositionKey() {
		return rowTranspositionKey;
	}

	public void setRowTranspositionKey(String KeyString) {
		rowTranspositionKey = new int[KeyString.length()];
		for(int i =0;i<KeyString.length();i++){
			char ch = KeyString.charAt(i);
			for(int j=0;j<KeyString.length();j++){
				if(i!=j&&ch ==  KeyString.charAt(j)){
					System.err.println("ERROR setRowTranspositionKey CHECK KEY NUMBERS");
					return;
				}else{
					rowTranspositionKey[i] = Integer.parseInt(""+ch);
				}
			}
		}
	}

	public int getRailFanceKey() {
		return railFanceKey;
	}

	public void setRailFanceKey(int railFanceKey) {
		this.railFanceKey = railFanceKey;
	}

	public String getOne_timePadKey() {
		return one_timePadKey;
	}

	public void setOne_timePadKey(String plainText) {
		plainText=plainText.toUpperCase();
		plainText=plainText.replaceAll(" ","");
		one_timePadKey = "";
		for(int i=0;i<plainText.length();i++){
			Random rand = new Random();
		    one_timePadKey += (char)(rand.nextInt(26) + Decript.asciiA);
		}
	}
	
	public void SET_ONETIMEPAD_KEY(String KEY){
		one_timePadKey = KEY;
	}

	public String getVig_key() {
		return vig_key;
	}

	public void setVig_key(String vig_key) {
		vig_key = vig_key.toUpperCase();
		this.vig_key = vig_key;
	}
	
	
}
