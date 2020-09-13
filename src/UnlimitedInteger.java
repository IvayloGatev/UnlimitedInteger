public class UnlimitedInteger {
	private String value;

	// A constructor which checks if the given number is in the correct format and
	// assigns it to private variable
	public UnlimitedInteger(String value) {
		if (!value.matches("[+-]?\\d+")) {
			throw new NumberFormatException();
		}
		this.value = value;
	}

	// returns the value of the UnlimitedInteger object
	public String toString() {
		return value;
	}

	public UnlimitedInteger plus(UnlimitedInteger op) {
		String op1 = this.toString();
		String op2 = op.toString();
		char op1Sign = getSign(op1);
		char op2Sign = getSign(op2);
		String result = "";

		if (op1.charAt(0) == '+' || op1.charAt(0) == '-') {
			op1 = omitSign(op1);
		}
		if (op2.charAt(0) == '+' || op2.charAt(0) == '-') {
			op2 = omitSign(op2);
		}

		if (op1.length() < op2.length()) {

			op1 = getLeadingZeroes(op2.length() - op1.length()) + op1;
		} else if (op2.length() < op1.length()) {
			op2 = getLeadingZeroes(op1.length() - op2.length()) + op2;
		}

		if (op1Sign == op2Sign) {
			result = addition(op1, op2);

			if (op1Sign == '-') {
				result = op1Sign + result;
			}

		} else if (isOp1Bigger(op1, op2)) {
			result = subtraction(op1, op2);

			if (op1Sign == '-') {
				result = op1Sign + result;
			}
		} else {
			result = subtraction(op2, op1);

			if (op2Sign == '-') {
				result = op2Sign + result;
			}
		}

		return new UnlimitedInteger(result);
	}

	public UnlimitedInteger times(UnlimitedInteger op) {
		String op1 = this.toString();
		String op2 = op.toString();
		char op1Sign = getSign(op1);
		char op2Sign = getSign(op2);
		String result = "";

		if (op1.charAt(0) == '+' || op1.charAt(0) == '-') {
			op1 = omitSign(op1);
		}
		if (op2.charAt(0) == '+' || op2.charAt(0) == '-') {
			op2 = omitSign(op2);
		}
		result = multiplication(op1, op2);
		if (op1Sign != op2Sign) {
			result = "-" + result;
		}

		return new UnlimitedInteger(result);
	}

	// A method which adds two numbers, represented as strings, digit by digit
	private static String addition(String op1, String op2) {
		String result = "";
		int carry = 0;
		int operation = 0;

		// Adds the numbers digit by digit. Assigns a carry bit if necessary
		for (int i = op1.length() - 1; i >= 0; i--) {
			int firstDigit = op1.charAt(i) - '0';
			int secondDigit = op2.charAt(i) - '0';

			operation = firstDigit + secondDigit + carry;
			carry = operation / 10;
			operation %= 10;
			result = operation + result;
		}

		// Appends the carry bit to the result if it is different from 0
		if (carry != 0) {
			result = carry + result;
		}

		return result;
	}

	// A method which subtracts two numbers, represented as strings, digit by digit
	private static String subtraction(String op1, String op2) {
		String result = "";
		int borrowed = 0;
		int operation = 0;

		// Subtracts the numbers digit by digit. Borrows a bit if necessary
		for (int i = op1.length() - 1; i >= 0; i--) {
			int firstDigit = op1.charAt(i) - '0';
			int secondDigit = op2.charAt(i) - '0';

			operation = firstDigit - secondDigit - borrowed;
			borrowed = 0;

			if (operation < 0) {
				borrowed++;
				operation += 10;
			}

			result = operation + result;
		}

		result = omitLeadingZeroes(result);
		return result;
	}

	// A method which multiplies two numbers, represented as strings, digit by digit
	private static String multiplication(String op1, String op2) {
		String intermediateResult = "";
		String result = "";
		int carry = 0;
		int operation = 0;

		// Multiplies the first number by every digit of the second number. Assigns a
		// carry bit if necessary
		for (int i = 0; i < op2.length(); i++) {
			for (int j = op1.length() - 1; j >= 0; j--) {
				int firstDigit = op1.charAt(j) - '0';
				int secondDigit = op2.charAt(i) - '0';

				operation = (carry + (firstDigit * secondDigit));
				carry = operation / 10;
				operation %= 10;
				intermediateResult = operation + intermediateResult;
			}

			if (carry != 0) {
				intermediateResult = carry + intermediateResult;
			}

			// Adds the current intermediate result to the sum of the previous ones.
			if (i == 0) {
				result = intermediateResult;
			} else {
				intermediateResult = getLeadingZeroes(result.length() - intermediateResult.length() + 1)
						+ intermediateResult;
				result += 0;
				result = addition(result, intermediateResult.toString());
			}
			intermediateResult = "";
		}

		result = omitLeadingZeroes(result);
		return result;
	}

	// A method which returns the sign of the current operator
	private static char getSign(String op) {
		if (op.charAt(0) == '-') {
			return '-';
		}

		return '+';

	}

	// A method which returns the operator, omitting its sign
	private static String omitSign(String op) {
		return op.substring(1);
	}

	// A method which returns the number of leading zeroes, represented as a string
	private static String getLeadingZeroes(int count) {
		String result = "";

		for (int i = 0; i < count; i++) {
			result += "0";
		}

		return result;
	}

	// A method which checks if operator 1 is bigger than operator 2
	private static boolean isOp1Bigger(String op1, String op2) {
		// Compares the numbers digit by digit
		for (int i = 0; i < op1.length(); i++) {
			if (op1.charAt(i) < op2.charAt(i)) {
				return false;
			} else if (op1.charAt(i) > op2.charAt(i)) {
				return true;
			}
		}

		return true;
	}

	// A method which omits the leading zeroes of an operator
	private static String omitLeadingZeroes(String op) {

		// Return the substring which starts with a number, different from 0
		for (int i = 0; i < op.length(); i++) {
			if (op.charAt(i) != '0') {
				return op.substring(i);
			}
		}

		return "0";
	}
}
