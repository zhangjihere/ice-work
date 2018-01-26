package org.tombear.rpcice.IceGrid.service;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Object;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;
import com.zeroc.IceBox.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tombear.rpcice.simple.hello.gen.HelloApi;
import org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus;
import org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg;
import org.tombear.rpcice.simple.hello.gen.entity.ResultMsg;

/**
 * HelloService in Grid
 *
 * Created by ji.zhang on 1/22/18.
 */
public class HelloServiceG implements HelloApi, Service {

    private Logger logger = LoggerFactory.getLogger(HelloServiceG.class);
    private ObjectAdapter adapter;
    private String name;

    @Override
    public void sayHello(int delay, Current current) {
        try {
            Thread.sleep(1000 * delay);
        } catch (InterruptedException ignore) {
        }
        logger.debug("Hello IceBox for Grid.--> {}", this.name);
    }

    @Override
    public ResultMsg process(ProcessMsg procMsg, Current current) {
        return null;
    }

    @Override
    public Cp4ccStatus check(String sessionId, Current current) {
        return null;
    }

    @Override
    public void shutdown(Current current) {

    }

    @Override
    public void start(String name, Communicator communicator, String[] args) {
        this.name = name;
        Properties props = communicator.getProperties();//from config.iceboxG and config.service.helloG
        adapter = communicator.createObjectAdapter("Hello-" + name);
        Object base = this;
        adapter.add(base, Util.stringToIdentity(props.getProperty("Hello.Identity")));
        adapter.activate();
        logger.debug("start Server {}", name);
    }

    @Override
    public void stop() {

    }
}
