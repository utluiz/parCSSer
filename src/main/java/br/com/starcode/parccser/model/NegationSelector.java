package br.com.starcode.parccser.model;

public class NegationSelector extends SimpleSelector {

    private SimpleSelector simpleSelector;
    
    public NegationSelector(
            SimpleSelector simpleSelector, 
            Context context) {
        super(context);
        this.simpleSelector = simpleSelector;
    }
    
    public SimpleSelector getSimpleSelector() {
        return simpleSelector;
    }

}
