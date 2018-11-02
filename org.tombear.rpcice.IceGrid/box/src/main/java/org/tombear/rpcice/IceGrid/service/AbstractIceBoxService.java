package org.tombear.rpcice.IceGrid.service;

import java.util.Arrays;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.IceGrid.utils.Ice2Slf4JLoggerI;
import org.tombear.rpcice.IceGrid.utils.PerfDispatchIntercepter;

/**
 * Base class for IceBoxService
 *
 * Created by ji.zhang on 1/29/18.
 */
public abstract class AbstractIceBoxService implements Service {
    private static Logger logger = LoggerFactory.getLogger(AbstractIceBoxService.class);

    private ObjectAdapter adapter;
    protected Identity id;
    protected String name;

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        this.name = name;
        Properties props = communicator.getProperties();
        Util.setProcessLogger(new Ice2Slf4JLoggerI("communicator"));
        adapter = communicator.createObjectAdapter(name);
        Identity id = Util.stringToIdentity(props.getProperty("Service.Identity"));
        Object object = this.createMyIceServiceObj(args);
        Object dispatchInterceptor = PerfDispatchIntercepter.addIceObject(id, object);
        adapter.add(dispatchInterceptor, id);
        adapter.activate();
        logger.info(name + " service started, with param size " + args.length + " detail:" + Arrays.toString(args));

        logger.debug(">>>AIBS>>>server_cust: {}", props.getProperty("server_cust"));
        logger.debug(">>>AIBS>>>service_cust: {}", props.getProperty("service_cust"));
        logger.debug(">>>AIBS>>>Service.Identity: {}", props.getProperty("Service.Identity"));
        logger.debug(">>>AIBS>>>args: {}", Arrays.toString(args));
    }

    /**
     * create specific servant object
     *
     * @param args servant option parameter from icegird
     */
    public abstract Object createMyIceServiceObj(String[] args);

    @Override
    public void stop() {
        logger.info("stopping service " + id + "......");
        adapter.destroy();
        PerfDispatchIntercepter.removeIceObject(id);
        logger.info("stopped service " + id + " stopped!");
    }
}
