#!/bin/sh

# set the class path,
# assumes the build was executed with maven copy-dependencies
# export BASE_CP=base.app.student.console/target/base.app.student.console-1.4.0-SNAPSHOT.jar:base.app.student.console/target/dependency/*;

# # call the java VM, e.g,
# java -cp $BASE_CP eapli.base.app.student.console.BaseStudent

exec java \
    -Dserver.port=8090 \
    -jar base.app.student.console/target/base.app.student.console-1.4.0-SNAPSHOT.jar
