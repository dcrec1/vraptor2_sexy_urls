package org.vraptor.converter.basic;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.vraptor.converter.Converter;
import org.vraptor.converter.LocaleDoubleConverter;
import org.vraptor.converter.LocaleFloatConverter;
import org.vraptor.converter.LocalePrimitiveDoubleConverter;
import org.vraptor.converter.LocalePrimitiveFloatConverter;
import org.vraptor.converter.SimpleConverterManager;

/**
 * Utility for registering all primitive and primitive-wrappers converters for
 * xpathing parameters.
 *
 * @author Guilherme Silveira
 */
public class BasicConverters {

	private static final Logger LOG = Logger.getLogger(BasicConverters.class);

	/**
	 * Registers those basic converters
	 *
	 */
	public static void register(SimpleConverterManager repository) {
		Converter[] converters = new Converter[]{
				new SimpleLongConverter(),
				new PrimitiveLongConverter(),
				new SimpleIntegerConverter(),
				new PrimitiveIntConverter(),
				new LocaleFloatConverter(),
				new LocalePrimitiveFloatConverter(),
				new LocaleDoubleConverter(),
				new LocalePrimitiveDoubleConverter(),
				new StringConverter(),
				new SimpleBooleanConverter(),
				new PrimitiveBooleanConverter(),
				new SimpleShortConverter(),
				new PrimitiveShortConverter(),
				new SimpleByteConverter(),
				new PrimitiveByteConverter(),
				new SimpleCharacterConverter(),
				new PrimitiveCharConverter(),
				new SimpleBigDecimalConverter(),
				new LocaleCalendarDateConverter(),
				new EnumConverter(),
				new LocaleConverter()
		};
		if(LOG.isDebugEnabled()) {
			LOG.debug("Registering the basic converters " + Arrays.toString(converters));
		}
		for (Converter converter : converters) {
			repository.register(converter);
		}
	}

}
