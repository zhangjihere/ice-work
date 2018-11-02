package org.tombear.rpcice.IceGrid;

import java.util.Arrays;

import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.IceGrid.utils.Ice2Slf4JLoggerI;


/**
 * custom IceBox Server wrap
 * Created by ji.zhang on 1/26/18.
 */
public class Slf4jIceBoxServer {
    private static Logger logger = LoggerFactory.getLogger(Slf4jIceBoxServer.class);

    public static void main(String[] args) {
        InitializationData initData = new InitializationData();
        Util.setProcessLogger(new Ice2Slf4JLoggerI("system"));
        initData.properties  = Util.createProperties();
        initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
//        initData.logger = new Ice2Slf4JLoggerI("system");

        logger.debug(">>>BOX>>>server_cust: {}", initData.properties.getProperty("server_cust"));
        logger.debug(">>>BOX>>>service_cust: {}", initData.properties.getProperty("service_cust"));
        logger.debug(">>>BOX>>>Service.Identity: {}", initData.properties.getProperty("Service.Identity"));
        logger.debug(">>>BOX>>>args: {}", Arrays.toString(args));

        Server server = new Server();
        System.exit(server.main("IceBox.Server", args, initData));
    }
}
