package com.mohamad.Ciphers;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Test {

	public static void main(String[] args) throws IOException {
		Key key = new Key();
		key.setVig_key("deceptive");
		Encrypt enc = new Encrypt();
		Decript dec = new Decript();
		
		String Ptxt = "wearediscoveredsaveyoursel";
		String Ctext = enc.VeginereEncryption(key, Ptxt);
		Ptxt = dec.VeginereDecryption(key, Ctext);
		
		Attack attaker = new Attack();
		File attFile = new File("VigAttackResult.txt");
		attaker.VisenereAttack(attFile, Ptxt, Ctext);
		System.out.println(Ctext+"\n"+Ptxt+"\n"+attaker.VisenereAttack(attFile, Ptxt, Ctext));
		
		key.setOne_timePadKey(Ptxt);
		Ctext = enc.OneTimePadEncryption(key, Ptxt);
		Ptxt = dec.OneTimePadDecryption(key, Ctext);
		System.out.println(Ctext+"\n"+Ptxt+"\n");
		
		key.setRailFanceKey(3);

		Ctext = enc.RailFanceEncryption(key, Ptxt);
		Ptxt = dec.RailFanceDecryption(key, Ctext);
		
		System.out.println(Ctext+"\n"+Ptxt+"\n"+attaker.RailFanceAttack(attFile, Ptxt, Ctext));
	
	
		File f = new File("rowT.txt");
		key.setRowTranspositionKey("3201456");
		Ctext = enc.RowTranspositionEncryption(key,"attakcpostponeduntiltwoam");
		Ptxt = dec.RowTranspositionDecryption(key, Ctext);
		System.out.println(Ctext+"\n"+Ptxt+"\n\n"+attaker.RowTranspositionAttack(f, Ptxt, Ctext));
		
	}

}
