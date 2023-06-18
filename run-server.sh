#!/bin/sh

# set the class path,
# assumes the build was executed with maven copy-dependencies
# export BASE_CP=base.app.board.web/target/base.app.board.web-1.4.0-SNAPSHOT.jar:base.app.board.web/target/dependency/*;

#REM call the java VM, e.g,
#java -cp $BASE_CP eapli.board.server.SBServerApp "$@"

PORT="${1:-9000}"
shift

exec java -jar base.app.board.server/target/base.app.board.server-1.4.0-SNAPSHOT.jar "${PORT}" "$@"
