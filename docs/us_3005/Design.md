# US3005- Design 

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


## 7/6/2023 - with rcomp's teacher

## 3-tier connection

1. Front-end(SBP_App)
2. Back-end (SBP_Server)
3. DataBase (H2 ond remote server)

There is also the **end-user(Web browser)** which will be any browser that wants to view a given board

## Protocols in use

- Between Front-end and the Back-end : SBP-Protocol(to be developed by the team)
- Between Front-end and the end-user : HTTP with AJAX

### Front-end (SBP_App)

- This program will have 2 roles(different ports??)
1. CLIENT - Communicate to the server using SBP_Protocol, possible actions that are implemented,
   as well as the payload needed handle this action
2. SERVER(Port 80, because of the browser) - Receive from the server a packet using SBP_Protocol, which specifies a code(related to a given action),
   and the respective payload, parsing this payload into a HTTP packet, using AJAX

### Back-end (SBP_SERVER)

- Receives a packet with the SBP_PROTOCOL format, verifies if the version is the same, and processes
  the payload according to the code specified
- Persist the operation components in the DataBase
- On success it returns the packet to the same SBP_App, **but on server Port(?)**

### End-user (Web Browser)

- Writes the **sbp_app(which one???) correspondent** ip and it must show the board chosen


# Duvidas

1. O end user(web browser), communica diretamente com o SBP_server ou com a SBP_app?
3. Como é que seria feita a autenticação para visualizar a board no browser? nova tentativa de autenticação
(há permissoes de escrita e leitura, ou seja não pode ser qualquer pessoa a visualizar a board)
4. It is possible to have multiple SBP_Apps running for different users in different boards , é possível haver varias SBP_App a correr em simultaneo para diferentes users e boards





