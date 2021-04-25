cc_binary(
    name = 'trade_hosting_quot_dispatcher',
    srcs = [
        'src/gen/trade_hosting_quot_dispatcher_constants.cpp',
        'src/gen/trade_hosting_quot_dispatcher_types.cpp',
        'src/gen/TradeHostingQuotDispatcher.cpp',
        'src/gen/TradeHostingQuotDispatcher_variables.cpp',
        'src/quot_dispatcher_entry.cpp',
        'src/main.cpp',
    ],
    incs = [
        'src/gen',
    ],
    deps = [
        '//cpp_platform/svr:svrcpp',
        '//quotation_subscribe/quotation/subscribe:subscribe_api'
    ]
)
