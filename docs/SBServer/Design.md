# SBApp/SBServer - Design

## Architecture - 3-tier

1. Front-end(SBP_App) - which support a server-side AJAX for the end-user(browser)
2. Back-end (SBP_Server)
3. DataBase (H2 on remote server)

## US_HANDLING LIFE PATH

### 3- PACKET HANDLING(LAYOUT)

- **OPERATION_CODE**: the server must receive the code(URI??) of the operation which the client wants to do(
  create/alter/update/delete(cells, post-its))
- **AUTH_TOKEN**: token used to identify the user and allows access to codes higher than AUTH, terminated by byte value of '13', it can be null
- **PAYLOAD**: payload to be used on the operation previously specified

the PAYLOAD layout must be explored and adapted to each one of the different use_cases

### 4 - SERVER-SIDE OPERATION HANDLING

- executes the operation(use_case) on the server-side;
- if the operation does not succeed communicate to the ip source that and error has occurred

### 5- UPDATE THE CLIENT INFO (after the server executes the operation and verify that it is a valid operation)

- it must communicate to all the known SUBSCRIBERS the packet data sent by the client

### 6- THE SUBSCRIBERS UPDATE

- the client has to process the same message protocol has the one received by the server, which contains the info update

## PROTOCOLS
- Between Front-end and the Back-end : SBP-Protocol(to be developed by the team)
- Between Front-end and the end-user : HTTP with AJAX

### Front-end (SBP_App)

- This program will have 2 roles

1. CLIENT - Communicate to the SBServer using SBP_Protocol, possible use cases need to be implemented,
   as well as the payload needed to handle the us

2. SERVER - Receive from the server a packet using SBP_Protocol, with the respective code and payload, it must
   parse the payload into a HTTP packet, using AJAX

### Back-end (SBP_SERVER)

- Receives a packet with the SBP_PROTOCOL format, verifies if the version is the same, and processes
  the payload according to the code specified
- Persist the operation components in the DataBase
- On success it returns the packet to the same SBP_App