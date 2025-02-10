# Learning Platform

## Install FreeBSD
 * https://community.hetzner.com/tutorials/freebsd-openzfs-via-linux-rescue
## Server Setup
```
# adduser admin (wheel, no passwd auth)
# add ssh public key for admin
    mkdir ~/.ssh
    touch ~/.ssh/authorized_keys
    vi ~/.ssh/authorized_keys
    chmod -R 700 ~/.ssh
# vi /etc/resolv.conf    
# pkg install sudo
    vi /usr/local/etc/sudoers
    %wheel ALL=(ALL:ALL) NOPASSWD: ALL    
# pkg install git
# git clone --depth 1 https://git.FreeBSD.org/ports.git /usr/ports
# cd /usr/ports && make index
# cd /usr/ports/shells/zsh && make install
# cd /usr/ports/shells/ohmyzsh && make install
# chsh -s /usr/local/bin/zsh
# cd /usr/ports/editors/vim && make install
# cd /usr/ports/devel/maven && make install
# cd /usr/ports/java/openjdk23 && make install
# cd /usr/ports/sysutils/htop && make install
# cd /usr/ports/devel/gh && make install
# gh auth login --hostname github.com
# gh repo clone softhlon-learning/learning-platform
# cd /usr/ports/www/nginx && make install
# copy SSL certificates to /home/admin/certs
# sudo sysrc nginx_enable=yes
# sudo service nginx start
# cd /usr/ports/www/node22 && make install
# cd /usr/ports/www/npm-node22 && make install
# npm install -y -g @angular/cli
# npm install -g yarn      
# /usr/ports/mail/postfix && make install
# cd /usr/ports/databases/postgresql17-server && make install
# sysrc postgresql_enable=yes
# service postgresql initdb
# service postgresql start
# sudo -i -u postgres
# CREATE DATABASE learning
# ALTER USER postgres WITH ENCRYPTED PASSWORD '@z9X}r6hF£>8J2r_';
# adduser platform
# touch /var/log/backend.log
# chown platform /var/log/backend.log
# touch /var/log/frontend.log
# chown platform /var/log/frontend.log
# mkdir /var/run/backend
# chown platform /var/run/backend
# mkdir /var/run/frontend        
# chown platform /var/run/frontend 
# vim /usr/local/etc/nginx/nginx.conf
# vim /usr/local/etc/rc.d/backend
# vim /usr/local/etc/rc.d/frontend
# vim /home/admin/scripts/upgrade
```
