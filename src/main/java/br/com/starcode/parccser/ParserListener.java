package br.com.starcode.parccser;

import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;

public interface ParserListener {

    void beginGroup(int number, int position);
    void endGroup(Selector group);
    void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator);
    
    void typeSelector(TypeSelector typeSelector);
    void classSelector(ClassSelector classSelector);
    void idSelector(HashSelector hashSelector);
    void attributeSelector(AttributeSelector attributeSelector);
    void pseudoSelector(PseudoSelector pseudoSelector);

    void negationTypeSelector(TypeSelector typeSelector);
    void negationClassSelector(ClassSelector classSelector);
    void negationIdSelector(HashSelector hashSelector);
    void negationAttributeSelector(AttributeSelector attributeSelector);
    void negationPseudoSelector(PseudoSelector pseudoSelector);
    
}
