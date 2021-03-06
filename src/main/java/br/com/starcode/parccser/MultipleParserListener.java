package br.com.starcode.parccser;

import br.com.starcode.parccser.model.AttributeSelector;
import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.Combinator;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;

/**
 * Utility class is someone needs more than one listener at the same time.
 */
public class MultipleParserListener implements ParserListener {

    private ParserListener[] listeners;
    
    public MultipleParserListener(ParserListener... listeners) {
        if (listeners == null) {
            throw new IllegalArgumentException("Null parameter!");
        }
       this.listeners = listeners;
    }

	public void beginGroup(int number, int position) {
		for (ParserListener listener : listeners) {
			listener.beginGroup(number, position);
		}
	}

	public void endGroup(Selector group) {
		for (ParserListener listener : listeners) {
			listener.endGroup(group);
		}
	}

	public void selectorSequence(SimpleSelectorSequence simpleSelector,
			Combinator combinator) {
		for (ParserListener listener : listeners) {
			listener.selectorSequence(simpleSelector, combinator);
		}
	}

	public void typeSelector(TypeSelector typeSelector) {
		for (ParserListener listener : listeners) {
			listener.typeSelector(typeSelector);
		}
	}

	public void classSelector(ClassSelector classSelector) {
		for (ParserListener listener : listeners) {
			listener.classSelector(classSelector);
		}
	}

	public void idSelector(HashSelector hashSelector) {
		for (ParserListener listener : listeners) {
			listener.idSelector(hashSelector);
		}
	}

	public void attributeSelector(AttributeSelector attributeSelector) {
		for (ParserListener listener : listeners) {
			listener.attributeSelector(attributeSelector);
		}
	}

	public void pseudoSelector(PseudoSelector pseudoSelector) {
		for (ParserListener listener : listeners) {
			listener.pseudoSelector(pseudoSelector);
		}
	}

	public void negationTypeSelector(TypeSelector typeSelector) {
		for (ParserListener listener : listeners) {
			listener.negationTypeSelector(typeSelector);
		}
	}

	public void negationClassSelector(ClassSelector classSelector) {
		for (ParserListener listener : listeners) {
			listener.negationClassSelector(classSelector);
		}
	}

	public void negationIdSelector(HashSelector hashSelector) {
		for (ParserListener listener : listeners) {
			listener.negationIdSelector(hashSelector);
		}
	}

	public void negationAttributeSelector(AttributeSelector attributeSelector) {
		for (ParserListener listener : listeners) {
			listener.negationAttributeSelector(attributeSelector);
		}
	}

	public void negationPseudoSelector(PseudoSelector pseudoSelector) {
		for (ParserListener listener : listeners) {
			listener.negationPseudoSelector(pseudoSelector);
		}
	}

}
