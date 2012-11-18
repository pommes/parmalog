package de.tyranus.framework.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Super class for any data objects, transfer objects, entity objets which main use is to store data
 * in fields. This class makes it possible to pre implement the equals, toString and hashCode
 * method.
 * 
 * @author tim
 */
public abstract class TAbstractData {

	private Map<String, Object> _fields;

	public TAbstractData() {
		_fields = new HashMap<String, Object>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if ((obj == null) || (obj.getClass() != this.getClass())) return false;

		// object must be TEntity at this point
		TAbstractData entity = (TAbstractData) obj;
		Set<String> thisKeys = _fields.keySet();
		Set<String> thatKeys = entity._fields.keySet();

		// Compare the keys
		if (thisKeys.size() != thatKeys.size()) return false;
		boolean areKeysEqual = true;
		for (String key : thisKeys) {
			areKeysEqual = areKeysEqual && thatKeys.contains(key);
			if (!areKeysEqual) return false;
		}

		// Compare values
		boolean areValuesEqual = true;
		for (String key : thisKeys) {
			areValuesEqual = areValuesEqual && _fields.get(key).equals(entity._fields.get(key));
			if (!areValuesEqual) return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 7; // prime number
		List<String> keys = new ArrayList<String>(_fields.keySet());
		Collections.sort(keys);
		Object field = null;
		for (String key : keys) {
			field = _fields.get(key);
			hash = 31 * hash + (null == field ? 0 : field.hashCode());
		}
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "[");
		List<String> keys = new ArrayList<String>(_fields.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			sb.append(key + "='" + _fields.get(key) + "', ");
		}
		if (!sb.toString().endsWith("[")) {
			sb.delete(sb.length() - 2, sb.length());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Returns the key set to iterate through it.
	 * 
	 * @return the key set.
	 */
	protected Set<String> keySet() {
		return _fields.keySet();
	}

	protected void setStringValue(String fieldname, String value) {
		_fields.put(fieldname, value);
	}

	protected String getStringValue(String fieldname) {
		return (String) _fields.get(fieldname);
	}

	protected void setIntValue(String fieldname, int value) {
		_fields.put(fieldname, Integer.valueOf(value));
	}

	protected int getIntValue(String fieldname) {
		return ((Integer) _fields.get(fieldname)).intValue();
	}

	protected void set(String fieldname, Object value) {
		_fields.put(fieldname, value);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Object> T get(Class<T> type, String fieldname) {
		return (T) _fields.get(fieldname);
	}
}
