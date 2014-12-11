package comandlinetool;

import java.io.IOException;

public class DBLS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please select a function:");
		System.out.println("1. List fles");
		System.out.println("2. Upload");
		System.out.println("3. Remove");
		System.out.println("4. Download");
		System.out.println("5. Return");
		int a;
		try {
			a = System.in.read();
			if(a == '1'){
				System.out.println("Please select a file:");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
