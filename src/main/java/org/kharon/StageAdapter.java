package org.kharon;

import java.awt.event.MouseEvent;

public abstract class StageAdapter implements StageListener {

  @Override
  public void stageClicked(MouseEvent e) { }

  @Override
  public void stageDragStarted(MouseEvent e) { }

  @Override
  public void stageDragStopped(MouseEvent e) { }

  @Override
  public void stageMoved(double x, double y) { }

  @Override
  public void stageZoomChanged(double d) { }

}
