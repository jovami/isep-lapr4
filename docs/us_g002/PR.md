## COURSE

## EXAM

## BOARD 

## MEETING

- Q: Boa tarde nos estamos a ter uma duvida sobre qual e a diferença entre meetings e classes ?
- A: Boa tarde. Os conceitos são bastante próximos mas existem diferenças. Por exemplo, as aulas são 
     regulares e têm participantes pré-definidos. As reuniões são definidas por qualquer utilizador, têm o 
     conceito de convite, podem-se especificar os participantes, etc. Ou seja, existem ainda diferenças significativas.

- Q: Can a meeting and class overlap? If so, should the system notify that there is an overlap and for which user(s)?
- A: Regarding classes:
      FRC09 - Schedule of Class A teacher schedule a class (always a recurring class, happens every week). 
      System must check if the Teacher is available for the class period
    Regarding Meetings:
      FRM01 - Schedule a Meeting A user schedules a meeting. The system must check if all participants are available and send invitations to participants.
    In the case of Meetings they should not be created if the participants are not available (i.e., they may have classes or other meetings at the same time).

- Q: While trying to understand the concept of a meeting, a specific question came to me:
   Can any user of the system invite any other user? For example, can a student invite another student who is in a different course, 
   or can a manager can create a meeting with any group of teachers.
- A: When in the document specification the term "User" is used it usually means "any user" of the system. 
     Therefore, any user of the system can schedule a meeting and be a participant in a meeting.

- Q: While trying to understand the concept of a meeting, a specific question came to me:
   Can any user of the system invite any other user? For example, can a student invite another student who is in a different course, 
   or can a manager can create a meeting with any group of teachers.
- A: When in the document specification the term "User" is used it usually means "any user" of the system. Therefore, any user of the system can schedule 
   a meeting and be a participant in a meeting.

- Q : As I read the little information we had about meetings, I had some doubts on this functional requirement:
     FRM04 List Participants The system displays the lists of participants in a meeting and the response status (accept or reject meeting).
     
     When mentioning "accept or reject meeting", I though to myself about an invite being sent to the user and 2 options for the response: 
     The sent invite has already as a response "Rejected", so that it can either be changed to "Accepted" or stays as it is, seeing that if the user doesn't accept it, he will be rejecting it.
     The sent invite has a response being  "No answer" and, at a certain time near the begining of the meeting, the answer would change to "Rejected". The answer can be changed before it at any time to "Accepted" or "Rejected"
      I think that the 1st option would go more towards what you are looking for (by reading FRM04, it would only show the users who "accept or reject the meeting") but I wanna be sure.

- A: Just a small note regarding meetings: their use cases have a priority (see excel file) level 5. This means that they usually only have to be developed by teams with 5 or more students (regarding essentially EAPLI and LAPR4).
     FRM01 relates to the fact that the system should check if participants are available before sending the invitations. For instance, checking if a user has no other class or meeting at the same time. The system should only invite participants with a free calendar at the time of the meeting.
     In FRM03, the user accepts or rejects an invitation.
     In FRM04, the status of someone that did not answer should be "no answer" or "unknown".
     To be noticed that there is "nothing" to do by the system at the time of the meeting. Nothing needs to "happen". The same applies for classes.

## CLASSES

## TEACHER
- A : A teacher can be in charge of several courses.
- A : The acronym should be formed by a sequence of capital letters. There is really no maximum number of characters, but I would provide the possibility of setting a maximum as a property configuration of the system (with a default value).
- A : Only teachers should be able to schedule classes.

## STUDENT

## MANAGER
- A : It is important to track changes that a manager has made but not mandatory.

## USER
- A : Each User as only on specific role in the system.
- A : I think a sequence of capitalised words should be used for the teacher acronym. Maybe the length could be a configuration setting of the application.
- A : I think it would be wise to enforce that acronyms be unique.
- A : It is important to track changes that a manager has made but not mandatory.
