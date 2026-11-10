package com.aritmatika.demo;

import com.aritmatika.demo.model.HasilAritmatika;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoApplicationTests {

	@Test
	void testArithmeticOperations() {
		HasilAritmatika tambah = new HasilAritmatika(12.0, "+", 8.0);
		assertEquals(20.0, tambah.getResult());
		assertEquals("20", tambah.getFormattedResult());

		HasilAritmatika kali = new HasilAritmatika(6.0, "*", 7.0);
		assertEquals(42.0, kali.getResult());
		assertEquals("42", kali.getFormattedResult());

		HasilAritmatika kurang = new HasilAritmatika(15.0, "-", 20.0);
		assertEquals(-5.0, kurang.getResult());
		assertEquals("-5", kurang.getFormattedResult());

		HasilAritmatika bagi = new HasilAritmatika(10.0, "/", 4.0);
		assertEquals(2.5, bagi.getResult());
		assertEquals("2.5", bagi.getFormattedResult());
	}

	@Test
	void testDivisionByZeroThrowsArithmeticException() {
		assertThrows(ArithmeticException.class, () -> new HasilAritmatika(10.0, "/", 0.0));
	}
}
