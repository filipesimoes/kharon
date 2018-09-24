package org.kharon.history;

import org.kharon.GraphPane;

public interface GraphAction {

  void undo(GraphPane graphPanel);

  void redo(GraphPane graphPanel);

}
