#!/bin/sh

# start
echo "Install octopus server start."
chmod +x /root/platform/install/common.sh

# common/shared stuff
/root/platform/install/common.sh

# postfix
cp /root/platform/config/postfix/main.cf.octopus /usr/local/etc/postfix/main.cf
sudo newaliases
service postfix start

# postgres replication user
sudo -u postgres -c psql -c "CREATE ROLE replication REPLICATION LOGIN PASSWORD '@z9X}r6hF£>8J2r_'";

# pg_hba.conf update
echo "host    replication     replication     10.0.0.2/32   md5" >> /var/db/postgres/data17/pg_hba.conf

# postgresql.conf update
echo "listen_addresses = 'localhost,10.0.0.1'" >> /var/db/postgres/data17/postgresql.conf

# restart service
service postgresql restart

# stop
echo "Install octopus server stop."
