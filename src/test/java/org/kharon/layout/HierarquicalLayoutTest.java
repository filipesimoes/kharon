package org.kharon.layout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.Node;
import org.kharon.layout.HierarquicalLayout.Level;

public class HierarquicalLayoutTest {

  private HierarquicalLayout layout = new HierarquicalLayout();

  @Test
  public void testCollectLowestDegreeNodes() {
    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);
    Node node2 = new Node("2", 1, 0);
    Node node3 = new Node("3", 1, 1);

    graph.addNodes(Arrays.asList(node0, node1, node2, node3));

    Edge edge0 = new Edge("0", node0, node1);
    Edge edge1 = new Edge("1", node0, node2);
    Edge edge2 = new Edge("2", node3, node2);

    graph.addEdges(Arrays.asList(edge0, edge1, edge2));

    List<Node> lowestDegreeNodes = layout.collectLowestDegreeNodes(graph.getNodes());
    assertEquals(2, lowestDegreeNodes.size());

    Set<String> ids = lowestDegreeNodes.stream().map(node -> node.getId()).collect(Collectors.toSet());
    assertTrue(ids.contains("0"));
    assertTrue(ids.contains("3"));
  }

  @Test
  public void testBuildLevels() {
    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);
    Node node2 = new Node("2", 1, 0);
    Node node3 = new Node("3", 1, 1);

    graph.addNodes(Arrays.asList(node0, node1, node2, node3));

    Edge edge0 = new Edge("0", node0, node1);
    Edge edge1 = new Edge("1", node0, node2);
    Edge edge2 = new Edge("2", node3, node2);

    graph.addEdges(Arrays.asList(edge0, edge1, edge2));

    List<Node> lowestDegreeNodes = Arrays.asList(node0, node3);

    List<Level> levels = layout.buildLevels(lowestDegreeNodes, graph);
    assertEquals(2, levels.size());

    Level level0 = levels.get(0);
    assertEquals(2, level0.nodes.size());
    Set<String> ids0 = level0.nodes.stream().map(node -> node.getId()).collect(Collectors.toSet());
    assertTrue(ids0.contains("0"));
    assertTrue(ids0.contains("3"));

    Level level1 = levels.get(1);
    assertEquals(2, level1.nodes.size());
    Set<String> ids1 = level1.nodes.stream().map(node -> node.getId()).collect(Collectors.toSet());
    assertTrue(ids1.contains("1"));
    assertTrue(ids1.contains("2"));
  }

  @Test
  public void testgetLevelDimesion() {

    Node node0 = new Node("0", 0, 0);
    node0.setSize(20);
    Node node1 = new Node("1", 0, 1);
    node1.setSize(40);

    Level level = new Level(Arrays.asList(node0, node1));

    Dimension dim = level.getDimension(HierarquicalLayout.DEFAULT_V_GAP, null);

    assertEquals(160, dim.width);
    assertEquals(40, dim.height);
  }

  @Test
  public void testGetTotalLevelDimesion() {
    Node node0 = new Node("0", 0, 0);
    node0.setSize(20);
    Node node1 = new Node("1", 0, 1);
    node1.setSize(40);

    Level level0 = new Level(Arrays.asList(node0, node1));

    Node node2 = new Node("2", 0, 0);
    node2.setSize(10);
    Node node3 = new Node("3", 0, 1);
    node3.setSize(10);

    Level level1 = new Level(Arrays.asList(node2, node3));

    Dimension dim = layout.getTotalDimension(Arrays.asList(level0, level1), null);

    assertEquals(160, dim.width);
    assertEquals(250, dim.height);
  }

  @Test
  public void testFindConnectedGraphsOneConnectedGraphOnly() {

    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);

    graph.addNode(node0);
    graph.addNode(node1);

    Edge edge0 = new Edge("0", node0, node1);

    graph.addEdge(edge0);

    List<Graph> graphs = layout.getConnectedSubGraphs(graph);

    assertEquals(1, graphs.size());
    assertEquals(2, graphs.get(0).getNodes().size());
    assertEquals(1, graphs.get(0).getEdges().size());
  }

  @Test
  public void testFindConnectedGraphsTwoDisconnectedGraphs() {

    Graph graph = new Graph();

    Node node0 = new Node("0", 0, 0);
    Node node1 = new Node("1", 0, 1);

    graph.addNode(node0);
    graph.addNode(node1);

    Edge edge0 = new Edge("0", node0, node1);
    graph.addEdge(edge0);

    Node node2 = new Node("2", 1, 0);
    Node node3 = new Node("3", 1, 1);

    graph.addNode(node2);
    graph.addNode(node3);

    Edge edge1 = new Edge("1", node2, node3);
    graph.addEdge(edge1);

    List<Graph> graphs = layout.getConnectedSubGraphs(graph);

    assertEquals(2, graphs.size());
    assertEquals(2, graphs.get(0).getNodes().size());
    assertEquals(1, graphs.get(0).getEdges().size());

    assertEquals(2, graphs.get(1).getNodes().size());
    assertEquals(1, graphs.get(1).getEdges().size());
  }

}
