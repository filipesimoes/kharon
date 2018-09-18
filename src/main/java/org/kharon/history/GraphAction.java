package org.kharon.history;

import org.kharon.GraphPanel;

public interface GraphAction {

  void undo(GraphPanel graphPanel);

  void redo(GraphPanel graphPanel);

}
