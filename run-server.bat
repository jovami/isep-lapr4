REM set the class path,

REM assumes the build was executed with maven copy-dependencies
REM SET BASE_CP=base.app.board.web\target\base.app.board.web-1.4.0-SNAPSHOT.jar;base.app.board.web\target\dependency\*;


REM call the java VM, e.g,
REM java -cp %BASE_CP% eapli.board.server.SBServerApp %1
java -jar base.app.board.server/target/base.app.board.server-1.4.0-SNAPSHOT.jar %1