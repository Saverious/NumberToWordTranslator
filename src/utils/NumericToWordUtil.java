package utils;

import java.util.Map;

public class NumericToWordUtil {
    private static final Map<String, String> OnesMap = Map.ofEntries(
            Map.entry("0", ""),
            Map.entry("1", "One"),
            Map.entry("2", "Two"),
            Map.entry("3", "Three"),
            Map.entry("4", "Four"),
            Map.entry("5", "Five"),
            Map.entry("6", "Six"),
            Map.entry("7", "Seven"),
            Map.entry("8", "Eight"),
            Map.entry("9", "Nine")
    );

    private static final Map<String, String> TensMap = Map.ofEntries(
            Map.entry("10", "Ten"),
            Map.entry("20", "Twenty"),
            Map.entry("30", "Thirty"),
            Map.entry("40", "Forty"),
            Map.entry("50", "Fifty"),
            Map.entry("60", "Sixty"),
            Map.entry("70", "Seventy"),
            Map.entry("80", "Eighty"),
            Map.entry("90", "Ninety")
    );

    private static final Map<String, String> ElevenToNineteenMap = Map.ofEntries(
            Map.entry("11", "Eleven"),
            Map.entry("12", "Twelve"),
            Map.entry("13", "Thirteen"),
            Map.entry("14", "Fourteen"),
            Map.entry("15", "Fifteen"),
            Map.entry("16", "Sixteen"),
            Map.entry("17", "Seventeen"),
            Map.entry("18", "Eighteen"),
            Map.entry("19", "Nineteen")
    );

    private static String removeLeadingZeros(String numericValue){
        int lengthOfNumericValue = numericValue.length();

        for(int i = 0; i < lengthOfNumericValue; i++){
            if(numericValue.charAt(i) == '0') continue;
            return numericValue.substring(i);
        }

        return "";
    }

    public static String placeValueReader(String numericValue){
        String newValue = removeLeadingZeros(numericValue);

        int lengthOfNumber = newValue.length();

        if(lengthOfNumber > 15){
            throw new RuntimeException("InvalidRange--> Value is too large to be read");
        }

        return switch(lengthOfNumber) {
            case 1 -> readSingleDigit(newValue);
            case 2 -> readDoubleDigits(newValue);
            case 3 -> readTripleDigits(newValue);
            case 4, 5, 6 -> readThousands(newValue);
            case 7, 8, 9 -> readMillions(newValue);
            case 10, 11, 12 -> readBillions(newValue);
            case 13, 14, 15 -> readTrillions(newValue);
            default -> "";
        };
    }

    public static String readSingleDigit(String value){
        if(value == null || value.isBlank()){
            throw new RuntimeException("Class.readSingleDigit-->Cannot read empty value");
        }

        return OnesMap.get(value);
    }

    public static String readDoubleDigits(String value){
        if(value == null || value.isBlank()){
            throw new RuntimeException("Class.readDoubleDigits-->Cannot read empty value");
        }

        char firstDigit = value.charAt(0);
        char secondDigit = value.charAt(1);

        if(secondDigit == '0'){
            return TensMap.get(value);
        }

        if(firstDigit == '1'){
            return ElevenToNineteenMap.get(value);
        }

        String placeValue = firstDigit + "0";
        String firstWord = TensMap.get(placeValue);
        String secondWord = OnesMap.get(secondDigit + "");

        return firstWord + " " + secondWord;
    }

    public static String readTripleDigits(String value){
        if(value == null || value.isBlank()){
            throw new RuntimeException("Class.readDoubleDigits-->Cannot read empty value");
        }

        char firstDigit = value.charAt(0);
        char secondDigit = value.charAt(1);
        char thirdDigit = value.charAt(2);

        String firstWord = readSingleDigit(firstDigit + "");
        String lastWord = placeValueReader(secondDigit + "" + thirdDigit);

        return lastWord.isEmpty() ? firstWord + " " + "Hundred" : firstWord + " " + "Hundred and" + " " + lastWord;
    }

    public static String readThousands(String numericValue){
        String reversedValue = new StringBuilder(numericValue).reverse().toString();
        String val1 = new StringBuilder(reversedValue.substring(3)).reverse().toString();
        String val2 = new StringBuilder(reversedValue.substring(0, 3)).reverse().toString();

        String val1InWords = placeValueReader(val1);
        String val2InWords = placeValueReader(val2);

        if(val2InWords.isBlank()){
            return val1InWords + " Thousand";
        }
        if(val2InWords.contains("Hundred")){
            return val1InWords + " Thousand, " + val2InWords;
        }

        return val1InWords + " Thousand and " + val2InWords;
    }

    public static String readMillions(String numericValue){
        String reversedValue = new StringBuilder(numericValue).reverse().toString();
        String val1 = new StringBuilder(reversedValue.substring(6)).reverse().toString();
        String val2 = new StringBuilder(reversedValue.substring(0, 6)).reverse().toString();

        String val1InWords = placeValueReader(val1);
        String val2InWords = placeValueReader(val2);

        if(val2InWords.isBlank()){
            return val1InWords + " Million";
        }
        if(val2InWords.contains("Thousand") || val2InWords.contains("Hundred")){
            return val1InWords + " Million, " + val2InWords;
        }

        return val1InWords + " Million and " + val2InWords;
    }

    public static String readBillions(String numericValue){
        String reversedValue = new StringBuilder(numericValue).reverse().toString();
        String val1 = new StringBuilder(reversedValue.substring(9)).reverse().toString();
        String val2 = new StringBuilder(reversedValue.substring(0, 9)).reverse().toString();

        String val1InWords = placeValueReader(val1);
        String val2InWords = placeValueReader(val2);

        if(val2InWords.isBlank()){
            return val1InWords + " Billion";
        }
        if(val2InWords.contains("Million") || val2InWords.contains("Thousand") || val2InWords.contains("Hundred")){
            return val1InWords + " Billion, " + val2InWords;
        }

        return val1InWords + " Billion and " + val2InWords;
    }

    public static String readTrillions(String numericValue){
        String reversedValue = new StringBuilder(numericValue).reverse().toString();
        String val1 = new StringBuilder(reversedValue.substring(12)).reverse().toString();
        String val2 = new StringBuilder(reversedValue.substring(0, 12)).reverse().toString();

        String val1InWords = placeValueReader(val1);
        String val2InWords = placeValueReader(val2);

        if(val2InWords.isBlank()){
            return val1InWords + " Trillion";
        }
        if(val2InWords.contains("Billion") || val2InWords.contains("Million") || val2InWords.contains("Thousand") || val2InWords.contains("Hundred")){
            return val1InWords + " Trillion, " + val2InWords;
        }

        return val1InWords + " Trillion and " + val2InWords;
    }
}