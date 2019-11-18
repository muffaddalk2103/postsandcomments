# postsandcomments

This is a simple web application which allows a user to post a message. Also users can comment on each post.

Following are the requirements for running the application
1. Java development kit 1.8 and above
2. Maven 3.3.9 and above
3. Postgres Database
4. Git - Latest version
5. Tortoise Git (Optional) - Latest Version

Assumption - All the softwares mentioned in requirements are installed.

To run the application, please perform following steps.
1. Clone the repository on local machine
2. Execute the DDL.sql file on postgres database
3. Change the database properties (server, username, password) in application.properties. Property file can be found in src/main/resources
4. change server.port property, if required in application.properties.
5. Run the application using command "mvn spring-boot:run"
6. Access application by connectiong to address https://localhost:[server.port] via browser

