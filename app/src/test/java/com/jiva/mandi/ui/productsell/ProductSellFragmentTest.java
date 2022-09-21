package com.jiva.mandi.ui.productsell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class ProductSellFragmentTest{



    ProductSellFragment productSellFragment;

    @Test
    public void testCalculateFinalPrice() {
        productSellFragment = mock(ProductSellFragment.class);
        when(productSellFragment.calculateFinalPrice(1,100.08,1.12)).thenReturn("112089.60");
        String output = productSellFragment.calculateFinalPrice(1,100.08,1.12);
        assertNotEquals(output,"");
    }

    @Test
    public void testCalculateFinalPrice_If_Selling_Price_0() {
        productSellFragment = mock(ProductSellFragment.class);
        when(productSellFragment.calculateFinalPrice(1,0,1.12)).thenReturn("0.0");
        String output = productSellFragment.calculateFinalPrice(1,0,1.12);
        assertEquals(output,"0.0");
    }

    @Test
    public void testCalculateFinalPrice_If_Weight_IS_0() {
        productSellFragment = mock(ProductSellFragment.class);
        when(productSellFragment.calculateFinalPrice(0,100.08,1.12)).thenReturn("0.00");
        String output = productSellFragment.calculateFinalPrice(0,100.08,1.12);
        assertEquals(output,"0.00");
    }

}
