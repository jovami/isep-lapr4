US 3002 -- Create a Board
==============================

# Analysis

## Business rules

- A board have a limited number of rows and columns
- Any board can have 3 states, Creates, Shared and Archived

## Unit tests

1. ensureCellCreation
2. ensureRowIdsAreAdded
3. ensureColumnsIdsAreAdded
4. ensurePostItCreation
5. ensurePostItCanAlterCell
6. ensurePostItCanBeMoved
7. ensureArchiveBoardState
8. ensureShareBoardState
9. ensureCreateBoardState
10. ensureSameAsVerify
11. ensureBoardSameName
12. ensureIdentity
13. ensureToString

# Design

The Creation of a board has a limit of rows and columns 20 and 10 respectively.

for boards to be made according to the domain and Business rules presented,  in the creation of a
board are also created all the cells corresponding and the rows and columns.

All boards when created have the `CREATED` state assigned.

## Classes

- Domain:
    + **Board**
    + **BoardTitle**
    + **BoardColumn**
    + **BoardRow**
    + **BoardState**
    + **Cell**
    + **PostIt**
- Controller:
    + **CreateBoardController**
- Repository:
    + **BoardRepository**

## Sequence Diagram

![diagram](createBoardSD.svg)
