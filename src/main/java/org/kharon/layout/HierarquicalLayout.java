package org.kharon.layout;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;

public class HierarquicalLayout extends AbstractHistoryEnabledLayout {

  private static final int DEFAULT_SUB_GRAPH_GAP = 30;
  static final double DEFAULT_V_GAP = 5d;
  static final double DEFAULT_H_GAP = 5d;
  private static final GraphSizeComparator GRAPH_SIZE_COMPARATOR = new GraphSizeComparator();

  private int maxLength = 30;

  private double vGap = DEFAULT_V_GAP;
  private double hGap = DEFAULT_H_GAP;

  public HierarquicalLayout(int maxLabelLength, double vGap, double hGap) {
    super();
    this.maxLength = maxLabelLength;
    this.vGap = vGap;
    this.hGap = hGap;
  }

  public HierarquicalLayout() {
    super();
  }

  public HierarquicalLayout(double vGap, double hGap) {
    super();
    this.vGap = vGap;
    this.hGap = hGap;
  }

  @Override
  protected void performLayout(Graph graph, LayoutAction action, FontMetrics fontMetrics) {
    List<Graph> subGraphs = getConnectedSubGraphs(graph);

    Collections.sort(subGraphs, Collections.reverseOrder(GRAPH_SIZE_COMPARATOR));

    Rectangle boundingBox = graph.getBoundingBox();

    int left = (int) boundingBox.getMinX();
    int middle = (int) (boundingBox.getMinY() + boundingBox.height / 2);
    for (Graph subGraph : subGraphs) {
      boundingBox = performLayoutSubGraph(subGraph, action, left, middle, fontMetrics);
      left += boundingBox.width + DEFAULT_SUB_GRAPH_GAP;
    }

  }

  private Rectangle performLayoutSubGraph(Graph subGraph, LayoutAction action, int left, int middle,
      FontMetrics fontMetrics) {

    List<Node> nodes = new ArrayList<>(subGraph.getNodes());

    List<Node> lowestDegreeNodes = collectLowestDegreeNodes(nodes);

    List<Level> levels = buildLevels(lowestDegreeNodes, subGraph);
    Dimension totalDim = getTotalDimension(levels, fontMetrics);
    int top = (int) middle - (totalDim.height / 2);
    int levelTop = top;
    for (Level level : levels) {
      Dimension dimension = level.getDimension(vGap, fontMetrics);
      int levelLeft = left + (totalDim.width / 2) - (dimension.width / 2);
      for (int index = 0; index < level.nodes.size(); index++) {
        Node node = level.nodes.get(index);
        action.move(node, levelLeft, levelTop);

        int size = node.getSize();
        int labelWidth = 0;
        String label = node.getShortenedLabel(maxLength);
        if (label != null && fontMetrics != null) {
          labelWidth = fontMetrics.stringWidth(label);
        }
        size = Math.max(size, labelWidth);

        levelLeft += size * (1 + vGap);
      }

      levelTop += dimension.height * (1 + hGap);
    }

    return new Rectangle(left, top, totalDim.width, totalDim.height);

  }

  public Dimension getTotalDimension(List<Level> levels, FontMetrics fontMetrics) {
    int width = 0;
    int height = 0;

    for (int index = 0; index < levels.size(); index++) {
      Level level = levels.get(index);
      Dimension dim = level.getDimension(vGap, fontMetrics);
      width = Math.max(width, dim.width);

      if (index + 1 < levels.size()) {
        height += dim.height * (1 + hGap);
      } else {
        height += dim.height;
      }
    }

    return new Dimension(width, height);
  }

  public List<Level> buildLevels(List<Node> lowestDegreeNodes, Graph graph) {
    Level level0 = new Level(maxLength, lowestDegreeNodes);

    List<Level> result = new ArrayList<>();
    result.add(level0);

    HashSet<Node> control = new HashSet<>();
    for (Node node : level0.nodes) {
      buildLevel(result, control, graph, node, 0);
    }

    return result;
  }

  private void buildLevel(List<Level> result, Set<Node> control, Graph graph, Node node, int height) {
    Set<Node> neighbours = graph.getOutcomingNeighbours(node);

    if (!neighbours.isEmpty()) {
      Level level;
      if (height + 1 < result.size()) {
        level = result.get(height + 1);
      } else {
        level = new Level(maxLength);
        result.add(level);
      }

      for (Node neighbour : neighbours) {
        if (control.add(neighbour)) {
          level.nodes.add(neighbour);
          buildLevel(result, control, graph, neighbour, height + 1);
        }
      }
    }

  }

  public List<Node> collectLowestDegreeNodes(Collection<Node> nodes) {
    List<Node> result = new ArrayList<>();

    int lowest = Integer.MAX_VALUE;

    for (Node node : nodes) {

      int incomingDegree = node.getIncomingDegree();
      if (incomingDegree == lowest) {
        result.add(node);
      } else if (incomingDegree < lowest) {
        result.clear();
        result.add(node);
        lowest = incomingDegree;
      }

    }

    return result;
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
    subGraph.addEdges(edges);
  }

  static class Level {

    int maxLength;
    List<Node> nodes;
    private Dimension dim;

    public Level(int maxLength, List<Node> nodes) {
      this.maxLength = maxLength;
      this.nodes = nodes;
    }

    public Level(int maxLength) {
      this.maxLength = maxLength;
      this.nodes = new ArrayList<>();
    }

    public Level(List<Node> nodes) {
      this.maxLength = 30;
      this.nodes = nodes;
    }

    public Dimension getDimension(double gap, FontMetrics fontMetrics) {
      if (dim == null) {
        int width = 0;
        int height = 0;

        for (int index = 0; index < nodes.size(); index++) {
          Node node = nodes.get(index);
          int size = node.getSize();
          int labelWidth = 0;
          String label = node.getShortenedLabel(maxLength);
          if (label != null && fontMetrics != null) {
            labelWidth = fontMetrics.stringWidth(label);
          }
          size = Math.max(size, labelWidth);
          height = Math.max(height, node.getSize());

          if (index + 1 < nodes.size()) {
            width += size * (1 + gap);
          } else {
            width += size;
          }
        }

        dim = new Dimension(width, height);
      }
      return dim;
    }

  }

  private static class GraphSizeComparator implements Comparator<Graph> {

    @Override
    public int compare(Graph o1, Graph o2) {
      return o1.getSize() - o2.getSize();
    }

  }

}
