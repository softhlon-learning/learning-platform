#!/bin/sh

echo "Install squid server start."
chmod +x /root/platform/install/common.sh

# common/shared stuff
/root/platform/install/common.sh

# postfix
cp /root/platform/config/postfix/main.cf.squid /usr/local/etc/postfix/main.cf
sudo newaliases
service postfix start

# postgresql.conf update
echo "listen_addresses = 'localhost,10.0.0.1'" >> /var/db/postgres/data17/postgresql.conf
echo "hot_standby = on" >> /var/db/postgres/data17/postgresql.conf

# prepare password for replication
touch /var/db/postgres/.pgpass
chown postgres:postgres /var/db/postgres/.pgpass
chmod 600 /var/db/postgres/.pgpass
echo "10.0.0.1:5432:*:replication:@z9X}r6hF£>8J2r_" >> /var/db/postgres/.pgpass

# start replication
service postgresql stop
cp /var/db/postgres/data17/postgresql.conf /tmp/postgresql.conf
cp /var/db/postgres/data17/pg_hba.conf /tmp/pg_hba.conf
rm -fr /var/db/postgres/data17/*
su - postgres -c 'pg_basebackup -h 10.0.0.1 -U replication -p 5432 -D /var/db/postgres/data17/  -Fp -Xs -P -R -w'
cp /tmp/postgresql.conf /var/db/postgres/data17/
cp /tmp/pg_hba.conf /var/db/postgres/data17/
chown -R postgres:postgres /var/db/postgres/data17

# restart service
service postgresql start

# ssh private key
cp /root/platform/config/.ssh/id_rsa /home/admin/.ssh/
chown -R admin:admin /home/admin/.ssh/
chmod -R 700 /home/admin/.ssh/

# make master/slave
cp /root/platform/config/scripts/make_master.sh /home/admin/scripts
cp /root/platform/config/scripts/make_slave.sh /home/admin/scripts
chmod -R +x /home/admin/scripts

echo "Install squid server stop."
