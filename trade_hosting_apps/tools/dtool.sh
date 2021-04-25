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
cd ${THIS_SCRIPT_REAL_DIR}/.. > /dev/null
SLED_TRADE_SYSTEM_DIR=$( pwd )
cd - > /dev/null

CPP_DIRS=(
"trade_hosting_upside_entry"
"trade_hosting_quot_dispatcher/daemon"
)

INSTALL_APPS=(
"trade_hosting_id_maker/id_maker_dao/java"
"trade_hosting_contract"
"trade_hosting_storage/java/daemon"
"trade_hosting_dealing/server"
"trade_hosting_quot_comb"
"trade_hosting_quot_dispatcher/daemon"
"trade_hosting_upside_entry"
"trade_hosting_tradeaccount_data/server"
"trade_hosting_push/server"
"trade_hosting_entry/java"
"trade_hosting_arbitrage/server"
"trade_hosting_asset/server"
"trade_hosting_history/server"
"trade_hosting_position_adjust/server"
"trade_hosting_position_statis/server"
"trade_hosting_startup"
)

function compile_apps() {
    pushd $SLED_TRADE_SYSTEM_DIR > /dev/null
    gradle clean build
    popd > /dev/null
    for cpp_dir in ${CPP_DIRS[@]};
    do
        pushd ${SLED_TRADE_SYSTEM_DIR}/${cpp_dir} > /dev/null
        echo "[COMPILE]${cpp_dir}..." 
        chmod u+x ./build.sh
        ./build.sh || exit 1
        popd > /dev/null
    done
}

function install_apps() {
    if [ $# -lt 1 ];then
        echo "install need one parameter" 
        exit 1
    fi
    echo "install app to hosts $1"
    for app_dir in ${INSTALL_APPS[@]};  
    do  
        pushd ${SLED_TRADE_SYSTEM_DIR}/${app_dir} > /dev/null
        fab install -H $1 || exit 1
        popd > /dev/null
    done  
}

function help() {
    echo "$0 compile|install (machine1,machine2...)"
}

if [ $# -lt 1 ];then
    help
    exit 1
fi

case "$1" in
    "compile")
        compile_apps
    ;;
    "install")
        if [ $# -lt 2 ];then
            echo "plese input machine list for install"
            exit 2
        fi
        install_apps $2
    ;;
    * )
        help
        exit 1
    ;;
esac


