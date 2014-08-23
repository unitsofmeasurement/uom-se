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
package tec.uom.se.util;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

import javax.measure.function.Nameable;

/**
 * TimedData is a container for a data value that keeps track of its age. This class keeps track of the birth time of a bit of data, i.e. time the object is instantiated.<br/>The TimedData MUST be immutable.
 * 
 * @param <T>
 *            The data value.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.4 ($Revision: 428 $)
 * @see <a href="http://en.wikipedia.org/wiki/Time_series"> Wikipedia:
 *      Time Series</a>
 */
public class TimedData<T> implements Nameable, Supplier<T> {
    private final T value;
    private long timestamp;
    private Instant instant;
    private String name;
    
    /**
     * Construct an instance of TimedData with a value and timestamp.
     *
     * @param data The value of the TimedData.
     * @param time The timestamp of the TimedData.
     */
    protected TimedData(T value, long time) {
    	this.value = value;
        this.timestamp = time;
        this.instant = Instant.ofEpochMilli(time);
    }    
    
    /**
     * Returns an {@code MeasurementRange} with the specified values.
     *
     * @param <T> the class of the value
     * @param val The minimum value for the measurement range.
     * @param time The maximum value for the measurement range.
     * @return an {@code MeasurementRange} with the given values
     */
    public static <T> TimedData<T> of(T val, long time) {
        return new TimedData<T>(val, time);
    }
    
    /**
     * Returns the time with which this TimedData was created.
     * 
     * @return the time of creation
     */
    public long getTimestamp() {
        return timestamp;
    }
    
	public String getName() {
		return name;
	}

//	@Override
	public T get() {
		return value;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals()
	 */
    @Override
    public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
        if(obj instanceof TimedData<?>)  {
            @SuppressWarnings("unchecked")
            final TimedData<T> other = (TimedData<T>) obj;
			return Objects.equals(get(), other.get())
					&& Objects.equals(getTimestamp(), other.getTimestamp())
					&& Objects.equals(getName(), other.getName());

        }
        return false;
    }

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
        return Objects.hash(value, name);
    }

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder()
            .append("data= ").append(get())
            .append(", timestamp= ").append(getTimestamp());
        if (name != null && name.length()>0) {
        	sb.append(", name= ").append(getName());
        }
        return sb.toString();
    }

	public Instant getInstant() {
		return instant;
	}

}
