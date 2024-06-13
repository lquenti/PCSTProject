package com.softwaretesting.testing.util;

public class DebugFactorial {

    // Java program to find factorial of given number

    // Method to find factorial of given number
    static int factorial(int n)
    {
        int res = 1;
        int i;
        for (i=2; i< n; i++)
            res *= i;
        return res;
    }

        // Driver method
    public static void main(String[] args)
    {
        int num = 5;
        System.out.println("Factorial of "+ num + " is " + factorial(5));
    }
}
