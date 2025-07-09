# Buy Recipes
Buy Recipes is an application that will allow people to buy their own recipes.

## Run this application locally
To run this application locally, please download and install Java 21 or newer and follow the steps below.

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

### 2. Clone the repository
Clone the repository to your desired folder. We will represent such a folder in the next steps as `<user-folder>`.

### 3.Execute the application
To execute the application, please follow the steps below

```shell
# Gets into the Project folder
cd <user-folder>/buy-recipes
    
# Builds the executable jar and run all tests
mvn clean verify

# Executes the application
java -jar target/buy-recipes.jar
```

Once the application is run for the first time, a liquibase migration will create all the necessary tables and indexes.

#### 3.1. Execute the file load-data.sql in your DB
This step is optional, and should only be executed if one wants to have some fake data inserted into the DB once the app
is executed and the DB tables and indexes are created.

### 4. Stop the application
To stop the application, one simply needs to execute `CTRL + C`.

## Possible Improvements
In this application, adding to and removing recipes from a cart are simply adding to and removing items from the cart's
items, and during the removal phase it's performing a best effort removal of items of a cart given a recipe. If any item
from a given recipe that is trying to be removed from the cart isn't found, the app returns a 409-CONFLICT. This means 
that one could potentially add a recipe with ingredients 1, 2, and 3 and remove a recipe with ingredients 1 and 2. This
decision was made due to time constraints when working on this task.

To improve this, one could create and maintain a relationship between carts and recipes to effectively validate what
recipes are actually added to a cart and only allow removing only recipes that are associated with a given cart. One
could also add to the cart item the recipe with which that specific product is associated to, which would improve the
tracking of the correct recipe even further, but due to the nature of this task and my time constraints, I decided not
to implement that, but I wanted to note that I am aware of possible improvements that could be made.
