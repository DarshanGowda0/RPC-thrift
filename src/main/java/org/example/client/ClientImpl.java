package org.example.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.example.thrift.CrossPlatformService;
import org.example.thrift.Response;

public class ClientImpl implements Client {
  private CrossPlatformService.Client client;

  public ClientImpl(String hostname, int portNumber) throws TTransportException {

    TSocket transport = new TSocket(hostname, portNumber);

    // timeouts
    transport.setConnectTimeout(5000);
    transport.setSocketTimeout(5000);

    transport.open();

    TProtocol protocol = new TBinaryProtocol(transport);
    client = new CrossPlatformService.Client(protocol);

  }

  @Override
  public void putRequest(String key, String value) throws TException {
    ClientLogger.logInfoMessage("REQUEST - PUT; " + "KEY : " + key + "; VALUE : " + value);
    Response response = client.putRequest(key, value);
    ClientLogger.logInfoMessage("RESPONSE for PUT : status -" + response.statusCode + " message -" +
            " " + response.message);
  }

  @Override
  public void getRequest(String key) throws TException {
    ClientLogger.logInfoMessage("REQUEST - GET; " + "KEY : " + key);
    Response response = client.getRequest(key);
    ClientLogger.logInfoMessage("RESPONSE for GET : status -" + response.statusCode + " message -" +
            " " + response.message);
  }

  @Override
  public void deleteRequest(String key) throws TException {
    ClientLogger.logInfoMessage("REQUEST - DELETE; " + "KEY : " + key);
    Response response = client.deleteRequest(key);
    ClientLogger.logInfoMessage("RESPONSE for DELETE : status -" + response.statusCode + " " +
            "message -" +
            " " + response.message);
  }
}
