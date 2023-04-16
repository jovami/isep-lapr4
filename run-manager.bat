REM set the class path,
REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.manager.console\target\base.app.manager.console-1.4.0-SNAPSHOT.jar;base.app.manager.console\target\dependency\*;

REM call the java VM, e.g, 
java -cp %BASE_CP% eapli.base.app.manager.console.BaseBackoffice
