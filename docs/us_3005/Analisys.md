# US_3005 -> VIEW A BOARD IN REAL-TIME

## Glossary that will be used

    SERVER: application that has the responsibility of guarantee that all SUBSCRIBERS receive the new board updates in real-time
    CLIENT: the one that wants to request a operation on the board(server)
    SUBSCRIBERS: ips that have successfully tried to AUTH for a given board access

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
- 
AUTH fails when:
- ip is already known(?)(Auth must be the first communication relating a board with the source ip address( between server and a client))
- no objects were found on the server dataBase
- no permissions to view the board
- board is archived(?)


## US_HANDLING LIFE PATH
### 3- PACKET HANDLING(LAYOUT)

- **OPERATION_CODE**: the server must receive the code(URI??) of the operation which the client wants to do(create/alter/update/delete(cells, post-its))
- **BOARD_IDENTITY**: to the server know to which board is the current packet
- **OPERATION_DATA**: payload to be used on the operation previously specified 

the OPERATION_DATA layout must be explored and adapted to each one of the different use_cases

### 4 - SERVER-SIDE OPERATION HANDLING 
- executes the operation(use_case) on the server-side;
- (maybe if the operation does not succeed communicate to the ip source that and error has occurred(?))
- close the new socket

### 5- UPDATE THE CLIENT INFO (after the server executes the operation and verify that it is a valid operation)
-  it must communicate to all the known ip's (SUBSCRIBERS) the packet data sented by the client

**NOTE: (OBSERVER PATTERN to be used on the real-time updates implementation)**

### 6- THE SUBSCRIBERS UPDATE

- the client has to do the same process has the one executed by the server(operation on the current board on the client)


CLOSING A CONNECTION
(CLOSE SOCKET MUST BE IMPLEMENTED ON THE CLIENT OR SERVER?)	



SHARE_BOARD
(refresh a list of board participants on the server-side repository)




(VIEW_BOARD-> implements the log-in and the current known ip's for each board)







