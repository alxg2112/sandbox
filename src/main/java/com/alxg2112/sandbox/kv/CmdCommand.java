package com.alxg2112.sandbox.kv;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import sun.font.FontUtilities;

/**
 * @author Alexander Gryshchenko
 */
public class CmdCommand {

	private static class StreamGobbler implements Runnable {

		private InputStream inputStream;
		private Consumer<String> consumer;

		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines()
					.forEach(consumer);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder();
		if (FontUtilities.isWindows) {
			builder.command("ping", "google.com");
		} else {
			builder.command("sh", "-c", "ls");
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		StreamGobbler streamGobbler =
				new StreamGobbler(process.getInputStream(), System.out::println);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;
		executorService.shutdown();
	}
}
