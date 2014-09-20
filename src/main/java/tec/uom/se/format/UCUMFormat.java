/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
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
package tec.uom.se.format;

import tec.uom.se.AbstractUnit;
import tec.uom.se.format.internal.ParseException;
import tec.uom.se.format.internal.TokenMgrError;
import tec.uom.se.format.internal.UCUMParser;
import tec.uom.se.function.AbstractConverter;
import tec.uom.se.function.MultiplyConverter;
import tec.uom.se.function.RationalConverter;
import tec.uom.se.unit.AnnotatedUnit;
import tec.uom.se.util.SI;
import tec.uom.se.util.SIPrefix;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;
import javax.measure.function.UnitConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParsePosition;
import java.util.*;


/**
 * <p> This class provides the interface for formatting and parsing
 * {@link AbstractUnit units} according to the <a
 * href="http://unitsofmeasure.org/">Uniform Code for Units of Measure</a>
 * (UCUM). </p>
 *
 * <p> For a technical/historical overview of this format please read <a
 * href="http://www.pubmedcentral.nih.gov/articlerender.fcgi?artid=61354"> Units
 * of Measure in Clinical Information Systems</a>. </p>
 *
 * <p> As of revision 1.16, the BNF in the UCUM standard contains an <a
 * href="http://unitsofmeasure.org/ticket/4">error</a>. I've attempted to work
 * around the problem by modifying the BNF productions for &lt;Term&gt;. Once the
 * error in the standard is corrected, it may be necessary to modify the
 * productions in the UCUMParser.jj file to conform to the standard. </p>
 *
 * @author <a href="mailto:eric-r@northwestern.edu">Eric Russell</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.5.1, 20 March 2014
 */
public abstract class UCUMFormat implements UnitFormat {

    /**
	 * 
	 */
//	private static final long serialVersionUID = 8586656823290135155L;
	
	// A helper to declare bundle names for all instances
    private static final String BUNDLE_BASE = UCUMFormat.class.getName();

    // /////////////////
    // Class methods //
    // /////////////////

    /**
	 * Returns the instance for formatting/parsing using the given variant
	 * @param variant the <strong>UCUM</strong> variant to use
	 */
    public static UCUMFormat getInstance(Variant variant) {
        switch (variant) {
        	case CASE_INSENSITIVE:
        		return Parsing.DEFAULT_CI;
        	case CASE_SENSITIVE:
        		 return Parsing.DEFAULT_CS;
        	case PRINT:
        		return Print.DEFAULT;
        	default:
        		throw new IllegalArgumentException("Unknown variant: " + variant);
        }
    }

    /**
	 * Returns an instance for formatting and parsing using user
	 * defined symbols
	 * @param variant the <strong>UCUM</strong> variant to use
	 * @param symbolMap the map of user defined symbols to use
	 */
    public static UCUMFormat getInstance(Variant variant, 
    		SymbolMap symbolMap) {
    	switch (variant) {
	     	case CASE_INSENSITIVE:
	     		return new Parsing(symbolMap, false);
	     	case CASE_SENSITIVE:
	     		return new Parsing(symbolMap, true);
	     	case PRINT:
	     		return new Print(symbolMap);
	     	default:
	     		throw new IllegalArgumentException("Unknown variant: " + variant);
    	}
    }
    /**
     * The symbol map used by this instance to map between
     * {@link AbstractUnit Unit}s and
     * <code>String</code>s.
     */
    final SymbolMap symbolMap;

    // ////////////////
    // Constructors //
    // ////////////////
    /**
	 * Base constructor.
	 */
    UCUMFormat(SymbolMap symbolMap) {
        this.symbolMap = symbolMap;
    }

    /////////////
    // Parsing //
    /////////////
    public abstract Unit<? extends Quantity<?>> parse(CharSequence csq,
            ParsePosition cursor) throws ParserException;
    
    @Override
    public abstract Unit<? extends Quantity<?>> parse(CharSequence csq) throws ParserException;

