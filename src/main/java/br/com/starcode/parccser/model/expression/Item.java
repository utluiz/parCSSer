package br.com.starcode.parccser.model.expression;

public class Item {
    
    private Type type;
    private String value;
    
    public Item(Type type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }

	@Override
	public String toString() {
		return "Item [type=" + type + ", value=" + value + "]";
	}
    
}