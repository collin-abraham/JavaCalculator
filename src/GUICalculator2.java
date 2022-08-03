/*
 * Name: GUICalculator2.java
 * Date: July 2022
 * Coder: Collin Little
 * Description: GUI Object to drive Calculator.java
 */


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.SwingConstants;

public class GUICalculator2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnConvert;
	private JButton btnExit;
	private JButton btnHex;
	private JButton btnBin;
	private JButton btnDec;
	private JButton btnClear;
	private JButton btnBackspace;
	private JButton btnPercentage;
	private JButton btnPlusMinus;
	private JButton btnSquared;
	private JButton btnSqrt;
	private JButton btnEmpty1;
	private JButton btnDivide;
	private JButton btnSeven;
	private JButton btnEight;
	private JButton btnNine;
	private JButton btnX;
	private JButton btnFour;
	private JButton btnFive;
	private JButton btnSix;
	private JButton btnSubtract;
	private JButton BtnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnAddition;
	private JButton btnEmpty2;
	private JButton btnZero;
	private JButton btnDecimal;
	private JButton btnCalculate;

	// launch the app
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUICalculator2 frame = new GUICalculator2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// takes an arraylist of strings and returns a built string 
	public String buildTextField(ArrayList<String> values)
	{
		StringBuilder sb = new StringBuilder();
		for (String x : values)
		{
			sb.append(x);
			sb.append("");
		}
		return sb.toString();
	}
	
	// returns true of the string provided can be converted into a Double (numeric)
	public boolean isNumeric(String inputString) 
	{
	    if (inputString == null) 
	    {
	        return false;
	    }
	    try 
	    {
	        double newDouble = Double.parseDouble(inputString);
	    } 
	    catch (NumberFormatException nfe) 
	    {
	        return false;
	    }
	    return true;
	}
	
	//  GUI object ++
	public GUICalculator2() {
		Calculator calc = new Calculator();
		ArrayList<String> txtFldList = new ArrayList<String>();
	
		setResizable(false);
		setBackground(Color.white);
		setFont(new Font("SansSerif", Font.PLAIN, 22));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 365);
		
		// create a new menu bar to contain File, Convert 
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// menu -> file
		mnFile = new JMenu("File");
		mnFile.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnFile);
		
		// menu -> file -> exit 
		btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnExit))
				{
					System.exit(0);
				}
			}
		});
		mnFile.add(btnExit);
		
		// menu -> convert 
		mnConvert = new JMenu("Convert");
		mnConvert.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnConvert);
		
		// menu -> convert -> hex
		// convert textField to hex and set internal state to hex 
		btnHex = new JButton("Hex");
		btnHex.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev)
			{
				if(ev.getSource().equals(btnHex) && !textField.getText().isEmpty())
				{
					// if they enter a decimal, convert it over to a double
					// then strip it off the decimal by converted to int
					// then convert back to a string and push it further along 
					if (!calc.isDecimalPressed())
						textField.setText(calc.convertHex(textField.getText()));
					else
					{
						double temp = Double.parseDouble(textField.getText());
						String toSet = Integer.toString((int)temp);
						textField.setText(calc.convertHex(toSet));
						calc.setDecimalPressed(false);
					}

						calc.setIsHex(true);
						
						if (calc.getIsBinary() == true)
							calc.setIsBinary(false);
						
						if (calc.getIsDecimal() == true)
							calc.setIsDecimal(false);
	
				}
			}
		});
		
		mnConvert.add(btnHex);
		
		// menu -> convert -> bin
		// convert textField to bin and set internal state to bin 
		btnBin = new JButton("Bin");
		btnBin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev)
			{
				if(ev.getSource().equals(btnBin) && !textField.getText().isEmpty())
				{
		
					// if they enter a decimal, convert it over to a double
					// then strip it off the decimal by converted to int
					// then convert back to a string and push it further along 
					if (!calc.isDecimalPressed())
						textField.setText(calc.convertHex(textField.getText()));
					else
					{
						double temp = Double.parseDouble(textField.getText());
						String toSet = Integer.toString((int)temp);
						textField.setText(calc.convertBin(toSet));
						calc.setDecimalPressed(false);
					}
					
					calc.setIsBinary(true);
					
					if (calc.getIsDecimal() == true)
						calc.setIsDecimal(false);
					
					if (calc.getIsHex() == true)
						calc.setIsHex(false);
				}
			}
		});
		mnConvert.add(btnBin);
		
		// menu -> convert -> dec
		// convert textField to dec and set internal state to dec 
		// this also allows something converted into dec from bin/hex to then be used in calculate()
		btnDec = new JButton("Dec");
		btnDec.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent ev)
		{
			if(ev.getSource().equals(btnDec) && !textField.getText().isEmpty())
			{
				String convertedtext = calc.convertDec(textField.getText());
				
				calc.setOperand(convertedtext);
				txtFldList.clear();
				txtFldList.add(convertedtext);
				textField.setText(buildTextField(txtFldList));
				
				calc.setIsDecimal(true);
				
				if (calc.getIsBinary() == true)
					calc.setIsBinary(false);
				
				if (calc.getIsHex() == true)
					calc.setIsHex(false);
			}
		}
		});
		mnConvert.add(btnDec);
		
		
		// new JPanel to contain the text field where our operators/operands will sit 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// contentPane -> textField
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		textField.setBorder(border);
		
		// new JPanel to contain the JButtons which will serve as functionality to change the textField around 
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 4, 5, 5));
		
		//  clear button - wipes the internal arraylist and textField
		btnClear = new JButton("C");
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {		
				if(ev.getSource().equals(btnClear))
				{
					calc.clear();
					txtFldList.clear();
					textField.setText("");
				}
			
			}
		});
		panel.add(btnClear);
		
		
		// backspace button reduces the string in textField by 1 and internal arraylist by 1 
		btnBackspace = new JButton("<-");
		btnBackspace.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				if (ev.getSource().equals(btnBackspace))
				{
					if (txtFldList.size() > 0)
					{
						txtFldList.remove(txtFldList.size()-1);
						
						calc.backspace();
					}
					
					textField.setText(buildTextField(txtFldList));
				}
			
			}
		});
		panel.add(btnBackspace);
		
		// percentage button 
		btnPercentage = new JButton("%");
		btnPercentage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {	
				
				if (ev.getSource().equals(btnPercentage))
				{
					String result = calc.findPercentage(textField.getText());
					
					txtFldList.clear();
					txtFldList.add(result);
					
				
					textField.setText(buildTextField(txtFldList));
				}
			}
		});
	
		
		panel.add(btnPercentage);
		
		
		// change negative/positive state 
		btnPlusMinus = new JButton("+/-");
		btnPlusMinus.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				if (ev.getSource().equals(btnPlusMinus))
				{
					//obtain the new value for text field after altering pos/neg state
					// build a string array of the new string, to be used with arraylist later 
					String newTxtFldValue = calc.togglePlusMinus();	  				
					String[] strArr = newTxtFldValue.split("");
					int loopcount = strArr.length;
					
					// if we are in a negative state 
					if(!calc.getIsNegative())
					{
						while(!txtFldList.isEmpty() && loopcount != -1)	// -1 so that it goes 1 extra iteration to account for the extra hyphen
						{
							if (txtFldList.get(txtFldList.size()-1) != "+"
									|| txtFldList.get(txtFldList.size()-1) != "/" 
									|| txtFldList.get(txtFldList.size()-1) != "*"
									|| (txtFldList.get(txtFldList.size()-1) != "-" && isNumeric(txtFldList.get(txtFldList.size()-2)))
							)
								{
									txtFldList.remove(txtFldList.size()-1);
									--loopcount;
								}
						}
					}
					
					// we are in a positive state 
					else
					{
						while(!txtFldList.isEmpty() && loopcount != 1)	
						{
							if (txtFldList.get(txtFldList.size()-1) != "+"
									|| txtFldList.get(txtFldList.size()-1) != "/" 
									|| txtFldList.get(txtFldList.size()-1) != "*"
									|| (txtFldList.get(txtFldList.size()-1) != "-" && isNumeric(txtFldList.get(txtFldList.size()-2)))
							)
								{
									txtFldList.remove(txtFldList.size()-1);
									--loopcount;
								}
						}
					}
					
					// add the values from strArr to the text field 
					for(int i = 0; i < strArr.length; ++i)
					{
						txtFldList.add(strArr[i]);
					}
					
					textField.setText(buildTextField(txtFldList));
				}
			}
		});
		panel.add(btnPlusMinus);
		
		
		// exponential button.. push current operand to the power of two 
		btnSquared = new JButton("x^2");
		btnSquared.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnSquared))
				{
					String result = calc.findSquared(textField.getText());
					
					txtFldList.clear();
					txtFldList.add(result);
					
				
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnSquared);
		
		// square root button 
		btnSqrt = new JButton("SQT");
		btnSqrt.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnSqrt))
				{
					String result = calc.findSquareRoot(textField.getText());
					
					txtFldList.clear();
					txtFldList.add(result);
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnSqrt);
		
		// dummy place holder button, does nothing but satisfies the layout of the calculator 
		btnEmpty1 = new JButton("");
		panel.add(btnEmpty1);
		
		// division operator  
		btnDivide = new JButton("/");
		btnDivide.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnDivide) && calc.getLastPressedState() == false)
				{
					calc.buildExpression("/");
					txtFldList.add("/");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnDivide);
		
		// "7" operand
		btnSeven = new JButton("7");
		btnSeven.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnSeven))
				{
					calc.buildOperand("7");
					txtFldList.add("7");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnSeven);
		
		// "8" operand
		btnEight = new JButton("8");
		btnEight.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnEight))
				{
					calc.buildOperand("8");
					txtFldList.add("8");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnEight);
		
		// "9" operand
		btnNine = new JButton("9");
		btnNine.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnNine))
				{
					calc.buildOperand("9");
					txtFldList.add("9");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnNine);
		
		// multiplication operator 
		btnX = new JButton("X");
		btnX.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnX) && calc.getLastPressedState() == false)
				{
					calc.buildExpression("*");
					txtFldList.add("*");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnX);
		
		// "4" operand 
		btnFour = new JButton("4");
		btnFour.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnFour))
				{
					calc.buildOperand("4");
					txtFldList.add("4");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnFour);
		
		// "5" operand 
		btnFive = new JButton("5");
		btnFive.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnFive))
				{
					calc.buildOperand("5");
					txtFldList.add("5");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnFive);
		
		// "6" operand 
		btnSix = new JButton("6");
		btnSix.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnSix))
				{
					calc.buildOperand("6");
					txtFldList.add("6");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnSix);
		
		// subtraction operator 
		btnSubtract = new JButton("-");
		btnSubtract.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnSubtract) && calc.getLastPressedState() == false)
				{
					calc.buildExpression("-");
					txtFldList.add("-");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnSubtract);
		
		// "1" operand
		BtnOne = new JButton("1");
		BtnOne.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(BtnOne))
				{
					calc.buildOperand("1");
					txtFldList.add("1");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(BtnOne);
		
		// "2" operand button
		btnTwo = new JButton("2");
		btnTwo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnTwo))
				{
					calc.buildOperand("2");
					txtFldList.add("2");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnTwo);
		
		// "3" operand button
		btnThree = new JButton("3");
		btnThree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnThree))
				{
					calc.buildOperand("3");
					txtFldList.add("3");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnThree);
		
		// addition operator 
		btnAddition = new JButton("+");
		btnAddition.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnAddition) && calc.getLastPressedState() == false)
				{
					
					if(calc.getIsBinary())
					{
						txtFldList.add(calc.convertDec(textField.getText()));
						calc.setIsBinary(false);
						calc.setIsDecimal(true);
					}
					else if(calc.getIsHex())
					{
						txtFldList.add(calc.convertDec(textField.getText()));
						calc.setIsHex(false);
						calc.setIsDecimal(true);
					}
					
					calc.buildExpression("+");
					txtFldList.add("+");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnAddition);
		
		//dummy button
		btnEmpty2 = new JButton("");
		panel.add(btnEmpty2);
		
		// "0" operand 
		btnZero = new JButton("0");
		btnZero.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnZero))
				{
					calc.buildOperand("0");
					txtFldList.add("0");
					
					textField.setText(buildTextField(txtFldList));
				}
								
			}
		});
		panel.add(btnZero);
		
		// "." operand 
		btnDecimal = new JButton(".");
		btnDecimal.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnDecimal) && !calc.isDecimalPressed())
				{
					calc.buildOperand(".");
					txtFldList.add(".");
					
					textField.setText(buildTextField(txtFldList));
					
					calc.setDecimalPressed(true);
				}
								
			}
		});
		panel.add(btnDecimal);
		
		// equals button, drives calculate() function 
		btnCalculate = new JButton("=");
		btnCalculate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {				
				
				if (ev.getSource().equals(btnCalculate))
				{
					String value = Double.toString(calc.calculate());
					txtFldList.clear();
					txtFldList.add(value);
					//calc.clear();
					calc.buildOperand(value);	
					calc.buildExpression(value);
					
					textField.setText(buildTextField(txtFldList));
					
					//System.out.println(txtFldList.toString());
				}
								
			}
		});
		panel.add(btnCalculate);
	}

}
