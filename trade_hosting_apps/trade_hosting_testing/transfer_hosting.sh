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



function help() {
	echo "$0 target_host"
}

if [ $# -lt 1 ];then
	help
	exit 1
fi

scp -P 36000 ${THIS_SCRIPT_REAL_DIR}/build/libs/trade_hosting_testing-1.0-SNAPSHOT-jar-with-dependencies.jar root@$1:/root/trade_hosting_testing-1.0-SNAPSHOT-jar-with-dependencies.jar