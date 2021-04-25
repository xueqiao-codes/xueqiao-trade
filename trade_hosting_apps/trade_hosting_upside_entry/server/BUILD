cc_library(
    name = 'trade_hosting_upside_entry_gen',
    srcs = [
        'src/gen/trade_hosting_upside_entry_constants.cpp',
        'src/gen/trade_hosting_upside_entry_types.cpp',
        'src/gen/trade_hosting_upside_events_constants.cpp',
        'src/gen/trade_hosting_upside_events_types.cpp',
        'src/gen/TradeHostingUpsideEntry_variables.cpp',
        'src/gen/TradeHostingUpsideEntry.cpp',
        'src/gen/client/trade_hosting_upside_entry_stub.cpp'
    ],
    export_incs = [
        'src/gen',
        'src/gen/client',
    ],
    deps = [
        '//cpp_platform/svr:svrcpp',
        '//trade_hosting_storage:trade_hosting_storage_gen',
    ]
)

cc_binary(
    name = 'trade_hosting_upside_ctp',
    srcs = [
        'src/subprocess/common/effective_reporter.cpp',
        'src/subprocess/common/order_validation.cpp',
        'src/subprocess/common/order_mapping_manager.cpp',
        'src/subprocess/common/performance_utils.cpp',
        'src/subprocess/ctp/upside_entry_subprocess.cpp',
        'src/subprocess/ctp/ctp_data_manager.cpp',
        'src/subprocess/ctp/ctp_deal_manager.cpp',
        'src/subprocess/ctp/ctp_instruments_holder.cpp',
        'src/subprocess/ctp/ctp_login_manager.cpp',
        'src/subprocess/ctp/ctp_order_mapping_backend.cpp',
        'src/subprocess/ctp/ctp_position_manager.cpp',
        'src/subprocess/ctp/ctp_rate_manager.cpp',
        'src/subprocess/ctp/ctp_upside_entry_handler.cpp',
        'src/subprocess/ctp/framework/ctp_request_dispatcher.cpp',
        'src/subprocess/ctp/handlers/ctp_login_handler.cpp',
        'src/subprocess/ctp/handlers/order_delete_handler.cpp',
        'src/subprocess/ctp/handlers/order_insert_handler.cpp',
        'src/subprocess/ctp/handlers/order_sync_state_batch_handler.cpp',
        'src/subprocess/ctp/handlers/order_sync_state_handler.cpp',
        'src/subprocess/ctp/handlers/order_sync_trades_handler.cpp',
        'src/subprocess/ctp/handlers/qry_all_orders_handler.cpp',
        'src/subprocess/ctp/handlers/qry_all_trades_handler.cpp',
        'src/subprocess/ctp/handlers/qry_exchange_margin_rate_handler.cpp',
        'src/subprocess/ctp/handlers/qry_instrument_commission_rate_handler.cpp',
        'src/subprocess/ctp/handlers/qry_instrument_margin_rate_handler.cpp',
        'src/subprocess/ctp/handlers/qry_instruments_handler.cpp',
        'src/subprocess/ctp/handlers/qry_investor_handler.cpp',
        'src/subprocess/ctp/handlers/qry_position_comb_detail_handler.cpp',
        'src/subprocess/ctp/handlers/qry_position_detail_handler.cpp',
        'src/subprocess/ctp/handlers/qry_position_handler.cpp',
        'src/subprocess/ctp/handlers/qry_settlement_info_confirm_handler.cpp',
        'src/subprocess/ctp/handlers/qry_settlement_info_handler.cpp',
        'src/subprocess/ctp/handlers/qry_trading_account_handler.cpp',
        'src/subprocess/ctp/handlers/req_settlement_info_confirm_handler.cpp',
        'src/subprocess/main.cpp',
    ],
    incs = [
        'src/subprocess/',
        'src/subprocess/common',
        'src/subprocess/ctp',
        'src/subprocess/ctp/framework',
        'src/subprocess/ctp/handlers',
    ],
    deps = [
        '//cpp_platform/attr:soldier_attr',
        '//cpp_platform/thirdparty/ctp:thosttraderapi',
        '//trade_hosting_framework:trade_hosting_framework',
        '//trade_hosting_storage:trade_hosting_storage',
        '//contract_convertor:contract_convertor',
        ':trade_hosting_upside_entry_gen',
        '#boost_program_options',
    ]
)

