package org.kharon.history;

import java.util.Collection;

import org.kharon.Edge;
import org.kharon.Graph;
import org.kharon.GraphPane;
import org.kharon.Node;

public class AddElementAction implements GraphAction {

  private Collection<Node> newNodes;
  private Collection<Edge> newEdges;

  public AddElementAction(Collection<Node> newNodes, Collection<Edge> newEdges) {
    super();
    this.newNodes = newNodes;
    this.newEdges = newEdges;
  }

  @Override
  public void undo(GraphPane graphPanel) {
    Graph graph = graphPanel.getGraph();
    graph.removeElements(this, newNodes, newEdges);
  }

  @Override
  public void redo(GraphPane graphPanel) {
    Graph graph = graphPanel.getGraph();
    graph.addElements(this, newNodes, newEdges);
  }

}
