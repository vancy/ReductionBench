package test.others;

public class ExceptionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int t = 0;
		int i = 9;
		try {
			i = t/0;
		} 
		catch (Exception e) {
			if (i==9) {
				throw new RuntimeException();
			}
		}
		finally {
			t = 88;
		}
		System.out.println("i="+i+" t="+t);
	}

}
