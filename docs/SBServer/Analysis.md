# SBApp/SBServer - Analysis

# OPENNING A CONNECTION (AUTH)

## 1. Server accepting new tcp connections

-  the server application must be always available to verify(accept or reject) possible new AUTH's

## 2. AUTH
-	**USERNAME** (systemUser identifier)
- 	**PASSWORD** (systemUser password)

**AUTH succes:**
- the combination username, password must correspond to a SystemUser in the database
- The SystemUser needs to: own a board, or needs to at least participates in a board

it is generated a new token that will only known by the client and the server, which allows to use it
as a authentication factor

**AUTH failure:**
- ip is already known(must have non existing connections relating a board with the source ip address)
- no systemUser was found on the server dataBase
- no permissions to read any board and does not own any board

If the auth request fails the SBServer Sends SBProtocol with ERR code
