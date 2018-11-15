package br.com.starcode.parccser;

import java.util.ArrayList;
import java.util.List;

import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;


public class MockListener implements ParserListener {

	final List<String> list = new ArrayList<String>();
	private int number;

	public void beginGroup(int number, int position) {
		list.add("beginGroup=" + number);
		this.number = number;
	}

	public void endGroup(Selector group) {
		list.add("endGroup=" + number);
	}

	public void typeSelector(TypeSelector typeSelector) {
		list.add("typeSelector=" + typeSelector.getType());
	}

	public void selectorSequence(SimpleSelectorSequence simpleSelector, Combinator combinator) {
		String c = combinator == null ? "null" : combinator.getSign();
		list.add("selectorSequence=" + c + "|" + simpleSelector);
	}

	public void classSelector(ClassSelector classSelector) {
		list.add("classSelector=" + classSelector.getClassName());
	}

	public void idSelector(HashSelector hashSelector) {
		list.add("idSelector=" + hashSelector.getName());
	}

	public void attributeSelector(AttributeSelector attributeSelector) {
		list.add("attributeSelector=" + attributeSelector);
	}

	public List<String> getList() {
		return list;
	}

	public void pseudoSelector(PseudoSelector pseudoSelector) {
		list.add("pseudoSelector=" + pseudoSelector.getType().toString() + "|" + pseudoSelector.getContext().toString());
	}

	public void negationTypeSelector(TypeSelector typeSelector) {
		list.add("negationTypeSelector=" + typeSelector.getType());
	}

	public void negationClassSelector(ClassSelector classSelector) {
		list.add("negationClassSelector=" + classSelector.getClassName());
	}

	public void negationAttributeSelector(AttributeSelector attributeSelector) {
		list.add("negationAttributeSelector=" + attributeSelector);
	}

	public void negationIdSelector(HashSelector hashSelector) {
		list.add("negationIdSelector=" + hashSelector.getName());
	}

	public void negationPseudoSelector(PseudoSelector pseudoSelector) {
		list.add("negationPseudoSelector=" + pseudoSelector.getType().toString() + "|" + pseudoSelector);
	}

}
