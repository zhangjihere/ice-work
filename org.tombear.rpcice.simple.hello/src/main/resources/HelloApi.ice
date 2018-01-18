
#pragma once

[["java:package:org.tombear.rpcice.simple.hello"]]
module gen
{   // below define the Ice generated code
    module entity
    {
        enum Cp4ccStatus { Cp4ccUnknown, Cp4ccInitial, Cp4ccReceive, Cp4ccDecode,
                               Cp4ccRecog, Cp4ccProcess, Cp4ccReady, Cp4ccRelease,
                               Cp4ccCancel, Cp4ccMaxStatus };

        enum Cp4ccFileType { Cp4ccERaw, Cp4ccDRaw, Cp4ccJpeg, Cp4ccMeta,
                                 Cp4ccPng, Cp4ccZip, Cp4ccFinal, Cp4ccOther,
                                 Cp4ccDIR, Cp4ccMaxFileType };

        ["java:getset", "protected"]
        class ProcessMsg
        {
            string sessionId;
            string s3Key;
            Cp4ccStatus status;
            Cp4ccFileType fileType;
        }


        ["java:getset", "protected"]
        class ResultMsg
        {
            string sessionId;
            string s3Key;
        }
    }

    interface HelloApi
    {
        idempotent void sayHello(int delay);
        // Notes, the module parameter must be declared above.
        idempotent entity::ResultMsg process(entity::ProcessMsg procMsg);
        entity::Cp4ccStatus check(string sessionId);
        void shutdown();
    }

}
