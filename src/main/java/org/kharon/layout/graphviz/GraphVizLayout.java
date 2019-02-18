package org.kharon.layout.graphviz;

import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kharon.Graph;
import org.kharon.layout.AbstractHistoryEnabledLayout;

public class GraphVizLayout extends AbstractHistoryEnabledLayout {

  private static final Logger LOGGER = Logger.getLogger(GraphVizLayout.class.getSimpleName());

  private GraphVizAlgorithm algorithm;

  public GraphVizLayout(GraphVizAlgorithm algo) {
    super();
    this.algorithm = algo;
  }

  @Override
  protected void performLayout(Graph graph, LayoutAction action, FontMetrics fontMetrics) {

    ExecutorService executorService = null;
    Process process = null;
    try {
      executorService = Executors.newSingleThreadExecutor();

      List<String> cmds = getCmds();
      ProcessBuilder processBuilder = new ProcessBuilder(cmds);
      process = processBuilder.start();

      executorService.submit(new InputLogger(Level.WARNING, process.getErrorStream()));
      GraphVizWriter writer = new GraphVizWriter(process.getOutputStream());
      writer.write(graph);

      GraphVizPlainReader reader = new GraphVizPlainReader();
      reader.read(process.getInputStream(), graph, action);

      process.destroy();
      process.waitFor();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      LOGGER.log(Level.WARNING, e.getMessage(), e);
    } finally {
      if (executorService != null) {
        executorService.shutdown();
      }
      if (process != null && process.isAlive()) {
        process.destroyForcibly();
      }
    }

  }

  private List<String> getCmds() {
    return Arrays.asList(algorithm.getCmd(), "-T", "plain", "-y");
  }

  private class InputLogger implements Runnable {

    private Level level;
    private InputStream in;

    public InputLogger(Level level, InputStream in) {
      super();
      this.level = level;
      this.in = in;
    }

    @Override
    public void run() {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
      String line = null;
      try {
        while ((line = reader.readLine()) != null) {
          LOGGER.log(level, line);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

}
