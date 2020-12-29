package com.online.store;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    // GUI Object constants.
    public static final String passwordPromptString = "Password";

    // FXML constants.
    public static final String fxmlFileBasePath = "/views/";
    public static final String registerAccountFxmlFileName = fxmlFileBasePath + "RegisterAccount.fxml";
    public static final String loginFxmlFileName = fxmlFileBasePath + "login.fxml";
    public static final String DeliveryFxmlFileName = fxmlFileBasePath + "Delivery.fxml";
    public static final String successfulDeliveryFxmlFileName = fxmlFileBasePath + "successfulDelivery.fxml";
    public static final String viewCartFileName = fxmlFileBasePath + "viewCart.fxml";
    public static final String applicationName = "Online Store";
    public static final String browseItemsFxmlFileName = fxmlFileBasePath + "browseItems.fxml";
    public static final String paymentFxmlFileName = fxmlFileBasePath + "Payment.fxml";

    public static final String serverPort = "8080";
    public static final String baseEndpointPath = "http://localhost:" + serverPort;

    public static final String registrationEndpoint = "/register-account";
    public static final String registrationEndpointWithBase = baseEndpointPath + registrationEndpoint;

    public static final String loginEndpoint = "/log-in";
    public static final String loginEndpointWithBase = baseEndpointPath + loginEndpoint;

    public static final String deliveryEndpoint = "/arrange-delivery";
    public static final String deliveryEndpointWithBase = baseEndpointPath + deliveryEndpoint;

    public static final String browseProductsInCategoryEndpoint = "/browse-category-items/";
    public static final String browseProductsInCategoryEndpointWithBase = baseEndpointPath + browseProductsInCategoryEndpoint;

    public static final String searchItemsByNameEndpointWithBase = baseEndpointPath + "/search-items-by-name/";

    public static final String addItemsToCartEndpoint = "/add-items-to-cart";
    public static final String addItemsToCartEndpointWithBase = baseEndpointPath + "/add-items-to-cart";

    public static final String paymentEndpoint = "/pay-for-order";
    public static final String paymentEndpointWithBase = baseEndpointPath + "/pay-for-order";
}
