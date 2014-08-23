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
package tec.uom.se.function;

/**
 * Represents a supplier of {@code double}-valued multiplication factors. This is a
 * {@code double}-producing specialization of {@code Supplier}.
 *
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="http://download.java.net/jdk8/docs/api/java/util/function/package-summary.html">functional interface</a>
 * whose functional method is {@link #getFactor()}.
 *
 * @see <a href="http://download.java.net/jdk8/docs/api/java/util/function/DoubleSupplier.html">DoubleSupplier</a>
 * @author Werner Keil
 * @version 0.4, $Date: 2014-03-18 23:55:19 +0100 (Di, 18 Mär 2014) $
 * @deprecated So far only used by Enum impl, consider removing
 */
//equivalent to @FunctionalInterface
public interface DoubleFactorSupplier {
    double getFactor();
}
