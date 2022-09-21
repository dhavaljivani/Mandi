package com.jiva.mandi.utils;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

public class AppUtilsTest {

    @Test
    public void testGetNewLoyaltyIdIfPassBlank() {
        String generatedLoyaltyId = AppUtils.getNewLoyaltyId("");
        assertEquals(generatedLoyaltyId, "S100");
    }

    @Test
    public void testGetNewLoyaltyIdIfPassNull() {
        String generatedLoyaltyId = AppUtils.getNewLoyaltyId(null);
        assertEquals(generatedLoyaltyId, "S100");
    }

    @Test
    public void testGetNewLoyaltyId() {
        String generatedLoyaltyId = AppUtils.getNewLoyaltyId("S100");
        assertEquals(generatedLoyaltyId, "S101");
    }

    @Test
    public void testGetNewLoyaltyIdPassOtherString() {
        String generatedLoyaltyId = AppUtils.getNewLoyaltyId("AAA");
        assertEquals(generatedLoyaltyId, "S100");
    }

    @Test
    public void testLoadJSONFromAssetSuccess() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String jsonString = AppUtils.loadJSONFromAsset(appContext, AppConstants.VILLAGE_LIST_JSON_FILE_NAME);
        assertNotEquals(jsonString, "");
    }

    @Test
    public void testLoadJSONFromAssetFail_1() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String jsonString = AppUtils.loadJSONFromAsset(appContext, "");
        assertEquals(jsonString, "");
    }

    @Test
    public void testLoadJSONFromAssetFail_2() {
        String jsonString = AppUtils.loadJSONFromAsset(null, null);
        assertEquals(jsonString, "");
    }
}