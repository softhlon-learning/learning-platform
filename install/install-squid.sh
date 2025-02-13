#!/bin/sh

echo "Install squid server start."
chmod +x /root/platform/install/common.sh

# common/shared stuff
/root/platform/install/common.sh

# postfix
cp /root/platform/config/postfix/main.cf.squid /usr/local/etc/postfix/main.cf
newaliases
service postfix start

# postgresql.conf update
echo "listen_addresses = 'localhost,10.0.0.1'" >> /var/db/postgres/data17/postgresql.conf
echo "hot_standby = on" >> /var/db/postgres/data17/postgresql.conf

# prepare password for replication
touch /home/postgres/.pgpass
chown postgres:postgres /home/postgres/.pgpass
chmod 600 /home/postgres/.pgpass
echo "10.0.0.1:5432:*:replication:@z9X}r6hF£>8J2r_" >> /home/postgres/.pgpass

# start replication
cp /var/db/postgres/data17/postgresql.conf /tmp/postgresql.conf
cp /var/db/postgres/data17/pg_hba.conf /tmp/pg_hba.conf
rm -fr v/var/db/postgres/data17/*
pg_basebackup -h 10.0.0.1 -U replication -p 5432 -D /var/db/postgres/data17/  -Fp -Xs -P -R -w
cp /tmp/postgresql.conf /var/db/postgres/data17/
cp /tmp/pg_hba.conf /var/db/postgres/data17/
chown -R postgres:postgres /var/db/postgres/data17

# restart service
service postgresql start

echo "Install squid server stop."
