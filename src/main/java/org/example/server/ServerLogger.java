package org.example.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger for all logging by the server classes.
 */
public class ServerLogger {

  private final static Logger LOGGER = Logger.getLogger(ServerLogger.class.getName());
  static FileHandler fileHandler;

  /**
   * static logger setup, that has to be called before using the logger
   *
   * @throws IOException - throws exception when logger file can't be accessed.
   */
  public static void setUp() throws IOException {
    fileHandler = new FileHandler("ServerLogger.log", true);
    SimpleFormatter simpleFormatter = new SimpleFormatter();
    fileHandler.setFormatter(simpleFormatter);
    LOGGER.addHandler(fileHandler);
    LOGGER.setLevel(Level.FINEST);
  }

  /**
   * Method used to log info messages.
   *
   * @param message - message to be logged
   */
  public static void logInfoMessage(String message) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    LOGGER.info(simpleDateFormat.format(System.currentTimeMillis()) + " " + message);
  }

  /**
   * Method used to log error/warning messages.
   *
   * @param message - message to be logged
   */
  public static void logErrorMessage(String message) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    LOGGER.warning(simpleDateFormat.format(System.currentTimeMillis()) + " " + message);
  }

}