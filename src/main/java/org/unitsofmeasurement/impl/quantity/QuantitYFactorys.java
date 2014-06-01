package org.unitsofmeasurement.impl.quantity;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Quantity;

/**
 * Utils for Quantity factory
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class QuantitYFactorys {

	private static final Logger logger = Logger
			.getLogger(QuantitYFactorys.class.getName());

	private static final Level LOG_LEVEL = Level.FINE;

	public static QuantityBuilder of(final Class<?> type) {
		logger.log(LOG_LEVEL, "Type: " + type + ": " + type.isInterface());
		if (!type.isInterface()) {
			return new QuantityUtilsClass();
		} else {
			return new QuantityUtilsInterface();
		}
	}
	/**
	 * A abstraction to factory producing simple quantities instances
	 */
	public interface QuantityBuilder {
		<Q extends Quantity<Q>> QuantityFactory<Q> create(Class<Q> type,
				Map<Class, QuantityFactory> instance);
	}
	
	/**
	 * Quantity utils Factory to concrete class
	 */
	static class QuantityUtilsClass implements QuantityBuilder {

		@Override
		public <Q extends Quantity<Q>> QuantityFactory<Q> create(Class<Q> type,
				Map<Class, QuantityFactory> instance) {
			QuantityFactory<Q> factory;

			if (Objects.nonNull(type) && Objects.nonNull(type.getInterfaces())
					& type.getInterfaces().length > 0) {
				logger.log(LOG_LEVEL, "Type0: " + type.getInterfaces()[0]);
				Class<?> type2 = type.getInterfaces()[0];

				factory = Optional.ofNullable(instance.get(type2)).orElse(
						new Default<>((Class<Q>) type2));
				instance.putIfAbsent(type2, factory);
			} else {
				factory = Optional.ofNullable(instance.get(type)).orElse(
						new Default<>(type));
				instance.putIfAbsent(type, factory);
			}
			return factory;
		}

	}
	/**
	 * Quantity utils Factory to interface class
	 */
	static class QuantityUtilsInterface implements QuantityBuilder {

		@Override
		public <Q extends Quantity<Q>> QuantityFactory<Q> create(Class<Q> type,
				Map<Class, QuantityFactory> instance) {
			QuantityFactory<Q> factory = Optional
					.ofNullable(instance.get(type)).orElse(new Default<>(type));
			instance.putIfAbsent(type, factory);
			return factory;
		}

	}
}
