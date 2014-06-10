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
package org.unitsofmeasurement.impl.format;

import org.unitsofmeasurement.impl.AbstractUnit;
import org.unitsofmeasurement.impl.function.AbstractConverter;
import org.unitsofmeasurement.impl.util.SIPrefix;

import javax.measure.Unit;
import javax.measure.function.UnitConverter;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p> This class provides a set of mappings between
 *     {@link AbstractUnit physical units} and symbols (both ways), between {@link SIPrefix prefixes}
 * and symbols (both ways), and from {@link AbstractConverter
 *     physical unit converters} to {@link SIPrefix prefixes} (one way). No attempt
 * is made to verify the uniqueness of the mappings.</p>
 *
 * <p> Mappings are read from a
 * <code>ResourceBundle</code>, the keys of which should consist of a
 * fully-qualified class name, followed by a dot ('.'), and then the name of a
 * static field belonging to that class, followed optionally by another dot and a
 * number. If the trailing dot and number are not present, the value associated
 * with the key is treated as a
 *     {@link SymbolMap#label(AbstractUnit, String) label}, otherwise if the trailing
 * dot and number are present, the value is treated as an {@link SymbolMap#alias(AbstractUnit,String) alias}.
 * Aliases map from String to Unit only, whereas labels map in both directions. A
 * given unit may have any number of aliases, but may have only one label.</p>
 *
 * @author <a href="mailto:eric-r@northwestern.edu">Eric Russell</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.3, March 20, 2014
 */
public final class SymbolMap {
	private static final Logger logger = Logger.getLogger(SymbolMap.class.getName());
	
    private final Map<String, Unit<?>> symbolToUnit;
    private final Map<Unit<?>, String> unitToSymbol;
    private final Map<String, Object> symbolToPrefix;
    private final Map<Object, String> prefixToSymbol;
    private final Map<UnitConverter, SIPrefix> converterToPrefix;

    /**
     * Creates an empty mapping.
     */
    private SymbolMap() {
        symbolToUnit = new HashMap<>();
        unitToSymbol = new HashMap<>();
        symbolToPrefix = new HashMap<>();
        prefixToSymbol = new HashMap<>();
        converterToPrefix = new HashMap<>();
    }

    /** 
     * Creates a symbol map from the specified resource bundle,
     *
     * @param rb the resource bundle.
     */
    private SymbolMap(ResourceBundle rb) {
        this();
        for (Enumeration<String> i = rb.getKeys(); i.hasMoreElements();) {
            String fqn = i.nextElement();
            String symbol = rb.getString(fqn);
            boolean isAlias = false;
            int lastDot = fqn.lastIndexOf('.');
            String className = fqn.substring(0, lastDot);
            String fieldName = fqn.substring(lastDot + 1, fqn.length());
            if (Character.isDigit(fieldName.charAt(0))) {
                isAlias = true;
                fqn = className;
                lastDot = fqn.lastIndexOf('.');
                className = fqn.substring(0, lastDot);
                fieldName = fqn.substring(lastDot + 1, fqn.length());
            }
            try {
                Class<?> c = Class.forName(className);
                Field field = c.getField(fieldName);
                Object value = field.get(null);
                if (value instanceof Unit<?>) {
                    if (isAlias) {
                        alias((Unit)value, symbol);
                    } else {
                        label((AbstractUnit<?>) value, symbol);
                    }
                } else if (value instanceof SIPrefix) {
                    label((SIPrefix) value, symbol);
                } else {
                    throw new ClassCastException("unable to cast " + value + " to Unit or Prefix");
                }
            } catch (Exception error) {
                logger.log(Level.SEVERE, "Error", error);
            }
        }
    }
    
    /** 
     * Creates a symbol map from the specified resource bundle,
     *
     * @param rb the resource bundle.
     */
    public static SymbolMap of(ResourceBundle rb) {
    	return new SymbolMap(rb);
    }

    /**
     * Attaches a label to the specified unit. For example:[code]
     *    symbolMap.label(DAY.multiply(365), "year");
     *    symbolMap.label(NonSI.FOOT, "ft");
     * [/code]
     *
     * @param unit the unit to label.
     * @param symbol the new symbol for the unit.
     */
    public void label(AbstractUnit<?> unit, String symbol) {
        symbolToUnit.put(symbol, unit);
        unitToSymbol.put(unit, symbol);
    }

    /**
     * Attaches an alias to the specified unit. Multiple aliases may be
     * attached to the same unit. Aliases are used during parsing to
     * recognize different variants of the same unit.[code]
     *     symbolMap.alias(NonSI.FOOT, "foot");
     *     symbolMap.alias(NonSI.FOOT, "feet");
     *     symbolMap.alias(SI.METER, "meter");
     *     symbolMap.alias(SI.METER, "metre");
     * [/code]
     *
     * @param unit the unit to label.
     * @param symbol the new symbol for the unit.
     */
    public void alias(Unit<?> unit, String symbol) {
        symbolToUnit.put(symbol, unit);
    }

    /**
     * Attaches a label to the specified prefix. For example:[code]
     *    symbolMap.label(SIPrefix.GIGA, "G");
     *    symbolMap.label(SIPrefix.MICRO, "µ");
     * [/code]
     */
    public void label(SIPrefix prefix, String symbol) {
        symbolToPrefix.put(symbol, prefix);
        prefixToSymbol.put(prefix, symbol);
        converterToPrefix.put(prefix.getConverter(), prefix);
    }

    /**
     * Returns the unit for the specified symbol.
     * 
     * @param symbol the symbol.
     * @return the corresponding unit or <code>null</code> if none.
     */
    public Unit<?> getUnit(String symbol) {
        return symbolToUnit.get(symbol);
    }

    /**
     * Returns the symbol (label) for the specified unit.
     *
     * @param unit the corresponding symbol.
     * @return the corresponding symbol or <code>null</code> if none.
     */
    public String getSymbol(AbstractUnit<?> unit) {
        return unitToSymbol.get(unit);
    }

    /**
     * Returns the prefix (if any) for the specified symbol.
     *
     * @param symbol the unit symbol.
     * @return the corresponding prefix or <code>null</code> if none.
     */
    public SIPrefix getPrefix(String symbol) {
        for (String pfSymbol : symbolToPrefix.keySet()) {
            if (symbol.startsWith(pfSymbol)) {
                return (SIPrefix) symbolToPrefix.get(pfSymbol);
            }
        }
        return null;
    }

    /**
     * Returns the prefix for the specified converter.
     *
     * @param converter the unit converter.
     * @return the corresponding prefix or <code>null</code> if none.
     */
    public SIPrefix getPrefix(UnitConverter converter) {
        return (SIPrefix) converterToPrefix.get(converter);
    }

    /**
     * Returns the symbol for the specified prefix.
     *
     * @param prefix the prefix.
     * @return the corresponding symbol or <code>null</code> if none.
     */
    public String getSymbol(SIPrefix prefix) {
        return prefixToSymbol.get(prefix);
    }
}
