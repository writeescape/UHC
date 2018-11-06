package net.upd4ting.uhcreloaded.util;

import java.lang.reflect.Field;

public class ReflectionUtils {

	public static void setFinalStatic(Field field, Object value) throws ReflectiveOperationException {
		field.setAccessible(true);
		Field mf = Field.class.getDeclaredField("modifiers");
		mf.setAccessible(true);
		mf.setInt(field, field.getModifiers() & 0xFFFFFFEF);
		field.set(null, value);
	}

	public static Object getValue(Class<?> source, Object val, String target) throws NoSuchFieldException, IllegalAccessException {
		Field f = source.getDeclaredField(target);
		f.setAccessible(true);
		return f.get(val);
	}
}