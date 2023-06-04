#!/bin/sh

# set the class path,
# assumes the build was executed with maven copy-dependencies
# export BASE_CP=base.app.teacher.console/target/base.app.teacher.console-1.4.0-SNAPSHOT.jar:base.app.teacher.console/target/dependency/*;

# # call the java VM, e.g,
# java -cp $BASE_CP eapli.base.app.teacher.console.BaseTeacher

exec java -jar base.app.teacher.console/target/base.app.teacher.console-1.4.0-SNAPSHOT.jar
