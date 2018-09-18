package org.kharon.history;

import org.kharon.GraphAdapter;
import org.kharon.GraphEvent;

public class GraphRecorder extends GraphAdapter {

  private GraphHistory history;

  public GraphRecorder(GraphHistory history) {
    super();
    this.history = history;
  }

  private boolean isOriginHistoryAction(GraphEvent e) {
    Object originator = e.getOriginator();
    return originator != null && originator instanceof GraphAction;
  }

  @Override
  public void elementsAdded(GraphEvent e) {
    if (!isOriginHistoryAction(e)) {
      AddElementAction action = new AddElementAction(e.getNodes(), e.getEdges());
      history.add(action);
    }
  }

  @Override
  public void elementsRemoved(GraphEvent e) {
    if (!isOriginHistoryAction(e)) {
      RemoveElementAction action = new RemoveElementAction(e.getNodes(), e.getEdges());
      history.add(action);
    }
  }

}