    ////////////////
    // Formatting //
    // //////////////
    public Appendable format(Unit<?> unknownUnit, Appendable appendable) throws IOException {
        if (!(unknownUnit instanceof AbstractUnit)) {
            throw new UnsupportedOperationException("The UCUM format supports only known units (AbstractUnit instances)");
        }
        AbstractUnit unit = (AbstractUnit) unknownUnit;
        CharSequence symbol;
        CharSequence annotation = null;
        if (unit instanceof AnnotatedUnit) {
            AnnotatedUnit annotatedUnit = (AnnotatedUnit) unit;
            unit = annotatedUnit.getActualUnit();
            annotation = annotatedUnit.getAnnotation();
        }
        String mapSymbol = symbolMap.getSymbol(unit);
        if (mapSymbol != null) {
            symbol = mapSymbol;
        } else if (unit.getProductUnits() != null) {
            @SuppressWarnings("unchecked")
			Map<? extends AbstractUnit<?>, Integer> productUnits = unit.getProductUnits();
            StringBuffer app = new StringBuffer();
            for (AbstractUnit<?> u : productUnits.keySet()) {
                StringBuffer temp = new StringBuffer();
                temp = (StringBuffer) format(u, temp);
                if ((temp.indexOf(".") >= 0) || (temp.indexOf("/") >= 0)) {
                    temp.insert(0, '(');
                    temp.append(')');
                }
                int pow = productUnits.get(u);
                if (app.length() > 0) { // Not the first unit.
                    if (pow >= 0) {
                        app.append('.');
                    } else {
                        app.append('/');
                        pow = -pow;
                    }
                } else { // First unit.
                    if (pow < 0) {
                        app.append("1/");
                        pow = -pow;
                    }
                }
                app.append(temp);
                if (pow != 1) {
                    app.append(Integer.toString(pow));
                }
            }
            symbol = app;
        } else if (!unit.isSI() || unit.equals(SI.KILOGRAM)) {
            final StringBuilder temp = new StringBuilder();
            UnitConverter converter;
            boolean printSeparator;
            if (unit.equals(SI.KILOGRAM)) {
                // A special case because KILOGRAM is a BaseUnit instead of
                // a transformed unit, for compatability with existing SI
                // unit system.
                format(SI.GRAM, temp);
                converter = SIPrefix.KILO.getConverter();
                printSeparator = true;
            } else {
                AbstractUnit<?> parentUnit = unit.getSystemUnit();
                converter = unit.getConverterTo(parentUnit);
                if (parentUnit.equals(SI.KILOGRAM)) {
                    // More special-case hackery to work around gram/kilogram
                    // incosistency
                    parentUnit = SI.GRAM;
                    converter = converter.concatenate(SIPrefix.KILO.getConverter());
                }
                format(parentUnit, temp);
                printSeparator = !parentUnit.equals(SI.ONE);
            }
            formatConverter(converter, printSeparator, temp);
            symbol = temp;
        } else if (unit.getSymbol() != null) {
            symbol = unit.getSymbol();
        } else {
            throw new IllegalArgumentException(
                    "Cannot format the given Object as UCUM units (unsupported unit "
                    + unit.getClass().getName()
                    + "). "
                    + "Custom units types should override the toString() method as the default implementation uses the UCUM format.");
        }

        appendable.append(symbol);
        if (annotation != null && annotation.length() > 0) {
            appendAnnotation(symbol, annotation, appendable);
        }

        return appendable;
    }

    void appendAnnotation(CharSequence symbol,
                          CharSequence annotation, Appendable appendable) throws IOException {
        appendable.append('{');
        appendable.append(annotation);
        appendable.append('}');
    }

