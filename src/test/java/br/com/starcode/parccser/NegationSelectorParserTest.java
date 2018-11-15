package br.com.starcode.parccser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.starcode.parccser.model.ClassSelector;
import br.com.starcode.parccser.model.HashSelector;
import br.com.starcode.parccser.model.NegationSelector;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class NegationSelectorParserTest {

    @Test
    public void doubleNegation() {

        boolean hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser(":not(:not(#id))", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);

    }

    @Test
    public void nagationPseudoElement() {

        boolean hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser(":not(::first-letter)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);

        hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser(":not(:first-letter)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);

        hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser(":not(::anyothger)", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);

    }

    @Test
    public void negateIdSelector() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input:not(#ident)", l);

        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("negationIdSelector=ident", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:not(#ident)", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:not(#ident)", sel1.getContext().toString());
        Assert.assertEquals("input:not(#ident)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(17, sel1.getContext().getEndPosition());

        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:not(#ident)", sss.getContext().toString());
        Assert.assertEquals("input:not(#ident)", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(17, sss.getContext().getEndPosition());

        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());
        Assert.assertFalse(ts.isUniversal());
        Assert.assertEquals("input:not(#ident)", ts.getContext().getSelector());
        Assert.assertEquals(0, ts.getContext().getStartPosition());
        Assert.assertEquals(5, ts.getContext().getEndPosition());

        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof NegationSelector);

        NegationSelector ns = (NegationSelector) ss;
        Assert.assertEquals("#ident", ns.getContext().toString());
        Assert.assertEquals("input:not(#ident)", ns.getContext().getSelector());

        ss = ns.getSimpleSelector();
        Assert.assertTrue(ss instanceof HashSelector);
        HashSelector hs = (HashSelector) ss;
        Assert.assertEquals("#ident", hs.getContext().toString());
        Assert.assertEquals("ident", hs.getName());

    }

    @Test
    public void negateClassSelector() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse(":not(._minha_classe)", l);

        Assert.assertEquals(4, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("negationClassSelector=_minha_classe", l.getList().get(1));
        Assert.assertEquals("selectorSequence=null|:not(._minha_classe)", l.getList().get(2));
        Assert.assertEquals("endGroup=0", l.getList().get(3));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals(":not(._minha_classe)", sel1.getContext().toString());
        Assert.assertEquals(":not(._minha_classe)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(20, sel1.getContext().getEndPosition());

        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(1, sss.getSimpleSelectors().size());
        Assert.assertEquals(":not(._minha_classe)", sss.getContext().toString());
        Assert.assertEquals(":not(._minha_classe)", sss.getContext().getSelector());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(20, sss.getContext().getEndPosition());

        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof NegationSelector);

        NegationSelector ns = (NegationSelector) ss;
        Assert.assertEquals("._minha_classe", ns.getContext().toString());
        Assert.assertEquals(":not(._minha_classe)", ns.getContext().getSelector());

        ss = ns.getSimpleSelector();
        Assert.assertTrue(ss instanceof ClassSelector);
        ClassSelector hs = (ClassSelector) ss;
        Assert.assertEquals("._minha_classe", hs.getContext().toString());
        Assert.assertEquals("_minha_classe", hs.getClassName());

    }

    @Test
    public void negateUniversalSelector() throws Exception {

        MockListener l = new MockListener();
        new Parser("*:not(*)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=*", l.getList().get(1));
        Assert.assertEquals("negationTypeSelector=*", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|*:not(*)", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

    }

    @Test
    public void negateTypeSelector() throws Exception {

        MockListener l = new MockListener();
        new Parser("tabl:not(table)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=tabl", l.getList().get(1));
        Assert.assertEquals("negationTypeSelector=table", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|tabl:not(table)", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

    }

    @Test
    public void negateAttributeSelector() throws Exception {

        MockListener l = new MockListener();
        new Parser("a:not([href])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=a", l.getList().get(1));
        Assert.assertEquals("negationAttributeSelector=href", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|a:not([href])", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

    }

    @Test
    public void negateAttributeSelector2() throws Exception {

        MockListener l = new MockListener();
        new Parser("a:not([href='1'])", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=a", l.getList().get(1));
        Assert.assertEquals("negationAttributeSelector=href='1'", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|a:not([href='1'])", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

    }

    @Test
    public void negatePseudoClass() throws Exception {

        MockListener l = new MockListener();
        new Parser("a:not(:disable)", l).interpret();
        ///System.out.println(l.getLista());
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=a", l.getList().get(1));
        Assert.assertEquals("negationPseudoSelector=PSEUDO_CLASS|disable", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|a:not(:disable)", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

    }

}
