package com.jiva.mandi.utils;


import static com.google.common.truth.Truth.assertThat;
import com.jiva.mandi.utils.CollectionUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilsTest {

	private final List<String> lstDemo = new ArrayList<>();

	@Test
	public void emptyCollectionReturnsTrueByIsEmpty() {
		Boolean result = CollectionUtils.isEmpty(lstDemo);
		assertThat(result).isTrue();
	}

	@Test
	public void nonEmptyCollectionReturnsFalseByIsEmpty() {
		lstDemo.add("Demo");
		Boolean result = CollectionUtils.isEmpty(lstDemo);
		assertThat(result).isFalse();
	}

	@Test
	public void emptyCollectionReturnsFalseByIsNotEmpty() {
		lstDemo.clear();
		Boolean result = CollectionUtils.isNotEmpty(lstDemo);
		assertThat(result).isFalse();
	}

	@Test
	public void nonEmptyCollectionReturnsTrueByIsNotEmpty() {
		lstDemo.add("Demo");
		Boolean result = CollectionUtils.isNotEmpty(lstDemo);
		assertThat(result).isTrue();
	}
}
