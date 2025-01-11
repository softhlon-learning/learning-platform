# Learning Platform

## Server Setup
```
sudo apt update
apt upgrade
adduser debian
echo "deb http://deb.debian.org/debian bookworm-backports main contrib non-free" >> /etc/apt/sources.list
sudo apt update
sudo apt -t bookworm-backports install linux-image-amd64
sudo shutdown -r
sudo apt install -y postgresql
sudo apt install -y nginx
sudo apt install -y gh
gh auth login --hostname github.com --with-token <<< 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
wget https://download.oracle.com/java/23/latest/jdk-23_linux-x64_bin.deb
sudo dpkg -i jdk-23_linux-x64_bin.deb
sudo apt install maven
sudo apt install -y git
curl -sL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs
sudo npm install -y -g @angular/cli
sudo npm install -g npm@11.0.0
```
