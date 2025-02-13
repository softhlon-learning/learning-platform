#!/bin/sh

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
sysrc nginx_enable=yes
sysrc postfix_enable=yes
sysrc postgresql_enable=yes
sysrc backend_enable=yes
sysrc frontend_enable=yes
sysrc pf_enable=yes

# certificates
mkdir /var/certs
cp -r /root/platform/config/certs/* /var/certs

# nginx
cp -r /root/platform/config/nginx/nginx.conf /usr/local/etc//nginx/nginx.conf
service nginx start

# postgresql
service postgresql initdb
service postgresql start

# postgresql - create database
su - postgres -c 'psql -c "CREATE DATABASE learning;"'
rm -fr /tmp/alter-user.sh

# postgresql - set user's password
echo "psql -c \"ALTER USER postgres WITH ENCRYPTED PASSWORD '@z9X}r6hF£>8J2r_';\"" >> /tmp/alter-user.sh
chmod o+x /tmp/alter-user.sh
su - postgres -c '/tmp/alter-user.sh'
rm -fr /tmp/alter-us.sh

mkdir /home
# user platform
pw adduser platform -d /home/platform -s /bin/sh -c "Learning Platform" -m -w no
# user admin
pw adduser admin -d /home/admin -s /usr/local/bin/zsh -c "Admin" -m -w no
pw group mod wheel -m admin

# backend
touch /var/log/backend.log
chown platform /var/log/backend.log
mkdir /var/run/backend
chown platform /var/run/backend
cp /root/platform/config/scripts/backend /usr/local/etc/rc.d/
chmod +x -R /usr/local/etc/rc.d/backend

# frontend
touch /var/log/frontend.log
chown platform /var/log/frontend.log
mkdir /var/run/frontend
chown platform /var/run/frontend
cp /root/platform/config/scripts/frontend /usr/local/etc/rc.d/
chmod +x -R /usr/local/etc/rc.d/frontend

# upgrade
mkdir /home/admin/scripts
cp /root/platform/config/scripts/upgrade /home/admin/scripts
chown -R admin:admin /home/admin/scripts
chmod +x /home/admin/scripts/upgrade

# gh authentication
cp -r /root/.config /home/admin/
chown -R admin:admin /home/admin/.config

# sudoers
echo "%wheel ALL=(ALL:ALL) NOPASSWD: ALL" >> /usr/local/etc/sudoers

# zsh
cp /root/platform/config/.zshrc /home/admin/

# .ssh/authorized_keys
mkdir /home/admin/.ssh
cp /root/platform/config/.ssh/authorized_keys /home/admin/.ssh/
chown -R admin:admin /home/admin/.ssh/
chmod -R 600 /home/admin/.ssh/

# pf
cp /root/platform/config/etc/pf.conf /etc/
sudo service pf start
