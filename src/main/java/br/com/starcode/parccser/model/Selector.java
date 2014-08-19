package br.com.starcode.parccser.model;

import java.util.List;

/**
 * Base class for selector element classes.
 */
public class Selector extends AbstractContext {

    private List<SimpleSelectorSequence> simpleSelectors;
    private List<Combinator> combinators;
    
    public Selector(List<SimpleSelectorSequence> simpleSelectors, List<Combinator> combinators, Context context) {
        super(context);
        this.simpleSelectors = simpleSelectors;
    }
    
    public List<SimpleSelectorSequence> getSelectors() {
        return simpleSelectors;
    }
    
    public List<Combinator> getCombinators() {
		return combinators;
	}
    
}
