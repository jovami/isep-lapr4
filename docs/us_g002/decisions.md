# Course

- We opted to add a start and end date to a course, due to its simplicity to model.

# Exams

- We opted to unify the common characteristics between exams and formative exams into an interface called
  Exams.

# Recurring Patterns

- Before talking to out teacher, we had two options: either generate and persist every class instance
  in the database or use a pattern to encapsulate the recurssion. After discussing with our teacher,
  we finally opted for the recurring pattern, not only for the agility it promotes but also due to the
  fact that we're not interested in managing classes that already occured as that was not a part of
  any of the established requirements.

- When it came to rescheduling a specific event, we found ourselves in another problem. We had
  two possible approaches to this situation: either split a recurring pattern into three or create
  an exception for that occurance. In group, we opted for the exception due to its simplicity and
  being more sofisticated than the other option.
