package br.com.starcode.parccser.model.expression;

public class EvaluatedExpression {
    
    private Integer a;
    private Integer b;
    private String text;
    
	public EvaluatedExpression(Integer a, Integer b, String text) {
		this.a = a;
		this.b = b;
		this.text = text;
	}
	
	public Integer getA() {
		return a;
	}
	
	public Integer getB() {
		return b;
	}
	
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "EvaluatedExpression [a=" + a + ", b=" + b + ", text=" + text + "]";
	}
    
}