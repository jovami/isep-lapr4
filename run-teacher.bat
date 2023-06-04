REM set the class path,
REM assumes the build was executed with maven copy-dependencies
REM SET BASE_CP=base.app.teacher.console\target\base.app.teacher.console-1.4.0-SNAPSHOT.jar;base.app.teacher.console\target\dependency\*;

REM REM call the java VM, e.g,
REM java -cp %BASE_CP% eapli.base.app.teacher.console.BaseTeacher

java -jar base.app.teacher.console\target\base.app.teacher.console-1.4.0-SNAPSHOT.jar
