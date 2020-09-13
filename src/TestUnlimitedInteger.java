public class TestUnlimitedInteger {
	public static void main(String[] args) {
		UnlimitedInteger a= new UnlimitedInteger(Terminal.getString("Please, input a:"));
		UnlimitedInteger b= new UnlimitedInteger(Terminal.getString("Please, input b:"));
		UnlimitedInteger c= new UnlimitedInteger(Terminal.getString("Please, input c:"));
		UnlimitedInteger x= new UnlimitedInteger(Terminal.getString("Please, input x:"));
		
		Terminal.put("Result: "+a.times(x.times(x)).plus(b.times(x)).plus(c));
	}

}
