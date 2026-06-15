import algorithms.GraphColoringScheduler;
import algorithms.GreedyScheduler;
import data.SampleData;
import java.util.List;
import models.StandAssignment;
import models.Tenant;
import utils.TimeHelper;

public class Main {
    public static void main(String[] args) {
        List<Tenant> tenants = SampleData.getTenants();

        System.out.println("=== DATA TENANT ===");
        for (Tenant t : tenants) {
            System.out.printf("- %s (%s) [%s s.d %s]\n", 
                t.getName(), t.getCategory(), 
                TimeHelper.format(t.getStartTime()), 
                TimeHelper.format(t.getEndTime())
            );
        }

        //Greedy
        System.out.println("\n=== HASIL GREEDY SCHEDULER ===");
        GreedyScheduler greedyScheduler = new GreedyScheduler();
        List<StandAssignment> greedyResults = greedyScheduler.schedule(tenants);
        for (StandAssignment assignment : greedyResults) {
            System.out.println(assignment);
        }

        //Graph-Coloring (Backtracking)
        System.out.println("\n=== HASIL GRAPH COLORING SCHEDULER  ===");
        GraphColoringScheduler gcScheduler = new GraphColoringScheduler();
        List<StandAssignment> gcResults = gcScheduler.schedule(tenants);
        for (StandAssignment assignment : gcResults) {
            System.out.println(assignment);
        }
    }
}