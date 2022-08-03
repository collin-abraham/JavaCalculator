import java.util.ArrayList;
import java.util.HashMap;

/*
* Name: Calculator.java
* Date: July 2022
* Coder: Collin Little
* Description: Calculator Object
*/

public class Calculator {
	
	//data members
	private String operand; 
	private String operator; 
	private boolean lastPressedWasOperator;
	private boolean decimalPressed;
	private ArrayList<String> list; 
	private boolean isBinary;
	private boolean isDecimal;
	private boolean isHex;
	private boolean isNegative; 
	
	// default constructor 
	public Calculator () {
		this.operand = "0.0"; 
		this.operator = "";
		this.decimalPressed = false;
		this.list = new ArrayList<String>();
		this.isDecimal = true;
		
	}
	
	// member access functions 
	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public boolean getLastPressedState() {
		return lastPressedWasOperator;
	}

	public boolean isDecimalPressed() {
		return decimalPressed;
	}

	public void setDecimalPressed(boolean decimalPressed) {
		this.decimalPressed = decimalPressed;
	}
	
	public void setIsDecimal(boolean state) {
		this.isDecimal = state;
	}
	
	public boolean getIsDecimal() {
		return isDecimal;
	}
	
	public void setIsBinary(boolean state) {
		this.isBinary = state;
	}
	
	public boolean getIsBinary() {
		return isBinary;
	}
	
	public void setIsHex(boolean state) {
		this.isHex = state;
	}
	
	public boolean getIsHex() {
		return isHex;
	}
	
	public void setIsNegative(boolean state) {
		this.isNegative = state;
	}
	
	public boolean getIsNegative() {
		return isNegative;
	}
	
	// cleans up the object to be ready for further calculations
	public void clear() {
		this.operand = "0.0"; 
		this.operator = "";
		this.decimalPressed = false;
		this.isNegative = false;
		this.isDecimal = true;
		this.isHex = false;
		this.isBinary = false;
		list.clear();
	}
	
	// remove the most recent entry from the internal arraylist 
	// if there's nothing in the arraylist, strip the last character off the operand string
	public void backspace() {
		if (list.size() > 0)
		{
			list.remove(list.size()-1);
		}
		else
		{
			if(operand != "0.0")
				setOperand(operand.substring(0,operand.length()-1));
		}
	}
	
	
	// returns the current value into a percentage by dividing by 100.0 
	public String findPercentage(String value) {
		double num = (Double.parseDouble(value)) / 100.0;
		String returnString = Double.toString(num);
		return returnString; 
		
	}
	
	// accesses the internal operand member
	// if the func is passed true it adds a hyphen in front of the operand and returns a new string
	// if the func is passed false it strips off the hyphen from the operand and returns the new string 
	public String togglePlusMinus() {
		
		if (!isNegative)
		{
			isNegative = true; 
			operand = "-" + operand;
			return operand;
		}
		else
		{
			isNegative = false;
			operand = operand.substring(1,operand.length());
			return operand; 
		}
	}
	
	// returns the value (after parsing) multiplied by itself & sets the internal operand to the new value 
	public String findSquared(String value) {
		double num = (Double.parseDouble(value)) * (Double.parseDouble(value));
		String returnString = Double.toString(num);
		this.operand = returnString;
		return returnString;
	}
	
	// returns the square root of the current value & sets the internal operand to new value 
	public String findSquareRoot(String value) {
		double num = Math.sqrt((Double.parseDouble(value)));
		String returnString = Double.toString(num);
		this.operand = returnString;
		return returnString;
	}
	
	// constructs the new operand based on the value passed
	// if operand is zero it will += whatever is passed otherwise set it to what was passed
	// we also state that the last value was not an operator, so we keep building operands correctly 
	public void buildOperand(String value) {
		if (!operand.equals("0.0"))
		{
			operand += value; 
		}
		if (operand.equals("0.0"))
		{
			operand = value; 
		}
		
		this.lastPressedWasOperator = false;
		
	}
	
	// constructs an expression operator based on value passed
	// sets the internal operator to that value and adds to the list 
	// resets the internal operand/operator  as well as confirms last value was an operator to prevent something strange like 8+++++3 
	
