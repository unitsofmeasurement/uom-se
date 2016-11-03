/*
 * Units of Measurement Implementation for Java SE
 * Copyright (c) 2005-2016, Jean-Marie Dautelle, Werner Keil, V2COM.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 *    and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of JSR-363 nor the names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package tec.uom.se.internal.format.l10n;

/**
 * <dl>
 * <dt><b>Title: </b>
 * <dd>Decimal Format</dd>
 * <p>
 * <dt><b>Description: </b>
 * <dd>This is a simple number formatting/ parsing class. Besides the simple number formatting it also interprets shortcuts for thousand (k) million
 * (m) and billion (b).
 * <p/>
 * This Number Format class was adapted from the public domain javascript class found at http://www.mredkj.com/javascript/nfdocs.html</dd>
 * <p>
 * </dl>
 * 
 * @author <a href="mailto:jasone@greenrivercomputing.com">Jason Essington</a>
 * @version $Revision: 0.2 $
 * @deprecated use NumberFormat
 */
class NumberFormat2 {
  public static final String COMMA = ",";
  public static final String PERIOD = ".";
  public static final char DASH = '-';
  public static final char LEFT_PAREN = '(';
  public static final char RIGHT_PAREN = ')';

  // k/m/b Shortcuts
  private static final String THOUSAND = "k";
  private static final String MILLION = "m";
  private static final String BILLION = "b";

  // currency position constants
  public static final int CUR_POS_LEFT_OUTSIDE = 0;
  public static final int CUR_POS_LEFT_INSIDE = 1;
  public static final int CUR_POS_RIGHT_INSIDE = 2;
  public static final int CUR_POS_RIGHT_OUTSIDE = 3;

  // negative format constants
  public static final int NEG_LEFT_DASH = 0;
  public static final int NEG_RIGHT_DASH = 1;
  public static final int NEG_PARENTHESIS = 2;

  // constant to signal that fixed precision is not to be used
  public static final int ARBITRARY_PRECISION = -1;

  private String inputDecimalSeparator = PERIOD; // decimal character used on
  // the original string

  private boolean showGrouping = true;
  private String groupingSeparator = COMMA; // thousands grouping character
  private String decimalSeparator = PERIOD; // decimal point character

  private boolean showCurrencySymbol = false;
  private String currencySymbol = "$";
  private int currencySymbolPosition = CUR_POS_LEFT_OUTSIDE;

  private int negativeFormat = NEG_LEFT_DASH;
  private boolean isNegativeRed = false; // wrap the output in html that will
  // display red?

  private int decimalPrecision = 0;
  private boolean useFixedPrecision = false;
  private boolean truncate = false; // truncate to decimalPrecision rather
  // than rounding?

  private boolean isPercentage = false; // should the result be displayed as a

  // percentage?

  private NumberFormat2() {
  }

  /**
   * returns the default instance of NumberFormat2
   * 
   * @return
   */
  public static NumberFormat2 getInstance() {
    NumberFormat2 nf = new NumberFormat2();
    return nf;
  }

  /**
   * Returns a currency instance of number format
   * 
   * @return
   */
  public static NumberFormat2 getCurrencyInstance() {
    return getCurrencyInstance("$", true);
  }

  /**
   * Returns a currency instance of number format that uses curSymbol as the currency symbol
   * 
   * @param curSymbol
   * @return
   */
  public static NumberFormat2 getCurrencyInstance(String curSymbol) {
    return getCurrencyInstance(curSymbol, true);
  }

  /**
   * Returns a currency instance of number format that uses curSymbol as the currency symbol and either commas or periods as the thousands separator.
   * 
   * @param curSymbol
   *          Currency Symbol
   * @param useCommas
   *          true, uses commas as the thousands separator, false uses periods
   * @return
   */
  public static NumberFormat2 getCurrencyInstance(String curSymbol, boolean useCommas) {
    NumberFormat2 nf = new NumberFormat2();
    nf.isCurrency(true);
    nf.setCurrencySymbol(curSymbol);
    if (!useCommas) {
      nf.setDecimalSeparator(COMMA);
      nf.setGroupingSeparator(PERIOD);
    }
    nf.setFixedPrecision(2);
    return nf;
  }

  /**
   * Returns an instance that formats numbers as integers.
   * 
   * @return
   */
  public static NumberFormat2 getIntegerInstance() {
    NumberFormat2 nf = new NumberFormat2();
    nf.setShowGrouping(false);
    nf.setFixedPrecision(0);
    return nf;
  }

