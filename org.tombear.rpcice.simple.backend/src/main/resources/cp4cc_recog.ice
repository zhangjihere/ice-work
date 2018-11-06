
#pragma once

[["java:package:org.tombear.rpcice.simple"]]
module backend
{   // below define the Ice generated code

    interface RecogApi
    {
        bool acceptRequest(string sessionID);
        string checkStatus(string sessionId);
    }

}