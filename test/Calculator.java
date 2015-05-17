import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public int calcSum(String path){
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		return lineReadTemplate(path, sumCallback, 0);
		

	}
	public Object calcMultiply(String path) {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(path, sumCallback, 1);
	}
	
	public Object concantenate(String path) {
		LineCallback<String> concatenateCallback = new LineCallback<String>() {
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(path, concatenateCallback, "");
	}

	private <T> T lineReadTemplate(String path, LineCallback<T> sumCallback, T result) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				result = sumCallback.doSomethingWithLine(line, result);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return result;
	}



}
