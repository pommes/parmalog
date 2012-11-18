package de.tyranus.framework.data;

import java.lang.reflect.Method;

/**
 * @author tim
 * 
 */
public class StackTraceHelper {

	// save it static to have it available on every call
	private static Method m;

	static {
		try {
			m = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
			m.setAccessible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMethodName(final int depth) {
		try {
			StackTraceElement element = (StackTraceElement) m.invoke(new Throwable(), depth + 1);
			return element.getMethodName();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
