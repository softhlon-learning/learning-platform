#!/bin/zsh

cd ..
mvn clean install
cd application
mvn spring-boot:run