cc_binary(
    name = 'trade_hosting_upside_es9',
    srcs = [
        'src/subprocess/common/effective_reporter.cpp',
        'src/subprocess/common/order_validation.cpp',
        'src/subprocess/common/order_mapping_manager.cpp',
        'src/subprocess/common/performance_utils.cpp',
        'src/subprocess/esunny9/upside_entry_subprocess.cpp',
        'src/subprocess/esunny9/framework/es9_error_mapping.cpp',
        'src/subprocess/esunny9/framework/es9_request_dispatcher.cpp',
        'src/subprocess/esunny9/handlers/es9_login_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_order_delete_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_order_sync_state_batch_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_order_insert_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_order_sync_state_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_order_sync_trades_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_account_fee_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_account_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_account_margin_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_all_orders_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_bill_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_commodity_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_fund_handler.cpp',
        'src/subprocess/esunny9/handlers/es9_qry_position_handler.cpp',
        'src/subprocess/esunny9/es9_data_manager.cpp',
        'src/subprocess/esunny9/es9_deal_manager.cpp',
        'src/subprocess/esunny9/es9_logger_cleaner.cpp',
        'src/subprocess/esunny9/es9_login_manager.cpp',
        'src/subprocess/esunny9/es9_order_insert_processor.cpp',
        'src/subprocess/esunny9/es9_order_delete_processor.cpp',
        'src/subprocess/esunny9/es9_order_mapping_backend.cpp',
        'src/subprocess/esunny9/es9_rate_manager.cpp',
        'src/subprocess/esunny9/es9_self_match_order_expired_cleaner.cpp',
        'src/subprocess/esunny9/es9_upside_entry_handler.cpp',
        'src/subprocess/main.cpp',
    ],
    incs = [
        'src/subprocess',
        'src/subprocess/common',
        'src/subprocess/esunny9',
        'src/subprocess/esunny9/framework',
        'src/subprocess/esunny9/handlers',
    ],
    deps = [
        '//cpp_platform/attr:soldier_attr',
        '//cpp_platform/thirdparty/esunny9:iTapTradeAPI9',
        '//trade_hosting_framework:trade_hosting_framework',
        '//trade_hosting_storage:trade_hosting_storage',
        '//contract_convertor:contract_convertor',
        ':trade_hosting_upside_entry_gen',
        '#boost_program_options',
    ]
)

cc_binary(
    name = 'trade_hosting_upside_ctp2_r',
    srcs = [
        'src/subprocess/common/effective_reporter.cpp',
        'src/subprocess/common/order_validation.cpp',
        'src/subprocess/common/order_mapping_manager.cpp',
        'src/subprocess/common/performance_utils.cpp',
        'src/subprocess/ctp2/upside_entry_subprocess.cpp',
        'src/subprocess/ctp2/ctp_data_manager.cpp',
        'src/subprocess/ctp2/ctp_deal_manager.cpp',
        'src/subprocess/ctp2/ctp_instruments_holder.cpp',
        'src/subprocess/ctp2/ctp_login_manager.cpp',
        'src/subprocess/ctp2/ctp_order_mapping_backend.cpp',
        'src/subprocess/ctp2/ctp_position_manager.cpp',
        'src/subprocess/ctp2/ctp_rate_manager.cpp',
        'src/subprocess/ctp2/ctp_upside_entry_handler.cpp',
        'src/subprocess/ctp2/framework/ctp_request_dispatcher.cpp',
        'src/subprocess/ctp2/handlers/ctp_authenticate_handler.cpp',
        'src/subprocess/ctp2/handlers/ctp_login_handler.cpp',
        'src/subprocess/ctp2/handlers/order_delete_handler.cpp',
        'src/subprocess/ctp2/handlers/order_insert_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_state_batch_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_state_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_trades_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_all_orders_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_all_trades_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_exchange_margin_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instrument_commission_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instrument_margin_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instruments_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_investor_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_comb_detail_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_detail_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_settlement_info_confirm_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_settlement_info_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_trading_account_handler.cpp',
        'src/subprocess/ctp2/handlers/req_settlement_info_confirm_handler.cpp',
        'src/subprocess/main.cpp',
    ],
    incs = [
        'src/subprocess/',
        'src/subprocess/common',
        'src/subprocess/ctp2',
        'src/subprocess/ctp2/framework',
        'src/subprocess/ctp2/handlers',
    ],
    deps = [
        '//cpp_platform/attr:soldier_attr',
        '//cpp_platform/thirdparty/ctp2/release:thosttraderapi_se_r',
        '//trade_hosting_framework:trade_hosting_framework',
        '//trade_hosting_storage:trade_hosting_storage',
        '//contract_convertor:contract_convertor',
        ':trade_hosting_upside_entry_gen',
        '#boost_program_options',
    ]
)

