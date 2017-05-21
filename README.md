# AirCompanyRest

A desktop application for administrating flight tickets buying for tourism agencies.

The application can be used by tourism agencies and has the following functionalities:

-Login window: The agency employee can login in his account.

-Main window: After a successful, a new window will open that shows information about current flights (destination,the date and hour of departure and the number of available seats remaining)

-Searching: The employee can search for a flight by introducing the destination and the date of departure. A new window will open, showing the flights that match the search parameters

-Buying: The employee can buy tickets for the clients. After a successful purchase, the flight database will update its current flights, and if a flight remains without free seats, it won't be shown anymore to the rest of the employees

-Logout window. The client can press the logout button and return back to the login screen

The program connects to a MySQL database made in XAMPP. The language used is Java, with the interface being made in JavaFX

This repository includes both the client and the server. Both of them use Spring Remoting to communicate with each other, and RabbitMQ to listen for updates in the database.

The program also contains a RESTful configuration for the element Flights. The implementation of REST was done using SpringMVC and Gradle. 
For now, the program contains a CRUD for the flights element.
