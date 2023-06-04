#!/bin/sh

# mvn jacoco:prepare-agent test jacoco:report

mvn verify \
    surefire-report:report \
    -Daggregate=true \
    checkstyle:checkstyle-aggregate \
    -Dmaven.test.failure.ignore=true
