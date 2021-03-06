package br.com.starcode.parccser.model;

public enum Combinator {
    
    ADJASCENT_SIBLING("+"), CHILD(">"), GENERAL_SIBLING("~"), DESCENDANT(" ");
    
    private String sign;
    
    private Combinator(String sign) {
        this.sign = sign;
    }
    
    public String getSign() {
        return sign;
    }
    
}
