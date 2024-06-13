package com.softwaretesting.testing.validator;

import org.springframework.stereotype.Service;

@Service
public class PhoneNumberValidator {
    private PhoneNumberValidator(){}
    public static boolean validate(String phoneNumber) {
        // Null is an invalid phone number, but I prefer return values over exceptions.
        if (phoneNumber == null) {
            return false;
        }

        // clean up string
        // those delimits are useful for the human, but do not harm the input.
        phoneNumber = phoneNumber
                .replace(" ", "")
                .replace("-", "")
                .replace("/", "");

        // minimal length phone number would be sth like
        // - current country
        // - mobile phone prefix
        // - 3 digits (my grandparents have that for their home number, never heard of less)
        //
        // 0 176 123 (without spaces)
        if (phoneNumber.length() < 7) {
            return false;
        }

        // Maximal length would be something like
        // - 3 digit country number (such as luxembourg)
        // - 5 digit prefix (such as Göttingen)
        // - a long number
        // 20 chars would probably work, but lets go for 25, should be tight enough
        if (phoneNumber.length() > 25) {
            return false;
        }

        // The first character has to be a zero or a plus.
        // But "+0" is not allowed.
        // And "00" is not allowed
        if (phoneNumber.charAt(0) == '+' && phoneNumber.charAt(1) == '0') {
            return false;
        } else if (phoneNumber.charAt(0) == '0' && phoneNumber.charAt(1) == '0') {
            return false;
        } else if ((phoneNumber.charAt(0) == '0' && phoneNumber.charAt(1) != '0') ||
                (phoneNumber.charAt(0) == '+' && phoneNumber.charAt(1) != '0')) {
            phoneNumber = phoneNumber.substring(1);
        } else {
            return false;
        }

        // Now everything that is not "0123456789" is invalid
        String allowedChars = "0123456789";

        // Let me also flex that I learned some java some time ago :)
        return phoneNumber.chars()
                .mapToObj(i -> (char)i)
                .allMatch(c -> allowedChars.indexOf(c) != -1);
    }

}
