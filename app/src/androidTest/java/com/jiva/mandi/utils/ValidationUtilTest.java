package com.jiva.mandi.utils;


import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class ValidationUtilTest {

    @Test
    public void isWightIsValid_Input_0() {
        boolean isValid = ValidationUtil.isWightIsValid("0");
        assertThat(isValid).isFalse();
    }

    @Test
    public void isWightIsValid_Input_0_with_point() {
        boolean isValid = ValidationUtil.isWightIsValid("0.00");
        assertThat(isValid).isFalse();
    }

    @Test
    public void isWightIsValid_Input_Blank_String() {
        boolean isValid = ValidationUtil.isWightIsValid("");
        assertThat(isValid).isFalse();
    }

    @Test
    public void isWightIsValid_Input_empty_String_1() {
        boolean isValid = ValidationUtil.isWightIsValid("  ");
        assertThat(isValid).isFalse();
    }

    @Test
    public void isNumeric() {
        assertThat(ValidationUtil.isNumeric("22")).isTrue();
        assertThat(ValidationUtil.isNumeric("5.05")).isTrue();
        assertThat(ValidationUtil.isNumeric("-200")).isTrue();
        assertThat(ValidationUtil.isNumeric("10.0d")).isTrue();
        assertThat(ValidationUtil.isNumeric("   22   ")).isTrue();

        assertThat(ValidationUtil.isNumeric(null)).isFalse();
        assertThat(ValidationUtil.isNumeric("")).isFalse();
        assertThat(ValidationUtil.isNumeric("abc")).isFalse();
    }


}