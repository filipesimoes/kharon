package org.kharon;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GraphShape {

  private Shape shape;
  private Paint fillPaint;
  private Paint strokePaint;

  private static final AffineTransform IDENTITY = new AffineTransform();

  public GraphShape(Shape shape) {
    super();
    this.shape = shape;
  }

  public Shape getShape() {
    return shape;
  }

  public Paint getFillPaint() {
    return fillPaint;
  }

  public void setFillPaint(Paint paint) {
    this.fillPaint = paint;
  }

  public Paint getStrokePaint() {
    return strokePaint;
  }

  public void setStrokePaint(Paint strokePaint) {
    this.strokePaint = strokePaint;
  }

  public final void draw(Graphics2D g2d) {
    draw(g2d, IDENTITY);
  }

  public final void draw(Graphics2D g2d, AffineTransform tx) {
    Paint oldPaint = g2d.getPaint();
    
    Shape txShape = tx.createTransformedShape(shape);

    if (fillPaint != null) {
      g2d.setPaint(fillPaint);
      g2d.fill(txShape);
    } else if (strokePaint != null) {
      g2d.setPaint(strokePaint);
      g2d.draw(txShape);
    } else {
      g2d.draw(txShape);
    }

    g2d.setPaint(oldPaint);
  }

}
