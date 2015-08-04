Stack:
  Spring Boot + Spring JPA
  Thymeleaf for templating
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
  all the required deependencies and runs the project. But if you 
  are behind firewall, you may need to set proxy settings in gradle.properties
  in the project directory.

  Also, in case of firewall blocking you to reach MySql instances, you may want to
  try following command instead...

  ./gradlew bootRun -PjvmArgs="-Dhttp.proxyHost=<your.proxy.host> -Dhttp.proxyPort=<proxy-port>"


