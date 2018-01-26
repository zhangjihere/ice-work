package org.tombear.rpcice.IceGrid;

import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Server;

import org.tombear.rpcice.IceGrid.utils.Ice2Slf4JLoggerI;


/**
 * custom IceBox Server wrap
 * Created by ji.zhang on 1/26/18.
 */
public class Slf4jIceBoxServer {
    public static void main(String[] args) {
        InitializationData initData = new InitializationData();
        Util.setProcessLogger(new Ice2Slf4JLoggerI("system"));
        initData.properties  = Util.createProperties();
        initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
//        initData.logger = new Ice2Slf4JLoggerI("system");

        Server server = new Server();
        System.exit(server.main("IceBox.Server", args, initData));
    }
}
