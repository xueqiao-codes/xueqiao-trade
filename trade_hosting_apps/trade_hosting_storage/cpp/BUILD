cc_library(
    name = 'trade_hosting_storage_gen',
    srcs = [
        'src/gen/client/contract_online_dao_service_stub.cpp',
        'src/gen/client/trade_hosting_storage_stub.cpp',
        'src/gen/client/trade_hosting_dealing_stub.cpp',
        'src/gen/broker_constants.cpp',
        'src/gen/broker_types.cpp',
        'src/gen/contract_constants.cpp',
        'src/gen/contract_online_dao_service_constants.cpp',
        'src/gen/contract_online_dao_service_types.cpp',
        'src/gen/contract_standard_constants.cpp',
        'src/gen/contract_standard_types.cpp',
        'src/gen/contract_types.cpp',
        'src/gen/ContractOnlineDaoService_variables.cpp',
        'src/gen/ContractOnlineDaoService.cpp',
        'src/gen/trade_hosting_basic_constants.cpp',
        'src/gen/trade_hosting_basic_types.cpp',
        'src/gen/trade_hosting_basic_events_constants.cpp',
        'src/gen/trade_hosting_basic_events_types.cpp',
        'src/gen/trade_hosting_basic_errors_constants.cpp',
        'src/gen/trade_hosting_basic_errors_types.cpp',
        'src/gen/trade_hosting_upside_position_constants.cpp',
        'src/gen/trade_hosting_upside_position_types.cpp',
        'src/gen/trade_hosting_storage_types.cpp',
        'src/gen/trade_hosting_storage_constants.cpp',
        'src/gen/TradeHostingStorage_variables.cpp',
        'src/gen/TradeHostingStorage.cpp',
        'src/gen/trade_hosting_dealing_constants.cpp',
        'src/gen/trade_hosting_dealing_types.cpp',
        'src/gen/TradeHostingDealing.cpp',
        'src/gen/TradeHostingDealing_variables.cpp',
        'src/gen/page_types.cpp',
        'src/gen/page_constants.cpp',
    ],
    export_incs = [
        'src/gen',
        'src/gen/client',
    ],
    deps = [
        '//cpp_platform/svr:svrcpp',
    ]
)

cc_library(
    name = 'trade_hosting_storage',
    srcs = [
        'src/apis/contract_mapping.cpp',
        'src/apis/errorinfo_helper.cpp',
        'src/apis/trade_hosting_storage_api.cpp',
    ],
    export_incs = [
        'src/apis',
    ],
    deps = [
        ':trade_hosting_storage_gen',
    ]
)