# Systems-Analysis-and-Design

This has been run on IntelliJ.

Create MySQL database "online_store".

Update application.properties file to the password and username of your MySQL config, for me, it was "root" for both.

Assign the database to the project. In IntelliJ, this can be done in the "Database" side menu, and then adding a MySQL database called "online_store".

Run script.sql.

Run mvn clean install. In IntelliJ, this can be done with a side menu "Maven", which can be use to select the options for a "mvn clean install".

In the Product and User classes, there may be an error shown for the line "@Table(name=<table_name>", where table_name is the appropriate table for the class. A solution to this in IntelliJ is to click on the error line, and click "Assign Data Source", then reshape the window that pops up as it may be small, then click on the database that was created to the store the data above. In my case, it was "online_store".

Run the OnlineStoreApplication.java class.

If the application does not run because of a port the application requires is being used by another application, then change the server.port in application.properties file if necessary. For example, the port can be changed from 8080 to 8090. The same applies to the serverPort variable in the Constants.java file in the repo.

 server.port: 8090
When the application is run, a JavaFX window will appear. The REST endpoints will still be accessible when the JavaFX GUI is running. In Postman, hit the endpoint (change "8080" to "8090"):

http://localhost:8080/add-items-to-catalog
And have the following be the input of the body in Postman, in a JSON format. The name of product can be changed, so as to allow for multiple items to be added to the store's database of available products.

{
    "productId": "134560",
    "name": "Example1",
    "description": "Example Product Description",
    "category" : "Example1",
    "price": "5.60",
    "quantityInStock": "1000"
}
Run the application again. Enter a username (which must be more than 6 characters long), email (which must end with "@gmail.com"), and a password (which must be more than 6 characters long). An example could be "Anila1" for usename, "Anila1@gmail.com" for email, and "Anila1" for password. Then click on "Register Account" to load the screen to browse items.

When the above details are inputted and verified, the browse items screen will appear. To load items in the first category, click on "First Category". The same applies to the second, third, and fourth categories. When the category is selected, the list of available products will be shown.

To add a specfic product in a category, type in the name of the product in the TextField on the top of the screen, and click on "Add to Cart".

Once you have added enough items have been added to the cart, click on the "View Cart" button.

The viewCart screen will then be loaded. To load the list of products in the cart, click on the button "Load Cart Items".

When you wish to pay for the order, click on the "Checkout" button.

You can pay for the order by providing these details, but you can feel free to test for other details. Then click on the "Pay for Order" button to verify the inputs and load the delivery screen.

"First Name":       "Anila"
"Last Name":        "Mjeda"
"Address Line 1":   "Address Line 1"
"Address Line 2":   "Address Line 2"
"Address Line 3":   "Address Line 3"
"Address Line 4":   "Address Line 4"
"Address Line 5":   "Address Line 5"
"Card Number":      "132465897012"
"CVV":              "231"
You can arrange for delivery by providing these details, but you can feel free to test for other details. Then click on the "Arrange Delivery" to verify the delivery info provided, and to load the success screen for the order.

"First Name":       "Anila"
"Last Name":        "Mjeda"
"Address Line 1":   "Address Line 1"
"Address Line 2":   "Address Line 2"
"Address Line 3":   "Address Line 3"
"Address Line 4":   "Address Line 4"
"Address Line 5":   "Address Line 5"
You can run through this process again, clicking on the "Login" button to log into your existing account, instead of having to create a new account.
