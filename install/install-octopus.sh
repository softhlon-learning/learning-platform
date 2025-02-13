#!/bin/sh

echo "Install octopus server start."
chmod +x /root/platform/install/common.sh

# common/shared stuff
/root/platform/install/common.sh

# postfix
cp /root/platform/config/postfix/main.cf.octopus /usr/local/etc/postfix/main.cf
service postfix start
newaliases

echo "Install octopus server stop."