  public static NumberFormat2 getPercentInstance() {
    NumberFormat2 nf = new NumberFormat2();
    nf.isPercentage(true);
    nf.setFixedPrecision(2);
    nf.setShowGrouping(false);
    return nf;
  }

  public String format(String num) {
    return toFormatted(parse(num));
  }

  public double parse(String num) {
    return asNumber(num, inputDecimalSeparator);
  }

  /**
   * Static routine that attempts to create a double out of the supplied text. This routine is a bit smarter than Double.parseDouble()
   * 
   * @param num
   * @return
   */
  public static double parseDouble(String num, String decimalChar) {
    return asNumber(num, decimalChar);
  }

  public static double parseDouble(String num) {
    return parseDouble(num, PERIOD);
  }

  public void setInputDecimalSeparator(String val) {
    inputDecimalSeparator = val == null ? PERIOD : val;
  }

  public void setNegativeFormat(int format) {
    negativeFormat = format;
  }

  public void setNegativeRed(boolean isRed) {
    isNegativeRed = isRed;
  }

  public void setShowGrouping(boolean show) {
    showGrouping = show;
  }

  public void setDecimalSeparator(String separator) {
    decimalSeparator = separator;
  }

  public void setGroupingSeparator(String separator) {
    groupingSeparator = separator;
  }

  public void isCurrency(boolean isC) {
    showCurrencySymbol = isC;
  }

  public void setCurrencySymbol(String symbol) {
    currencySymbol = symbol;
  }

  public void setCurrencyPosition(int cp) {
    currencySymbolPosition = cp;
  }

  public void isPercentage(boolean pct) {
    isPercentage = pct;
  }

  /**
   * Sets the number of fixed precision decimal places should be displayed. To use arbitrary precision,
   * setFixedPrecision(NumberFormat2.ARBITRARY_PRECISION)
   * 
   * @param places
   */
  public void setFixedPrecision(int places) {
    useFixedPrecision = places != ARBITRARY_PRECISION;
    this.decimalPrecision = places < 0 ? 0 : places;
  }

  /**
   * Causes the number to be truncated rather than rounded to its fixed precision.
   * 
   * @param trunc
   */
  public void setTruncate(boolean trunc) {
    truncate = trunc;
  }

  /**
   * 
   * @param preSep
   *          raw number as text
   * @param PERIOD
   *          incoming decimal point
   * @param decimalSeparator
   *          outgoing decimal point
   * @param groupingSeparator
   *          thousands separator
   * @return
   */
  private String addSeparators(String preSep) {
    String nStr = preSep;
    int dpos = nStr.indexOf(PERIOD);
    String nStrEnd = "";
    if (dpos != -1) {
      nStrEnd = decimalSeparator + nStr.substring(dpos + 1, nStr.length());
      nStr = nStr.substring(0, dpos);
    }
    int l = nStr.length();
    for (int i = l; i > 0; i--) {
      nStrEnd = nStr.charAt(i - 1) + nStrEnd;
      if (i != 1 && ((l - i + 1) % 3) == 0)
        nStrEnd = groupingSeparator + nStrEnd;
    }
    return nStrEnd;
  }

