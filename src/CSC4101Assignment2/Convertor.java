package CSC4101Assignment2;

/**
 * This program converts any decimal number (within the specified double range) to
 * its binary equivalent in accordance with the IEEE754 standard, including its 32 and 64-bit variants.
 *
 * @author Aidan Eiler
 */
public class Convertor {
    /**
     * This method converts a number into the IEEE754 standard binary representation.
     *
     * @param number - number which will be printed to terminal
     */
    public static void IEEE754(double number) {

        StringBuilder bin_string = double_to_binary(number, false);
        StringBuilder bin_string2 = double_to_binary(number, true);
        {
            StringBuilder exponent = normalize(bin_string, false, number);
            StringBuilder exponent2 = normalize(bin_string2, true, number);

            int sign_bit = number >= 0 ? 0 : 1;
            bin_string.insert(0, exponent + " ");
            bin_string2.insert(0, exponent2 + " ");

            bin_string.insert(0, sign_bit + " ");
            bin_string2.insert(0, sign_bit + " ");
        }

        System.out.println("Input " + number);
        System.out.println(bin_string);
        System.out.println(bin_string2);

    }

    /**
     * This method converts any double number into its binary representation.
     *
     * @param number - number that will be converted to binary
     * @param is_64  - if true, the returned number will return a 52-bit mantissa
     * @return StringBuilder that represents the binary number
     */
    private static StringBuilder double_to_binary(double number, boolean is_64) {
        StringBuilder bin_string = new StringBuilder();

        if (!(number < 1 && number > -1)) {
            //seperates whole number and decimal for processing
            double integral = Math.abs((int) number);
            double fractional = Math.abs(number) - integral; //note: is open for representation error

            {//integer to binary
                boolean done = false;
                while (!done) {
                    //double integral2 = integral;
                    bin_string.append((int) integral % 2);
                    integral = (int) (integral / 2);
                    if (integral == 0) {
                        done = true;
                    }

                }
                bin_string = bin_string.reverse();
            }//end of integer to binary

            {//fractional to binary
                bin_string.append('.');
                int size = 0;
                int size_max = 23;
                if (is_64) {
                    size_max = 52;
                }
                while (size < size_max) {
                    fractional *= 2;
                    bin_string.append((int) fractional);
                    if ((int) fractional == 1) {
                        fractional -= 1;
                    }
                    size++;
                }
            }//end of fractional to binary
        } else {
            {//fractional to binary
                if (number < 0) {
                    number *= -1;
                }
                for (int i = 1; number < 1; i++) {
                    number *= 2;
                }
                if ((int) number == 1) {
                    number -= 1;
                }
                int size = 0;
                int size_max = 23;
                if (is_64) {
                    size_max = 52;
                }
                while (size < size_max) {
                    number *= 2;
                    bin_string.append((int) number);
                    if ((int) number == 1) {
                        number -= 1;
                    }
                    size++;
                }
            }//end of fractional to binary
        }
        return bin_string;
    }

    /**
     * @param bin_string - StringBuilder that will be nornalised
     * @param is_64      - mark true if normalizing 64-bit binaries
     * @param number     - original number wanting to be converted to binary
     * @return StringBuilder representing binary exponent
     */
    private static StringBuilder normalize(StringBuilder bin_string, boolean is_64, double number) {
        int exp = 0;
        if (!(number < 1 && number > -1)) {
            int one_pos = -1, dec_pos = -1;
            {
                boolean one_found = false, dec_found = false;
                for (int i = 0; i < bin_string.length(); i++) {
                    if (!one_found && bin_string.charAt(i) == '1') {
                        one_pos = i;
                        one_found = true;
                    } else if (!dec_found && bin_string.charAt(i) == '.') {
                        dec_pos = i;
                        dec_found = true;
                    }
                    if (one_found && dec_found) {
                        break;
                    }
                }
            }
            if (number < 1 && number > -1) {
                exp = dec_pos - one_pos;
            } else {
                exp = dec_pos - one_pos - 1;
            }
            bin_string.deleteCharAt(dec_pos);
            if (!(number < 1 && number > -1)) {
                bin_string.insert(one_pos + 1, '.');
            } else {
                bin_string.insert(one_pos, '.');
            }
            bin_string.delete(0, one_pos + 2);

            if (is_64) {
                bin_string.delete(52, bin_string.length());
            } else {
                bin_string.delete(23, bin_string.length());
            }
        } else {
            int i = 0;
            while (number < 1){
                i++;
                number *= 2;
            }
            exp = i * -1;
        }
        if (is_64) {
            exp += 1023;
        } else {
            exp += 127;
        }
        System.out.println(exp);
        StringBuilder exponent = new StringBuilder(integer_to_binary(exp));
        System.out.println(exponent);
        return exponent;
    }

    /**
     * This method converts any integer to binary.
     *
     * @param number - number wanting to be converted to binary
     * @return binary representation of number in the form of StringBuilder
     */
    public static StringBuilder integer_to_binary(int number) {
        StringBuilder bin_string = new StringBuilder();
        {//integer to binary
            boolean done = false;
            while (!done) {
                //double integral2 = integral;
                System.out.println(number + " is NUMBER");
                System.out.println(number%2 + " is NUMBER %2");
                bin_string.append((int) number % 2);
                System.out.println(number/2 + " is NUMBER /2");
                number = (int) (number / 2);
                System.out.println(number + " is NUMBER AT END");
                if (number == 0) {
                    done = true;
                }

            }
            bin_string = bin_string.reverse();
        }//end of integer to binary
        return bin_string;
    }

    public static void main(String[] args) {
        IEEE754(0.085);
    }
}