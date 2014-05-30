/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2010-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.unitsofmeasurement.impl.format.internal;

import org.unitsofmeasurement.impl.AbstractUnit;
import org.unitsofmeasurement.impl.format.SymbolMap;
import org.unitsofmeasurement.impl.util.SI;
import org.unitsofmeasurement.impl.util.SIPrefix;

import static org.unitsofmeasurement.impl.format.internal.UCUMParserConstants.*;

/**
 * <p> 
 * Parser definition for parsing {@link AbstractUnit Unit}s
 * according to the <a href="http://unitsofmeasure.org">
 * Uniform Code for Units of Measure</a>.
 * 
 * @author <a href="mailto:eric-r@northwestern.edu">Eric Russell</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @see <a href="http://unitsofmeasure.org">UCUM</a>
 * @version 5.1, April 16, 2014
 */
public class UCUMParser {

    private SymbolMap symbols;

    public UCUMParser(SymbolMap symbols, java.io.InputStream in) {
        this(in);
        this.symbols = symbols;
    }

//
// Parser productions
//
    final public AbstractUnit parseUnit() throws ParseException {
        AbstractUnit u;
        u = Term();
        jj_consume_token(0);
        {
            return u;
        }
        throw new Error("Missing return statement in function");
    }
    
    final public AbstractUnit Term() throws ParseException {
        AbstractUnit result = SI.ONE;
        AbstractUnit temp = SI.ONE;
        result = Component();
        label_1:
        while (true) {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case DOT:
                case SOLIDUS:
                    break;
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case DOT:
                    jj_consume_token(DOT);
                    temp = Component();
                    result = result.multiply(temp);
                    break;
                case SOLIDUS:
                    jj_consume_token(SOLIDUS);
                    temp = Component();
                    result = result.divide(temp);
                    break;
                default:
                    jj_la1[1] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {
            return result;
        }
        throw new Error("Missing return statement in function");
    }

    final public AbstractUnit Component() throws ParseException {
        AbstractUnit result = SI.ONE;
        Token token = null;
        if (jj_2_1(2147483647)) {
            result = Annotatable();
            token = jj_consume_token(ANNOTATION);
            {
                return result.annotate(token.image.substring(1, token.image.length() - 1));
            }
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ATOM:
                    result = Annotatable(); {
                    return result;
                }
                break;
                case ANNOTATION:
                    token = jj_consume_token(ANNOTATION); {
                    return result.annotate(token.image.substring(1, token.image.length() - 1));
                }
                break;
                case FACTOR:
                    token = jj_consume_token(FACTOR);
                    long factor = Long.parseLong(token.image); {
                    return result.multiply(factor);
                }
                break;
                case SOLIDUS:
                    jj_consume_token(SOLIDUS);
                    result = Component(); {
                    return SI.ONE.divide(result);
                }
                break;
                case 14:
                    jj_consume_token(14);
                    result = Term();
                    jj_consume_token(15); {
                    return result;
                }
                break;
                default:
                    jj_la1[2] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        throw new Error("Missing return statement in function");
    }

    final public AbstractUnit Annotatable() throws ParseException {
        AbstractUnit result = SI.ONE;
        Token token1 = null;
        Token token2 = null;
        if (jj_2_2(2147483647)) {
            result = SimpleUnit();
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case SIGN:
                    token1 = jj_consume_token(SIGN);
                    break;
                default:
                    jj_la1[3] = jj_gen;
            }
            token2 = jj_consume_token(FACTOR);
            int exponent = Integer.parseInt(token2.image);
            if ((token1 != null) && token1.image.equals("-")) {
                {
                    return result.pow(-exponent);
                }
            } else {
                {
                    return result.pow(exponent);
                }
            }
        } else {
            switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
                case ATOM:
                    result = SimpleUnit(); {
                    return result;
                }
                break;
                default:
                    jj_la1[4] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        throw new Error("Missing return statement in function");
    }

    final public AbstractUnit SimpleUnit() throws ParseException {
        Token token = null;
        token = jj_consume_token(ATOM);
        AbstractUnit unit = symbols.getUnit(token.image);
        if (unit == null) {
            SIPrefix prefix = symbols.getPrefix(token.image);
            if (prefix != null) {
                String prefixSymbol = symbols.getSymbol(prefix);
                unit = symbols.getUnit(token.image.substring(prefixSymbol.length()));
                if (unit != null) {
                    {
                        return unit.transform(prefix.getConverter());
                    }
                }
            }
            {
                throw new ParseException();
            }
        } else {
            {
                return unit;
            }
        }
        throw new Error("Missing return statement in function");
    }

    private boolean jj_2_1(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_1();
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(0, xla);
        }
    }

    private boolean jj_2_2(int xla) {
        jj_la = xla;
        jj_lastpos = jj_scanpos = token;
        try {
            return !jj_3_2();
        } catch (LookaheadSuccess ls) {
            return true;
        } finally {
            jj_save(1, xla);
        }
    }

    private boolean jj_3_1() {
        if (jj_3R_2())
            return true;
        return jj_scan_token(ANNOTATION);
    }

    private boolean jj_3R_5() {
        return jj_3R_3();
    }

    private boolean jj_3R_4() {
        if (jj_3R_3())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(10))
            jj_scanpos = xsp;
        return jj_scan_token(FACTOR);
    }

