package br.com.starcode.parccser;

import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;

public abstract class AbstractParserListener implements ParserListener {
	
	public void beginGroup(int number, int position) {
	}

	public void endGroup(Selector group) {
	}

	public void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator) {
	}

	public void typeSelector(TypeSelector typeSelector) {
	}

	public void classSelector(ClassSelector classSelector) {
	}

	public void idSelector(HashSelector hashSelector) {
	}

	public void attributeSelector(AttributeSelector attributeSelector) {
	}

	public void pseudoSelector(PseudoSelector pseudoSelector) {
	}

	public void negationTypeSelector(TypeSelector typeSelector) {
	}

	public void negationClassSelector(ClassSelector classSelector) {
	}

	public void negationIdSelector(HashSelector hashSelector) {
	}

	public void negationAttributeSelector(AttributeSelector attributeSelector) {
	}

	public void negationPseudoSelector(PseudoSelector pseudoSelector) {
	}
	
}
