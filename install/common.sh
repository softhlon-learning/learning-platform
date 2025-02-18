#!/bin/sh

# timezone
tzsetup Europe/Warsaw

# system packages
pkg install -y vim
pkg install -y htop
pkg install -y nmap
pkg install -y mutt
pkg install -y sudo
pkg install -y zsh
pkg install -y ohmyzsh
pkg install -y maven
pkg install -y openjdk23
pkg install -y nginx
pkg install -y node22
pkg install -y npm-node22
pkg install -y postfix
pkg install -y opendkim
pkg install -y postgresql17-server
pkg install -y openssh-portable

# npm packages
npm install -y -g @angular/cli
npm install -y -g yarn

# rc.conf configuration
sysrc nginx_enable=yes
sysrc postfix_enable=yes
sysrc postgresql_enable=yes
sysrc backend_enable=yes
sysrc frontend_enable=yes
sysrc milteropendkim_enable=yes
sysrc pf_enable=yes
sysrc sshd_enable=no
sysrc openssh_enable=yes

# sysctl.conf
cp /root/platform/config/etc/sysctl.conf /etc/sysctl.conf

# certificates
mkdir /var/certs
cp -r /root/platform/config/certs/* /var/certs

mkdir /home

# user admin
pw adduser admin -d /home/admin -s /usr/local/bin/zsh -c "Admin" -m -w no
pw group mod wheel -m admin

# user platform
pw adduser platform -d /home/platform -s /bin/sh -c "Learning Platform" -m -w no

# user support
pw adduser support -d /home/support -s /bin/sh -c "Support Team" -m -w no

# user report
pw adduser report -d /home/report -s /bin/sh -c "DMARC Report" -m -w no

# user contact
pw adduser contact -d /home/contact -s /bin/sh -c "Contact" -m -w no

# nginx
mkdir -p /home/admin/nginx
cp -r /root/platform/config/nginx/nginx.conf /usr/local/etc/nginx/nginx.conf
cp -r /root/platform/config/nginx/page* /home/admin/nginx
chmod -R o+rx /home/admin/nginx
service nginx start

# postgresql
service postgresql initdb
service postgresql start

# postgresql - create database
su - postgres -c 'psql -c "CREATE DATABASE learning;"'
rm -fr /tmp/alter-user.sh

# postgresql - set user's password
sudo -u postgres psql -c "ALTER USER postgres WITH ENCRYPTED PASSWORD '@z9X}r6hF£>8J2r_';"

# backend
touch /var/log/backend.log
chown platform /var/log/backend.log
mkdir /var/run/backend
chown platform /var/run/backend
cp /root/platform/config/scripts/backend /usr/local/etc/rc.d/
chmod -R +x /usr/local/etc/rc.d/backend

# frontend
touch /var/log/frontend.log
chown platform /var/log/frontend.log
mkdir /var/run/frontend
chown platform /var/run/frontend
cp /root/platform/config/scripts/frontend /usr/local/etc/rc.d/
chmod -R +x /usr/local/etc/rc.d/frontend

# upgrade
mkdir /home/admin/scripts
cp /root/platform/config/scripts/upgrade.sh /home/admin/scripts
chown -R admin:admin /home/admin/scripts
chmod +x /home/admin/scripts/upgrade.sh

# gh authentication
cp -r /root/.config /home/admin/
chown -R admin:admin /home/admin/.config

# sudoers
echo "%wheel ALL=(ALL:ALL) NOPASSWD: ALL" >> /usr/local/etc/sudoers

# zsh
cp /root/platform/config/.zshrc /home/admin/

# muttrc
cp /root/platform/config/.muttrc /home/admin/

# .ssh/authorized_keys
mkdir /home/admin/.ssh
cp /root/platform/config/.ssh/authorized_keys /home/admin/.ssh/
chown -R admin:admin /home/admin/.ssh/
chmod -R 700 /home/admin/.ssh/

# pf
cp /root/platform/config/etc/pf.conf /etc/
#sudo service pf start

# mailer
cp /root/platform/config/etc/mail/mailer.conf /etc/mail/mailer.conf

# opendkim
pw groupmod mailnull -m postfix
mkdir /usr/local/etc/mail/keys
cp -r /root/platform/config/etc/mail/keys/* /usr/local/etc/mail/keys/
cp /root/platform/config/etc/mail/opendkim.conf /usr/local/etc/mail/
chown -R mailnull:mailnull /usr/local/etc/mail/keys/java-fullstack.tech
chmod 550 /usr/local/etc/mail/keys/java-fullstack.tech
chmod 600 /usr/local/etc/mail/keys/java-fullstack.tech/*

service milter-opendkim start
