package fr.vergne.gameGenetic.gui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import fr.vergne.ioutils.StringUtils;

public class Launcher {

	private static final Logger logger = Logger.getLogger(Launcher.class
			.getName());

	public static void main(String[] args) {
		try {
			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			PrintStream printer = new PrintStream(stream);
			printer.println(".level = INFO");
			printer.println("handlers = java.util.logging.FileHandler, java.util.logging.ConsoleHandler");
			printer.println("java.util.logging.SimpleFormatter.format = %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$s: %5$s [%2$s]%6$s%n");

			printer.println("java.util.logging.FileHandler.pattern = game.log");
			printer.println("java.util.logging.FileHandler.level = ALL");
			printer.println("java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter");

			printer.println("java.util.logging.ConsoleHandler.level = ALL");
			printer.println("java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter");

			// Retrieve custom configuration
			File defaultFile = new File("log.ini");
			if (defaultFile.exists()) {
				printer.println(StringUtils.readFromFile(defaultFile));
			} else {
				// use only default configuration
			}
			printer.close();

			// Apply configuration
			final PipedOutputStream po = new PipedOutputStream();
			final PipedInputStream pi = new PipedInputStream(po);
			stream.writeTo(po);
			po.close();
			LogManager.getLogManager().readConfiguration(pi);
			pi.close();
			logger.info("Loggers configured.");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Impossible to configure the loggers", e);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("Launching game...");
				try {
					Game app = new Game();
					app.start();
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
				logger.info("Game terminated.");
			}
		}).start();
	}
}
