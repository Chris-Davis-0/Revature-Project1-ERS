# Revature-Project1-ERS
## Project Description
An expense reimbursement system for for the Britain's Secret Intelligence Service.

The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. 
All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. 
Finance managers can log in and view all reimbursement requests and past history for all employees in the company. 
Finance managers are authorized to approve and deny requests for expense reimbursement.

## Technologies Used
- Java 8
- HTML 5
- CSS 3
- Bootstrap 4 
- JavaScript ES6
- JQuery 3.2
- AJAX/Jackson 2.11 
- DBeaver 7.3 
- MariaDB JDBC 2.6.2
- Postman 7.36
- Javalin 3.10
- Hibernate 5.4
- JUnit 4.12
- Mockito 1.10
- Selenium 3.141
- Log4j 1.2
- Spring Tools Suite 4
- Maven 3.6

## Features
### Implemented
- Multiple security clearance levels: Operative, Secretary Chief, and Head
- Create reimbursement requests with comments, request amount, and 6 type categories: Travel, Food, Lodging, Equipment, Legal, or Other.
- View your submitted requests and check their status: Pending, Denied, or Accepted.
- Click on your submitted request to view accordian table of user and resolver comments.
- Process the requests of other users with lower clearance levels, assigning status and optional comment.
- Filter reimbursements by status.
### Todo
- Create cleaner interface for author/resolver comments.
- Add profile page with ability to update user information.
- Password encryption.
- Ability to upload reciept images when making requests.
- Add additional reimbursement filters/search for request author
- Add sort-by option for each column.
- Dynamically update list after request resolution.

## Getting Started
In CLI use command: git clone https://github.com/Chris-Davis-0/Revature-Project1-ERS.git to pull project into your local repository
Click code -> Download zip to get a zip file of project.
In IDE of your choice (developed in STS 4), open/import as a Maven project. POM will manage all dependencies for you.

Start project under com.revature.chrisdavis ApplicationDriver.java.
Default port is 9009. Once Javalin has started server, enter http://localhost:9009/html/login.html to enter main page.
Going anywhere else will reroute you to failed login attempt page.

To reinitialize database with initial information, uncomment line 64 on ApplicationDriver.java.
You will also want to change src/main/resources/hibernate.cfg.xml from update to create on line 15.
Make sure to change this back to update after initialization run (and re-comment out initializeDatabase invocation on ApplicationDriver.java).

## Usage
Login page. See ApplicationDriver.java for user/password information
![GitHub Logo](/Readme-images/1.png)


Example of an incorrect login, or attempt to access features after logout.
![GitHub Logo](/Readme-images/10png.png)


The main welcome page, you can access logout through user name.
![GitHub Logo](/Readme-images/2.png)


Click Create Reimbursements in the Reimbursements dropdown menu for this modal
![GitHub Logo](/Readme-images/3.png)


Click My Reimbursements in the Reimbursements dropdown menu for a table of submitted reimbursements of that user.
Click on a table row to expand, revealing author/resolver comments
![GitHub Logo](/Readme-images/4.png)


If you are logged in at a Secretary or higher level, click My Reimbursements -> Process reimbursements.
View table of all submitted reimbursements that you have clearance level access to.
![GitHub Logo](/Readme-images/5.png)


Click Status:All to change filter as desired.
![GitHub Logo](/Readme-images/6.png)


Filter to depending for easier access to reimbursements awaiting your approval.
![GitHub Logo](/Readme-images/7.png)


Click on pending to pop up request resolution modal. Set status and optional comment before clicking resolve.
![GitHub Logo](/Readme-images/8.png)


Go back to Process Request to see the resolved request.
![GitHub Logo](/Readme-images/9.png)

## License
ERS is licensed under the terms of the GPL Open Source license and is available for free.


