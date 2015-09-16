@echo off
set classpath=../..

call %java_home%\bin\javac Server.java Client.java
call %java_home%\bin\rmic -d %classpath% examples.rmi.AccountImpl examples.rmi.BankImpl
