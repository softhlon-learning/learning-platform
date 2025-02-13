#!/bin/sh

sudo -u postgres pg_ctl promote -D /var/db/postgres/data17/
