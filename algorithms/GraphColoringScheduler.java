package algorithms;
import java.util.ArrayList;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class GraphColoringScheduler {
    private static final int MAX_COLORS = 5;

    public List<StandAssignment> schedule(List<Tenant> tenants) {
        List<StandAssignment> assignments = new ArrayList<>();
        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        int numberOfVertices = tenants.size();
        int[] colors = new int[numberOfVertices];
        
        boolean[][] graph = new boolean[numberOfVertices][numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                if (i != j) {
                    Tenant t1 = tenants.get(i);
                    Tenant t2 = tenants.get(j);
                    // Cek jika jadwal bertabrakan
                    if (Math.max(t1.getStartTime(), t2.getStartTime()) < Math.min(t1.getEndTime(), t2.getEndTime())) {
                        graph[i][j] = true;
                    }
                }
            }
        }

        if (graphColoring(0, graph, colors, MAX_COLORS, numberOfVertices)) {
            for (int i = 0; i < numberOfVertices; i++) {
                assignments.add(new StandAssignment(tenants.get(i), colors[i]));
            }
        } else {
            System.out.println(">>> [Graph Coloring] Gagal! Tidak ada solusi kombinasi warna dengan 5 zona.");
        }

        return assignments;
    }

    private boolean graphColoring(int vertex, boolean[][] graph, int[] colors, int maxColors, int numberOfVertices) {
        if (vertex == numberOfVertices) {
            return true;
        }

        for (int color = 1; color <= maxColors; color++) {
            
            if (isSafe(vertex, color, colors, graph, numberOfVertices)) {
                colors[vertex] = color; // colors[vertex] ← color

                if (graphColoring(vertex + 1, graph, colors, maxColors, numberOfVertices)) {
                    return true;
                }

                colors[vertex] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int vertex, int color, int[] colors, boolean[][] graph, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++) {
            if (graph[vertex][i] && color == colors[i]) {
                return false;
            }
        }
        return true;
    }
}