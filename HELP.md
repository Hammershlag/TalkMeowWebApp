# TalkMeow

TalkMeow is a simple chat application that allows users to 
communicate with each other in real-time.

## Why that name?

TalkMeow is an app to Talk Now but with a cat twist. 
The main theme of the application is cats. Meow.

## Must before running
1. Docker installed and running. Correct container for fallback database will be created automatically.
2. Use `init.sql` file to create the database and tables in the fallback database.
3. `.env` file created in resources folder with following format (if the data is not present here, enter your own):
    ```shell
    #Database
    DATABASE_URL=
    DATABASE_USERNAME=
    DATABASE_PASSWORD=
    
    # Fallback database
    FALLBACK_DATABASE_URL=jdbc:postgresql://localhost:5432/talkMeowDatabase
    FALLBACK_DATABASE_USERNAME=VeryHardLogin
    FALLBACK_DATABASE_PASSWORD=VeryStrongPassword
    ```
4. Plugin installed that will make use of `.env` file. For example: `EnvFile` by `Borys Pierov` in IntelliJ IDEA and `.env` added to run configuration

5. To run application from terminal you have to have the above environmental variables set up, Java 21 or higher installed, docker running. To run use ```java -jar .\TalkMeow-0.0.1-SNAPSHOT.jar```.
This ```.jar``` is located in ```\application```
## Important endpoints
1. `/home` - Home page
2. `/admin/home` - Admin home page
3. `/manager/home` - Manager home page

## Features
Evrything important present in the applicatio is present in the report and explained in details.
    







