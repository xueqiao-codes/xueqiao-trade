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
TRADE_HOSTING_APPS_SYSTEM_DIR=$( pwd )
cd - > /dev/null

INSTALL_APPS_BASEDIR="/usr/local/soldier/apps"

function copyFile2Remote() {
	local target_host=$1
	local local_file=$2
	local remote_file=$3
	scp -P 36000 $local_file root@$target_host:$remote_file || exit
}

function installJava() {
	if [ ! -d "build/libs" ];then
		echo "please build before can install, no build/libs directory found!"
		exit 1
	fi
	local target_host=$1
	local install_module_name=$2
	local install_app_dir=$INSTALL_APPS_BASEDIR/$install_module_name
	
	local jar_file=$( ls -al build/libs | grep "jar-with-dependencies.jar$" | grep -v grep | awk '{print $NF}' )
	local remote_jar_file="/tmp/$(date +%s)_${jar_file//\//_}"
	local remote_supervisor_conf="/tmp/$(date +%s)_${install_module_name}_supervisor.conf"
	copyFile2Remote $target_host build/libs/$jar_file $remote_jar_file || exit
	copyFile2Remote $target_host supervisor.conf $remote_supervisor_conf || exit
	ssh -p 36000 root@$target_host \
		"cp -f ${remote_jar_file} ${install_app_dir}/${jar_file} && \
		cp -f ${remote_supervisor_conf} ${install_app_dir}/supervisor.conf && \
		supervisorctl update &&
		supervisorctl restart $install_module_name &&
		rm -f ${remote_jar_file} ${remote_supervisor_conf}" || exit
}

function installCpp() {
	local cpp_builddir="build64_release"
	if [ ! -d "$cpp_builddir" ];then
		echo "please build before can install, no build64_release directory found!"
		exit 1
	fi
	
	local target_host=$1
	local install_module_name=$2
	local install_app_dir=$INSTALL_APPS_BASEDIR/$install_module_name
	
	local bin_file="${install_module_name}"
	local remote_bin_file="/tmp/$(date +%s)_${bin_file//\//_}"
	local remote_supervisor_conf="/tmp/$(date +%s)_${install_module_name}_supervisor.conf"
	copyFile2Remote $target_host ${cpp_builddir}/$bin_file $remote_bin_file || exit
	copyFile2Remote $target_host supervisor.conf $remote_supervisor_conf || exit
	ssh -p 36000 root@$target_host \
		"cp -f ${remote_bin_file} ${install_app_dir}/${bin_file} && \
		cp -f ${remote_supervisor_conf} ${install_app_dir}/supervisor.conf && \
		supervisorctl update &&
		supervisorctl restart $install_module_name &&
		rm -f ${remote_bin_file} ${remote_supervisor_conf}" || exit
}

function installTradeHostingUpsideEntry() {
	local cpp_builddir="build64_release"
	if [ ! -d "$cpp_builddir" ];then
		echo "please build before can install, no build64_release directory found!"
		exit 1
	fi
	
	local target_host=$1
	local install_app_dir=$INSTALL_APPS_BASEDIR/trade_hosting_upside_entry
	
	local remote_entry_file="/tmp/$(date +%s)_trade_hosting_upside_entry"
	local remote_ctp_file="/tmp/$(date +%s)_trade_hosting_upside_ctp"
	local remote_ctp2_r_file="/tmp/$(date +%s)__trade_hosting_upside_ctp2_r"
	local remote_ctp2_t_file="/tmp/$(date +%s)__trade_hosting_upside_ctp2_t"
	local remote_es9_file="/tmp/$(date +%s)_trade_hosting_upside_es9"
	local remote_supervisor_conf="/tmp/$(date +%s)_trade_hosting_upside_entry_supervisor.conf"
	copyFile2Remote $target_host  ${cpp_builddir}/trade_hosting_upside_entry $remote_entry_file || exit
	copyFile2Remote $target_host  ${cpp_builddir}/trade_hosting_upside_ctp $remote_ctp_file || exit
	copyFile2Remote $target_host  ${cpp_builddir}/trade_hosting_upside_ctp2_r $remote_ctp2_r_file || exit
	copyFile2Remote $target_host  ${cpp_builddir}/trade_hosting_upside_ctp2_t $remote_ctp2_t_file || exit
	copyFile2Remote $target_host  ${cpp_builddir}/trade_hosting_upside_es9 $remote_es9_file || exit
	copyFile2Remote $target_host supervisor.conf $remote_supervisor_conf || exit
	ssh -p 36000 root@$target_host \
		"cp -f ${remote_entry_file} ${install_app_dir}/trade_hosting_upside_entry && \
		cp -f ${remote_ctp_file} ${install_app_dir}/trade_hosting_upside_ctp && \
		cp -f ${remote_ctp2_r_file} ${install_app_dir}/trade_hosting_upside_ctp2_r && \
		cp -f ${remote_ctp2_t_file} ${install_app_dir}/trade_hosting_upside_ctp2_t && \
		cp -f ${remote_es9_file} ${install_app_dir}/trade_hosting_upside_es9 && \
		cp -f ${remote_supervisor_conf} ${install_app_dir}/supervisor.conf && \
		supervisorctl update &&
		supervisorctl restart $install_module_name &&
		rm -f ${remote_entry_file} ${remote_ctp_file} ${remote_es9_file} ${remote_supervisor_conf}" || exit
}

function install() {
	local target_host=$1
	local target_dir=$2
		
	if [ ! -d $target_dir ];then
		echo "Directory $target_dir is not existed!"
		exit 1
	fi
	
	pushd $target_dir > /dev/null
	
	if [ ! -f supervisor.conf ];then
		echo "No supervisor.conf in $target_dir!"
		exit 1
	fi
	
	local install_module_name=$( head -1 supervisor.conf | awk -F ':' '{print $2}' | awk -F ']' '{print $1}' | awk '{print $1}' )
	if [ "z" = "z$install_module_name" ];then
		echo "Failed to found install module name! Please check the supervisor.conf first line is [program:xxxx]"
		exit 1
	fi
	
	if [ "$install_module_name" == "trade_hosting_upside_entry" ];then
		installTradeHostingUpsideEntry $target_host || exit
	else
		if [ -f "build.gradle" ];then
			installJava $target_host $install_module_name || exit
		elif [ -f "BLADE_ROOT" ];then
			installCpp $target_host $install_module_name || exit
		else
			echo "Unkonw install type! Not gradle project or blade project!"
			exit 1
		fi
	fi

	popd > /dev/null
}

function help() {
	echo "$0 target_host [app_dir(optional, default is current dir)]"
}

if [ $# -lt 1 ];then
	help
	exit 1
fi

if [ $# -eq 1 ];then
	install $1  "."
else
	install $1 $2
fi
