# Threads

## Client
**The Client has 3 major Threads:**
- 1 to process the UI
- 1 to be listening for incoming updates
- 1 to be sending outgoing updates to the browser

## Server
**The Server has 2 major Threads:**
- 1 to Accept incoming connections
- 1 to Handle each of the requests(codes) sent by the Client


## SD - Sequence Diagram

- **In order to process each change onto a board, the following diagram explains what happens between the applications.**
- **Here is the complement content to follow the Diagram:**


- The Client does a change on the board and sends it to the Server.
- The Server receives the request (with the thread to accept the requests), and each request triggers a thread 
to send the updates to the list of connected users (mapped by a token). (Using Observer Pattern)
- The Client receives the change and acts as a server to send those updates to the browser in order to be displayed.


<br>

![diagram](./Threads.svg)
