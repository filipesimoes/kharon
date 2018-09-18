package org.kharon.history;

import java.util.Collection;

import org.kharon.Edge;
import org.kharon.GraphPanel;
import org.kharon.Node;

public class RemoveElementAction extends AddElementAction {

  public RemoveElementAction(Collection<Node> newNodes, Collection<Edge> newEdges) {
    super(newNodes, newEdges);
  }

  @Override
  public void undo(GraphPanel graphPanel) {
    super.redo(graphPanel);
  }

  @Override
  public void redo(GraphPanel graphPanel) {
    super.undo(graphPanel);
  }

}
