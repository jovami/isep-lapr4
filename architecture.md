Project Structure Architecture
==============================
# Project structure

- `eapli.base.consoleapp`:
    + Presentation using console.
    + Main class.
    + Application properties in resource folder.

- `eapli.base.bootstrap`
    + Bootstrap data. This should be ignored on a *real* installation.

- `eapli.base.core`
    + Use case controllers, model, and persistence.


# Architecture

The application follows a typical layered approach

    UI -> Controller -+-> Domain
                      |     ^
                      |     |
                      +-> Repositories


## Domain objects with persistence knowledge or not

Two different approaches are possible:

- pure domain objects without any knowledge of the persistence.
- domain objects that can save and load themselves from persistence (thus, an Active Record).

In the first case, the controller is responsible for obtaining the domain objects
from the repository, asking the domain objects to perform the business logic and
then pass them back to the repository. In this case, the domain objects can "easily"
be tested as they do not depend on any other package. This gets trickier when we
need/want to have lazy load of collections...

In the second case, the controller asks the domain object class to load a certain
instance, asks that object to perform the business operation and then asks the object
to save itself back to the database


## Passing domain objects to the UI or not

The decision is to use domain objects outside of the controllers boundary. One could
argue that domain objects should be known only "inside" the application boundary and
as such other data structures should be returned to outside layers, i.e., DTO (Data Transfer Objects).


## Performing calculations in memory or directly at the persistence layer

Both approaches have advantages and disadvantages:

- In memory:
    + **Advantages:**
        * Allows the use of business logic in code.
    + **Disadvantages:**
        * performance may be poor.

- At the persistence layer:
    + **Advantages:**
        * Use of aggregated SQL functions is straight forward.
        * Better performance.
    + **Disadvantages:**
        + Complicated business logic is hard to implement.

For more information on the topic, check out [this article.](http://www.martinfowler.com/articles/dblogic.html)

## Factoring out common behavior

Use services at the application or domain layer

## Can controllers call other controllers?

It is best if they call application services

## Should the UI/controller create domain objects directly

Should the rules for the Creator pattern be fully enforced, e.g., the responsibility to
create a Payment should be of Expense, or can the controller/UI create a Payment and
pass it to the Expense?

## How to reuse behavior between controllers

Factor out common behavior in an application service.

## When showing movements grouped by type, who performs the sum operation? UI, Controller or Domain object?

the UI might not be smart enough to compute the total sum with enough precision, and
would carry a burden for the computer running the interface

the Controller might indeed perform such calculation as it has all the data is needs
for a short period of time, but it is not the controller function to perform mathematical
operations

the domain object might indeed be the very best resource to calculate the sum for each
expense type, but it would not make sense to delegate the domain object to the interface.

## When showing movements gruped by type, should types with no values also appear?

this can be another strategy


# References and bibliography

Start by reading the essential material listed in [EAPLI framework](https://bitbucket.org/pag_isep/eapli.framework/src/master/README.md)

## JPA

- [Entities or DTOs in JPA Queries](https://thoughts-on-java.org/entities-dtos-use-projection/)
- [Primary key mapping](https://thoughts-on-java.org/primary-key-mappings-jpa-hibernate/)

## Other useful readings

T.B.D.