  protected String toFormatted(double num) {
    String nStr;

    if (isPercentage)
      num = num * 100;

    nStr = useFixedPrecision ? toFixed(Math.abs(getRounded(num)), decimalPrecision) : Double.toString(num);

    nStr = showGrouping ? addSeparators(nStr) : nStr.replaceAll("\\" + PERIOD, decimalSeparator);

    String c0 = "";
    String n0 = "";
    String c1 = "";
    String n1 = "";
    String n2 = "";
    String c2 = "";
    String n3 = "";
    String c3 = "";

    String negSignL = "" + ((negativeFormat == NEG_PARENTHESIS) ? LEFT_PAREN : DASH);
    String negSignR = "" + ((negativeFormat == NEG_PARENTHESIS) ? RIGHT_PAREN : DASH);

    if (currencySymbolPosition == CUR_POS_LEFT_OUTSIDE) {
      if (num < 0) {
        if (negativeFormat == NEG_LEFT_DASH || negativeFormat == NEG_PARENTHESIS)
          n1 = negSignL;
        if (negativeFormat == NEG_RIGHT_DASH || negativeFormat == NEG_PARENTHESIS)
          n2 = negSignR;
      }
      if (showCurrencySymbol)
        c0 = currencySymbol;
    } else if (currencySymbolPosition == CUR_POS_LEFT_INSIDE) {
      if (num < 0) {
        if (negativeFormat == NEG_LEFT_DASH || negativeFormat == NEG_PARENTHESIS)
          n0 = negSignL;
        if (negativeFormat == NEG_RIGHT_DASH || negativeFormat == NEG_PARENTHESIS)
          n3 = negSignR;
      }
      if (showCurrencySymbol)
        c1 = currencySymbol;
    } else if (currencySymbolPosition == CUR_POS_RIGHT_INSIDE) {
      if (num < 0) {
        if (negativeFormat == NEG_LEFT_DASH || negativeFormat == NEG_PARENTHESIS)
          n0 = negSignL;
        if (negativeFormat == NEG_RIGHT_DASH || negativeFormat == NEG_PARENTHESIS)
          n3 = negSignR;
      }
      if (showCurrencySymbol)
        c2 = currencySymbol;
    } else if (currencySymbolPosition == CUR_POS_RIGHT_OUTSIDE) {
      if (num < 0) {
        if (negativeFormat == NEG_LEFT_DASH || negativeFormat == NEG_PARENTHESIS)
          n1 = negSignL;
        if (negativeFormat == NEG_RIGHT_DASH || negativeFormat == NEG_PARENTHESIS)
          n2 = negSignR;
      }
      if (showCurrencySymbol)
        c3 = currencySymbol;
    }
    nStr = c0 + n0 + c1 + n1 + nStr + n2 + c2 + n3 + c3 + (isPercentage ? "%" : "");

    if (isNegativeRed && num < 0) {
      nStr = "<font color='red'>" + nStr + "</font>";
    }

    return nStr;
  }

  /**
   * javascript only rounds to whole numbers, so we need to shift our decimal right, then round, then shift the decimal back left.
   * 
   * @param val
   * @return
   */
  private double getRounded(double val) {
    double exp = Math.pow(10, decimalPrecision);
    double rounded = val * exp;
    if (truncate)
      rounded = rounded >= 0 ? Math.floor(rounded) : Math.ceil(rounded);
    else
      rounded = Math.round(rounded);
    return rounded / exp;
  }

  private static native String toFixed(double val, int places) /*-{
                                                               return val.toFixed(places);
                                                               }-*/;

  private static double asNumber(String val, String inputDecimalValue) {
    String newVal = val;
    boolean isPercentage = false;
    // remove % if there is one
    if (newVal.indexOf('%') != -1) {
      newVal = newVal.replaceAll("\\%", "");
      isPercentage = true;
    }

    // convert abbreviations for thousand, million and billion
    newVal = newVal.toLowerCase().replaceAll(BILLION, "000000000");
    newVal = newVal.replaceAll(MILLION, "000000");
    newVal = newVal.replaceAll(THOUSAND, "000");

    // remove any characters that are not digit, decimal separator, +, -, (,
    // ), e, or E
    String re = "[^\\" + inputDecimalValue + "\\d\\-\\+\\(\\)eE]";
    newVal = newVal.replaceAll(re, "");

    // ensure that the first decimal separator is a . and remove the rest.
    int index = newVal.indexOf(inputDecimalValue);
    if (index != -1) {
      newVal = newVal.substring(0, index) + PERIOD + (newVal.substring(index + inputDecimalValue.length()).replaceAll("\\" + inputDecimalValue, ""));
    }

    // convert right dash and paren negatives to left dash negative
    if (newVal.charAt(newVal.length() - 1) == DASH) {
      newVal = newVal.substring(0, newVal.length() - 1);
      newVal = DASH + newVal;
    } else if (newVal.charAt(0) == LEFT_PAREN && newVal.charAt(newVal.length() - 1) == RIGHT_PAREN) {
      newVal = newVal.substring(1, newVal.length() - 1);
      newVal = DASH + newVal;
    }

    Double parsed;
    try {
      parsed = new Double(newVal);
      if (parsed.isInfinite() || parsed.isNaN())
        parsed = (double) 0;
    } catch (NumberFormatException e) {
      parsed = (double) 0;
    }

    return isPercentage ? parsed / 100 : parsed;
  }
}
