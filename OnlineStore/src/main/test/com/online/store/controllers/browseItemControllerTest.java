package com.online.store.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.online.store.models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.online.store.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class browseItemControllerTest {
    BrowseItemsController browseItemController;
    private ProductService productService;

    /*
    Test 1 Information
     */
    Iterable<String> listAllCategories = Arrays.asList("Toys", "Games", "Letters", "Balls");


    /*
    Test 2 Information
     */
    String testCategory = "Toys";
    Product productTest2 = new Product( 11221,  "13263724",  "Tedada",  "Big Toy",  "Toys",  null,  23);
    Iterable<Product> listAllCategoryItems =  Arrays.asList(productTest2);


    /*
    Test 3 Information
     */
    String testSearch = "Board Games";
    Product productTest3 = new Product(17226743,  "16372438",  "Chess",  "Mind Focused Making Game",  "Board Games",  null,  150);
    Iterable<Product> listAllSearchItems =  Arrays.asList(productTest3);


    @Before
    public void beforeFunctionTest() {
    	productService = mock(ProductService.class);
    	browseItemController = new BrowseItemsController(productService);
    }

    //Test 1
    @Test
    public void listAllUniqueCategoriesTest() {
    	 MockHttpServletRequest request = new MockHttpServletRequest();
         RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
          
         when(productService.listUniqueCategories()).thenReturn(listAllCategories);

         ResponseEntity<Iterable<String>>responseEntity = browseItemController.browseAllCategories();
         assertNotNull(responseEntity.getBody().toString());

         List<String> testUniqueCategories = new ArrayList<String>();
         responseEntity.getBody().iterator().forEachRemaining(testUniqueCategories::add);

         Assert.assertEquals(200, responseEntity.getStatusCodeValue());

         assertEquals(testUniqueCategories.size(), 4);
         assertEquals(testUniqueCategories.get(0), "Toys");
         assertEquals(testUniqueCategories.get(3), "Balls");
    }

    //Test 2
    @Test
    public void browseCategoryItems() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(productService.listCategoryItems(testCategory)).thenReturn(listAllCategoryItems);

        ResponseEntity<Iterable<Product>>responseEntity = browseItemController.browseCategoryItems(testCategory);
        assertNotNull(responseEntity.getBody().toString());

        List<Product> testItemsInCategory = new ArrayList<Product>();
        responseEntity.getBody().iterator().forEachRemaining(testItemsInCategory::add);

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(testItemsInCategory.size(), 1);
    }

    // Test 3
    @Test
    public void searchForItemByName() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(productService.listCategoryItems(testSearch)).thenReturn(listAllSearchItems);

        ResponseEntity<Iterable<Product>>responseEntity = browseItemController.browseCategoryItems(testSearch);
        assertNotNull(responseEntity.getBody().toString());

        List<Product> testSearchItems = new ArrayList<Product>();
        responseEntity.getBody().iterator().forEachRemaining(testSearchItems::add);

        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        assertEquals(testSearchItems.size(), 1);
    }
}
