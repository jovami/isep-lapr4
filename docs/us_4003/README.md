US 4003 -- Accept/Reject Meeting Request
========================================================

# Analysis
## Business rules

- Every user on the system must be able to accept and refuse a meeting request.
- The user that creates the meeting is already considered to be accepted.


## Unit tests

In order to accurately test this functionality, we need to interact
with the Aggregate Root repositories, meaning unit tests aren't the best approach here.

Instead, integration tests should be performed.

# Design

The event(meeting) scheduled will be mostly handled by the **TimeTableService** which will implement:

The **DTO** pattern will be applied in order to decrease the coupling between the UI and
the domain classes.

To avoid code duplication, the **strategy pattern** will be applied in the **MeetingParticipantRepository**,
by creating a `ofStates(states)` that will provide a list of participants whose `state` matches any
in `states`. This makes this service very flexible as it can be repurposed for any other use
case that requires a list of meetings with certain states.

## Classes
- Domain:
    + **Meeting**
    * **Description**
    + **MeetingParticipant**
    + **MeetingParticipantStatus**
- Controller:
    + **AcceptRejectMeetingRequestController**
    + **AcceptRejectMeetingRequestService**
- Repository:
    + **MeetingParticipantRepository**
    + **MeetingRepository**
- DTO:
    + **MeetingDTO**
    + **MeetingDTOMapper**



## Sequence diagram

![SD-US4003](./AcceptRejectMeetingRequestSD.svg)
