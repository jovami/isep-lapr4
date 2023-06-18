#!/bin/sh

## set the class path,
## assumes the build was executed with maven copy-dependencies
# export BASE_CP=base.app.board.web/target/base.app.board.web-1.4.0-SNAPSHOT.jar:base.app.board.web/target/dependency/*;

# java -cp $BASE_CP eapli.board.client.SBPClientApp "$@"

IP="${1}"
PORT="${2:-9000}"

if [ $# -gt 0 ]; then
    IP="${1}"
    shift
fi

if [ $# -gt 0 ]; then
    PORT="${1}"
    shift
fi


exec java -jar base.app.board.client/target/base.app.board.client-1.4.0-SNAPSHOT.jar "${IP}" "${PORT}" "$@"
