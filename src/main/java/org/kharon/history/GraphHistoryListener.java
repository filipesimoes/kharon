package org.kharon.history;

public interface GraphHistoryListener {

  void historyChanged(GraphHistory history, GraphAction action);

}
