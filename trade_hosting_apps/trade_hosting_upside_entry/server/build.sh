#!/bin/bash
#

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
CURRENT_SCRIPT_DIR=$( pwd )
cd - > /dev/null

CPP_PLATFORM_DIR="/data/code/soldier/platform/cpp_platform"

LIBRARYS=("xueqiao-contract/contract_convertor")
BRANCHS=("master")

pushd ${CURRENT_SCRIPT_DIR} > /dev/null

if [ ! -L "${CURRENT_SCRIPT_DIR}/cpp_platform" ];then
    ln -sf $CPP_PLATFORM_DIR cpp_platform || exit 1
fi
if [ ! -L "${CURRENT_SCRIPT_DIR}/trade_hosting_framework" ];then
    ln -sf ${CURRENT_SCRIPT_DIR}/../../trade_hosting_framework/cpp trade_hosting_framework || exit 1
fi
if [ ! -L "${CURRENT_SCRIPT_DIR}/trade_hosting_storage" ];then
    ln -sf ${CURRENT_SCRIPT_DIR}/../../trade_hosting_storage/cpp trade_hosting_storage || exit 1
fi

for((i=0;i<${#LIBRARYS[@]};i++));
do
  repository=${LIBRARYS[$i]}
  dir=${repository##*/}
  
  if [ ! -d $dir ];then
    git clone -b ${BRANCHS[$i]} ssh://git@git.soldier-tools.svc:29418/$repository.git || exit 
  else
    pushd $dir 
    git checkout ${BRANCHS[$i]} || exit
    git pull || exit
    popd 
  fi
done

source cpp_platform/envsetup.sh
blade build || exit 1
popd > /dev/null