cc_binary(
    name = 'trade_hosting_upside_ctp2_t',
    srcs = [
        'src/subprocess/common/effective_reporter.cpp',
        'src/subprocess/common/order_validation.cpp',
        'src/subprocess/common/order_mapping_manager.cpp',
        'src/subprocess/common/performance_utils.cpp',
        'src/subprocess/ctp2/upside_entry_subprocess.cpp',
        'src/subprocess/ctp2/ctp_data_manager.cpp',
        'src/subprocess/ctp2/ctp_deal_manager.cpp',
        'src/subprocess/ctp2/ctp_instruments_holder.cpp',
        'src/subprocess/ctp2/ctp_login_manager.cpp',
        'src/subprocess/ctp2/ctp_order_mapping_backend.cpp',
        'src/subprocess/ctp2/ctp_position_manager.cpp',
        'src/subprocess/ctp2/ctp_rate_manager.cpp',
        'src/subprocess/ctp2/ctp_upside_entry_handler.cpp',
        'src/subprocess/ctp2/framework/ctp_request_dispatcher.cpp',
        'src/subprocess/ctp2/handlers/ctp_authenticate_handler.cpp',
        'src/subprocess/ctp2/handlers/ctp_login_handler.cpp',
        'src/subprocess/ctp2/handlers/order_delete_handler.cpp',
        'src/subprocess/ctp2/handlers/order_insert_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_state_batch_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_state_handler.cpp',
        'src/subprocess/ctp2/handlers/order_sync_trades_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_all_orders_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_all_trades_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_exchange_margin_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instrument_commission_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instrument_margin_rate_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_instruments_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_investor_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_comb_detail_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_detail_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_position_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_settlement_info_confirm_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_settlement_info_handler.cpp',
        'src/subprocess/ctp2/handlers/qry_trading_account_handler.cpp',
        'src/subprocess/ctp2/handlers/req_settlement_info_confirm_handler.cpp',
        'src/subprocess/main.cpp',
    ],
    incs = [
        'src/subprocess/',
        'src/subprocess/common',
        'src/subprocess/ctp2',
        'src/subprocess/ctp2/framework',
        'src/subprocess/ctp2/handlers',
    ],
    deps = [
        '//cpp_platform/attr:soldier_attr',
        '//cpp_platform/thirdparty/ctp2/test:thosttraderapi_se_t',
        '//trade_hosting_framework:trade_hosting_framework',
        '//trade_hosting_storage:trade_hosting_storage',
        '//contract_convertor:contract_convertor',
        ':trade_hosting_upside_entry_gen',
        '#boost_program_options',
    ]
)

cc_binary(
    name = 'trade_hosting_upside_entry',
    srcs = [
        'src/mainprocess/main_processor.cpp',
        'src/mainprocess/subprocess_manager.cpp',
        'src/mainprocess/upside_effective_manager.cpp',
        'src/mainprocess/upside_entry_mainprocess.cpp',
        'src/mainprocess/main.cpp',
    ],
    incs = [
        'src/mainprocess',
    ],
    deps = [
        '//cpp_platform/attr:soldier_attr',
        '//trade_hosting_framework:trade_hosting_framework',
        '//trade_hosting_storage:trade_hosting_storage',
        '//contract_convertor:contract_convertor',
        ':trade_hosting_upside_entry_gen',
        '#boost_program_options',
    ]
)

cc_binary(
    name = 'tpidump',
    srcs = [
        'src/tool/dump_processinfo.cpp',
    ], 
    deps = [
        ':trade_hosting_upside_entry_gen',
    ]
)

cc_binary(
    name = 'posdump',
    srcs = [
        'src/tool/dump_positions.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen'
    ]
)

cc_binary(
    name = 'get_funds',
    srcs = [
        'src/tool/get_funds.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen'
    ]
)

cc_binary(
    name = 'get_settlement_info',
    srcs = [
        'src/tool/get_settlement_info.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen'
    ]
)

cc_binary(
    name = 'get_net_positions',
    srcs = [
        'src/tool/get_net_positions.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen'
    ]
)

cc_binary(
    name = 'oporder_test',
    srcs = [
        'src/tool/oporder_test.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen',
        '//trade_hosting_storage:trade_hosting_storage',
    ],
)

cc_binary(
    name = 'syncorder_test',
    srcs = [
        'src/tool/syncorder_test.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen',
        '//trade_hosting_storage:trade_hosting_storage',
    ]
)

cc_binary(
    name = 'dump_ratiodetails',
    srcs = [
        'src/tool/dump_ratiodetails.cpp',
    ],
    deps = [
        ':trade_hosting_upside_entry_gen',
        '//trade_hosting_storage:trade_hosting_storage',
    ]
)

