This is an example how to create a jasper report with Spring Framework. Integrated with quartz scheduler,
the application can be run with some interval time. (Default 10s)

How to run it :
1. Copy file.json to your directory
2. Change absolute path to get file.json in "com.test.jasper.GenerateFromJsonJob" class
3. Build application with "mvn install" within directory.
4. the jar file will be able to execute. The application should trigger the quartz scheduler
   to run in interval time that has been decided.

That's all :)

Thanks
Kahfi Maulana