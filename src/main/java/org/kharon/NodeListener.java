package org.kharon;

import java.awt.event.MouseEvent;
import java.util.Collection;

public interface NodeListener {

  void nodeClicked(Node node, MouseEvent e);

  void nodePressed(Node node, MouseEvent e);

  void nodeReleased(Node node, MouseEvent e);

  void nodeDragStarted(Collection<Node> nodes, MouseEvent e);

  void nodeDragStopped(Collection<Node> nodes, MouseEvent e);

  void nodeDragged(Collection<Node> nodes, MouseEvent e);

  void nodeHover(Node node, MouseEvent e);

  void nodeOut(MouseEvent e);

}
