#!/bin/bash
if [ -L "$0" ];then
	THIS_SCRIPT_REAL_DIR=$( dirname $0 )
else
	# run this script directly, can be run everywhere
	THIS_SCRIPT_REAL_DIR="$(dirname "${BASH_SOURCE:-$0}")"
	if [ "$( echo ${THIS_SCRIPT_REAL_DIR} | grep '^/' | wc -l )" = "0" ];then
		THIS_SCRIPT_REAL_DIR="$( pwd )/${THIS_SCRIPT_REAL_DIR}"
	fi
fi
cd ${THIS_SCRIPT_REAL_DIR} > /dev/null
TRADE_HOSTING_APPS_DIR=$( pwd )
cd - > /dev/null

pushd trade_hosting_quot_dispatcher/daemon > /dev/null
chmod a+x build.sh || exit
./build.sh || exit
popd > /dev/null

pushd trade_hosting_upside_entry/server > /dev/null
chmod a+x build.sh || exit
./build.sh || exit
popd > /dev/null

pushd ${TRADE_HOSTING_APPS_DIR} > /dev/null
gradle clean build || exit

#pushd tools/jmxmon > /dev/null
#mvn clean package || exit 
#popd > /dev/null


popd > /dev/null