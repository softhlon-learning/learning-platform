# Learning Platform

## Install FreeBSD
 * https://community.hetzner.com/tutorials/freebsd-openzfs-via-linux-rescue
## Server Setup
```
# pkg install git
# git clone --depth 1 https://git.FreeBSD.org/ports.git /usr/ports
# cd /usr/ports && make index
# cd /usr/ports/security/sudo && make install
# cd /usr/ports/shels/zsh && make install
# cd /usr/ports/devel/maven && make install
# cd /usr/ports/javq/openjdk23 && make install
# cd /usr/ports/devel/gh && make install
# gh auth login --hostname github.com
# gh repo clone softhlon-learning/learning-platform
# cd /usr/ports/www/nginx && make install
# sudo sysrc nginx_enable=yes
# sudo service nginx start
# cd /usr/ports/www/node22 && make install
# cd /usr/ports/www/npm-node22 && make install
# npm install -y -g @angular/cli
# cd /usr/ports/databases/postgresql17-server && make install
# sysrc postgresql_enable=yes
# service postgresql initdb
# service postgresql start
# sudo -i -u postgres
# CREATE DATABASE learning
# ALTER USER postgres WITH ENCRYPTED PASSWORD '@z9X}r6hF£>8J2r_';
# vim /usr/local/etc/nginx/nginx.conf
```