    /**
	 * Formats the given converter to the given StringBuffer. This is similar to
	 * what {@link ConverterFormat} does, but there's no need to worry about
	 * operator precedence here, since UCUM only supports multiplication,
	 * division, and exponentiation and expressions are always evaluated left-
	 * to-right.
	 * 
	 * @param converter
	 *            the converter to be formatted
	 * @param continued
	 *            <code>true</code> if the converter expression should begin
	 *            with an operator, otherwise <code>false</code>. This will
	 *            always be true unless the unit being modified is equal to
	 *            Unit.ONE.
	 * @param buffer
	 *            the <code>StringBuffer</code> to append to. Contains the
	 *            already-formatted unit being modified by the given converter.
	 */
    void formatConverter(UnitConverter converter, boolean continued,
            StringBuilder buffer) {
        boolean unitIsExpression = ((buffer.indexOf(".") >= 0) || (buffer.indexOf("/") >= 0));
        SIPrefix prefix = symbolMap.getPrefix(converter);
        if ((prefix != null) && (!unitIsExpression)) {
            buffer.insert(0, symbolMap.getSymbol(prefix));
        } else if (converter == AbstractConverter.IDENTITY) {
            // do nothing
        } else if (converter instanceof MultiplyConverter) {
            if (unitIsExpression) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            MultiplyConverter multiplyConverter = (MultiplyConverter) converter;
            double factor = multiplyConverter.getFactor();
            long lFactor = (long) factor;
            if ((lFactor != factor) || (lFactor < -9007199254740992L)
                    || (lFactor > 9007199254740992L)) {
                throw new IllegalArgumentException(
                        "Only integer factors are supported in UCUM");
            }
            if (continued) {
                buffer.append('.');
            }
            buffer.append(lFactor);
        } else if (converter instanceof RationalConverter) {
            if (unitIsExpression) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            RationalConverter rationalConverter = (RationalConverter) converter;
            if (!rationalConverter.getDividend().equals(BigInteger.ONE)) {
                if (continued) {
                    buffer.append('.');
                }
                buffer.append(rationalConverter.getDividend());
            }
            if (!rationalConverter.getDivisor().equals(BigInteger.ONE)) {
                buffer.append('/');
                buffer.append(rationalConverter.getDivisor());
            }
        } else { // All other converter type (e.g. exponential) we use the string representation.
            buffer.insert(0, converter.toString() + "(");
            buffer.append(")");
        }
    }

//    static final ResourceBundle.Control getControl(final String key) {
//    	return new ResourceBundle.Control() {
//      		 @Override
//   		 public List<Locale> getCandidateLocales(String baseName, Locale locale) {
//   		     if (baseName == null)
//   			 throw new NullPointerException();
//   		     if (locale.equals(new Locale(key))) {
//   			 return Arrays.asList(
//   			     locale,
//   			     Locale.GERMANY,
//   			     // no Locale.GERMAN here
//   			     Locale.ROOT);
//   		     } else if (locale.equals(Locale.GERMANY)) {
//   			 return Arrays.asList(
//   			     locale,
//   			     // no Locale.GERMAN here
//   			     Locale.ROOT);
//   		     }
//   		     return super.getCandidateLocales(baseName, locale);
//      		 }
//	     };
//    }
    
    // /////////////////
    // Inner classes //
    // /////////////////
    
    /**
     * Variant of unit representation in the UCUM standard
     * 
     * @see <a href="http://unitsofmeasure.org/ucum.html#section-Character-Set-and-Lexical-Rules">
     * UCUM - Character Set and Lexical Rules</a>
     */
    public static enum Variant {
    	CASE_SENSITIVE, CASE_INSENSITIVE, PRINT
    }
    
    /**
	 * The Print format is used to output units according to the "print" column
	 * in the UCUM standard. Because "print" symbols in UCUM are not unique,
	 * this class of UCUMFormat may not be used for parsing, only for
	 * formatting.
	 */
    private static final class Print extends UCUMFormat {

        /**
         *
         */
//        private static final long serialVersionUID = 2990875526976721414L;
        private static final SymbolMap PRINT_SYMBOLS = SymbolMap.of(
                ResourceBundle.getBundle(BUNDLE_BASE + "_Print"));
        private static final Print DEFAULT = new Print(PRINT_SYMBOLS);

        public Print(SymbolMap symbols) {
            super(symbols);
        }

        @Override
        public Unit<? extends Quantity<?>> parse(CharSequence csq,
                ParsePosition pos) throws IllegalArgumentException {
            throw new UnsupportedOperationException(
                    "The print format is for pretty-printing of units only. Parsing is not supported.");
        }

