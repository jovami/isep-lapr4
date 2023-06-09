US 3004 -- As User, I want to share a board
=========================================================

# Analysis
## Business rules
This functionality has to follow specific business rules for it to work
as intended, those business rules are:
  - User logged can only add participants to a board that he/she owns.
  - The participants invited have to be system users.



## Unit tests

In order to accurately test this functionality, we need to interact
with the Aggregate Root repositories, meaning unit tests aren't the best approach here.

Instead, integration tests should be performed.

# Design
To better answer this problem a service named **ShareBoardService** will be 
implemented with the following method added to it:

- `listBoardsUserOwns()` 
This method will perform the
needed database search operations in order to find all the boards that the user logged owns.
The **DTO pattern** will be used to display a list of the boards in the UI (**BoardDTO**).

- `shareBoard(BoardDTO board, List<SystemUserNameEmailDTO> users)` 
  This method will perform the creation of a **BoardParticipant** , meaning that for the board passed , for each
  user in the list of users, a new **BoardParticipant(Board board, SystemUser participant)** will be created and 
  saved in the repository **BoardParticipantRepository**

- `boardParticipants(BoardDTO board)`
  This method will perform the
  needed database search operations in order to find all the participants of a specific board .
  The **DTO pattern** will be used to display a list of the boards in the UI (**BoardParticipantDTO**).



## Classes
- Domain:
    + **Board**
    + **BoardParticipant**
    + **SystemUser**
- Controller:
    + **ShareBoardService**
    + **ShareBoardController**
    + **AuthorizationService**
- Repository:
    + **BoardRepository**
    + **BoardParticipantRepository**
    + **UserRepository**
- DTO:
    + **BoardDTO**
    + **BoardMapper**
    + **BoardParticipantDTO**
    + **BoardParticipantMapper**
    + **SystemUserNameEmailDTO**

## Sequence diagram
![sd](./sd.svg)