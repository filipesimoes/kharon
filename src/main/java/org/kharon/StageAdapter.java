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
  public void stageDragged(MouseEvent e) { }

  @Override
  public void stageZoomChanged(double d) { }

}
