import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class CalcTest {

	@Test
	public void sumOfNumbers() throws IOException {
		Calculator calculater = new Calculator();
		int sum = calculater.calcSum(getClass().getResource("number.txt").getPath());
		assertEquals(10, sum);
	}

}
