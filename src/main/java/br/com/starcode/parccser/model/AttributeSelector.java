package br.com.starcode.parccser.model;

public class AttributeSelector extends SimpleSelector {

    private String name;
    private AttributeOperator operator;
    private StringValue value;
    
    public AttributeSelector(
            String name, 
            AttributeOperator operator,
            StringValue value,
            Context context) {
        super(context);
        this.name = name;
        this.operator = operator;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public AttributeOperator getOperator() {
        return operator;
    }
    
    public StringValue getValue() {
        return value;
    }
    
}
