package br.gov.pf.labld.kharon;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class NodeBoundingBox {

  private List<Shape> boxes = new ArrayList<>(2);

  public void addBox(Shape box) {
    this.boxes.add(box);
  }

  public boolean contains(double x, double y) {
    for (Shape box : boxes) {
      if (box.contains(x, y)) {
        return true;
      }
    }
    return false;
  }

  public List<Shape> getBoxes() {
    return boxes;
  }

}
