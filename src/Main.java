import utils.NumericToWord;

import java.math.BigDecimal;


public class Main {
    public static void main(String[] args) {
        BigDecimal number = new BigDecimal("82724764624");
        NumericToWord numericToWord = new NumericToWord(number);
        String valueInWords = numericToWord.translate();

        System.out.println(number + ": " + valueInWords);
    }
}