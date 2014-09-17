package tec.uom.se.quantity;

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
class QuantityFactories {
/* FIXME the name of both this class and the of() method are misleading
	"*Factories" suggests a static method facade like JDK Collections or JSR 354 MonetaryAmounts,...
	However the of() method doesn't return an instance of QuantityFactories but a QuantityBuilder.
	Some renaming required either of() into something more appropriate or the whole class */
	private static final Logger logger = Logger
			.getLogger(QuantityFactories.class.getName());

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
	 * An abstraction to producing simple quantities instances
	 */
	public interface QuantityBuilder {
		<Q extends Quantity<Q>> AbstractQuantityFactory<Q> build(Class<Q> type,
				Map<Class, AbstractQuantityFactory> instance);
	}

	/**
	 * Quantity utils Factory to concrete class
	 */
	static class QuantityUtilsClass implements QuantityBuilder {

		@Override
		public <Q extends Quantity<Q>> AbstractQuantityFactory<Q> build(Class<Q> type,
				Map<Class, AbstractQuantityFactory> instance) {
			AbstractQuantityFactory<Q> factory;

			if (Objects.nonNull(type) && Objects.nonNull(type.getInterfaces())
					& type.getInterfaces().length > 0) {
				logger.log(LOG_LEVEL, "Type0: " + type.getInterfaces()[0]);
				Class<?> type2 = type.getInterfaces()[0];

				factory = Optional.ofNullable(instance.get(type2)).orElse(
						new DefaultQuantityFactory<>((Class<Q>) type2));
				instance.putIfAbsent(type2, factory);
			} else {
				factory = Optional.ofNullable(instance.get(type)).orElse(
						new DefaultQuantityFactory<>(type));
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
		public <Q extends Quantity<Q>> AbstractQuantityFactory<Q> build(Class<Q> type,
				Map<Class, AbstractQuantityFactory> instance) {
			AbstractQuantityFactory<Q> factory = Optional
					.ofNullable(instance.get(type)).orElse(
							new DefaultQuantityFactory<>(type));
			instance.putIfAbsent(type, factory);
			return factory;
		}

	}
}