    private boolean jj_3_2() {
        if (jj_3R_3())
            return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_scan_token(10))
            jj_scanpos = xsp;
        return jj_scan_token(FACTOR);
    }

    private boolean jj_3R_3() {
        return jj_scan_token(ATOM);
    }

    private boolean jj_3R_2() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_4()) {
            jj_scanpos = xsp;
            if (jj_3R_5())
                return true;
        }
        return false;
    }
    /** Generated Token Manager. */
    public UCUMParserTokenManager token_source;

    SimpleCharStream jj_input_stream;

    /** Current token. */
    public Token token;

    /** Next token. */
    public Token jj_nt;

    private int jj_ntk;

    private Token jj_scanpos, jj_lastpos;

    private int jj_la;

    private int jj_gen;

    final private int[] jj_la1 = new int[5];

    static private int[] jj_la1_0;

    static {
        jj_la1_init_0();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[]{0x1800, 0x1800, 0x7300, 0x400, 0x2000,};
    }
    final private JJCalls[] jj_2_rtns = new JJCalls[2];

    private boolean jj_rescan = false;

    private int jj_gc = 0;

    /** Constructor with InputStream. */
    public UCUMParser(java.io.InputStream stream) {
        this(stream, null);
    }

    /** Constructor with InputStream and supplied encoding */
    public UCUMParser(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source = new UCUMParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /** Reinitialise. */
    public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }

    /** Reinitialise. */
    public void ReInit(java.io.InputStream stream, String encoding) {
        try {
            jj_input_stream.ReInit(stream, encoding, 1, 1);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /** Constructor. */
    public UCUMParser(java.io.Reader stream) {
        jj_input_stream = new SimpleCharStream(stream, 1, 1);
        token_source = new UCUMParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /** Reinitialise. */
    public void ReInit(java.io.Reader stream) {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /** Constructor with generated Token Manager. */
    public UCUMParser(UCUMParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    /** Reinitialise. */
    public void ReInit(UCUMParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 5; i++) {
            jj_la1[i] = -1;
        }
        for (int i = 0; i < jj_2_rtns.length; i++) {
            jj_2_rtns[i] = new JJCalls();
        }
    }

    private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null)
            token = token.next;
        else
            token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (JJCalls jj_2_rtn : jj_2_rtns) {
                    JJCalls c = jj_2_rtn;
                    while (c != null) {
                        if (c.gen < jj_gen)
                            c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    static private final class LookaheadSuccess extends java.lang.Error {

        /**
         *
         */
        private static final long serialVersionUID = -1747326813448392305L;

    }
    final private LookaheadSuccess jj_ls = new LookaheadSuccess();

    private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0;
            Token tok = token;
            while (tok != null && tok != jj_scanpos) {
                i++;
                tok = tok.next;
            }
            if (tok != null)
                jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind)
            return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos)
            throw jj_ls;
        return false;
    }

    /** Get the next Token. */
    final public Token getNextToken() {
        if (token.next != null)
            token = token.next;
        else
            token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /** Get the specific Token. */
    final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null)
                t = t.next;
            else
                t = t.next = token_source.getNextToken();
        }
        return t;
    }

    private int jj_ntk() {
        if ((jj_nt = token.next) == null)
            return (jj_ntk = (token.next = token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }
    private java.util.List<int[]> jj_expentries = new java.util.ArrayList<>();

    private int[] jj_expentry;

    private int jj_kind = -1;

    private int[] jj_lasttokens = new int[100];

    private int jj_endpos;

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100)
            return;
        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];
            System.arraycopy(jj_lasttokens, 0, jj_expentry, 0, jj_endpos);
            jj_entries_loop:
            for (int[] jj_expentry1 : jj_expentries) {
                int[] oldentry = (int[]) (jj_expentry1);
                if (oldentry.length == jj_expentry.length) {
                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            continue jj_entries_loop;
                        }
                    }
                    jj_expentries.add(jj_expentry);
                    break;
                }
            }
            if (pos != 0)
                jj_lasttokens[(jj_endpos = pos) - 1] = kind;
        }
    }

    /** Generate ParseException. */
    public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[16];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 5; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 16; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    /** Enable tracing. */
    final public void enable_tracing() {
    }

    /** Disable tracing. */
    final public void disable_tracing() {
    }

    private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 2; i++) {
            try {
                JJCalls p = jj_2_rtns[i];
                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg;
                        jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                        }
                    }
                    p = p.next;
                } while (p != null);
            } catch (LookaheadSuccess ls) {
            }
        }
        jj_rescan = false;
    }

    private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) {
                p = p.next = new JJCalls();
                break;
            }
            p = p.next;
        }
        p.gen = jj_gen + xla - jj_la;
        p.first = token;
        p.arg = xla;
    }

    static final class JJCalls {

        int gen;

        Token first;

        int arg;

        JJCalls next;

    }
}
