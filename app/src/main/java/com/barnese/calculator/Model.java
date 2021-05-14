package com.barnese.calculator;



public class Model {

    final static int MAXSIZE = 9;
    static char[] charArr = new char[MAXSIZE];
    static int pos = 0;
    static float displayValue = 0f;
    static char operation = 'E';
    static float firstVal = 0f;
    static float secondVal = 0f;
    static float result = 0f;
    static char previousChar = 'E';
    static char currentChar = 'E';

    //Return the displayValue to a string
    //Return the string as an integer or a float as appropriate

    public static String getValue() {
        int displayValInt = (int) displayValue;
        if (pos != 0)
            return String.valueOf(charArr);
        else if (displayValInt - displayValue == 0) {
            if (currentChar == '.')
                return Integer.toString(displayValInt) + '.';
            return Integer.toString(displayValInt);
        } else
            return String.format("%10f", displayValue);
    }

    //input a character, and then parse the character depending if
    //it is a number, decimal, operation, or clear.
    public static String enterValue(char c) {
        previousChar = currentChar;
        currentChar = c;
        //input a number can either be a decimal or integer
        if (Character.isDigit(c) || currentChar == '.') {
            //if the previous character is a = sign, and a new number is entered that acts like a clear
            if (previousChar == '=') {
                clear();
                charArr[pos] = c;
                ++pos;
                firstVal = Float.parseFloat(String.valueOf(charArr));
                displayValue = firstVal;
            } else if (!containsDotAlready() || currentChar != '.') {
                //if a decimal is the first digit, record the first digit as a zero so . is converted to 0.
                if (currentChar == '.' && pos == 0) {
                    charArr[0] = '0';
                    charArr[1] = '.';
                    pos = 2;
                }
                //enter digits, if after a clear the digits are for the first number before the operation
                //if after an operation, the numbers are for the second digit (ie. 2 + 3)
                //numbers are stored in an character array as the user continues to enter values up to MAXSIZE
                else if (pos < MAXSIZE) {
                    charArr[pos] = c;
                    ++pos;
                    if (operation == 'E') {
                        firstVal = Float.parseFloat(String.valueOf(charArr));
                        displayValue = firstVal;
                    } else {
                        secondVal = Float.parseFloat(String.valueOf(charArr));
                        displayValue = secondVal;
                    }

                }
            }
        }
        //How to deal with operations
        else if (currentChar == '+' || currentChar == '-' || currentChar == 'x' || currentChar == '/') {
            //two operations in a row just changes the operation ( + then - sets the operation to -)
            if (previousChar == '+' || previousChar == '-' || previousChar == 'x' || previousChar == '/')
                operation = currentChar;
                //if the previous character is a = sign, calculate using the previous operation
                // 2+3 = 5 = 8 = 11 = 14 (continue to + 3)
            else if (previousChar == '=') {
                operation = 'E';
                Model.calc();
                operation = currentChar;
            }

            //this uses calculates the operation, and sets up for the next operation.
            // 9 + 3 - -> 12 - (then wait for next value)
            else {
                Model.calc();

                operation = currentChar;

            }
        }
        // this runs the calculation (9 + 3 = 12)
        else if (c == '=') {


            Model.calc();

            //The is for AC
        } else if (c == 'c') {
            Model.clear();
            //this is a catch for an error, I could use a throw instead.
        } else
            System.out.println("ERROR!");
        //the method returns a string of the current display value
        return getValue();
    }

    // this sets up a switch statement for doing a calculation
    public static void calc() {

        switch (operation) {
            //E is a case if there is no operation set already, this is the case after clear is called.
            case 'E':
                result = firstVal;

                break;
            case '+':
                result = firstVal + secondVal;
                break;
            case '-':
                result = firstVal - secondVal;
                break;
            case 'x':
                result = firstVal * secondVal;
                break;
            case '/':
                result = firstVal / secondVal;
                break;
            default:
                System.out.println("ERROR");

        }
        // set the display value with the result of the operation
        //set the first value as the result, next number will then replace the secondVal
        //reset the character array to accept new numbers

        displayValue = result;
        firstVal = result;
        charArr = new char[MAXSIZE];
        pos = 0;

    }

    //clear back to starting values.
    public static void clear() {
        secondVal = 0;
        firstVal = 0;
        result = 0;
        displayValue = 0;
        charArr = new char[MAXSIZE];
        pos = 0;
        operation = 'E';
    }

    //test so that the user can't enter a number like 3.234.21
    public static boolean containsDotAlready() {
        for (char c : charArr)
            if (c == '.')
                return true;
        return false;
    }


}


