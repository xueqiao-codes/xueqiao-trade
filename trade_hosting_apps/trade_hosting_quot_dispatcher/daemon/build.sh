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

LIBRARYS=("xueqiao-quotation/quotation_common" "xueqiao-quotation/quotation_subscribe")
BRANCHS=("master" "master")

pushd ${CURRENT_SCRIPT_DIR} > /dev/null

CPP_PLATFORM_DIR="/data/code/soldier/platform/cpp_platform"
if [ ! -L "${CURRENT_SCRIPT_DIR}/cpp_platform" ];then
    ln -sf $CPP_PLATFORM_DIR cpp_platform
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
