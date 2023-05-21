US 3001 -- As Project Manager, I want the team to prepare the communication infrastructure for the Shared Boards and the deployment of the solution
==================================================
# Communication Infrastructure for Shared Boards Deployment

## Overview

----------------
This README provides a theoretical overview of the communication infrastructure required for deploying the Shared Boards solution.
The goal is to establish a client-server architecture where the client application can access shared boards via a server.
The communication between these components must adhere to a specific application protocol defined in a document
from the computer network domain. Additionally, the client application should not directly access the relational
database but only interact with the server application. The deployment of the solution is recommended to utilize
multiple network nodes, with the relational database server and shared board server preferably deployed in the cloud.

## Functional Requirements

----------------
1. Client-Server Architecture: The solution must follow a client-server architecture.
   The client application will serve as the user interface for accessing the shared boards,
   while the server application will handle the underlying business logic and data management.

2. Application Protocol: The communication between the client application and the server
   application must adhere to a specific application protocol. Refer to the relevant document
   from the computer network domain to understand the details of this protocol.

3. Restricted Database Access: The client application should not directly access the
   relational database. Instead, it should interact solely with the server application,
   which will handle all database operations on behalf of the client.

## Deployment Recommendations

----------------
1. Network Nodes: Deploy the solution using multiple network nodes to ensure scalability, fault tolerance,
   and separation of concerns. Consider the following nodes:
    - Client Nodes: These nodes will host the client application and provide the user interface for
      accessing shared boards. They can be desktops, laptops, or mobile devices used by end-users.
    - Server Nodes: These nodes will host the server application and handle the business logic
      and data management. It is advisable to deploy the server nodes in a cloud environment to
      leverage the benefits of scalability, availability, and managed services.
    - Database Nodes: The relational database server should be deployed on separate nodes from
      the server application. It is recommended to deploy the database server in the cloud or on
      dedicated hardware for better performance and scalability.

2. Cloud Deployment: Consider deploying the solution in the cloud, such as using infrastructure-as-a-service
   (IaaS) or platform-as-a-service (PaaS) providers. This allows for easy scalability, automated management, and high availability. Popular cloud providers include Amazon Web Services (AWS), Microsoft Azure, and Google Cloud Platform (GCP).

3. Security Considerations: Ensure appropriate security measures are implemented to protect the communication
   infrastructure, including encryption, secure network protocols, access controls, and regular security updates.
   Consult with security experts to assess and implement the necessary security measures for your specific deployment scenario.

4. Documentation: Provide comprehensive documentation, including installation guides, configuration instructions,
   and troubleshooting steps, to assist with the setup, configuration, and maintenance of the communication infrastructure.
   This documentation should be easily accessible to the team members responsible for deploying and managing the solution.

<br>
<br>

# Shared Board Protocol (SBP) - Summary

## Introduction
The Shared Board Protocol (SBP) is an application protocol designed to facilitate data exchanges between the Shared Board App and the Shared Board Server network applications. It operates on the basis of a client-server model using TCP (Transmission Control Protocol) for reliable communication.

## SBP Description
1. **TCP-Based Communication**: SBP relies on TCP connections, requiring an initial establishment of a connection before any data exchange can occur.
2. **Client-Server Model**: The client application (Shared Board App) initiates the TCP connection with the server application (Shared Board Server), which accepts incoming connection requests.
3. **Bidirectional Communication**: Once the TCP connection is established, both the client and server applications can initiate data exchanges by sending requests. The counterpart application must be available to receive and respond to requests.
4. **Mandatory Request-Response Model**: Every request (sent by client or server) must have a corresponding response (sent by server or client) following a common message format.
5. **Persistent Connection**: The TCP connection remains open and is used for all required data exchanges while the client application is running.

## SBP Message Format
Every data exchange through the TCP connection (requests and responses) must comply with the bytes sequence description in Table 1, this is the message format version one. This message format is not expected to change during the SBS development.

