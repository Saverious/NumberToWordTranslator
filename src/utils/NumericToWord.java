package utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumericToWord {
    private final String numericValueAsString;

    public NumericToWord(BigDecimal value){
        DecimalFormat df = new DecimalFormat("#.##");
        this.numericValueAsString = df.format(value);
    }

    public String translate(){
        return NumericToWordUtil.placeValueReader(this.numericValueAsString);
    }
}