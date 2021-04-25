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


JAVA_APPS=(
trade_hosting_arbitrage/server
trade_hosting_asset/server
trade_hosting_contract
trade_hosting_dealing/server
trade_hosting_entry/java
trade_hosting_history/server
trade_hosting_id_maker/id_maker_dao/java
trade_hosting_position_adjust/server
trade_hosting_position_statis/server
trade_hosting_push/server
trade_hosting_quot_comb/server
trade_hosting_startup
trade_hosting_storage/java/daemon
trade_hosting_tradeaccount_data/server
trade_hosting_tasknote/server
trade_hosting_position_fee/server
trade_hosting_risk_manager/server
)

CPP_APPS=(
trade_hosting_quot_dispatcher/daemon
)

UPSIDE_ENTRY_BINS=(
trade_hosting_upside_entry
trade_hosting_upside_ctp
trade_hosting_upside_es9
trade_hosting_upside_ctp2_r
trade_hosting_upside_ctp2_t
)

INSTALL_APPS_BASEDIR="/usr/local/soldier/apps"

function getAppName() {
	local app=$1
	echo ${app%%/*}
}

function getSupervisorLink() {
	local supervisor_path=$1
	echo ${supervisor_path//\//_}
}

function installJavaApp() {
	local java_app=$1
	local app_name=$(getAppName $java_app)
	
	if [ "trade_hosting_storage" = "$app_name" ];then
		app_name="trade_hosting_storage_daemon"
	fi
	
	echo "--->Install java app for $app_name in $java_app..."
	
	local install_app_dir="$INSTALL_APPS_BASEDIR/$app_name"
	mkdir -p $install_app_dir || exit
	
	pushd $java_app > /dev/null
	
		# find jar with dependencies in build/libs
		pushd build/libs > /dev/null
			jar_file=$( ls -al | grep "jar-with-dependencies.jar$" | grep -v grep | awk '{print $NF}' )
			cp -f $jar_file $install_app_dir || exit
	
			pushd $install_app_dir  > /dev/null
				ln -sf $install_app_dir/$jar_file $app_name-jar-with-dependencies.jar
			popd > /dev/null
	
		popd > /dev/null
	
		cp -f supervisor.conf $install_app_dir || exit
	
		local supervisor_path="$install_app_dir/supervisor.conf"
		ln -sf $supervisor_path "/etc/supervisor/conf.d/$( getSupervisorLink $supervisor_path )" || exit
	
	popd > /dev/null
}

function installCppApp() {
	local cpp_app=$1
	local app_name=$(getAppName $cpp_app)
	echo "--->Install cpp app for $app_name in $cpp_app..."
	
	local install_app_dir="$INSTALL_APPS_BASEDIR/$app_name"
	mkdir -p $install_app_dir || exit
	
	local build_dir="build64_release"
	
	pushd $cpp_app > /dev/null
		
		pushd $build_dir > /dev/null
			cp -f $app_name $install_app_dir || exit
		popd > /dev/null
		
		ldd ${build_dir}/$app_name | grep "${build_dir}" | awk '{print $1}' | while read dependency
		do
			local target_dir=$(dirname "${install_app_dir}/${dependency}")
			mkdir -p ${target_dir} || exit
			
			cp -f $dependency ${target_dir} || exit
		done
		
		cp -f supervisor.conf $install_app_dir || exit
		
		local supervisor_path="$install_app_dir/supervisor.conf"
		ln -sf $supervisor_path "/etc/supervisor/conf.d/$( getSupervisorLink $supervisor_path )" || exit
	
	popd > /dev/null
}

function installUpsideEntry() {
	local install_app_dir="$INSTALL_APPS_BASEDIR/trade_hosting_upside_entry"
	mkdir -p $install_app_dir || exit
	
	local build_dir="build64_release"
	pushd trade_hosting_upside_entry/server > /dev/null
		
		for upside_entry_bin in ${UPSIDE_ENTRY_BINS[@]};
		do
			pushd $build_dir > /dev/null
			cp -f $upside_entry_bin $install_app_dir || exit
			popd > /dev/null
			
			ldd ${build_dir}/$upside_entry_bin | grep "${build_dir}" | awk '{print $1}' | while read dependency
			do
				local target_dir=$(dirname "${install_app_dir}/${dependency}")
				mkdir -p ${target_dir} || exit
			
				cp -f $dependency ${target_dir} || exit
			done
		done
		
		cp -f supervisor.conf $install_app_dir || exit
		
		local supervisor_path="$install_app_dir/supervisor.conf"
		ln -sf $supervisor_path "/etc/supervisor/conf.d/$( getSupervisorLink $supervisor_path )" || exit
	popd > /dev/null
}

function installBuildVersion() {
	if [ "z" != "z${BUILD_VERSION}" ];then
		echo "${BUILD_VERSION}" > /build_version || exit
	fi
}

function main() {
	pushd ${TRADE_HOSTING_APPS_DIR} > /dev/null

	mkdir -p ${INSTALL_APPS_BASEDIR} || exit
	
	for java_app in ${JAVA_APPS[@]};
	do
		installJavaApp $java_app || exit
	done
	
	for cpp_app in ${CPP_APPS[@]};
	do
		installCppApp $cpp_app || exit
	done
	
	installUpsideEntry || exit
	
	cp entrypoint.sh  / || exit
	chmod a+x /entrypoint.sh || exit
	popd > /dev/null
	
	installBuildVersion || exit
}

main || exit