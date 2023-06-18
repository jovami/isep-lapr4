REM set the class path,
REM assumes the build was executed with maven copy-dependencies
REM SET BASE_CP=base.app.student.console\target\base.app.student.console-1.4.0-SNAPSHOT.jar;base.app.student.console\target\dependency\*;

REM REM call the java VM, e.g,
REM java -cp %BASE_CP% eapli.base.app.student.console.BaseStudent

set "JAVA_OPTS=-Dserver.port=8090"

REM Call the Java VM with the -jar option
java %JAVA_OPTS% -jar "base.app.student.console/target/base.app.student.console-1.4.0-SNAPSHOT.jar"