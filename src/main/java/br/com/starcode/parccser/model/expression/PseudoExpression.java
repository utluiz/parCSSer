package br.com.starcode.parccser.model.expression;

import java.util.List;

import br.com.starcode.parccser.model.Context;

public class PseudoExpression {
    
    private List<Item> items;
    private EvaluatedExpression evaluatedExpression;
    private String text;
    private Context context;
    
    public PseudoExpression(List<Item> items, EvaluatedExpression evaluatedExpression, String text, Context context) {
        this.items = items;
        this.evaluatedExpression = evaluatedExpression;
        this.text = text;
        this.context = context;
    }
    
    public Context getContext() {
		return context;
	}
    
    public String getText() {
        return text;
    }
    
    public List<Item> getItems() {
        return items;
    }
    
    public EvaluatedExpression getEvaluatedExpression() {
		return evaluatedExpression;
	}
    
    /**
     * If has the format (an+b), e.g: (2n+1), (n), (4n-1), ... 
     */
    public boolean isValidGroupExpression() {
    	return evaluatedExpression != null;
    }

	@Override
	public String toString() {
		return "PseudoExpression [items=" + items + ", evaluatedExpression="
				+ evaluatedExpression + ", text=" + text + ", context=" + context
				+ "]";
	}

}
