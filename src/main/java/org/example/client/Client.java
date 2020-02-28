package org.example.client;

import org.apache.thrift.TException;

/**
 * Client interface for all apps to implement the following requests.
 */
public interface Client {

  /**
   * Request method that sends PUT requests to the server.
   *
   * @param key   - key to be stored in the host-machine
   * @param value - value associated with the key
   */
  void putRequest(String key, String value) throws TException;

  /**
   * Request method that sends GET requests to the server.
   *
   * @param key - key of the entry to be fetched from the host server
   */
  void getRequest(String key) throws TException;

  /**
   * Request method that sends DELETE requests to the server.
   *
   * @param key - key of the entry to be deleted from the host server
   */
  void deleteRequest(String key) throws TException;


}
