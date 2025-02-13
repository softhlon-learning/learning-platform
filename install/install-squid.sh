#!/bin/sh

echo "Install squid server start."
chmod +x /root/platform/install/common.sh

# common/shared stuff
/root/platform/install/common.sh

# postfix
cp /root/platform/config/postfix/main.cf.squid /usr/local/etc/postfix/main.cf
service postfix start
newaliases

echo "Install squid server stop."
