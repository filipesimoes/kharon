package org.kharon;

import java.util.ArrayList;
import java.util.Collection;

public class OverlappedEdges extends Edge{
    
    private Collection<Edge> incomingEdges = new ArrayList<>();
    private Collection<Edge> outcomingEdges = new ArrayList<>();
    
    public OverlappedEdges(Node source, Node target) {
        this(source.getId(), target.getId());
    }

    public OverlappedEdges(String source, String target) {
        this.source = source;
        this.target = target;
        if(source.compareTo(target) <= 0)
            this.id = source + "-" + target;
        else
            this.id = target + "-" + source;
    }
    
    public void addIncomingEdge(Edge edge) {
        this.incomingEdges.add(edge);
    }
    
    public void addOutcomingEdge(Edge edge) {
        this.outcomingEdges.add(edge);
    }
    
    public Collection<Edge> getEdges() {
        Collection<Edge> result = new ArrayList<>(incomingEdges);
        result.addAll(outcomingEdges);
        return result;
    }
    
    public int getEdgeCount() {
        return incomingEdges.size() + outcomingEdges.size();
    }
    
    public boolean isDoubleDirection() {
        return incomingEdges.size() > 0 && outcomingEdges.size() > 0;
    }

    public boolean isReverseDirection() {
        return incomingEdges.size() > 0 && outcomingEdges.size() == 0; 
    }
}
