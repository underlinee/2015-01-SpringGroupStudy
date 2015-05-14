import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class JUnitTest {

	
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
//	static ApplicationContext contextObject = null;
	
	@Test public void test1() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
		
//		assertThat(contextObject == null || contextObject == this.context, is(true));
//		contextObject = this.context;
	}
	
	@Test public void test2() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
//		
//		assertTrue(contextObject == null || contextObject == this.context);
//		contextObject = this.context;
	}
	
	@Test public void test3() {
		assertThat(testObjects, not(hasItem(this)));
		testObjects.add(this);
//		
//		assertThat(contextObject, either(is(nullValue())).or(is(this.contextObject)));
//		contextObject = this.context;
	}
}
