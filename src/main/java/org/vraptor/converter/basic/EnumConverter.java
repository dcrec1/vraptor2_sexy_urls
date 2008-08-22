package org.vraptor.converter.basic;

import org.vraptor.LogicRequest;
import org.vraptor.converter.ConversionException;
import org.vraptor.converter.Converter;

/**
 * Basic String to Enumeration converter. If the value begins with a number uses
 * it as the ordinal position, otherwise uses it as its name. Uses the error key
 * invalid_value if unable to parse its information.
 * 
 * @author Guilherme Silveira
 */
public class EnumConverter implements Converter {

	private static final Class<?>[] SUPPORTED_TYPES = new Class[] { Enum.class };

	@SuppressWarnings("unchecked")
	public Object convert(String value, Class<?> type, LogicRequest context)
			throws ConversionException {
		if (value == null || value.equals("")) {
			return null;
		}
		Class<? extends Enum> enumType = (Class<? extends Enum>) type;
		if (Character.isDigit(value.charAt(0))) {
			return resolveByOrdinal(value, type, enumType);
		} else {
			return resolveByName(value, type, enumType);
		}
	}

	public Class<?>[] getSupportedTypes() {
		return SUPPORTED_TYPES;
	}

	/**
	 * @param value
	 * @param type
	 * @param enumType
	 * @return
	 * @throws ConversionException
	 */
	@SuppressWarnings("unchecked")
	private Object resolveByName(String value, Class<?> type,
			Class<? extends Enum> enumType) throws ConversionException {
		try {
			return Enum.valueOf(enumType, value);
		} catch (IllegalArgumentException e) {
			throw new ConversionException("invalid_value",
					"Invalid enumeration value for type " + type.getName()
							+ " value " + value);
		}
	}

	/**
	 * @param value
	 * @param type
	 * @param enumType
	 * @return
	 * @throws ConversionException
	 */
	private Object resolveByOrdinal(String value, Class<?> type,
			Class<? extends Enum> enumType) throws ConversionException {
		try {
			int ordinal = Integer.parseInt(value);
			if (ordinal >= enumType.getEnumConstants().length) {
				throw new ConversionException("invalid_value",
						"Invalid enumeration value for type " + type.getName()
								+ " value " + value);
			}
			return enumType.getEnumConstants()[ordinal];
		} catch (NumberFormatException e) {
			throw new ConversionException("invalid_value",
					"Invalid enumeration value for type " + type.getName()
							+ " value " + value);
		}
	}

}
