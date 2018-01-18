// **********************************************************************
//
// Copyright (c) 2003-2017 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.0
//
// <auto-generated>
//
// Generated from file `HelloApi.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package org.tombear.rpcice.simple.hello.gen;

public interface HelloApi extends com.zeroc.Ice.Object
{
    void sayHello(int delay, com.zeroc.Ice.Current current);

    org.tombear.rpcice.simple.hello.gen.entity.ResultMsg process(org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg procMsg, com.zeroc.Ice.Current current);

    org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus check(String sessionId, com.zeroc.Ice.Current current);

    void shutdown(com.zeroc.Ice.Current current);

    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::org::tombear::rpcice::simple::hello::gen::HelloApi"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::org::tombear::rpcice::simple::hello::gen::HelloApi";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sayHello(HelloApi obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_delay;
        iceP_delay = istr.readInt();
        inS.endReadParams();
        obj.sayHello(iceP_delay, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_process(HelloApi obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        final com.zeroc.IceInternal.Holder<org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg> icePP_procMsg = new com.zeroc.IceInternal.Holder<>();
        istr.readValue(v -> icePP_procMsg.value = v, org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg.class);
        istr.readPendingValues();
        inS.endReadParams();
        org.tombear.rpcice.simple.hello.gen.entity.ProcessMsg iceP_procMsg = icePP_procMsg.value;
        org.tombear.rpcice.simple.hello.gen.entity.ResultMsg ret = obj.process(iceP_procMsg, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeValue(ret);
        ostr.writePendingValues();
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_check(HelloApi obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_sessionId;
        iceP_sessionId = istr.readString();
        inS.endReadParams();
        org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus ret = obj.check(iceP_sessionId, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        org.tombear.rpcice.simple.hello.gen.entity.Cp4ccStatus.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_shutdown(HelloApi obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        obj.shutdown(current);
        return inS.setResult(inS.writeEmptyParams());
    }

    final static String[] _iceOps =
    {
        "check",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "process",
        "sayHello",
        "shutdown"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_check(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_process(this, in, current);
            }
            case 6:
            {
                return _iceD_sayHello(this, in, current);
            }
            case 7:
            {
                return _iceD_shutdown(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
