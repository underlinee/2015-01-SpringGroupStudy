import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalcTest {
	Calculator calculator;
	String numFilepath;

	@Before
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("number.txt").getPath();
	}

	@Test
	public void sumOfNumbers() {
		assertEquals(calculator.calcSum(numFilepath), 10);
	}

	@Test
	public void multiplyOfNumbers() {
		assertEquals(calculator.calcMultiply(numFilepath), 24);
	}

	@Test
	public void concatenateOfNumbers() {
		assertEquals(calculator.concantenate(numFilepath), "1234");
	}

}
