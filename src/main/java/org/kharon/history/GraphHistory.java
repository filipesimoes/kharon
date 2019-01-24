package org.kharon.history;

import java.util.ArrayList;
import java.util.List;

import org.kharon.GraphPane;

public class GraphHistory {

  private List<GraphAction> actions = new ArrayList<>();

  private int maxSize;

  private int position = -1;

  private GraphPane graphPanel;

  private NodeMoveRecorder moveRecorder;
  private GraphRecorder graphRecorder;

  private List<GraphHistoryListener> listeners = new ArrayList<>();

  public GraphHistory(GraphPane graphPanel, int maxSize) {
    super();
    this.maxSize = maxSize;
    this.graphPanel = graphPanel;
    this.moveRecorder = new NodeMoveRecorder(this);
    this.graphRecorder = new GraphRecorder(this);
  }

  public GraphHistory(GraphPane graphPanel) {
    this(graphPanel, 50);
  }

  public void clear() {
    this.actions.clear();
    this.position = 0;
  }

  public void add(List<GraphAction> newActions) {
    for (GraphAction action : newActions) {
      add(action);
    }
  }

  public void add(GraphAction action) {
    if (position < actions.size() - 1) {
      for (int index = this.actions.size() - 1; index > position; index--) {
        actions.remove(index);
      }
    }
    actions.add(action);

    if (actions.size() > maxSize) {
      actions.remove(0);
    }

    position = actions.size() - 1;

    notifyHistoryChanged(action);
  }

  public void undo() {
    GraphAction previous = getPrevious();
    if (previous != null) {
      position--;
      previous.undo(graphPanel);
      notifyHistoryChanged(previous);
      graphPanel.resetBuffer();
    }
  }

  public void redo() {
    GraphAction next = getNext();
    if (next != null) {
      position++;
      next.redo(graphPanel);
      notifyHistoryChanged(next);
      graphPanel.resetBuffer();
    }
  }

  public boolean isUndoPossible() {
    return getPrevious() != null;
  }

  public boolean isRedoPossible() {
    return getNext() != null;
  }

  public int getPosition() {
    return position;
  }

  public GraphAction getPrevious() {
    if (position >= 0) {
      return this.actions.get(position);
    } else {
      return null;
    }
  }

  public GraphAction getNext() {
    int nextIndex = position + 1;
    if (nextIndex <= this.actions.size() - 1) {
      return this.actions.get(nextIndex);
    } else {
      return null;
    }
  }

  public NodeMoveRecorder getMoveRecorder() {
    return moveRecorder;
  }

  public GraphRecorder getGraphRecorder() {
    return graphRecorder;
  }

  private void notifyHistoryChanged(GraphAction action) {
    for (GraphHistoryListener listener : listeners) {
      listener.historyChanged(this, action);
    }
  }

  public void addListener(GraphHistoryListener listener) {
    this.listeners.add(listener);
  }

  public void removeListener(GraphHistoryListener listener) {
    this.listeners.remove(listener);
  }

}
