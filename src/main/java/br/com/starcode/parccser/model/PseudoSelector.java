package br.com.starcode.parccser.model;

import br.com.starcode.parccser.model.expression.PseudoExpression;

public class PseudoSelector extends SimpleSelector {

    private String name;
    private PseudoType type;
    private boolean doubleColon;
    private PseudoExpression expression;
    
    public PseudoSelector(
            String name, 
            PseudoType type,
            boolean doubleColon,
            PseudoExpression expression,
            Context context) {
        super(context);
        this.name = name;
        this.type = type;
        this.doubleColon = doubleColon;
        this.expression = expression;
    }
    
    public String getName() {
        return name;
    }
    
    public PseudoType getType() {
        return type;
    }
    
    public boolean getDoubleColon() {
        return doubleColon;
    }
    
    public PseudoExpression getExpression() {
        return expression;
    }
    
}
