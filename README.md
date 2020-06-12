Installation guide:
-------------------------------------------------------------
1.Upload database.sql to a new database.


2. Modify the following rows in application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/$DBNAMEuseUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Sofia

spring.datasource.username=$dbusername

spring.datasource.password=$password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect (specify your mysql dialect. I used XAMPP built in phpmyadmin which uses MariaDBDialect)

3.Run com.cloudruid.groceries -> CloudruidApplication as Spring Boot application.
