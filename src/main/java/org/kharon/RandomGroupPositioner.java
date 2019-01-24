package org.kharon;

public class RandomGroupPositioner implements GroupPositioner {

  @Override
  public void place(NodeGroup group, Node node) {
    int x = group.getX();
    int y = group.getY();

    int dX = (int) (Math.random() * 100 - 50);
    int dY = (int) (Math.random() * 100 - 50);

    node.setX(x + dX);
    node.setY(y + dY);
  }

}
