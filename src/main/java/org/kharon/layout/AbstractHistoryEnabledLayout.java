package org.kharon.layout;

import java.awt.FontMetrics;
import java.util.Arrays;
import java.util.List;

import org.kharon.Graph;
import org.kharon.GraphPane;
import org.kharon.Node;
import org.kharon.history.GraphAction;
import org.kharon.history.MoveNodeAction;

public abstract class AbstractHistoryEnabledLayout implements Layout, HistoryEnabledLayout {

  @Override
  public List<GraphAction> performLayout(GraphPane graphPane, FontMetrics fontMetrics) {
    Graph graph = graphPane.getGraph();
    MoveNodeAction action = new MoveNodeAction();
    performLayout(graph, new HistoryEnabledLayoutAction(action), fontMetrics);
    return Arrays.asList(action);
  }

  @Override
  public void performLayout(Graph graph) {
    performLayout(graph, new SimpleLayoutAction(), null);
  }

  /**
   * 
   * @param graph
   * @param action
   * @param fontMetrics - may be null
   */
  protected abstract void performLayout(Graph graph, LayoutAction action, FontMetrics fontMetrics);

  private static class SimpleLayoutAction extends LayoutAction {

    @Override
    public void move(Node node, int oldX, int oldY, int x, int y) {
      node.setX(x);
      node.setY(y);
    }

  }

  private static class HistoryEnabledLayoutAction extends LayoutAction {

    private MoveNodeAction action;

    public HistoryEnabledLayoutAction(MoveNodeAction action) {
      super();
      this.action = action;
    }

    @Override
    public void move(Node node, int oldX, int oldY, int x, int y) {
      action.setMoved(node, oldX, oldY, x, y);
      node.setX(x);
      node.setY(y);
    }

  }

  public static abstract class LayoutAction {

    void move(Node node, int x, int y) {
      move(node, node.getX(), node.getY(), x, y);
    }

    public abstract void move(Node node, int oldX, int oldY, int x, int y);

  }

}
