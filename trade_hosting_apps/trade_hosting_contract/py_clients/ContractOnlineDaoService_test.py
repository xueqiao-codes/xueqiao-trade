#!/usr/bin/env python
# test for service ContractOnlineDaoService
import random
from contract_online_dao_service.ttypes import *
from contract_online_dao_service.constants import *
from contract_online_dao_service.client.ContractOnlineDaoServiceStub import *

stub=ContractOnlineDaoServiceStub()
#using like stub.xxxfunc(routeKey=random.randint(0, 100000), timeout=3000, args...)
#testing...

stub.setPeerAddr("127.0.0.1")
print(stub.reqSledExchange(0, 3000, ReqSledContractOption(), 0, 10))