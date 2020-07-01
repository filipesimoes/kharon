package org.kharon.layout.graphviz;

import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;
import org.kharon.layout.AbstractHistoryEnabledLayout;

public class GraphVizLayout extends AbstractHistoryEnabledLayout {

  private static final int DEFAULT_SUB_GRAPH_GAP = 30;

  private static final Logger LOGGER = Logger.getLogger(GraphVizLayout.class.getSimpleName());

  private GraphVizAlgorithm algorithm;
  private GraphVizResolver graphVizResolver;

  public GraphVizLayout(GraphVizAlgorithm algo) {
    super();
    this.algorithm = algo;
    this.graphVizResolver = new GraphVizDefaultResolver();
  }

  public GraphVizLayout(GraphVizAlgorithm algorithm, GraphVizResolver graphVizResolver) {
    super();
    this.algorithm = algorithm;
    this.graphVizResolver = graphVizResolver;
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
      GraphVizPlainReader reader = new GraphVizPlainReader();

      List<Graph> subGraphs = getConnectedSubGraphs(graph);

      Rectangle boundingBox = graph.getBoundingBox();

      int left = (int) boundingBox.getMinX();
      int middle = (int) (boundingBox.getMinY() + boundingBox.height / 2);

      for (Graph subGraph : subGraphs) {
        writer.write(subGraph);
        boundingBox = reader.read(process.getInputStream(), subGraph, left, middle, action);
        left += boundingBox.width + DEFAULT_SUB_GRAPH_GAP;
      }

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

  public List<Graph> getConnectedSubGraphs(Graph graph) {
    List<Graph> subGraphs = new ArrayList<>();

    if (!graph.isEmpty()) {
      Set<Node> processed = new HashSet<>();
      Set<Node> nodes = graph.getNodes();

      Iterator<Node> iterator = nodes.iterator();
      while (iterator.hasNext()) {
        Node start = iterator.next();

        if (!processed.contains(start)) {
          Graph subGraph = new Graph();
          visit(start, processed, subGraph, graph);
          subGraphs.add(subGraph);
        }
      }
    }
    return subGraphs;
  }

  private void visit(Node start, Set<Node> control, Graph subGraph, Graph graph) {
    control.add(start);

    subGraph.addNode(start);

    Set<Node> neighbours = graph.getNeighbours(start);
    for (Node neighbour : neighbours) {
      if (!control.contains(neighbour)) {
        visit(neighbour, control, subGraph, graph);
      }
    }

    Collection<Edge> edges = graph.getEdges(start);
    HashMap<String, Edge> overlappedEdges = new HashMap<>();
    for(Edge edge : edges) {
      overlappedEdges.putIfAbsent(edge.getSource() + "-" + edge.getTarget(), edge);
    }
    subGraph.addEdges(overlappedEdges.values());
  }

  private List<String> getCmds() {
    String binaryPath = this.graphVizResolver.resolveBinaryPath(algorithm);
    return Arrays.asList(binaryPath, "-T", "plain", "-y");
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
