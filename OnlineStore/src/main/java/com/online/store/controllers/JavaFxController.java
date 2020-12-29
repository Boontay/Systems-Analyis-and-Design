package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.Constants;
import com.online.store.exceptions.UnsuccessfulEndpointCommunicationException;
import com.online.store.exceptions.UnsuccessfulPaymentException;

import com.online.store.models.Product;
import com.online.store.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JavaFxController {
    private final Logger LOGGER = LoggerFactory.getLogger(JavaFxController.class);

    @FXML
    public Button registerAccountButton;
    public Text registerAccountPasswordText;
    public Text registerAccountEmailText;
    public Text registerAccountUsernameText;
    public PasswordField registerAccountPasswordTextField;
    public TextField registerAccountEmailTextField;
    public TextField registerAccountUsernameTextField;
    public Button registerToLoginButton;

    @FXML
    public Button loginButton;
    public PasswordField loginPasswordTextField;
    public TextField loginUsernameTextField;
    public Text loginUsernameText;
    public Text loginPasswordText;
    public Button loginToRegisterAccountButton;

    @FXML
    public Text deliveryFirstNameText;
    public Text deliveryLastNameText;
    public Text deliveryAddressText;
    public TextField deliveryFirstNameTextField;
    public TextField deliveryLastNameTextField;
    public TextField deliveryAddressFirstLineTextField;
    public TextField deliveryAddressSecondLineTextField;
    public TextField deliveryAddressThirdLineTextField;
    public TextField deliveryAddressFourthLineTextField;
    public TextField deliveryAddressFifthLineTextField;
    public Button arrangeDeliveryButton;
    
    @FXML
    public Button removeItemButton;
    public Button searchItemButton;
    @FXML public TextArea listOfItemsViewCartTextArea;

    @FXML
    public TextField searchBar;
    public Button viewCartButton;
    public Button addToCartButton;
    public Button checkoutButton;

    @FXML
    public Text paymentFirstNameText;
    public Text paymentLastNameText;
    public Text paymentAddressText;
    public Text paymentCardNumberText;
    public Text paymentCvvText;
    public TextField paymentFirstNameTextField;
    public TextField paymentLastNameTextField;
    public TextField paymentAddressFirstLineTextField;
    public TextField paymentAddressSecondLineTextField;
    public TextField paymentAddressThirdLineTextField;
    public TextField paymentAddressFourthLineTextField;
    public TextField paymentAddressFifthLineTextField;
    public TextField paymentCardNumberTextField;
    public TextField paymentCvvTextField;

    public Button payForOrderButton;
    public Button firstCategoryButton;
    public Button secondCategoryButton;
    public Button thirdCategoryButton;
    public Button fourthCategoryButton;
    public TextArea listOfItemsBrowseItemsTextArea;

    public Button loadViewCart;

    public static User user = new User();

    @FXML
    private void handleRegisterAccountButtonAction(ActionEvent event) throws Exception {
        JSONObject registrationJson = new JSONObject();
        registrationJson.put("username", registerAccountUsernameTextField.getText());
        registrationJson.put("email", registerAccountEmailTextField.getText());
        registrationJson.put("password", registerAccountPasswordTextField.getText());

        hitPostEndpoint(Constants.registrationEndpointWithBase, registrationJson.toString());
        loadNewScene(Constants.browseItemsFxmlFileName, registerAccountButton);
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("username", loginUsernameTextField.getText());
        loginJson.put("password", loginPasswordTextField.getText());

        hitPostEndpoint(Constants.loginEndpointWithBase, loginJson.toString());
        loadNewScene(Constants.browseItemsFxmlFileName, loginButton);
    }

    @FXML
    private void handleLoginToRegisterAccountButtonAction(ActionEvent event) throws Exception {
        loadNewScene(Constants.registerAccountFxmlFileName, loginToRegisterAccountButton);
    }

    @FXML
    private void handleRegisterToLoginButtonAction(ActionEvent event) throws Exception {
        loadNewScene(Constants.loginFxmlFileName, registerToLoginButton);
    }

    @FXML
    private void handleDeliveryButtonAction(ActionEvent event) throws Exception {
        List<String> deliveryAddressList = new ArrayList<>(5);
        deliveryAddressList.add(deliveryAddressFirstLineTextField.getText());
        deliveryAddressList.add(deliveryAddressSecondLineTextField.getText());
        deliveryAddressList.add(deliveryAddressThirdLineTextField.getText());
        deliveryAddressList.add(deliveryAddressFourthLineTextField.getText());
        deliveryAddressList.add(deliveryAddressFifthLineTextField.getText());

        JSONArray jsonArray = new JSONArray(deliveryAddressList);

        JSONObject deliveryJson = new JSONObject();
        deliveryJson.put("firstName", deliveryFirstNameTextField.getText());
        deliveryJson.put("lastName", deliveryLastNameTextField.getText());
        deliveryJson.put("address", jsonArray);

        hitPostEndpoint(Constants.deliveryEndpointWithBase, deliveryJson.toString());
        loadNewScene(Constants.successfulDeliveryFxmlFileName, arrangeDeliveryButton);
    }

    public void handleViewCartButton(ActionEvent actionEvent) throws IOException {
        loadNewScene(Constants.viewCartFileName, viewCartButton);
    }

    private void getUserCartInfo() {
        List<Product> products = user.getCart();
        ArrayList<String> productNames = getProductNamesFromProductsArrayList((ArrayList<Product>) products);

        StringBuilder namesStringBuilder = new StringBuilder();

        for (String s : productNames) {
            namesStringBuilder.append(s).append(System.lineSeparator());
        }

        listOfItemsViewCartTextArea.setText(namesStringBuilder.toString());
    }

    public void handleCheckoutButton(ActionEvent actionEvent) throws IOException {
        loadNewScene(Constants.paymentFxmlFileName, checkoutButton);
    }

    public void handlePaymentButtonAction(ActionEvent actionEvent) throws IOException {
        LOGGER.info("Loading delivery page.");

        List<String> addressList = new ArrayList<>(5);
        addressList.add(paymentAddressFirstLineTextField.getText());
        addressList.add(paymentAddressSecondLineTextField.getText());
        addressList.add(paymentAddressThirdLineTextField.getText());
        addressList.add(paymentAddressFourthLineTextField.getText());
        addressList.add(paymentAddressFifthLineTextField.getText());

        JSONArray jsonArray = new JSONArray(addressList);

        JSONObject paymentJson = new JSONObject();
        paymentJson.put("firstName", paymentFirstNameTextField.getText());
        paymentJson.put("lastName", paymentLastNameTextField.getText());
        paymentJson.put("address", jsonArray);
        paymentJson.put("cardNumber", paymentCardNumberTextField.getText());
        paymentJson.put("cardCVV", paymentCvvTextField.getText());

        // hit endpoint here, calling the right endpoint for the payment controller, sending the paymentJson as the parameter.
        hitPostEndpoint(Constants.paymentEndpointWithBase, paymentJson.toString());

        loadNewScene(Constants.DeliveryFxmlFileName, payForOrderButton);
    }

    private void hitPostEndpoint(String endpoint, String input) {
        String postMethodName = "POST";

        try {
            URL url = new URL(endpoint);

            HttpURLConnection postConnection = (HttpURLConnection)url.openConnection();
            postConnection.setRequestMethod(postMethodName);
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);

            OutputStream outputStream = postConnection.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = postConnection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                getEndpointErrorLogger(postMethodName, endpoint, responseCode);
            } else {
                getEndpointInfoLogger(postMethodName, endpoint);
            }

        } catch (IOException ex) {
            throw new UnsuccessfulEndpointCommunicationException();
        }
    }

    private void getEndpointInfoLogger(String requestMethod, String endpoint) {
        LOGGER.info("Successful communication with {} endpoint: {}", requestMethod, endpoint);
    }

    private void getEndpointErrorLogger(String requestMethod, String endpoint, int errorCode) {
        LOGGER.error("Unsuccessful communication with {} endpoint: {}. Error code: {}", requestMethod, endpoint, errorCode);
    }

    private String hitGetEndpoint(String endpoint) {
        String getMethodName = "GET";

        try {
            URL url = new URL(endpoint);
            String readLine;
            HttpURLConnection getConnection = (HttpURLConnection)url.openConnection();
            getConnection.setRequestMethod(getMethodName);

            int responseCode = getConnection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                getEndpointErrorLogger(getMethodName, endpoint, responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));

                StringBuilder response = new StringBuilder();

                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }

                in.close();

                getEndpointInfoLogger(getMethodName, endpoint);

                return response.toString();
            }

        } catch (IOException ex) {
            throw new UnsuccessfulEndpointCommunicationException();
        }

        return String.format("Unsuccessful communication with GET endpoint: %s", endpoint);
    }

    private void loadNewScene(String filename, Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = fxmlLoader.load(getClass().getResourceAsStream(filename));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private ArrayList<Product> convertStringToProductsArrayList(String string) throws JsonProcessingException {
        JSONArray jsonArray = new JSONArray(string);

        ArrayList<Product> products = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Product product = new ObjectMapper().readValue(jsonObject.toString(), Product.class);

            products.add(product);
        }

        return products;
    }

    private ArrayList<String> getProductNamesFromProductsArrayList(ArrayList<Product> products) {
        ArrayList<String> names = new ArrayList<>();

        for (Product p : products) {
            names.add(p.getName());
        }

        return names;
    }

    public void handleFirstCategoryButtonAction(ActionEvent actionEvent) throws JsonProcessingException {
        handleCategoryButton("123");
    }

    public void handleAddToCartButton(ActionEvent actionEvent) throws JsonProcessingException {
        // checks database that item exists in database.
        // thi is done with searchForItemByName endpoint.
        String productString = hitGetEndpoint(Constants.searchItemsByNameEndpointWithBase + Objects.requireNonNull(searchBar.getText()));

        ArrayList<Product> productArrayList = convertStringToProductsArrayList(productString);

        // adds product to user's catalog.
        ObjectMapper mapper = new ObjectMapper();
        String jsonMapper = mapper.writeValueAsString(productArrayList.get(0));

        hitPostEndpoint(Constants.addItemsToCartEndpointWithBase, jsonMapper);
    }

    private void handleCategoryButton(String categoryName) throws JsonProcessingException {
        String productsString = hitGetEndpoint(Constants.browseProductsInCategoryEndpointWithBase + categoryName);

        ArrayList<Product> productsArrayList = convertStringToProductsArrayList(productsString);

        ArrayList<String> productNamesArrayList = getProductNamesFromProductsArrayList(productsArrayList);

        StringBuilder namesStringBuilder = new StringBuilder();

        for (String s : productNamesArrayList) {
            namesStringBuilder.append(s).append(System.lineSeparator());
        }

        listOfItemsBrowseItemsTextArea.setText(namesStringBuilder.toString());
    }

    public void handleSecondCategoryButtonAction(ActionEvent actionEvent) throws JsonProcessingException {
        handleCategoryButton("12345");
    }

    public void handleThirdCategoryButtonAction(ActionEvent actionEvent) throws JsonProcessingException {
        handleCategoryButton("Example1");
    }

    public void handleFourthCategoryButtonAction(ActionEvent actionEvent) throws JsonProcessingException {
        handleCategoryButton("1234");
    }

    public void handleLoadViewCartButton(ActionEvent actionEvent) {
        getUserCartInfo();
    }
}