	public void buildExpression(String value) {
				
		//push back operand into array list
		list.add(operand);
			
			//decide which operation was pressed, set the operator to that value and push it into the list
			if (value.equals("+"))
			{
				this.operator = "+";
				list.add(operator);
			}
			else if (value.equals("-"))
			{
				this.operator = "-";
				list.add(operator);
			}
			else if (value.equals("/"))
			{
				this.operator = "/";
				list.add(operator);
			}
			else if (value.equals("*"))
			{
				this.operator = "*";
				list.add(operator);
			}
			
			//System.out.println("Pushed -> " + operator);
			
			//clear fields
			this.operand = "0.0"; 
			this.operator = "";
			this.lastPressedWasOperator = true;
			this.decimalPressed = false;
			
	}
	
	// main algorithm to work through expression evaluation 
	// deals with multiplication/division first, then addition/subtraction 
	public double calculate() {

		// add current operand to list
		list.add(operand);
		
		//loop through the list checking for multiplication and division operators first
		//if found apply the operator to the index before and after i 
		double beforeIndex, afterIndex, resultAfterOperator = 0; 
		for (int i = 0; i <  list.size(); ++i)
		{
			if (list.get(i).equals("*") || list.get(i).equals("/")) 
			{
				beforeIndex = Double.parseDouble(list.get(i-1));
				afterIndex = Double.parseDouble(list.get(i+1));
				
				// check for 0 mult/div
				if (beforeIndex == 0.0 || afterIndex == 0.0)
					resultAfterOperator = 0.0;
				else
				{
					if (list.get(i).equals("*"))
					{
						resultAfterOperator = beforeIndex * afterIndex;
					}
					else if (list.get(i).equals("/"))
					{
						resultAfterOperator = beforeIndex / afterIndex;
					}
				}
				//remove previous records and insert the multiplied or divided result back into the arraylist
				list.remove(i+1);
				list.remove(i);
				list.remove(i-1);
				
				list.add(i-1, Double.toString(resultAfterOperator));				
			}
		}//end for
		
		//loop through the list again checking for addition or subtraction operators 
		for (int i = 0; i <  list.size(); ++i) 
		{
			if (list.get(i).equals("+") || list.get(i).equals("-"))
			{
				beforeIndex = Double.parseDouble(list.get(i-1));
				afterIndex = Double.parseDouble(list.get(i+1));
				
				if (list.get(i).equals("+"))
				{
					resultAfterOperator = beforeIndex + afterIndex;
				}
				else if (list.get(i).equals("-"))
				{
					resultAfterOperator = beforeIndex - afterIndex;
				}
				
				//remove previous records and insert the added or subtracted result back into the arraylist
				list.remove(i+1);
				list.remove(i);
				list.remove(i-1);
				
				list.add(i-1, Double.toString(resultAfterOperator));	
			}
		}//end for
		
		//return the final remaining index of the ArrayList
		
		return Double.parseDouble(list.get(0));
		
	}//end calculate()
	
	
	/********************************************************
	 * CONVERSION FUNCTIONS
	 * ******************************************************
	 */
	
	// have a look at the string..  if we encounter something that isn't 1 or 0 we return false 
	// the user can enter a binary number from the get go, so we check to see if it is 
	public boolean checkStringForBinary(String value) {
		boolean state = true;
		
		// strip off 0b if it exists 
		if (value.charAt(0) == '0' && value.charAt(1) == 'b')
			value = value.substring(0,2);
	
		// if we encounter something that isn't 1 or 0, break the loop and return the false state 
		for (int i = 0; i < value.length(); i++)
		{
			if (value.charAt(i) != '1' && value.charAt(i) != '0')
			{
				state = false;
				break;
			}				
		}

		return state;
	}
	
