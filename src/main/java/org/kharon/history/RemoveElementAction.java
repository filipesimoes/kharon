package org.kharon.history;

import java.util.Collection;

import org.kharon.Edge;
import org.kharon.GraphPane;
import org.kharon.Node;

public class RemoveElementAction extends AddElementAction {

  public RemoveElementAction(Collection<Node> newNodes, Collection<Edge> newEdges) {
    super(newNodes, newEdges);
  }

  @Override
  public void undo(GraphPane graphPanel) {
    super.redo(graphPanel);
  }

  @Override
  public void redo(GraphPane graphPanel) {
    super.undo(graphPanel);
  }

}
