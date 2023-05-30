US 4004 --- As User, I want to view a list of participants in my meeting and their status
========================================================================================

# Analysis

**Note**: This information was extracted from [this forum thread](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=23327#p29513).

Any user can view the participants of a meeting provided they're a participant themselves
or they're the organizer.

The time at which the meeting occurred is not relevant; meaning the user should be able
to view the list of participants of meeting from the past/future or ongoing meetings.

## Unit tests

None. This feature requires integration tests, which are out of scope for this POC.

# Design
## User interface and use case controller
This functionality is **exactly** the same regardless of user role
(teachers, managers and students can all participate in meetings), which means
a single use case controller is required and the user interface should be implemented
in the `base.app.common.console` module and then added to the menu of each user role.

## Implementation details/constraints
Once again, **DTO** objects will be used so as to decouple the UI from the domain classes.


Before being able to view the list of participants, the user must select a meeting
from those that they are a part of; meaning a `meetingsOfUser` method should be
implemented in the **MeetingParticipantRepository**.

However, due to our approach to the Meeting and MeetingParticipant classes,
such method isn't enough, as the organizer/owner of a meeting is not persisted
as a "Meeting participant".

This then leads us to also implement a `organizedBy` method in the **MeetingRepository**,
as the owner is an attribute of Meeting.

The two lists return by the above methods would need to be merged together before
they're returned to the UI; however, as accessing two different repositories and merging
the lists by them returned isn't a task for a Use Case controller, a separate service should
be created for the task. Hence the existence of the **ListMeetingsService**.

The implementation of MeetingParticipant could also cause some issues when listing the participants
of the select meeting; however, the Meeting object the UI interacts with (meeting chosen from a list)
holds information regarding the organizer, so the UI can display information about this user with
no problem.

### Improvements

One aspect to consider would possibly be including the meeting organizer as a MeetingParticipant, although
this would require some further thinking, since:

- The organizer does not have a **status** regarding their own meeting
    + It would not make sense for the organizer to have status *pending*, *rejected*, nor *pending*
- Adding an *organizer* attribute to the MeetingParticipant class (or as an additional status) would
require implementing mechanisms to guarantee a meeting can never have more than one organizer.

## Classes

- Domain:
    + **Meeting**
    + **MeetingParticipant**
    + **SystemUser**
- Controller:
    + **ListMeetingParticipantsController**
    + **ListMeetingsService**
    + **MyUserService**
- Repository:
    + **MeetingRepository**
    + **MeetingParticipantRepository**
- DTO:
    + **MeetingOfUserDTO**
    + **MeetingOfUserDTOMapper**
    + **MeetingParticipantDTO**
    + **MeetingParticipantDTOMapper**

## Sequence diagram
The [following sequence diagram](./sd.svg) was developed to answer this use case:

![diagram](./sd.svg)
