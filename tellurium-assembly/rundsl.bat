@echo off
SET TELLURIUM_HOME=.
SET TELLURIUM_CLASSPATH=%TELLURIUM_HOME%\lib\selenium-java-client-driver.jar;%TELLURIUM_HOME%\lib\selenium-server.jar;%TELLURIUM_HOME%\lib\groovy-all-1.6.0.jar;%TELLURIUM_HOME%\dist\tellurium-0.7.0.jar;%TELLURIUM_HOME%\out\test\;%TELLURIUM_HOME%\out\production\
java -cp %TELLURIUM_CLASSPATH% org.telluriumsource.dsl.DslScriptExecutor %1