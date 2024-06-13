package com.softwaretesting.testing.validator;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.softwaretesting.testing.validator.PhoneNumberValidator.validate;

public class PhoneNumberValidatorTest {

    /* Here are the failing test cases */

    /* The empty string is not a correct number */
    @Test
    public void emptyFails() {
        assertFalse(validate(""));
    }

    /* null is not a correct number, but we do not want an exception */
    @Test
    public void nullFails() {
        assertFalse(validate(null));
    }

    /* A string too short to be a number, although properly formatted */
    @Test
    public void tooShortFails() {
        assertFalse(validate("+49123"));
    }

    /* A string too short to be a number, although properly formatted */
    @Test
    public void justLongEnoughWorks() {
        assertTrue(validate("0176123"));
    }

    /* A string too long to be a number, although properly formatted */
    @Test
    public void tooLongFails() {
        StringBuilder sb = new StringBuilder(1000);
        sb.append('0');
        for (int i=0; i<999; i++) {
            sb.append('5');
        }
        assertFalse(validate(sb.toString()));
    }

    @Test
    public void maximumLongStillWorks() {
        assertTrue(validate("0123123123123123123123123"));
    }

    /* The '+' is allowed, but only at the beginning */
    @Test
    public void multiplePlussesFails() {
        assertFalse(validate("+49176+794502"));
    }

    /* Double zero prefix is not allowed */
    @Test
    public void doubleZeroPrefixFails() {
        assertFalse(validate("00176794502"));
    }

    /* A phone number can only start with '0' or a country code, i.e. with '+' */
    @Test
    public void nonZeroPrefixFails() {
        assertFalse(validate("176794502"));
    }

    /* A phone number is made out of numbers :D */
    @Test
    public void onlyCharactersFail() {
        assertFalse(validate("abcdeasdjlkasd"));

    }

    /* plus zero fails */
    @Test
    public void plusZeroFails() {
        assertFalse(validate("+0176794502"));
    }

    /* A phone number is still made out of numbers */
    @Test
    public void infixCharactersFail() {
        assertFalse(validate("+491767a4502"));
    }

    /* Here are the passing test cases */

    /* A normal zero-prefixed, i.e. local country, number */
    @Test
    public void zeroPrefixWorks() {
        assertTrue(validate("0176794502"));
    }

    /* A normal '+49', i.e. international germany, number */
    @Test
    public void countryCodePrefixWorks() {
        assertTrue(validate("+49176794502"));
    }

    /* A normal '+1', i.e. international US+Canada, number */
    @Test
    public void usCountryCodePrefixWorks() {
        assertTrue(validate("+1176794502"));
    }

    /* A normal number, with space seperators for the human */
    @Test
    public void spaceSeparatorWorks() {
        assertTrue(validate("+49 176 794502"));
    }

    /* A normal number, with dash seperators for the human */
    @Test
    public void dashSeparatorWorks() {
        assertTrue(validate("+49-176-794502"));
    }

    /* A normal number, with slash seperators for the human */
    @Test
    public void slashSeparatorWorks() {
        assertTrue(validate("+49/176/794502"));
    }

    /* A normal number, with too much whitespace */
    @Test
    public void paddedValueWorks() {
        assertTrue(validate("  +49 176 794502  "));
    }
}