|   Field    | Offset (bytes) | Length(bytes) | Description                                                                                                                                                                                                                                                                                                    |
| :--------: | :------------: | :-----------: | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|  VERSION   |       0        |       1       | SBP message format version. This field is a single byte and should be                                                                                                                                                                                                                                          | interpreted as an unsigned integer (0 to 255). The present message formatversion number is one.                                                                                                                                 |
|    CODE    |       1        |       1       | This field identifies the type of request or response, it should be interpretedas an unsigned integer (0 to 255).                                                                                                                                                                                              |
| D_LENGTH_1 |       2        |       1       | Both these fields are to be interpreted as unsigned integer numbe(0to255).The length of the DATA field is to be calculated as:D_LENGTH_1 + 256 x D_LENGTH_2Thelength of the DATAfield may be zero, meaning it does notexist.                                                                                   | Both these fields are to be interpreted as unsigned integer numbe(0to255).The length of the DATA field is to be calculated as:D_LENGTH_1 + 256 x D_LENGTH_2The length of the DATA field may be zero, meaning it does not exist. |
| D_LENGTH_2 |       3        |       1       | These two fields are used to specify the length in bytes of the DATA field.Both these fields are to be interpreted as unsigned integer numbers (0 to255).The length of the DATA field is to be calculated as:D_LENGTH_1 + 256 x D_LENGTH_2The length of the DATA field may be zero, meaning it does not exist. |
|    DATA    |       4        |       -       | Contains data to meet the specific needs of the participating applications,the content depends on the message code.                                                                                                                                                                                            |


## SBP Message Codes
Several fundamental message codes are defined for SBP, which must be implemented by all applications using the protocol. The following codes are accepted without preceding authentication:

| CODE  | Meaning                                                                                                                    |
| :---: | -------------------------------------------------------------------------------------------------------------------------- |
|   0   | COMMTEST - Communication test request with no effect other than receiving an ACK response.                                 |
|   1   | DISCONN - End of session request. Both applications are expected to close the session after receiving a code two response. |
|   2   | ACK - Generic acknowledgment and success response message.                                                                 |
|   3   | ERR - Error response message, usually accompanied by a human-readable error explanation in the DATA field.                 |
|   4   | AUTH - User authentication request.                                                                                        |

Additional unique message codes may be established during development for new features, as long as the message format remains unchanged.

## User Authentication

-Prior to successful AUTH, the server must ignore any request from the client with a code value above four and send back an ERR message as response.

-The user authentication is achieved by a username and password pair, both will be provided to the client application (Shared Board App) by the local user running it.

-The username and the password values are incorporated in the AUTH request at the DATA field as two null terminated strings of ASCII codes, first the username, followed by the password.

-The response to an AUTH request may be an ACK, meaning the authentication was successful, or an ERR, meaning it has failed. In the latter case, additional AUTH requests could be tried by the client.

-By the end of sprint B of the Integrative Project of the 4th Semester, these five fundamental message codes are expected to be implemented and fully operational in the Shared Board App application (client) and the Shared Board Server applications.

-Please note that this summary provides an overview of the Shared Board Protocol (SBP) and its key components. For detailed implementation and usage, refer to the official documentation.


# Main Conclusions

The communication infrastructure for deploying the Shared Boards solution requires a client-server architecture,
adhering to a specific application protocol. The client application should not directly access the relational
database but interact with the server application. Deploying the solution using multiple network nodes,
including cloud-based deployment for scalability and separation of concerns, is recommended.
Ensure the implementation of appropriate security measures and provide comprehensive documentation
to facilitate the setup and maintenance of the infrastructure.

Once established, the TCP connection between the client and the server is kept alive and is used
for all required data exchanges while the client application is running. It only stops with a code 1 DISCONN message.

<br>

## Stored In 4B (int) + Data
|                             |                             |                                     |                               |                       |
| :-------------------------: | :-------------------------: | :---------------------------------: | :---------------------------: | :-------------------: |
|             1B              |             1B              |                 1B                  |              1B               |  MSG Code Dependency  |
|           Version           |            Code             |             D LENGHT 1              |          D LENGHT 2           |         Data          |
| Unsigned integer (0 to 255) | Unsigned integer (0 to 255) | Unsigned integer numbers (0 to 255) | D_LENGTH_1 + 256 x D_LENGTH_2 | According to MSG Code |


