package org.kharon;

import java.awt.event.MouseEvent;
import java.util.Collection;

public abstract class NodeAdapter implements NodeListener {

  @Override
  public void nodeClicked(Node node, MouseEvent e) { }

  @Override
  public void nodePressed(Node node, MouseEvent e) { }

  @Override
  public void nodeReleased(Node node, MouseEvent e) { }

  @Override
  public void nodeDragStarted(Collection<Node> nodes, MouseEvent e) { }

  @Override
  public void nodeDragStopped(Collection<Node> nodes, MouseEvent e) { }

  @Override
  public void nodeDragged(Collection<Node> nodes, MouseEvent e) { }

  @Override
  public void nodeHover(Node node, MouseEvent e) { }

  @Override
  public void nodeOut(MouseEvent e) { }

}
