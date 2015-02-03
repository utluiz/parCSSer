package br.com.starcode.parccser.model;

public class StringValue extends AbstractContext {

	private String actualValue;
	
	public StringValue(Context context, String actualValue) {
		super(context);
		this.actualValue = actualValue;
	}
	
	public String getActualValue() {
		return actualValue;
	}
	
}
