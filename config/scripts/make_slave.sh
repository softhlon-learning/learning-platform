#!/bin/sh

# dump slave
sudo -u postgres pg_dump -c learning > /tmp/learning.dump

# restore master from slave
scp /tmp/learning.dump admin@10.0.0.1:
ssh admin@10.0.0.1 -t 'sudo -u postgres psql -d learning < /home/admin/learning.dump'

# continue on slave
sudo service postgresql stop

sudo -u postgres cp /var/db/postgres/data17/postgresql.conf /tmp/postgresql.conf
sudo -u postgres cp /var/db/postgres/data17/pg_hba.conf /tmp/pg_hba.conf
sudo /bin/sh -c 'rm -fr /var/db/postgres/data17/*'
sudo -u postgres pg_basebackup -h 10.0.0.1 -U replication -p 5432 -D /var/db/postgres/data17/  -Fp -Xs -P -R -w
sudo -u postgres cp /tmp/postgresql.conf /var/db/postgres/data17/
sudo -u postgres cp /tmp/pg_hba.conf /var/db/postgres/data17/

sudo service postgresql start
