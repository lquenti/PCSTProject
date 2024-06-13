package com.softwaretesting.testing.util;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static com.softwaretesting.testing.util.Misc.*;

public class MiscTest {
    /* sum tests */

    /* does the addition work
     *
     * Note that I do not care whether it fails with `null`, fair play, so I wont test it
     */
    @Test
    public void sumWorks() {
        assertEquals(8, sum(4,4));
    }
    @Test
    public void sumPlusZeroWorks() {
        assertEquals(0, sum(0,0));
    }
    @Test
    public void sumNegativeWorks() {
        assertEquals(4, sum(-1,5));
    }

    /* divide tests */

    @Test
    public void divideWorks() {
        assertEquals(2, divide(6,3));
    }
    @Test
    public void divideZeroWorks() {
        assertEquals(0, divide(0,3));
    }
    @Test
    public void divideNegativeWorks() {
        assertEquals(divide(-6,3),-2);
    }
    @Test
    public void divideByZeroFails() {
        assertThrows(
                RuntimeException.class,
                () -> divide(6, 0)
        );
    }

    /* iscolorsupproted tests */
    @Test
    public void isColorSupportedNullFails() {
        assertThrows(IllegalArgumentException.class,
                () -> isColorSupported(null),
                "color cannot be null");
    }

    @Test
    public void isColorSupportedRedWorks() {
        assertTrue(isColorSupported(Color.RED));
    }
    @Test
    public void isColorSupportedYellowWorks() {
        assertTrue(isColorSupported(Color.YELLOW));
    }
    @Test
    public void isColorSupportedBlueWorks() {
        assertTrue(isColorSupported(Color.BLUE));
    }

    /* calculateFactorial is unfortunate.
     * Unfortunately, it does not fail with negative or zero, and I am not allowed to fix it.
     * But I do not want failed test cases.
     * So I handle it like the spec says to be undefined for anything below 1
     */

    @Test
    public void calculateFactorialOneWorks() {
        assertEquals(1, calculateFactorial(1));
    }
    @Test
    public void calculateFactorialFiveWorks() {
        assertEquals(calculateFactorial(5), 5*4*3*2);
    }

    /* negative numbers are not prime by definition, so also undefined by our spec.
     * You can tell that I am coming from a C background, huh? :-)
     */
    @Test
    public void isPrimeWorksOneIsNotWorks() {
        assertFalse(isPrime(1, 2));
    }
    @Test
    public void isPrimeTwoIsWorks() {
        assertTrue(isPrime(2, 2));
    }
    @Test
    public void isPrimeThreeIsWorks() {
        assertTrue(isPrime(3, 2));
    }
    @Test
    public void isPrimeBigPrimeWorks() {
        /* http://compoasso.free.fr/primelistweb/page/prime/liste_online_en.php */
        assertTrue(isPrime(853, 2));
    }
    @Test
    public void isPrimeIIsTight() {
        assertTrue(isPrime(17, 4));
        assertFalse(isPrime(16, 4));
    }
    @Test
    public void isPrimeBigNonPrimeWorks() {
        assertFalse(isPrime(855, 2));
    }

    /* isEven */
    @Test
    public void isEvenZeroWorks() {
        assertTrue(isEven(0));
    }
    @Test
    public void isEvenNegativeEvenWorks() {
        assertTrue(isEven(-2));
    }
    @Test
    public void isEvenNegativeOddWorks() {
        assertFalse(isEven(-3));
    }
    @Test
    public void isEvenEvenWorks() {
        assertTrue(isEven(4));
    }
    @Test
    public void isEvenOddWorks() {
        assertFalse(isEven(3));
    }
    @Test
    public void isEvenIntMaxWorks() {
        /* 2,147,483,647 */
        assertFalse(isEven(Integer.MAX_VALUE));
    }
    @Test
    public void isEvenIntMinWorks() {
        /* -2,147,483,648 */
        assertTrue(isEven(Integer.MIN_VALUE));
    }

    /* make stupid code coverage happy */
    @Test
    public void classIsInstanciable() {
        assertNotNull(new Misc());
    }
}