	// converts the current value to a hex from decimal or binary 
	public String convertHex (String value) {
		
		// is the current internal state a decimal? dec -> hex 
		if (this.isDecimal) 
		{
			double valueParsed = Double.parseDouble(value);
			
		    String digits = "0123456789ABCDEF";
		    double base = 16.0;   
		    String hex = "";
		    
		    if (valueParsed <= 0) 
	    	{
		    	return "0";
	    	}
		    
		    while (valueParsed > 0) 
		    {
		        double digit = valueParsed % base;       
		        
		        if (digit >= 1)
		        {
		        	hex = digits.charAt((int) digit) + hex;
		        }
		        else 
		        {
		        	break;
		        }
		        valueParsed = valueParsed / base;
		    }
		    
		    return "0x" + hex;
		}
		
		// is the current state binary? bin -> hex
		// we convert to a decimal, set internal state to decimal instead of binary
		// then recall convertHex as a decimal instead 
		else if(this.isBinary)
		{
			String binFromDec = convertDec(value);
			this.setIsBinary(false);
			this.setIsDecimal(true);
			return convertHex(binFromDec);
		}
		
		else
		{
			return value;
		}
	}
	
	// converts the current value to a decimal form hex or bin 
	public String convertDec (String value) {
		
		// if the current state is binary OR the user enters a binary value manually such as 10110011 (calls checkStringForBinary just in case)
		if (this.getIsBinary() || checkStringForBinary(value))
		{
			
			// strip off 0b from binary
			if (value.charAt(0) == '0' && value.charAt(1) == 'b')
				value = value.substring(2,value.length());
			
			int valueParsed = Integer.parseInt(value);
			
			int decimalNumber = 0;
			int i = 0;
			
			while (valueParsed != 0) 
			{
				decimalNumber += (Math.pow(2, i++) * (valueParsed % 10));
				valueParsed /= 10;
			}
			
			return Integer.toString(decimalNumber); 
			
		}
		
		// current state is hex, so we do a hex to dec conversion 
		else if (this.getIsHex())
		{
			// strip off 0x
			if (value.charAt(0) == '0' && value.charAt(1) == 'x')
				value = value.substring(2,value.length());
			
			String hexString = "0123456789ABCDEF";
			value = value.toUpperCase();			
			int toReturn = 0;
			
			// loop through the string 
			// add the hex value multiplied by 16 to our number to return
			for(int i = 0; i < value.length(); i++)
			{
				char currentChar = value.charAt(i);
				int num = hexString.indexOf(currentChar);
				toReturn = 16 * toReturn + num;
			}
			
			return Integer.toString(toReturn);
		}
		else 
			return value;
	}
	
	// convert value to binary from decimal or hex 
	public String convertBin (String value) {
		
		// current state is decimal?
		if (this.getIsDecimal()) 
		{
			int valueParsed = Integer.parseInt(value);
			
			int binaryResult = 0;
			int multiplier = 1;
			int base = 2;
			
			// grab the remainder of the value / base (2)
			// reset the value to its current value / base (2)
			// end result is the remainder + value multiplied by where we are in the decimal place column (start at 1's.. 10's... 100's... and so on)
			// increment place value of decimal column
			while (valueParsed > 0)
			{
				int leftover = (int) (valueParsed % base);
				valueParsed = valueParsed / base; 
				binaryResult = binaryResult + leftover * multiplier;
				multiplier = multiplier * 10; 
			}
			
			
			return "0b" + Integer.toString(binaryResult);
		}
		
		// current state is hex? 
		else if(this.getIsHex())
		{
			// strip off 0x
			value = value.substring(2,value.length());
			
			// store converted binary sequence & build a hashmap to store all the hex/bin combos
			String bin = "";
			HashMap<Character,String> combos = new HashMap<Character,String>();
			combos.put('0', "0000");
			combos.put('1', "0001");
			combos.put('2', "0010");
			combos.put('3', "0011");
			combos.put('4', "0100");
			combos.put('5', "0101");
			combos.put('6', "0110");
			combos.put('7', "0111");
			combos.put('8', "1000");
			combos.put('9', "1001");
			combos.put('A', "1010");
			combos.put('B', "1011");
			combos.put('C', "1100");
			combos.put('D', "1101");
			combos.put('E', "1110");
			combos.put('F', "1111");
			
			int i;
			char ch;
			
			// loop through value (which is hex) to extract each character, find it in the hashmap
			// add to the bin string the corresponding hex value
			for (i = 0; i < value.length(); i++)
			{
				ch = value.charAt(i);
				if (combos.containsKey(ch))
					bin += combos.get(ch);					
			}
			
			return "0b" + bin;
		}
		else 
			return "";

		
	}
	
}//end class
