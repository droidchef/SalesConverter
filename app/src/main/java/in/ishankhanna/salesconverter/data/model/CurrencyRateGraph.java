package in.ishankhanna.salesconverter.data.model;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Created by ishan on 03/04/16.
 */
public class CurrencyRateGraph {

    private final SimpleDirectedWeightedGraph<String, RateEdge> graph
            = new SimpleDirectedWeightedGraph<>(RateEdge.class);

    private RateEdge edge;

    public void addVertex(String name) {
        graph.addVertex(name);
    }

    public void addEdge(String from, String to, double weight) {
        edge = graph.addEdge(from, to);
        graph.setEdgeWeight(edge, weight);
    }

    public SimpleDirectedWeightedGraph<String, RateEdge> getGraph() {
        return graph;
    }



}
