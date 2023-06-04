ECHO OFF
ECHO make sure JAVA_HOME is set to JDK folder
ECHO make sure maven is on the system PATH

REM mvn %1 %2 dependency:copy-dependencies package

mvn %1 %2 package
