package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exceptions.DuplicateKeyException;
import exceptions.InvalidKeyException;
import exceptions.KeyNotFoundException;
import exceptions.ThresholdExceededException;
import main.CleverSIDC;

class CleverSIDCTest {

	CleverSIDC csidc;

	@BeforeEach
	void setUp() throws DuplicateKeyException, InvalidKeyException, ThresholdExceededException {
		csidc = new CleverSIDC(6);
		csidc.add(csidc, 11111111, "test value for 11111111");
		csidc.add(csidc, 22222222, "test value for 22222222");
		csidc.add(csidc, 33333333, "test value for 33333333");
		csidc.add(csidc, 44444444, "test value for 44444444");
		csidc.add(csidc, 55555555, "test value for 55555555");
	}


	@Test
	@DisplayName("Testing generate method")
	void testGenerate() throws KeyNotFoundException {
		int newID = csidc.generate();
		assertAll("key",
				() -> assertNotEquals(11111111, newID),
				() -> assertNotEquals(22222222, newID),
				() -> assertNotEquals(33333333, newID),
				() -> assertNotEquals(44444444, newID),
				() -> assertNotEquals(55555555, newID));
		
	}
	
	@Test
	@DisplayName("Testing insert method with unique key")
	void testValidInsert() throws DuplicateKeyException, InvalidKeyException, ThresholdExceededException {
		int newNode = csidc.add(csidc, 12345678, "test value for 12345678");
		assertEquals(12345678, newNode);
	}
	
	@Test
	@DisplayName("Testing insert method with duplicate key")
	void testInvalidInsert() {
		Throwable exception = assertThrows(DuplicateKeyException.class, () -> csidc.add(csidc, 11111111, "test value for 11111111"));
		assertEquals("Key already exists. ", exception.getMessage());
	}

}
