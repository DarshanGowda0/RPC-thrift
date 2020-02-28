package org.example.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger class for all client classes to use.
 */
public class ClientLogger {
  private final static Logger LOGGER = Logger.getLogger(ClientLogger.class.getName());


  /**
   * static setup method to initialize the logger.
   *
   * @throws IOException - throws exception when logger file can't be created.
   */
  public static void setUp() throws IOException {
    FileHandler fileHandler = new FileHandler("ClientLogger.log", true);
    SimpleFormatter simpleFormatter = new SimpleFormatter();
    fileHandler.setFormatter(simpleFormatter);
    LOGGER.addHandler(fileHandler);
    LOGGER.setLevel(Level.FINEST);
  }

  /**
   * helper method to log info messages.
   *
   * @param message - message to be logged
   */
  public static void logInfoMessage(String message) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    LOGGER.info(simpleDateFormat.format(System.currentTimeMillis()) + " " + message);
  }

  /**
   * helper method to log error/warning messages.
   *
   * @param message - message to be logged
   */
  public static void logErrorMessage(String message) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    LOGGER.warning(simpleDateFormat.format(System.currentTimeMillis()) + " " + message);
  }

}
