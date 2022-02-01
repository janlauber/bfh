/*
 * Project and Training 2: News Reader - Computer Science, Berner Fachhochschule
 */
package ch.bfh.spacenews;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * A JUnit test class - remove this one.
 */
public class AppTest {

	/**
	 * Tests the App's 'sayHello() method."
	 */
	@Test
	public void sayHelloTest() {
		App a = new App();
		String answer = a.sayHello();
		assertEquals("Hello World!", answer);

	}
}
