package org.kharon;

import java.awt.event.MouseEvent;

public interface EdgeListener {
    
    void edgeClicked(Edge edge, MouseEvent e);
    
    void edgeHovered(Edge edge, MouseEvent e);
    
    void edgeOut(Edge edge, MouseEvent e);

}
