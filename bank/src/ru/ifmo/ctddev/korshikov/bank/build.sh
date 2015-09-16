#!/bin/bash
export CLASSPATH=../../../../../

javac Server.java Client.java
rmic -d $CLASSPATH ru.ifmo.ctddev.korshikov.bank.AccountImpl ru.ifmo.ctddev.korshikov.bank.BankImpl
