#!/bin/sh

echo "Install common packages"

# system packages
pkg install -y vim
pkg install -y htop
pkg install -y sudo
pkg install -y zsh
pkg install -y ohmyzsh
pkg install -y maven
pkg install -y openjdk23
pkg install -y nginx
pkg install -y node22
pkg install -y npm-node22
pkg install -y postfix
pkg install -y postgresql17-server

# npm packages
npm install -y -g @angular/cli
npm install -y -g yarn

# rc.conf configuration
sudo sysrc pf_enable=yes
sudo sysrc nginx_enable=yes
sudo sysrc postfix_enable=yes
sudo sysrc postgresql_enable=yes
sudo sysrc backend_enable=yes
sudo sysrc frontend_enable=yes

# postfix
newaliases

# postgresql
service postgresql initdb
service postgresql start

# create database
su - postgres -c 'psql -c "CREATE DATABASE learning;"'
rm -fr /tmp/alter-user.sh

# set user's password
echo "psql -c \"ALTER USER postgres WITH ENCRYPTED PASSWORD '@z9X}r6hF£>8J2r_';\"" >> /tmp/alter-user.sh
chmod o+x /tmp/alter-user.sh
su - postgres -c '/tmp/alter-user.sh'
rm -fr /tmp/alter-us.sh
