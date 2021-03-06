package br.com.starcode.parccser.model;

public enum AttributeOperator {
    
    EQUALS("="), INCLUDES("~="), DASH_MATCH("|="), 
    PREFIX_MATCH("^="), SUFFIX_MATCH("$="), SUBSTRING_MATCH("*=");
    
    private String sign;
    
    private AttributeOperator(String sign) {
        this.sign = sign;
    }
    
    public String getSign() {
        return sign;
    }
    
}
