#!/bin/zsh

cd ..
mvn clean install
if [ $? -eq 0 ]; then
else
  echo "Build failed. Application will not be started"
  cd application
  exit 1
fi
cd application
mvn spring-boot:run
