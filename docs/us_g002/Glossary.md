# Glossary

# User

- **User** - Registered entity on the system, composed by a date of birth, tax payer number, full
  name and short name.
- **Teacher** - An individual who is responsible for scheduling lectures for a course and is
  capable of creating exams.
- **Acronym** - An additional unique identifier to a teacher.
- **Student** - An individual who is enrolled in a course, capable of taking exams.
- **MecanographicNumber** - An additional unique identifier to a student.
- **Manager/Administrator** - An individual who is responsible for managing the system,
  including managing users, courses, and enrollment.

# Exam

- **Exam** - A formal assessment or evaluation of a student's knowledge or skills in a course.
- **FormativeExam** - Similar to an exam, except it should be treated as a training tool for
  students.
- **FormativeExamSpecification** - A template used to automatically generate formative exams.
- **ExamDate** - Refers to both start and end dates of the exam.
- **ExamSpecification** - A set of rules and guidelines defined by a teacher that outline the
  structure, format, and content of an exam.
- **ExamTitle** - The name or label given to a specific exam as part of the exam specification.
- **ExamSection** - A distinct part of an exam that contains one or more questions related to a
  specific topic or subject area.
- **ExamHeader** - Part of the exam that contains important information regarding the feedback type
  (none, on submission, after closing) and the grade type (none, on submission, after closing).
- **Question** - A task or prompt presented to a student as part of an exam that requires a
  response or answer.
- **QuestionFeedback** - The response given to the student's answers in an exam, which may include
  feedback on correctness or explanation of the correct answer.
- **ExamGrade** - The score or mark received by a student for an exam, typically calculated
  based on the number of correct answers or a predefined grading scheme.
- **ExamFeedback** - The overall response given to the student regarding their performance on the
  exam, which may include feedback on strengths and weaknesses and areas for improvement.

# Course

- **Course** - A course represents an area of study, in which students can enroll; it has a teacher
  in charge (regent) but it may also have additional teachers.
- **CourseCode** - A unique code or identifier associated with a specific course, e.g., "JAVA-1".
- **CourseTitle** - The name or label given to a specific course.
- **CourseDescription** - A detailed explanation or overview of the content, goals,
  objectives, and outcomes of a course.
- **CourseCapacity** - The maximum and minimum number of students that a course can accommodate.

# Event

An event refers to either a lecture or a meeting.

## Meeting

- **Meeting** - An event or gathering where individuals come together to discuss or address a
  particular topic or issue.
- **MeetingParticipant** - A user who integrates a meeting.

## Lecture

- **Lecture** - Also known as a **class**, it is a single session of a course that can occur
  on a weelky basis (regular lecture) or it can be one time event (extra lecture).
- **LectureAttendant** - A user who attends to a lecture.

## EventPattern

- **RecurringPattern** - A set of rules that defines the frequency (weekly or once), duration,
  and timing of recurring events, such as lectures or meetings. A recurring pattern includes
  a start/end date, a day of the week, and a start/end time.
- **Exception** - A period when a recurring pattern won't take effect. This is used to specify
  dates or times when an event will not occur (for instance, if it was rescheduled),
  even if it is part of a recurring pattern.

# Board

- **Board** - A virtual space for sharing information through the use of digital post-its.
- **Post-it** - A digital representation of a small note used to store information, either in
  the form of text or an image.
- **BoardTitle** - The name or label given to a specific board to help identify its purpose.
- **BoardParticipant** - A user who has access to a board and can create, edit or view post-its.
- **BoardAdmin** - The user who creates a board and has the ability to archive it.
- **BoardHistory** - A record of all post-its that have been created or modified on the board.
- **BoardStatus** - Indicates the current status of a board, whether it is in the process of
  being created, shared with others, or archived.
- **Cell** - A specific position on the board where a post-it is placed. A cell is identified by its
  column and row. A cell also contains a header.
