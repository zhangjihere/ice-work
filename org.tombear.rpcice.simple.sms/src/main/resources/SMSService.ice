
#pragma once

[["java:package:org.tombear.rpcice.simple.sms"]]
module gen
{   // below define the Ice generated code
    interface SMSService
    {
        string sendSMS(string msg, int index, int delay);
    }
}
