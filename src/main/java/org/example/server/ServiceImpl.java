package org.example.server;

import org.apache.thrift.TException;
import org.example.thrift.CrossPlatformService;
import org.example.thrift.InvalidKeyException;
import org.example.thrift.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements CrossPlatformService.Iface {

  // thread safe hashMap
  Map<String, String> dataStore = new ConcurrentHashMap<>();

  //  TODO: add logging
  @Override
  public Response putRequest(String key, String value) throws InvalidKeyException, TException {
    ServerLogger.logInfoMessage("REQUEST - PUT; KEY -" + key + " VALUE -" + value + " thread -" + Thread.currentThread().getId());
    dataStore.put(key, value);
    Response response = new Response(200, "Successfully stored");
    ServerLogger.logInfoMessage("RESPONSE - PUT; statusCode -" + response.statusCode + " message " +
            "- " + response.message);
    return response;
  }

  @Override
  public Response getRequest(String key) throws InvalidKeyException, TException {
    ServerLogger.logInfoMessage("REQUEST - GET; KEY -" + key + " thread -" + Thread.currentThread().getId());
    Response response;
    if (!dataStore.containsKey(key))
      response = new Response(404, "Key not found");
    else
      response = new Response(200, dataStore.get(key));

    ServerLogger.logInfoMessage("RESPONSE - GET; statusCode -" + response.statusCode + " message " +
            "- " + response.message);

    return response;
  }

  @Override
  public Response deleteRequest(String key) throws InvalidKeyException, TException {
    ServerLogger.logInfoMessage("REQUEST - DELETE; KEY -" + key + " thread -" + Thread.currentThread().getId());
    Response response;
    if (!dataStore.containsKey(key))
      response = new Response(404, "Key not found");
    else {
      dataStore.remove(key);
      response = new Response(200, "Successfully deleted!");
    }
    ServerLogger.logInfoMessage("RESPONSE - DELETE; statusCode -" + response.statusCode + " " +
            "message - " + response.message);

    return response;
  }

  @Override
  public boolean ping() throws TException {
    return true;
  }
}
