#!/bin/bash

function installBasic() {
	yum -y install zip unzip which 
	yum -y install epel-release
	yum -y install python-pip
	yum -y install dmidecode
	yum -y install lshw
}

function installMysqlClient() {
	yum -y install mysql
}

function installJava() {
	rpm -ivh java/jdk-11.0.1_linux-x64_bin.rpm
}

function installMaven() {
	pushd maven > /dev/null
	tar xzvf apache-maven-3.3.9-bin.tar.gz || exit
	mv apache-maven-3.3.9 /usr/local/ || exit
	ln -sf /usr/local/apache-maven-3.3.9/bin/mvn /usr/local/bin/mvn || exit
	cp -f conf/settings.xml /usr/local/apache-maven-3.3.9/conf/ || exit
	popd > /dev/null
}

function installGradle() {
	pushd gradle > /dev/null
	unzip gradle-4.8.1-bin.zip || exit
	mv gradle-4.8.1 /usr/local/ || exit
	echo "export GRADLE_HOME=/usr/local/gradle-4.8.1" >> /etc/profile || exit
	echo "export PATH=\$PATH:\$GRADLE_HOME/bin" >> /etc/profile || exit
	popd > /dev/null
}

function installSSH() {
	pushd sshd > /dev/null
	yum -y install openssh-server || exit
	ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key
	ssh-keygen -t dsa -f /etc/ssh/ssh_host_dsa_key
	ssh-keygen -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key 
	ssh-keygen -t ed25519 -f /etc/ssh/ssh_host_ed25519_key 
	cp -f sshd_config /etc/ssh/ || exit
	cp -f sshd_supervisor.conf /etc/supervisor/conf.d/ || exit
	popd > /dev/null
}

function installSupervisord() {
	pushd supervisord > /dev/null
	pip install supervisor -i http://mirrors.cloud.aliyuncs.com/pypi/simple/ --trusted-host mirrors.cloud.aliyuncs.com || exit
	mkdir -p /etc/supervisor/conf.d || exit
	cp -f supervisord.conf /etc/supervisor/ || exit
	popd > /dev/null
}

function installCpKeysTimer() {
	pushd cp_keys_timer > /dev/null
	cp -f cp_keys_timer.sh /usr/sbin/ || exit
	chmod a+x /usr/sbin/cp_keys_timer.sh || exit
	cp -f cp_keys_timer_supervisor.conf /etc/supervisor/conf.d/ || exit
	popd > /dev/null
}


function main() {

	installBasic
	installMysqlClient
	
	installSupervisord
	installSSH
	installCpKeysTimer
	
	installJava
	installMaven
	installGradle
	
	
}

main