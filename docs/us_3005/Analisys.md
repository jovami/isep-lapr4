# US_3005 -> VIEW A BOARD IN REAL-TIME

## Glossary that will be used

    SERVER: application that has the responsibility of guarantee that all SUBSCRIBERS receive the new board updates in real-time
    CLIENT: the one that wants to request a operation on the board(server)
    SUBSCRIBERS: ips that have successfully tried to AUTH for a given board access
    SBP_Protocol : the protocol that SBP programs use to communicate with each other

## OPENNING A CONNECTION (AUTH)
### 1. Server accepting new tcp connections

-  the server application must be always available to verify(accept or reject) possible new AUTH's

### 2. AUTH

#### AUTH LAYOUT:
-	**USERNAME** (systemUser identifier)
- 	**BOARD_ID** (Board identifier)
- 	**PASSWORD**

**NOTE:(no need to have an authentication, if i use directly the systemUser identifier(the server searches in his database for the participant))**

Auth success:
1. the new client(ip client) is added to a collection of board SUBSCRIBERS:
2. responds to the client and MAYBE acknowledge the client with the current board state 
    (use the board as a payload(?) and the client constructs a board(that is only persisted in the server side???)

AUTH is not successfully:
- responds to the client with a code that means ERROR

AUTH SUCESS when:



AUTH fails when:
- ip is already known(?)(Auth must be the first communication relating a board with the source ip address( between server and a client))
- no objects were found on the server dataBase
- no permissions to view the board
- board is archived(?)

