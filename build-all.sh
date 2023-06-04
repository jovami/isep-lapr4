#!/bin/sh

# make sure $JAVA_HOME is set to JDK folder
# make sure maven is on the system $PATH

# mvn "$1" dependency:copy-dependencies package
exec mvn package "$@"
