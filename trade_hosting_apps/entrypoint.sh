#!/bin/bash

INSTALL_APPS=(
"jmxmon"
"trade_hosting_startup"
"trade_hosting_storage_daemon"
"trade_hosting_quot_dispatcher"
"trade_hosting_contract"
"trade_hosting_upside_entry"
"trade_hosting_dealing"
"trade_hosting_push_server"
"trade_hosting_quot_comb"
"trade_hosting_entry"
"trade_hosting_arbitrage"
"trade_hosting_asset"
"trade_hosting_history"
"trade_hosting_tradeaccount_data"
"trade_hosting_id_maker"
"trade_hosting_position_adjust"
"trade_hosting_position_statis"
"trade_hosting_tasknote"
"trade_hosting_position_fee"
"trade_hosting_risk_manager"
)

for APP in ${INSTALL_APPS[@]};  
do
	if [ ! -d /data/applog/apps/$APP ];then
		mkdir -p /data/applog/apps/$APP
	fi
done

/usr/bin/supervisord
