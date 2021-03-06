package br.com.starcode.parccser.model;

public class HashSelector extends SimpleSelector {
    
    private String hash;
    
    public HashSelector(
            String hash, 
            Context context) {
        super(context);
        this.hash = hash;
    }

    public String getName() {
        return hash;
    }

}
