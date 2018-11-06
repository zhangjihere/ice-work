
#pragma once

[["java:package:org.tombear.rpcice.simple.recog"]]
module Cp4ccBackend
{   // below define the Ice generated code

    interface Cp4ccRecogApi
    {
        bool acceptRequest(string sessionID);
        string checkStatus(string sessionId);
    }

}