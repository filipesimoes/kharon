package org.kharon.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kharon.Graph;
import org.kharon.GraphPane;
import org.kharon.Node;

public class MoveNodeAction implements GraphAction {

  private List<String> nodeIds;

  private List<Integer> oldXs;
  private List<Integer> oldYs;

  private List<Integer> newXs;
  private List<Integer> newYs;

  public MoveNodeAction(String nodeId, int oldX, int oldY, int newX, int newY) {
    super();
    this.nodeIds = Arrays.asList(nodeId);
    this.oldXs = Arrays.asList(oldX);
    this.oldYs = Arrays.asList(oldY);
    this.newXs = Arrays.asList(newX);
    this.newYs = Arrays.asList(newY);
  }

  public MoveNodeAction(List<String> nodeIds, List<Integer> oldXs, List<Integer> oldYs, List<Integer> newXs,
      List<Integer> newYs) {
    super();
    this.nodeIds = nodeIds;
    this.oldXs = oldXs;
    this.oldYs = oldYs;
    this.newXs = newXs;
    this.newYs = newYs;
  }
  
  public MoveNodeAction() {
    super();
    this.nodeIds = new ArrayList<>();
    this.oldXs = new ArrayList<>();
    this.oldYs = new ArrayList<>();
    this.newXs = new ArrayList<>();
    this.newYs = new ArrayList<>();
  }

  public void setMoved(Node node, int x, int y) {
    this.nodeIds.add(node.getId());
    this.oldXs.add(node.getX());
    this.oldYs.add(node.getY());
    this.newXs.add(x);
    this.newYs.add(y);
  }

  @Override
  public void undo(GraphPane graphPanel) {
    act(graphPanel, oldXs, oldYs);
  }

  @Override
  public void redo(GraphPane graphPanel) {
    act(graphPanel, newXs, newYs);
  }

  private void act(GraphPane graphPanel, List<Integer> xs, List<Integer> ys) {
    Graph graph = graphPanel.getGraph();
    for (int index = 0; index < xs.size(); index++) {
      String nodeId = nodeIds.get(index);
      if (graph.containsNode(nodeId)) {
        Integer x = xs.get(index);
        Integer y = ys.get(index);
        Node node = graph.getNode(nodeId);
        node.setX(x);
        node.setY(y);
      }
    }
  }

}
