Stack:
  Spring Boot + Spring JPA + Spring MVC
  Thymeleaf for templating
  Bootstrap.js for providing basic styling
  MySQL as data store hosted on Amazon RDS

To Run:
  Install JDK 1.8
  Clone this repository
  Change into EmployeeDirectory
  execute ./gradlew bootRun

Working Application is hosted on an Amazon EC2 instance
   http://ec2-52-27-138-243.us-west-2.compute.amazonaws.com:8080
   login: jsmith@hspring.com password: password

Additional Notes:
  ./gradlew bootRun is all you need to do to run the app. This downloads
  all the required dependencies and runs the project. The application can be
  accessed at localhost:8080. But if you are behind firewall, you may need 
  to set proxy settings in gradle.properties in the project directory.

  Also, in case of firewall blocking you to reach MySql instances on RDS, you may 
  want to try following command instead...

  ./gradlew bootRun -PjvmArgs="-Dhttp.proxyHost=<your.proxy.host> -Dhttp.proxyPort=<proxy-port>"

  Other alternative is to create db locally and modify application.properties.


