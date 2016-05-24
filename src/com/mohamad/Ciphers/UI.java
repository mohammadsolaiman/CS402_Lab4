package com.mohamad.Ciphers;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.TextArea;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Button;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Window.Type;
import javax.swing.JPanel;
import java.awt.Label;
import java.awt.Color;

public class UI {

	private JFrame frame;
	private String KEY_PATH = "KEY.txt";
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setType(Type.POPUP);
		frame.setResizable(false);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.setBounds(100, 100, 731, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JRadioButton vigenere_rb = new JRadioButton("Vigenere");
		buttonGroup.add(vigenere_rb);
		vigenere_rb.setSelected(true);
		vigenere_rb.setBounds(20, 418, 90, 23);
		frame.getContentPane().add(vigenere_rb);

		JRadioButton oneTimePad_rb = new JRadioButton("OneTimePad");
		buttonGroup.add(oneTimePad_rb);
		oneTimePad_rb.setBounds(126, 418, 90, 23);
		frame.getContentPane().add(oneTimePad_rb);

		JRadioButton rowTrans_rb = new JRadioButton("RowTransposition");
		buttonGroup.add(rowTrans_rb);
		rowTrans_rb.setBounds(334, 418, 90, 23);
		frame.getContentPane().add(rowTrans_rb);

		JRadioButton railFance_rb = new JRadioButton("RailFance");
		buttonGroup.add(railFance_rb);
		railFance_rb.setBounds(231, 418, 90, 23);
		frame.getContentPane().add(railFance_rb);

		TextArea log = new TextArea();
		log.setBounds(10, 340, 414, 72);
		frame.getContentPane().add(log);

		Panel panel = new Panel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBounds(10, 10, 414, 100);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		TextField enc_srcPathTextField = new TextField();
		enc_srcPathTextField.setText("PlainText.txt");
		enc_srcPathTextField.setBounds(10, 17, 276, 22);
		panel.add(enc_srcPathTextField);

		TextField enc_result_textField = new TextField();
		enc_result_textField.setText("EncryptionResult....");
		enc_result_textField.setBounds(10, 45, 276, 22);
		panel.add(enc_result_textField);

		Button button = new Button("Open");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = enc_srcPathTextField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "File not found!..\n");
				}
			}
		});
		button.setBounds(292, 17, 56, 22);
		panel.add(button);

		Button button_1 = new Button("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(enc_srcPathTextField, frame);
			}
		});
		button_1.setBounds(354, 17, 56, 22);
		panel.add(button_1);

		Button button_2 = new Button("Browse");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(enc_result_textField, frame);
			}
		});
		button_2.setBounds(354, 45, 56, 22);
		panel.add(button_2);

		Button button_3 = new Button("Open");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = enc_result_textField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "File not found!..\n");
				}
			}
		});
		button_3.setBounds(292, 45, 56, 22);
		panel.add(button_3);

		JLabel lblEncription = DefaultComponentFactory.getInstance().createLabel(
				"  Encription---------------------------------------------------------------------------------------");
		lblEncription.setFont(new Font("Papyrus", Font.PLAIN, 11));
		lblEncription.setHorizontalAlignment(SwingConstants.LEFT);
		lblEncription.setBounds(0, 0, 414, 14);
		panel.add(lblEncription);

		Button button_8 = new Button("Encrypt");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String srcPath = enc_srcPathTextField.getText();
				String resultPath = enc_result_textField.getText();
				resultPath = "EncryptionResult.txt";
				File srcFile = new File(srcPath);
				File keyFile = new File(KEY_PATH);
				File result = new File(resultPath);
				FileWriter fr;
				try {

					fr = new FileWriter(result);
					enc_result_textField.setText(resultPath);

					fr.close();
				} catch (Exception e) {
					System.out.println("exiption\n");
				}
				try {

					Scanner sc = new Scanner(srcFile);
					Scanner keySc = new Scanner(keyFile);
					fr = new FileWriter(result);

					String selectedAlgo = "";
					JRadioButton[] rbs = { vigenere_rb, oneTimePad_rb, railFance_rb, rowTrans_rb };

					for (JRadioButton rb : rbs) {
						if (rb.isSelected()) {
							selectedAlgo = rb.getText();
							break;
						}
					}

					switch (selectedAlgo) {

					case "Vigenere": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setVig_key(keyword);

						String PlainText = "";
						while (sc.hasNext()) {
							PlainText += sc.next();
						}
						Encrypt enc = new Encrypt();
						String out = enc.VeginereEncryption(key, PlainText);
						fr.write(out);
						break;
					}
					case "OneTimePad": {
						File oneTimeKey = new File("OneTimeKey.txt");
						FileWriter keyFW = new FileWriter(oneTimeKey);
						Key key = new Key();

						String PlainText = "";
						while (sc.hasNext()) {
							PlainText += sc.next();
						}

						key.setOne_timePadKey(PlainText);
						keyFW.write(key.getOne_timePadKey());
						keyFW.close();

						log.setText(log.getText()+key.getOne_timePadKey()+"\n");
						Encrypt enc = new Encrypt();
						String out = enc.OneTimePadEncryption(key, PlainText);
						fr.write(out);
						break;
					}
					case "RailFance": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setRailFanceKey(Integer.parseInt(keyword));

						String PlainText = "";
						while (sc.hasNext()) {
							PlainText += sc.next();
						}
						Encrypt enc = new Encrypt();
						String out = enc.RailFanceEncryption(key, PlainText);
						fr.write(out);
						break;
					}
					case "RowTransposition": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setRowTranspositionKey(keyword);

						String PlainText = "";
						while (sc.hasNext()) {
							PlainText += sc.next();
						}
						Encrypt enc = new Encrypt();
						String out = enc.RowTranspositionEncryption(key, PlainText);
						fr.write(out);
						break;
					}

					}

					fr.close();
					keySc.close();
					sc.close();

				} catch (FileNotFoundException e) {
					log.setText(log.getText() + "File Not Found!..\nPleas check PlainText Path and Key Path\n");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.setText(log.getText() + e.getMessage() + "\n");
					e.printStackTrace();
				}

			}
		});
		button_8.setBounds(292, 73, 118, 22);
		panel.add(button_8);

		Button button_15 = new Button("Choose Key");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TextField tf = new TextField();
				Browse(tf, frame);
				KEY_PATH = tf.getText().toString();
			}
		});
		button_15.setBounds(10, 73, 118, 22);
		panel.add(button_15);

		Button button_16 = new Button("OpenKeyFile");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Open(KEY_PATH);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "ERROR: Key file is not found!..\n");
				}
			}
		});
		button_16.setBounds(134, 73, 118, 22);
		panel.add(button_16);

		Panel panel_1 = new Panel();
		panel_1.setBackground(SystemColor.inactiveCaptionBorder);
		panel_1.setBounds(10, 116, 414, 100);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		TextField dec_src_textField = new TextField();
		dec_src_textField.setText("EncryptionResult.txt");
		dec_src_textField.setBounds(10, 16, 276, 22);
		panel_1.add(dec_src_textField);

		TextField dec_resultTextField = new TextField();
		dec_resultTextField.setText("DecryptionResult....");
		dec_resultTextField.setBounds(10, 44, 276, 22);
		panel_1.add(dec_resultTextField);

		Button button_7 = new Button("Open");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = dec_src_textField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "Error file path!..\n");
				}
			}
		});
		Button button_6 = new Button("Browse");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(dec_src_textField, frame);
			}
		});

		Button button_4 = new Button("Browse");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(dec_resultTextField, frame);
			}
		});
		button_4.setBounds(354, 44, 56, 22);
		panel_1.add(button_4);

		Button button_5 = new Button("Open");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = dec_resultTextField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "Error file path!..\n");
				}
			}
		});
		button_5.setBounds(292, 44, 56, 22);
		panel_1.add(button_5);

		button_6.setBounds(354, 16, 56, 22);
		panel_1.add(button_6);

		button_7.setBounds(292, 16, 56, 22);
		panel_1.add(button_7);

		JLabel lblDecription = DefaultComponentFactory.getInstance().createLabel(
				"  Decription---------------------------------------------------------------------------------------");
		lblDecription.setFont(new Font("Papyrus", Font.PLAIN, 11));
		lblDecription.setHorizontalAlignment(SwingConstants.LEFT);
		lblDecription.setBounds(0, 0, 414, 14);
		panel_1.add(lblDecription);

		Button button_9 = new Button("Decrypt");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String srcPath = dec_src_textField.getText();
				String resultPath = dec_resultTextField.getText();
				resultPath = "DecryptionResult.txt";
				File srcFile = new File(srcPath);
				File keyFile = new File(KEY_PATH);
				File result = new File(resultPath);
				FileWriter fr;
				try {

					fr = new FileWriter(result);
					dec_resultTextField.setText(resultPath);

					fr.close();
				} catch (Exception e) {
					System.out.println("exiption\n");
				}
				try {

					Scanner sc = new Scanner(srcFile);
					Scanner keySc = new Scanner(keyFile);
					fr = new FileWriter(result);

					String selectedAlgo = "";
					JRadioButton[] rbs = { vigenere_rb, oneTimePad_rb, railFance_rb, rowTrans_rb };

					for (JRadioButton rb : rbs) {
						if (rb.isSelected()) {
							selectedAlgo = rb.getText();
							break;
						}
					}

					switch (selectedAlgo) {
					case "Vigenere": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setVig_key(keyword);

						String CipherText = "";
						while (sc.hasNext()) {
							CipherText += sc.next();
						}
						Decript dec = new Decript();
						String out = dec.VeginereDecryption(key, CipherText);
						fr.write(out);
						break;
					}
					case "OneTimePad": {
						File oneTimeKey = new File("OneTimeKey.txt");
						Scanner oneTimeKeyScanner = new Scanner(oneTimeKey);
						String keySt = oneTimeKeyScanner.next();
						oneTimeKeyScanner.close();
						Key key = new Key();

						String PlainText = "";
						while (sc.hasNext()) {
							PlainText += sc.next();
						}

						key.SET_ONETIMEPAD_KEY(keySt);

						Decript dec = new Decript();
						String out = dec.OneTimePadDecryption(key, PlainText);
						fr.write(out);
						break;
					}
					case "RailFance": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setRailFanceKey(Integer.parseInt(keyword));

						String CipherText = "";
						while (sc.hasNext()) {
							CipherText += sc.next();
						}
						Decript dec = new Decript();
						String out = dec.RailFanceDecryption(key, CipherText);
						fr.write(out);
						break;
					}
					case "RowTransposition": {
						String keyword = keySc.next();
						Key key = new Key();
						key.setRowTranspositionKey(keyword);

						String CipherText = "";
						while (sc.hasNext()) {
							CipherText += sc.next();
						}
						Decript dec = new Decript();
						String out = dec.RowTranspositionDecryption(key, CipherText);
						fr.write(out);
						break;
					}

					}

					fr.close();
					keySc.close();
					sc.close();

				} catch (FileNotFoundException e) {
					log.setText(log.getText() + "File Not Found!..\nPleas check CipherText Path and Key Path\n");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.setText(log.getText() + e.getMessage() + "\n");
					e.printStackTrace();
				}

			}
		});
		button_9.setBounds(292, 72, 118, 22);
		panel_1.add(button_9);

		Button button_17 = new Button("OpenKeyFile");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Open(KEY_PATH);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "ERROR: Key file is not found!..\n");
				}
			}
		});
		button_17.setBounds(134, 72, 118, 22);
		panel_1.add(button_17);

		Button button_18 = new Button("Choose Key");
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TextField tf = new TextField();
				Browse(tf, frame);
				KEY_PATH = tf.getText().toString();
			}
		});
		button_18.setBounds(10, 72, 118, 22);
		panel_1.add(button_18);

		Panel panel_2 = new Panel();
		panel_2.setBackground(SystemColor.inactiveCaptionBorder);
		panel_2.setBounds(10, 222, 414, 100);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblBruteforceAttack = DefaultComponentFactory.getInstance().createLabel(
				"  Attack--------------------------------------------------------------------------------------------");
		lblBruteforceAttack.setFont(new Font("Papyrus", Font.PLAIN, 11));
		lblBruteforceAttack.setHorizontalAlignment(SwingConstants.LEFT);
		lblBruteforceAttack.setBounds(0, 0, 414, 14);
		panel_2.add(lblBruteforceAttack);

		TextField attack_srcTextField = new TextField();
		attack_srcTextField.setText("AttackInput.txt");
		attack_srcTextField.setBounds(10, 17, 276, 22);
		panel_2.add(attack_srcTextField);

		TextField attack_resultTextField = new TextField();
		attack_resultTextField.setText("Results...");
		attack_resultTextField.setBounds(10, 45, 276, 22);
		panel_2.add(attack_resultTextField);

		Button button_12 = new Button("Open");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = attack_resultTextField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "Error file path!..\n");
				}
			}
		});
		button_12.setBounds(292, 45, 56, 22);
		panel_2.add(button_12);

		Button button_13 = new Button("Browse");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(attack_resultTextField, frame);
			}
		});
		button_13.setBounds(354, 45, 56, 22);
		panel_2.add(button_13);

		Button button_10 = new Button("Open");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = attack_srcTextField.getText();
				try {
					Open(path);
				} catch (IllegalArgumentException e) {
					log.setText(log.getText() + "Error file path!..\n");
				}
			}
		});
		button_10.setBounds(292, 17, 56, 22);
		panel_2.add(button_10);

		Button button_11 = new Button("Browse");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Browse(attack_srcTextField, frame);
			}
		});
		button_11.setBounds(354, 17, 56, 22);
		panel_2.add(button_11);

		Button button_14 = new Button("RUN");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				long startTime = System.currentTimeMillis();
				String plainTextPath = enc_srcPathTextField.getText();
				String srcPath = attack_srcTextField.getText();
				String resultPath = attack_resultTextField.getText();
				resultPath = "AttackResult.txt";
				File srcFile = new File(srcPath);
				File result = new File(resultPath);
				File plainTextFile = new File(plainTextPath);
				String PlainText = "";

				FileWriter fr;
				try {

					fr = new FileWriter(result);
					attack_resultTextField.setText(resultPath);

					fr.close();
				} catch (Exception e) {
					System.out.println("exiption\n");
				}
				try {

					Scanner sc = new Scanner(srcFile);
					fr = new FileWriter(result);

					String selectedAlgo = "";
					JRadioButton[] rbs = { vigenere_rb, oneTimePad_rb, railFance_rb, rowTrans_rb };

					for (JRadioButton rb : rbs) {
						if (rb.isSelected()) {
							selectedAlgo = rb.getText();
							break;
						}
					}

					Attack attacker = new Attack();
					
					String PTxt = "";
					String CTxt = "";
					boolean inPlain = true;
					
					while (sc.hasNext()) {
						String next = sc.next();
						if(next.equals("#"))
							inPlain = false;
						else if(inPlain)
							PTxt += next;
						else
							CTxt += next;
					}
					
					switch (selectedAlgo) {
					case "Vigenere": {
						attacker.VisenereAttack(result, PTxt, CTxt);
						break;
					}
					case "OneTimePad": {
						attacker.OneTimePadAttack(result, PTxt, CTxt);
						break;
					}
					case "RailFance": {
						attacker.RailFanceAttack(result, PTxt, CTxt);
						break;
					}
					case "RowTransposition": {
						attacker.RowTranspositionAttack(result, PTxt, CTxt);
						break;
					}

					}

					fr.close();
					sc.close();

				} catch (FileNotFoundException e) {
					log.setText(log.getText() + "File Not Found!..\nPleas check CipherText Path and Key Path\n");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.setText(log.getText() + e.getMessage() + "\n");
					e.printStackTrace();
				}

				long endTime = System.currentTimeMillis();
				log.setText(log.getText() + "Total Attack Time: 	" + (endTime - startTime) + "	Ms.\n");
			}
		});
		button_14.setBounds(292, 73, 118, 22);
		panel_2.add(button_14);

		JLabel lblLog = DefaultComponentFactory.getInstance().createLabel(
				"----------------------------------------------------Log--------------------------------------------------");
		lblLog.setFont(new Font("Papyrus", Font.PLAIN, 11));
		lblLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblLog.setBounds(0, 324, 434, 14);
		frame.getContentPane().add(lblLog);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(430, 10, 288, 431);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		TextArea PlainTxtArea = new TextArea();
		PlainTxtArea.setBounds(0, 66, 288, 114);
		panel_3.add(PlainTxtArea);

		Label label = new Label("Plain Text");
		label.setBounds(10, 48, 62, 22);
		panel_3.add(label);

		TextArea CipherTxtArea = new TextArea();
		CipherTxtArea.setBounds(0, 317, 288, 114);
		panel_3.add(CipherTxtArea);

		Label label_1 = new Label("Cipher Text");
		label_1.setBounds(10, 299, 62, 22);
		panel_3.add(label_1);

		TextField KeyTxtField = new TextField();
		KeyTxtField.setBounds(54, 214, 224, 22);
		panel_3.add(KeyTxtField);

		Label label_2 = new Label("KEY");
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(6, 214, 42, 22);
		panel_3.add(label_2);

		Label TimeLalel = new Label("TIME  909 Ms");
		TimeLalel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		TimeLalel.setForeground(Color.RED);
		TimeLalel.setAlignment(Label.CENTER);
		TimeLalel.setBounds(54, 186, 166, 22);
		panel_3.add(TimeLalel);

		Button button_19 = new Button("Encrypt    \\||/");
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double startTime = System.currentTimeMillis();
				String PlainText = PlainTxtArea.getText();
				String KEY_ = KeyTxtField.getText();

				Scanner keySc = new Scanner(KEY_), sc = new Scanner(PlainText);

				String selectedAlgo = "";
				JRadioButton[] rbs = { vigenere_rb, oneTimePad_rb, railFance_rb, rowTrans_rb };

				for (JRadioButton rb : rbs) {
					if (rb.isSelected()) {
						selectedAlgo = rb.getText();
						break;
					}
				}

				switch (selectedAlgo) {

				case "Vigenere": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setVig_key(keyword);

					String Ptxt = "";
					while (sc.hasNext()) {
						Ptxt += sc.next();
					}
					Encrypt enc = new Encrypt();
					String out = "Vigenere Encryption Result :\n\n\t";
					out += enc.VeginereEncryption(key, Ptxt);
					CipherTxtArea.setText(out);
					break;
				}
				case "OneTimePad": {
					File oneTimeKey = new File("OneTimeKey.txt");
					FileWriter keyFW;
					try {
						keyFW = new FileWriter(oneTimeKey);

						Key key = new Key();

						String Ptxt = "";
						while (sc.hasNext()) {
							Ptxt += sc.next();
						}

						key.setOne_timePadKey(Ptxt);
						keyFW.write(key.getOne_timePadKey());

						keyFW.close();
						Encrypt enc = new Encrypt();
						String out = "OneTimePad Encryption Result :\n\n\t";
						out += enc.OneTimePadEncryption(key, Ptxt);
						CipherTxtArea.setText(out);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				case "RailFance": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setRailFanceKey(Integer.parseInt(keyword));

					String Ptxt = "";
					while (sc.hasNext()) {
						Ptxt += sc.next();
					}
					Encrypt enc = new Encrypt();
					String out = "RailFance Encryption Result :\n\n\t" + enc.RailFanceEncryption(key, Ptxt);
					CipherTxtArea.setText(out);

					break;
				}
				case "RowTransposition": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setRowTranspositionKey(keyword);

					String Ptxt = "";
					while (sc.hasNext()) {
						Ptxt += sc.next();
					}
					Encrypt enc = new Encrypt();
					String out = "RowTransposition Encryption Result :\n\n\t"
							+ enc.RowTranspositionEncryption(key, Ptxt);
					CipherTxtArea.setText(out);

					break;
				}

				}

				keySc.close();
				sc.close();

				double time = System.currentTimeMillis() - startTime;
				TimeLalel.setText("Time  " + time + " ms.");

			}

		});
		button_19.setBounds(178, 261, 100, 22);
		panel_3.add(button_19);

		Button button_20 = new Button("Decrypt    /||\\");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double startTime = System.currentTimeMillis();
				String KEY_ = KeyTxtField.getText();
				String CipherTXT = CipherTxtArea.getText();

				char[] ca = CipherTXT.toCharArray();

				for (int i = 0; i < ca.length; i++) {
					if (ca[i] == ':') {
						CipherTXT = CipherTXT.substring(i + 1);
						break;
					}
				}

				Scanner keySc = new Scanner(KEY_), sc = new Scanner(CipherTXT);

				String selectedAlgo = "";
				JRadioButton[] rbs = { vigenere_rb, oneTimePad_rb, railFance_rb, rowTrans_rb };

				for (JRadioButton rb : rbs) {
					if (rb.isSelected()) {
						selectedAlgo = rb.getText();
						break;
					}
				}

				switch (selectedAlgo) {
				case "Vigenere": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setVig_key(keyword);

					String Ctxt = "";
					while (sc.hasNext()) {
						Ctxt += sc.next();
					}
					Decript dec = new Decript();
					String out = dec.VeginereDecryption(key, Ctxt);
					PlainTxtArea.setText(out);
					break;
				}
				case "OneTimePad": {
					File oneTimeKey = new File("OneTimeKey.txt");
					
					try {
						Scanner oneTimekeySc = new Scanner(oneTimeKey);

						Key key = new Key();
						key.SET_ONETIMEPAD_KEY(oneTimekeySc.next());
						String Ctxt = "";
						while (sc.hasNext()) {
							Ctxt += sc.next();
						}

						Decript dec = new Decript();
						String out = dec.OneTimePadDecryption(key, Ctxt);
						PlainTxtArea.setText(out);
						
						oneTimekeySc.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					break;
				}
				case "RailFance": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setRailFanceKey(Integer.parseInt(keyword));

					String Ctxt = "";
					while (sc.hasNext()) {
						Ctxt += sc.next();
					}
					Decript dec = new Decript();
					String out = dec.RailFanceDecryption(key, Ctxt);
					PlainTxtArea.setText(out);

					break;
				}
				case "RowTransposition": {
					String keyword = keySc.next();
					Key key = new Key();
					key.setRowTranspositionKey(keyword);

					String Ctxt = "";
					while (sc.hasNext()) {
						Ctxt += sc.next();
					}
					Decript dec = new Decript();
					String out = dec.RowTranspositionDecryption(key, Ctxt);
					PlainTxtArea.setText(out);

					break;
				}
				}

				keySc.close();
				sc.close();

				double time = System.currentTimeMillis() - startTime;
				TimeLalel.setText("Time  " + time + " ms.");

			}
		});
		button_20.setBounds(10, 261, 100, 22);
		panel_3.add(button_20);

		JLabel lblCryptograpy = new JLabel("CRYPTOGRAPY");
		lblCryptograpy.setForeground(Color.GREEN);
		lblCryptograpy.setFont(new Font("Viner Hand ITC", Font.BOLD | Font.ITALIC, 16));
		lblCryptograpy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCryptograpy.setBounds(54, 11, 154, 30);
		panel_3.add(lblCryptograpy);

		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { frame.getContentPane() }));
	}

	public void Browse(TextField tf, Component comp) {
		JFileChooser c = new JFileChooser();
		String currText = tf.getText();
		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(comp);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			tf.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
			// dir.setText(c.getCurrentDirectory().toString());
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
			tf.setText(currText);
			// dir.setText("");
		}
	}

	public void Open(String filePath) throws IllegalArgumentException {
		File f = new File(filePath);
		Desktop dt = Desktop.getDesktop();
		try {
			dt.open(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
