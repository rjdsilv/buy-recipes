# Buy Recipes
Buy Recipes is an application that will allow people to buy their own recipes.

## Run this application locally
To run this application locally, please follow the steps below.

### 1. Install Postgresql
Go to the [download Postgresql 17.5](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) link and download
the appropriate version. Alternatively, one can run it through Docker.

#### 1.1. Set your postgres user password
Set your postgres user password to anything you'd like to set.

#### 1.2. Create the necessary databases
Create the database `buy_recipes` and `buy_recipes_test` using the user `postgres` as the database owner. We are using
the default Postgres user here for simplicity.

#### 1.3. Update the application files
Go to the files `application.yml` and `application-test.yml` and update the property `spring.datasource.password` with
the password value you set in step `1.1`.

#### 1.4. Execute the file load-data.sql in your DB
This step is optional, and should only be executed if one wants to have some fake data inserted into the DB before
executing the app

### 2. Clone the repository
Clone the repository to your desired folder. We will represent such a folder in the next steps as `<user-folder>`.

### 2.Execute the application
To execute the application, please follow the steps below

```jshelllanguage
# Gets into the Project folder
cd <user-folder>/buy-recipes
    
# Builds the executable jar and run all tests
mvn clean verify

# Executes the application
java -jar target/buy-recipes.jar
```