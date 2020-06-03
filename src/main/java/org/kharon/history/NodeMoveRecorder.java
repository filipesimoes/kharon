package org.kharon.history;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kharon.Node;
import org.kharon.NodeAdapter;

public class NodeMoveRecorder extends NodeAdapter {

  private GraphHistory history;

  private List<String> nodeIds;

  private List<Integer> oldXs;
  private List<Integer> oldYs;

  public NodeMoveRecorder(GraphHistory history) {
    super();
    this.history = history;
  }

  @Override
  public void nodeDragStarted(Collection<Node> nodes, MouseEvent e) {

    nodeIds = new ArrayList<>();

    oldXs = new ArrayList<>();
    oldYs = new ArrayList<>();

    for (Node node : nodes) {
      nodeIds.add(node.getId());

      oldXs.add(node.getX());
      oldYs.add(node.getY());
    }
  }

  @Override
  public void nodeDragStopped(Collection<Node> nodes, MouseEvent e) {

    List<Integer> newXs = new ArrayList<>();
    List<Integer> newYs = new ArrayList<>();

    if(nodeIds == null) {
      return;
    }

    for (String nodeId : nodeIds) {
      for (Node node : nodes) {
        if (node.getId().equals(nodeId)) {

          newXs.add(node.getX());
          newYs.add(node.getY());

          nodes.remove(node);
          break;
        }
      }
    }

    MoveNodeAction action = new MoveNodeAction(nodeIds, oldXs, oldYs, newXs, newYs);
    history.add(action);

    release();
  }

  private void release() {
    nodeIds = null;
    oldXs = null;
    oldYs = null;
  }

}
