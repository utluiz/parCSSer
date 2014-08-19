package br.com.starcode.parccser.model;

public class AbstractContext {

    protected Context context;
    
    protected AbstractContext(Context context) {
        this.context = context;
    }
    
    public Context getContext() {
        return context;
    }
    
    @Override
    public String toString() {
        return context.toString();
    }

}
