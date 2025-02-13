#!/bin/sh

sudo -u postgres pg_dump learning > /tmp/learning.dump
sudo service postgresql stop

sudo -u postgres cp /var/db/postgres/data17/postgresql.conf /tmp/postgresql.conf
sudo -u postgres cp /var/db/postgres/data17/pg_hba.conf /tmp/pg_hba.conf
sudo /bin/sh -c 'rm -fr /var/db/postgres/data17/*'
sudo -u postgres pg_basebackup -h 10.0.0.1 -U replication -p 5432 -D /var/db/postgres/data17/  -Fp -Xs -P -R -w
sudo -u postgres cp /tmp/postgresql.conf /var/db/postgres/data17/
sudo -u postgres cp /tmp/pg_hba.conf /var/db/postgres/data17/

sudo service postgresql start
