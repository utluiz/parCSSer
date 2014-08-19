package br.com.starcode.parccser.model;

/**
 * Base class for element classes that have context
 */
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
