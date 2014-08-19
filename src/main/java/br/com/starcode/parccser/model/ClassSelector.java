package br.com.starcode.parccser.model;

public class ClassSelector extends SimpleSelector  {
    
    private String name;
    
    public ClassSelector(String name, Context context) {
        super(context);
        this.name = name;
    }

    public String getClassName() {
        return name;
    }
    
}
