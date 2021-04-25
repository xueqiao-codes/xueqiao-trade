package org.soldier.platform.id_maker_dao.helper;

import org.soldier.platform.id_maker_dao.client.IdMakerDaoAsyncStub;
import org.soldier.platform.id_maker_dao.client.IdMakerDaoStub;

public class IdMakerDaoStubFactory {

    public static IdMakerDaoStub getStub() {
        IdMakerDaoStub stub = new IdMakerDaoStub();
        stub.setPeerAddr("127.0.0.1");
        return stub;
    }

    public static IdMakerDaoAsyncStub getAsyncStub() {
        IdMakerDaoAsyncStub anyncStub = new IdMakerDaoAsyncStub();
        anyncStub.setPeerAddr("127.0.0.1");
        return anyncStub;
    }
}