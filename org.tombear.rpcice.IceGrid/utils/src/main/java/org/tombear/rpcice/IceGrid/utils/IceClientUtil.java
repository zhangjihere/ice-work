package org.tombear.rpcice.IceGrid.utils;

import java.util.HashMap;
import java.util.Map;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;

/**
 * Util for wrapping Ice Client
 * Created by ji.zhang on 1/29/18.
 */
public class IceClientUtil {

    private static volatile Communicator ic = null;
    private static Map<Class<?>, ObjectPrx> cls2PrxMap = new HashMap<>();
    private static volatile long lastAccessTimestamp;
    private static volatile MonitorThread monitorThread;
    private static long clientTimeout;
    private static final String locatorKey = "Ice.Default.Locator";
    private static final String clientTimeoutKey = "Ice.ACM.Client.Timeout";
    private static final String configFile = "config.client";

    //lazy loading Communicator
    public static Communicator getIceCommunicator() {
        if (ic == null) {
            synchronized (IceClientUtil.class) {
                if (ic == null) {
                    ic = Util.initialize(new String[]{"--Option=Value"}, configFile);
                    Properties props = ic.getProperties();
                    String iceLocator = props.getProperty(locatorKey);
                    clientTimeout = Integer.parseInt(props.getProperty(clientTimeoutKey));
                    System.out.println("Ice client's locator is " + iceLocator + " proxy cache time out seconds: " + clientTimeout);
                    createMonitorThread();
                }
            }
        }
        lastAccessTimestamp = System.currentTimeMillis();
        return ic;
    }

    /**
     * create monitor daemon thread
     */
    private static void createMonitorThread() {
        monitorThread = new MonitorThread();
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    /**
     * close communitor, release resources
     *
     * @param removeServiceCache remove Map cache
     */
    public static void closeCommunicator(boolean removeServiceCache) {
        System.out.println(">>>close Ice Communicator");
        synchronized (IceClientUtil.class) {
            if (ic != null) {
                safeShutdown();
                monitorThread.interrupt();
                if (removeServiceCache && !cls2PrxMap.isEmpty()) {
                    try {
                        cls2PrxMap.clear();
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void safeShutdown() {
        try {
            ic.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ic.destroy();
            ic = null;
        }
    }

    // use Reflection to create Object Proxy
    private static ObjectPrx createIceProxy(Communicator communicator, Class<?> serviceCls) {
        ObjectPrx proxy;
        String serviceName = serviceCls.getSimpleName();
        int pos = serviceName.lastIndexOf("Prx");
        if (pos <= 0) {
            throw new IllegalArgumentException("Invalid ObjectPrx class, class name must end with Prx");
        }
        String realIceObjectName = serviceName.substring(0, pos);// such as HelloApi, SMSService
        try {
            ObjectPrx base = communicator.stringToProxy(realIceObjectName + "Obj");
            java.lang.reflect.Method m1 = serviceCls.getMethod("uncheckedCast", ObjectPrx.class);
            proxy = (ObjectPrx) m1.invoke(null, base);
            return proxy;
        } catch (IllegalAccessException | NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * API is used by client for get Ice Service instance
     *
     * @param serviceCls class whose Instance implemented Ice servive proxy interface
     * @return ObjectPrx
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T getServicePrx(Class<T> serviceCls) {
        ObjectPrx proxy = cls2PrxMap.get(serviceCls);
        if (proxy != null) {
            lastAccessTimestamp = System.currentTimeMillis();
            return (T) proxy;
        }
        proxy = createIceProxy(getIceCommunicator(), serviceCls);
        cls2PrxMap.put(serviceCls, proxy);
        lastAccessTimestamp = System.currentTimeMillis();
        return (T) proxy;
    }

    static class MonitorThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000L);
                    if (lastAccessTimestamp + clientTimeout * 1000L < System.currentTimeMillis()) {
                        closeCommunicator(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
