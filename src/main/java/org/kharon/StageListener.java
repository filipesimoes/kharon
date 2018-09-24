package org.kharon;

import java.awt.event.MouseEvent;

public interface StageListener {

  void stageClicked(MouseEvent e);

  void stageDragStarted(MouseEvent e);

  void stageDragStopped(MouseEvent e);

  void stageMoved(double x, double y);

  void stageZoomChanged(double d);

}
