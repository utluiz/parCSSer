package br.com.starcode.parccser;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.starcode.parccser.model.PseudoSelector;
import br.com.starcode.parccser.model.PseudoType;
import br.com.starcode.parccser.model.Selector;
import br.com.starcode.parccser.model.SimpleSelector;
import br.com.starcode.parccser.model.SimpleSelectorSequence;
import br.com.starcode.parccser.model.TypeSelector;
import br.com.starcode.parccser.model.expression.EvaluatedExpression;
import br.com.starcode.parccser.model.expression.Type;

/**
 * Selector list:
 * http://www.w3.org/TR/css3-selectors/#selectors
 *
 */
public class PseudoSelectorParserTest {

    @Test
    public void pseudoClassSelector() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input:enabled", l);

        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|enabled", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:enabled", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:enabled", sel1.getContext().toString());
        Assert.assertEquals("input:enabled", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(13, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:enabled", sss.getContext().toString());
        Assert.assertEquals(0, sss.getContext().getStartPosition());
        Assert.assertEquals(13, sss.getContext().getEndPosition());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("enabled", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("enabled", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertNull(ps.getExpression());

    }

    @Test
    public void pseudoElementSelector() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input::anything", l);

        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_ELEMENT|anything", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input::anything", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input::anything", sel1.getContext().toString());
        Assert.assertEquals("input::anything", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(15, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input::anything", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("anything", ps.getContext().toString());
        Assert.assertTrue(ps.getDoubleColon());
        Assert.assertEquals("anything", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_ELEMENT, ps.getType());
        Assert.assertNull(ps.getExpression());

    }

    @Test
    public void specialPseudoElementSelector() throws Exception {

        MockListener l = new MockListener();
        new Parser("input:first-line", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_ELEMENT|first-line", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:first-line", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        l = new MockListener();
        new Parser("input:first-letter", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_ELEMENT|first-letter", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:first-letter", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        l = new MockListener();
        new Parser("input:before", l).interpret();
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_ELEMENT|before", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:before", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        l = new MockListener();
        List<Selector> selectors = Parser.parse("input:after", l);
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_ELEMENT|after", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:after", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:after", sel1.getContext().toString());
        Assert.assertEquals("input:after", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(11, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:after", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("after", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("after", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_ELEMENT, ps.getType());
        Assert.assertNull(ps.getExpression());

    }

    @Test
    public void pseudoClassMultiple() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input:enabled:hover", l);

        Assert.assertEquals(6, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|enabled", l.getList().get(2));
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|hover", l.getList().get(3));
        Assert.assertEquals("selectorSequence=null|input:enabled:hover", l.getList().get(4));
        Assert.assertEquals("endGroup=0", l.getList().get(5));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:enabled:hover", sel1.getContext().toString());
        Assert.assertEquals("input:enabled:hover", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(19, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(3, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:enabled:hover", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("enabled", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("enabled", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertNull(ps.getExpression());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(2);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("hover", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("hover", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertNull(ps.getExpression());

    }

    @Test
    public void doublePseudoElement() {

        boolean hasException = false;
        try {
            MockListener l = new MockListener();
            new Parser("input::anything::other", l).interpret();
        } catch (ParserException e) {
            //e.printStackTrace();
            hasException = true;
        }
        Assert.assertTrue(hasException);

    }

    @Test
    public void pseudoClassParameter() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input:eq(1)", l);
        Assert.assertEquals(5, l.getList().size());
        Assert.assertEquals("beginGroup=0", l.getList().get(0));
        Assert.assertEquals("typeSelector=input", l.getList().get(1));
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(1)", l.getList().get(2));
        Assert.assertEquals("selectorSequence=null|input:eq(1)", l.getList().get(3));
        Assert.assertEquals("endGroup=0", l.getList().get(4));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(1)", sel1.getContext().toString());
        Assert.assertEquals("input:eq(1)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(11, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(1)", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(1)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("1", ps.getExpression().getText());
        Assert.assertEquals(1, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("1", ps.getExpression().getItems().get(0).getValue());

        EvaluatedExpression item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertNull(item.getA());
        Assert.assertEquals(1, item.getB().intValue());
        Assert.assertNull(item.getText());

    }

    @Test
    public void pseudoClassParameterExpression() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = Parser.parse("input:eq(2n+1)", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(2n+1)", l.getList().get(2));

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(2n+1)", sel1.getContext().toString());
        Assert.assertEquals("input:eq(2n+1)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(14, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(2n+1)", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        PseudoSelector ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(2n+1)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("2n+1", ps.getExpression().getText());
        Assert.assertEquals(4, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("2", ps.getExpression().getItems().get(0).getValue());
        Assert.assertEquals(Type.IDENTIFIER, ps.getExpression().getItems().get(1).getType());
        Assert.assertEquals("n", ps.getExpression().getItems().get(1).getValue());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(2).getType());
        Assert.assertEquals("+", ps.getExpression().getItems().get(2).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(3).getType());
        Assert.assertEquals("1", ps.getExpression().getItems().get(3).getValue());

        EvaluatedExpression item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(2, item.getA().intValue());
        Assert.assertEquals(1, item.getB().intValue());
        Assert.assertNull(item.getText());

        l = new MockListener();
        selectors = Parser.parse("input:eq( 2n + 1 )", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(2n+1)", l.getList().get(2));

        Assert.assertEquals(1, selectors.size());
        sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(2n+1)", sel1.getContext().toString());
        Assert.assertEquals("input:eq( 2n + 1 )", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(18, sel1.getContext().getEndPosition());

        //selector 1
        sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(2n+1)", sss.getContext().toString());

        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(2n+1)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("2n+1", ps.getExpression().getText());
        Assert.assertEquals(4, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("2", ps.getExpression().getItems().get(0).getValue());
        Assert.assertEquals(Type.IDENTIFIER, ps.getExpression().getItems().get(1).getType());
        Assert.assertEquals("n", ps.getExpression().getItems().get(1).getValue());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(2).getType());
        Assert.assertEquals("+", ps.getExpression().getItems().get(2).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(3).getType());
        Assert.assertEquals("1", ps.getExpression().getItems().get(3).getValue());

        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(2, item.getA().intValue());
        Assert.assertEquals(1, item.getB().intValue());
        Assert.assertNull(item.getText());

        l = new MockListener();
        selectors = Parser.parse("input:eq( +1.5n + 3.5)", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(+1.5n+3.5)", l.getList().get(2));

        Assert.assertEquals(1, selectors.size());
        sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(+1.5n+3.5)", sel1.getContext().toString());
        Assert.assertEquals("input:eq( +1.5n + 3.5)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(22, sel1.getContext().getEndPosition());

        //selector 1
        sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(+1.5n+3.5)", sss.getContext().toString());

        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(+1.5n+3.5)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("+1.5n+3.5", ps.getExpression().getText());
        Assert.assertEquals(5, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("+", ps.getExpression().getItems().get(0).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(1).getType());
        Assert.assertEquals("1.5", ps.getExpression().getItems().get(1).getValue());
        Assert.assertEquals(Type.IDENTIFIER, ps.getExpression().getItems().get(2).getType());
        Assert.assertEquals("n", ps.getExpression().getItems().get(2).getValue());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(3).getType());
        Assert.assertEquals("+", ps.getExpression().getItems().get(3).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(4).getType());
        Assert.assertEquals("3.5", ps.getExpression().getItems().get(4).getValue());

        Assert.assertFalse(ps.getExpression().isValidGroupExpression());

        l = new MockListener();
        selectors = Parser.parse("input:eq(10px)", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(10px)", l.getList().get(2));

        Assert.assertEquals(1, selectors.size());
        sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(10px)", sel1.getContext().toString());
        Assert.assertEquals("input:eq(10px)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(14, sel1.getContext().getEndPosition());

        //selector 1
        sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(10px)", sss.getContext().toString());

        //simple selector 1
        ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(10px)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("10px", ps.getExpression().getText());
        Assert.assertEquals(2, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("10", ps.getExpression().getItems().get(0).getValue());
        Assert.assertEquals(Type.IDENTIFIER, ps.getExpression().getItems().get(1).getType());
        Assert.assertEquals("px", ps.getExpression().getItems().get(1).getValue());

        Assert.assertFalse(ps.getExpression().isValidGroupExpression());

    }

    @Test
    public void pseudoClassParameterExpressionMinus() throws Exception {

        MockListener l = new MockListener();
        List<Selector> selectors = new Parser("input:eq(2n-1)", l).interpret();
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(2n-1)", l.getList().get(2));

        PseudoSelector ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        EvaluatedExpression item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(2, item.getA().intValue());
        Assert.assertEquals(-1, item.getB().intValue());
        Assert.assertNull(item.getText());

        l = new MockListener();
        selectors = Parser.parse("input:eq( -2n - 1)", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(-2n-1)", l.getList().get(2));

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(-2, item.getA().intValue());
        Assert.assertEquals(-1, item.getB().intValue());
        Assert.assertNull(item.getText());

        Assert.assertEquals(1, selectors.size());
        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:eq(-2n-1)", sel1.getContext().toString());
        Assert.assertEquals("input:eq( -2n - 1)", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(18, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:eq(-2n-1)", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("eq(-2n-1)", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("eq", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("-2n-1", ps.getExpression().getText());
        Assert.assertEquals(5, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("-", ps.getExpression().getItems().get(0).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(1).getType());
        Assert.assertEquals("2", ps.getExpression().getItems().get(1).getValue());
        Assert.assertEquals(Type.IDENTIFIER, ps.getExpression().getItems().get(2).getType());
        Assert.assertEquals("n", ps.getExpression().getItems().get(2).getValue());
        Assert.assertEquals(Type.SIGNAL, ps.getExpression().getItems().get(3).getType());
        Assert.assertEquals("-", ps.getExpression().getItems().get(3).getValue());
        Assert.assertEquals(Type.NUMBER, ps.getExpression().getItems().get(4).getType());
        Assert.assertEquals("1", ps.getExpression().getItems().get(4).getValue());

    }

    @Test
    public void pseudoClassParameterConstant() throws Exception {

    	MockListener l = new MockListener();
    	List<Selector> selectors = new Parser("input:eq(n)", l).interpret();
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|eq(n)", l.getList().get(2));

        PseudoSelector ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        EvaluatedExpression item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(1, item.getA().intValue());
        Assert.assertNull(item.getB());
        Assert.assertNull(item.getText());

    	l = new MockListener();
    	selectors = new Parser("input:lang(pt-br)", l).interpret();
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|lang(pt-br)", l.getList().get(2));

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("pt-br", ps.getExpression().getText());

    	l = new MockListener();
    	selectors = new Parser("input:lang('pt-br')", l).interpret();
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|lang('pt-br')", l.getList().get(2));

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("pt-br", ps.getExpression().getText());

    	l = new MockListener();
        selectors = Parser.parse("input:lang(\"pt-br\")", l);
        Assert.assertEquals("pseudoSelector=PSEUDO_CLASS|lang(\"pt-br\")", l.getList().get(2));

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        Assert.assertEquals(1, selectors.size());
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("pt-br", ps.getExpression().getText());

        Selector sel1 = selectors.get(0);
        Assert.assertEquals(1, sel1.getSelectors().size());
        Assert.assertEquals(0, sel1.getCombinators().size());
        Assert.assertEquals("input:lang(\"pt-br\")", sel1.getContext().toString());
        Assert.assertEquals("input:lang(\"pt-br\")", sel1.getContext().getSelector());
        Assert.assertEquals(0, sel1.getContext().getStartPosition());
        Assert.assertEquals(19, sel1.getContext().getEndPosition());

        //selector 1
        SimpleSelectorSequence sss = sel1.getSelectors().get(0);
        Assert.assertEquals(2, sss.getSimpleSelectors().size());
        Assert.assertEquals("input:lang(\"pt-br\")", sss.getContext().toString());

        //simple selector 1
        SimpleSelector ss = sss.getSimpleSelectors().get(0);
        Assert.assertTrue(ss instanceof TypeSelector);

        TypeSelector ts = (TypeSelector) ss;
        Assert.assertEquals("input", ts.getContext().toString());
        Assert.assertEquals("input", ts.getType());

        //simple selector 2
        ss = sss.getSimpleSelectors().get(1);
        Assert.assertTrue(ss instanceof PseudoSelector);

        ps = (PseudoSelector) ss;
        Assert.assertEquals("lang(\"pt-br\")", ps.getContext().toString());
        Assert.assertFalse(ps.getDoubleColon());
        Assert.assertEquals("lang", ps.getName());
        Assert.assertEquals(PseudoType.PSEUDO_CLASS, ps.getType());
        Assert.assertEquals("\"pt-br\"", ps.getExpression().getContext().toString());
        Assert.assertEquals("pt-br", ps.getExpression().getText());
        Assert.assertEquals(1, ps.getExpression().getItems().size());
        Assert.assertEquals(Type.STRING, ps.getExpression().getItems().get(0).getType());
        Assert.assertEquals("pt-br", ps.getExpression().getItems().get(0).getValue());

    }

    @Test
    public void pseudoClassExpressions() throws Exception {

    	MockListener l = new MockListener();
    	List<Selector> selectors = new Parser("input:eq(n+1)", l).interpret();

        PseudoSelector ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        EvaluatedExpression item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(1, item.getA().intValue());
        Assert.assertEquals(1, item.getB().intValue());
        Assert.assertNull(item.getText());

    	l = new MockListener();
    	selectors = new Parser("input:eq(n-0)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(1, item.getA().intValue());
        Assert.assertEquals(0, item.getB().intValue());
        Assert.assertNull(item.getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(-n)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(-1, item.getA().intValue());
        Assert.assertNull(item.getB());
        Assert.assertNull(item.getText());

        l = new MockListener();
    	selectors = new Parser("input:eq()", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertNull(item);

        l = new MockListener();
    	selectors = new Parser("input:eq(odd)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertNull(item);
        Assert.assertEquals("odd", ps.getExpression().getText());

        l = new MockListener();
    	selectors = new Parser("input:eq('odd')", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertNull(item);
        Assert.assertEquals("odd", ps.getExpression().getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(-)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("-", ps.getExpression().getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(++)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("++", ps.getExpression().getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(+1)", l).interpret();

    	ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals(1, item.getB().intValue());
        Assert.assertNull(item.getA());
        Assert.assertNull(item.getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(+1-)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());
        Assert.assertEquals("+1-", ps.getExpression().getText());

        l = new MockListener();
    	selectors = new Parser("input:eq(+1-1.5)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());

        l = new MockListener();
    	selectors = new Parser("input:eq(+1n-1.5)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertFalse(ps.getExpression().isValidGroupExpression());

        l = new MockListener();
    	selectors = new Parser("input:eq(+1n-1)", l).interpret();

        ps = (PseudoSelector) selectors.get(0).getSelectors().get(0).getSimpleSelectors().get(1);
        item = ps.getExpression().getEvaluatedExpression();
        Assert.assertTrue(ps.getExpression().isValidGroupExpression());

	}

}
