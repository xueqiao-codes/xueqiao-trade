#
# install script
#

import sys
sys.path.append("/public/scripts/machine")

import os

from fabric.contrib import project, console, files
from common import getEnv
from supervisord import *
from epower import *

app_name="jmxmon"
app_version="0.0.2"
install_dir="/usr/local/soldier/apps/"+ app_name
target_jar_file=app_name + "-" + app_version + "-jar-with-dependencies.jar" 
running_jar_file=app_name + "-jar-with-dependencies.jar"

def _install(env, fabfile_dir):
    run("mkdir -p " + install_dir )
    if not files.exists("/data/applog/apps/" + app_name):
        run("mkdir -p /data/applog/apps/" + app_name)
    run('cp -f ' + fabfile_dir + "/conf.properties " + install_dir)
    run('cp -f ' + fabfile_dir + "/target/" + target_jar_file + " " + install_dir)
   
    with cd(install_dir) : 
        run('ln -sf ' + target_jar_file + " " + running_jar_file)
    run('cp -f ' + fabfile_dir + '/supervisor.conf ' + install_dir)
    addSupervisorConfig(install_dir)
    run("supervisorctl update")
    restart()
    

def install():
    _install(getEnv(), os.path.dirname(__file__))

def restart():
    run("supervisorctl restart " + app_name)

def uninstall():
    removeSupervisorConfig(install_dir)
    run("supervisorctl update")
    run("rm -rf " + install_dir)
    