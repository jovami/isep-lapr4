REM set the class path,

REM assumes the build was executed with maven copy-dependencies
SET BASE_CP=base.app.board.web\target\base.app.board.web-1.4.0-SNAPSHOT.jar;base.app.board.web\target\dependency\*;


REM call the java VM, e.g,
java -cp %BASE_CP% eapli.board.server.SBPServerApp %1