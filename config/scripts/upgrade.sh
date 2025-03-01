#!/bin/sh

# start
echo Upgrade started.

# stop services
sudo service frontend stop
sudo service backend stop
sudo rm -fr /home/admin/learning-platform
cd

# clone gh repository
gh repo clone softhlon-learning/learning-platform
cd /home/admin/learning-platform/backend

# build backend
mvn clean install
cp application/target/backend.jar application/backend.jar
mvn clean

# sunc S3 content
sudo chmod +x /home/admin/learning-platform/install/s3-content-sync.sh
sudo /home/admin/learning-platform/install/s3-content-sync.sh

# start backend service
sudo service backend start

# build frontned
cd /home/admin/learning-platform/frontend
yarn install
sudo chown -R platform /home/admin/learning-platform/frontend

# start frontend service
sudo service frontend start

# stop
echo Upgrade finished.
