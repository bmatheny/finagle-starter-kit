#!/bin/sh

if [ -z "$SBT_OPTS" ]; then
	SBT_OPTS="-Xmx4096m -Xms4096m -XX:NewSize=768m -XX:MaxPermSize=1024m";
fi
if [ -z "$SBT_BOOT_PROPERTIES" ]; then
	SBT_BOOT_PROPERTIES="" #-Dsbt.boot.properties=`dirname $0`/project/sbt.boot.properties"
fi
export SBT_PROXY_REPO="http://10.60.26.92:8081/nexus/content/groups/public/"
java ${SBT_OPTS} ${SBT_BOOT_PROPERTIES} -Dactors.minPoolSize=128 -Dactors.corePoolSize=256 -Dactors.maxPoolSize=512 -jar `dirname $0`/lib/sbt-launch.jar "$@"
