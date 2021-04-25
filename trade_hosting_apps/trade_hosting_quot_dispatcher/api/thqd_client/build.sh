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
if [ ! -f "${CURRENT_SCRIPT_DIR}/cpp_platform" ];then
    ln -sf $CPP_PLATFORM_DIR cpp_platform
fi

pushd ${CURRENT_SCRIPT_DIR} > /dev/null
source cpp_platform/envsetup.sh
blade build
popd > /dev/null
