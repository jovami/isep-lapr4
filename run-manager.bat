REM set the class path,
REM assumes the build was executed with maven copy-dependencies
REM SET BASE_CP=base.app.manager.console\target\base.app.manager.console-1.4.0-SNAPSHOT.jar;base.app.manager.console\target\dependency\*;

REM REM call the java VM, e.g,
REM java -cp %BASE_CP% eapli.base.app.manager.console.BaseManager

java -jar base.app.manager.console\target\base.app.manager.console-1.4.0-SNAPSHOT.jar
