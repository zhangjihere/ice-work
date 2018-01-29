package org.tombear.rpcice.IceGrid.utils;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.DispatchInterceptor;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.OutputStream;
import com.zeroc.Ice.Request;
import com.zeroc.Ice.UserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performance DispatcherInterceptor for Ice Servant
 *
 * Created by ji.zhang on 1/29/18.
 */
public class PerfDispatchIntercepter extends DispatchInterceptor {
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PerfDispatchIntercepter.class);

    // used for disposing Ice Servant Object which will be intercepted, Key is service ID, Value is point to Servant
    private static final Map<Identity, Object> id2ObjectMAP = new ConcurrentHashMap<>();
    // singleton
    private static final PerfDispatchIntercepter self = new PerfDispatchIntercepter();

    public static PerfDispatchIntercepter getINSTANCE() {
        return self;
    }

    /**
     * add Ice Servant Object which will be intercepted
     *
     * @param id     Ice Identity
     * @param iceObj Servant
     * @return a DispatchInterceptor
     */
    public static DispatchInterceptor addIceObject(Identity id, Object iceObj) {
        id2ObjectMAP.put(id, iceObj);
        return self;
    }

    public static void removeIceObject(Identity id) {
        logger.info("remove ice object " + id);
        id2ObjectMAP.remove(id);
    }

    /**
     * this method used to do any interception like AOP
     *
     * @param request represent current request
     */
    @Override
    public CompletionStage<OutputStream> dispatch(Request request) {
        Current current = request.getCurrent();
        Identity theId = current.id;
        String inf = "dispatch req, method: " + request.getCurrent().operation +
                ", service: " + theId.name +
                ", server address: \n" + current.con;
        logger.info(inf + " begin");
        Object object = id2ObjectMAP.get(theId);
        try {
            return object.ice_dispatch(request);
        } catch (UserException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
