package com.jiva.mandi.utils;

import java.util.Collection;

public class CollectionUtils {

	/**
	 * Null-safe check if the specified collection is empty.
	 *
	 * <p>
	 * Null returns true.
	 *
	 * @param coll
	 *            the collection to check, may be null
	 * @return true if empty or null
	 */
	public static boolean isEmpty(final Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}

	/**
	 * Null-safe check if the specified collection is not empty.
	 *
	 * <p>
	 * Null returns false.
	 *
	 * @param coll
	 *            the collection to check, may be null
	 * @return true if non-null and non-empty
	 */
	public static boolean isNotEmpty(final Collection<?> coll) {
		return !isEmpty(coll);
	}

}
