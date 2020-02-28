package org.example.client;

import org.apache.thrift.TException;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main client app which provides the interface for the user to send requests.
 */
public class ClientApp {

  public static void main(String[] args) {

    if (args.length != 2) {
      System.out.println("Usage - client <host-name> <port-number>");
      return;
    }

    String hostName = args[0];
    int portNumber = Integer.parseInt(args[1]);

    try {
      ClientLogger.setUp();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Scanner scanner = new Scanner(System.in);
    Client client = null;
    try {
      client = new ClientImpl(hostName, portNumber);

      // prepopulate 5 requests
      prePopulateOperations(client);


      while (true) {
        System.out.println("Enter the operation to be performed:\n"
                + "PUT <key> <value>\n"
                + "GET <key>\n"
                + "DELETE <key>");
        String inputLine = scanner.nextLine();
        String[] inputs = inputLine.split("\\s+");
        switch (inputs[0].toLowerCase()) {
          case "put":
            if (inputs.length == 3) {
              client.putRequest(inputs[1], inputs[2]);
            } else {
              logInvalidRequest("Invalid PUT request received => " + inputLine);
            }
            break;
          case "get":
            if (inputs.length == 2) {
              client.getRequest(inputs[1]);
            } else {
              logInvalidRequest("Invalid GET request received => " + inputLine);
            }
            break;
          case "delete":
            if (inputs.length == 2) {
              client.deleteRequest(inputs[1]);
            } else {
              logInvalidRequest("Invalid DELETE request received => " + inputLine);
            }
            break;
          default:
            logInvalidRequest("Invalid request received => " + inputLine);
        }
      }

    } catch (TException e) {
      ClientLogger.logErrorMessage(e.getMessage());
      e.printStackTrace();
    }

  }

  private static void prePopulateOperations(Client client) throws TException {
    // PUT requests
    for (int i = 0; i < 5; i++) {
      client.putRequest("key_" + i, "val_" + i);
    }

    // GET requests
    for (int i = 0; i < 5; i++) {
      client.getRequest("key_" + i);
    }

    // DELETE requests
    for (int i = 0; i < 5; i++) {
      client.deleteRequest("key_" + i);
    }

  }

  private static void logInvalidRequest(String message) {
    ClientLogger.logErrorMessage(message);
  }

}