        @Override
        void appendAnnotation(CharSequence symbol,
                              CharSequence annotation, Appendable appendable)
                throws IOException {
            if (symbol != null && symbol.length() > 0) {
                appendable.append('(');
                appendable.append(annotation);
                appendable.append(')');
            } else {
                appendable.append(annotation);
            }
        }

        @Override
        public Unit<? extends Quantity<?>> parse(CharSequence csq) throws IllegalArgumentException {
        	return parse(csq, new ParsePosition(0));
        
		}
    }

    /**
	 * The Parsing format outputs formats and parses units according to the
	 * "c/s" or "c/i" column in the UCUM standard, depending on which SymbolMap
	 * is passed to its constructor.
	 */
    private static final class Parsing extends UCUMFormat {

//        private static final long serialVersionUID = -922531801940132715L;
        private static final SymbolMap CASE_SENSITIVE_SYMBOLS = SymbolMap.of(
                ResourceBundle.getBundle(BUNDLE_BASE + "_CS", new Locale("", "CS"),
               	     new ResourceBundle.Control() {
               		 @Override
               		 public List<Locale> getCandidateLocales(String baseName, Locale locale) {
               		     if (baseName == null)
               			 throw new NullPointerException();
               		     if (locale.equals(new Locale("", "CS"))) {
               		    	 return Arrays.asList(
               					     locale,
               					     Locale.ROOT);
               		     }
               		     return super.getCandidateLocales(baseName, locale);
               		 }
               	     }));
        private static final SymbolMap CASE_INSENSITIVE_SYMBOLS = SymbolMap.of(
                ResourceBundle.getBundle(BUNDLE_BASE + "_CI", 
                  	     new ResourceBundle.Control() {
                  		 @Override
                  		 public List<Locale> getCandidateLocales(String baseName, Locale locale) {
                  		     if (baseName == null)
                  			 throw new NullPointerException();
                  		     if (locale.equals(new Locale("", "CI"))) {
                  		    	 return Arrays.asList(
                  					     locale,
                  					     Locale.ROOT);
                  		     } else if (locale.equals(Locale.GERMANY)) { // TODO why GERMANY?
                  			 return Arrays.asList(
                  			     locale,
                  			     // no Locale.GERMAN here
                  			     Locale.ROOT);
                  		     }
                  		     return super.getCandidateLocales(baseName, locale);
                  		 }
                  	     }));
        private static final Parsing DEFAULT_CS = new Parsing(
                CASE_SENSITIVE_SYMBOLS, true);
        private static final Parsing DEFAULT_CI = new Parsing(
                CASE_INSENSITIVE_SYMBOLS, false);
        private final boolean caseSensitive;

        public Parsing(SymbolMap symbols, boolean caseSensitive) {
            super(symbols);
            this.caseSensitive = caseSensitive;
        }

        @Override
        public Unit<? extends Quantity<?>> parse(CharSequence csq,
                ParsePosition cursor) throws ParserException {
            // Parsing reads the whole character sequence from the parse
            // position.
            int start = cursor.getIndex();
            int end = csq.length();
            if (end <= start) {
                return SI.ONE;
            }
            String source = csq.subSequence(start, end).toString().trim();
            if (source.length() == 0) {
                return SI.ONE;
            }
            if (!caseSensitive) {
                source = source.toUpperCase();
            }
            UCUMParser parser = new UCUMParser(symbolMap,
                    new ByteArrayInputStream(source.getBytes()));
            try {
                Unit<?> result = parser.parseUnit();
                cursor.setIndex(end);
                return result;
            } catch (ParseException e) {
                if (e.currentToken != null) {
                    cursor.setErrorIndex(start + e.currentToken.endColumn);
                } else {
                    cursor.setErrorIndex(start);
                }
                throw new ParserException(e);
            } catch (TokenMgrError e) {
                cursor.setErrorIndex(start);
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        
        @SuppressWarnings("rawtypes")
		@Override
        public Unit<? extends Quantity<?>> parse(CharSequence csq) throws ParserException {
        	return parse(csq, new ParsePosition(0));
        }
    }
}
