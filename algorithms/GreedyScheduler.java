package algorithms;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class GreedyScheduler {
    private static final int MAX_ZONES = 5;

    public List<StandAssignment> schedule(List<Tenant> tenants) {
        List<StandAssignment> assignments = new ArrayList<>();
        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        List<Tenant> sortedTenants = new ArrayList<>(tenants);
        sortedTenants.sort(Comparator.comparingInt(Tenant::getStartTime));

        List<List<Tenant>> zones = new ArrayList<>();

        for (Tenant tenant : sortedTenants) {
            int assignedZoneNumber = -1;

            for (int i = 0; i < zones.size(); i++) {
                boolean hasConflict = false;
                for (Tenant existingTenant : zones.get(i)) {
                    if (Math.max(tenant.getStartTime(), existingTenant.getStartTime()) < 
                        Math.min(tenant.getEndTime(), existingTenant.getEndTime())) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    assignedZoneNumber = i + 1;
                    zones.get(i).add(tenant);
                    break;
                }
            }

            if (assignedZoneNumber == -1 && zones.size() < MAX_ZONES) {
                List<Tenant> newZone = new ArrayList<>();
                newZone.add(tenant);
                zones.add(newZone);
                assignedZoneNumber = zones.size();
            }

            if (assignedZoneNumber != -1) {
                assignments.add(new StandAssignment(tenant, assignedZoneNumber));
            } else {
                System.out.println(">>> [Greedy] Tenant Ditolak (Tidak Muat): " + tenant.getName());
            }
        }

        return assignments;
    }
}