#!/bin/sh

mvn jacoco:prepare-agent test jacoco:report
