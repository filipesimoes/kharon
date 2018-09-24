package org.kharon;

import java.awt.event.MouseEvent;

public interface StageListener {

  void stageClicked(MouseEvent e);

  void stageDragStarted(MouseEvent e);

  void stageDragStopped(MouseEvent e);

  void stageDragged(MouseEvent e);

  void stageZoomChanged(double d);

}
