#!/bin/bash
export CLASSPATH=../../../../../

rmiregistry &
java ru.ifmo.ctddev.korshikov.bank.Server
