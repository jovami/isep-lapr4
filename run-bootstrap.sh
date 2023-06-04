#!/bin/sh

# set the class path,
# assumes the build was executed with maven copy-dependencies
# export BASE_CP=base.app.bootstrap/target/base.app.bootstrap-1.4.0-SNAPSHOT.jar:base.app.bootstrap/target/dependency/*;

# call the java VM, e.g,
# java -cp $BASE_CP eapli.base.app.bootstrap.BaseBootstrap "-smoke:basic" "-bootstrap:demo"

exec java -jar base.app.bootstrap/target/base.app.bootstrap-1.4.0-SNAPSHOT.jar
