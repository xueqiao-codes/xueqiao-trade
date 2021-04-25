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
CURRENT_SCRIPT_DIR=$( pwd )
cd - > /dev/null


pushd ${THIS_SCRIPT_REAL_DIR} > /dev/null

./build.sh || exit 1

source cpp_platform/envsetup.sh

blade build --generate-java || exit 1

rm -f java/src/main/java/xueqiao/trade/hosting/quot/dispatcher/client/swig/*.java
cp -f build64_release/xueqiao/trade/hosting/quot/dispatcher/client/swig/*.java java/src/main/java/xueqiao/trade/hosting/quot/dispatcher/client/swig/ || exit 1
cp -f build64_release/libTHQDClient_java.so java/src/main/resources/linux || exit 1

pushd java
mvn package || exit 1
popd > /dev/null

#echo "runing test...."
#chmod u+x java/test/test.sh
#java/test/test.sh || exit 1

echo "Finished..."

popd > /dev/null