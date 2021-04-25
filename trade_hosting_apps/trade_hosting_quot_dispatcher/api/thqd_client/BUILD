cc_library(
    name = 'thqd_client_api',
    srcs = [
        'src/gen/client/trade_hosting_quot_dispatcher_stub.cpp',
        'src/gen/trade_hosting_quot_dispatcher_constants.cpp',
        'src/gen/trade_hosting_quot_dispatcher_types.cpp',
        'src/gen/TradeHostingQuotDispatcher.cpp',
        'src/gen/TradeHostingQuotDispatcher_variables.cpp',
        'src/thqd_receivor.cpp',
    ],
    export_incs = [
        'src'
    ],
    incs = [
        'src/gen',
        'src/gen/client'
    ],
    deps = [
        '//cpp_platform/svr:svrcpp',
        '//cpp_platform/thirdparty/cppzmq:cppzmq',
    ]
)

cc_library(
    name = "thqd_client_swig",
    srcs = [
        'src/swig/thqd_swig.cpp',
    ],
    export_incs = [
        'src/swig',
    ],
    deps = [
        ':thqd_client_api',
    ]
)

swig_library(
    name = 'THQDClient',
    srcs = [
        'src/swig/THQDClient.i',
    ],
    java_package = 'xueqiao.trade.hosting.quot.dispatcher.client.swig',
    java_lib_packed = True,
    deps = [
        ':thqd_client_swig',
        '//cpp_platform/thirdparty/java/jni:jni_headers',
    ]
)


cc_binary(
    name = 'thqd_receivor_test',
    srcs = [
        'test/thqd_receivor_test.cpp',
    ],
    deps = [
        ':thqd_client_api',
    ]
)