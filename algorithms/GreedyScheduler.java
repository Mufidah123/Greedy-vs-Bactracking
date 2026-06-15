package algorithms;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class GreedyScheduler {

    public List<StandAssignment> schedule(List<Tenant> tenants) {
        List<StandAssignment> assignments = new ArrayList<>();
        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        List<Tenant> sortedTenants = new ArrayList<>(tenants);
        sortedTenants.sort(Comparator.comparingInt(Tenant::getEndTime));

        Tenant firstTenant = sortedTenants.get(0);
        assignments.add(new StandAssignment(firstTenant, 1));
        int lastFinish = firstTenant.getEndTime();

        for (int i = 1; i < sortedTenants.size(); i++) {
            Tenant current = sortedTenants.get(i);
            
            if (current.getStartTime() >= lastFinish) {
                assignments.add(new StandAssignment(current, 1));
                lastFinish = current.getEndTime();
            } else {
                System.out.println(">>> [Greedy] Tenant Tereliminasi (Bentrok): " + current.getName());
            }
        }

        return assignments;
    }
}