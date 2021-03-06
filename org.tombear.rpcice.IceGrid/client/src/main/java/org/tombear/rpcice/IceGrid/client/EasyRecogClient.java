package org.tombear.rpcice.IceGrid.client;

import org.tombear.rpcice.IceGrid.utils.IceClientUtil;
import org.tombear.rpcice.simple.backend.RecogApiPrx;


/**
 * east client for IceGrid with IceBox
 * Created by ji.zhang on 1/29/18.
 */
public class EasyRecogClient {

    public static void main(String[] args) {
        RecogApiPrx recogApiPrx = IceClientUtil.getServicePrx(RecogApiPrx.class);
        recogApiPrx = recogApiPrx.ice_twoway().ice_secure(false);
        boolean b = recogApiPrx.acceptRequest("71574e02-df55-4c91-9abb-ccc314467f55");
        System.out.printf("accept: %b\n", b);

    }
}
