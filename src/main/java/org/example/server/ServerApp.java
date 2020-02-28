package org.example.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.example.thrift.CrossPlatformService;

import java.io.IOException;

public class ServerApp {

  private TServer server;

  public ServerApp(int portNumber) throws TTransportException, IOException {
    ServerLogger.setUp();
    TServerTransport serverTransport = new TServerSocket(portNumber);
    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
            .processor(new CrossPlatformService.Processor<>(new ServiceImpl())));

    ServerLogger.logInfoMessage("Server starting at port-number:" + portNumber);
    server.serve();
  }


  public void stop() {
    if (server != null && server.isServing()) {
      ServerLogger.logInfoMessage("Stopping the server... ");
      server.stop();
    }
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage - server <service-port-number>");
      return;
    }

    int portNumber = Integer.parseInt(args[0]);
    try {
      ServerApp serverApp = new ServerApp(portNumber);
    } catch (TTransportException e) {
      e.printStackTrace();
      ServerLogger.logErrorMessage(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
