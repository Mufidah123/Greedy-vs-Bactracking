package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class GreedyScheduler {

    private final int totalZones;
    private final List<Tenant> rejectedTenants = new ArrayList<>();

    public GreedyScheduler(int totalZones) {
        this.totalZones = totalZones;
    }

    public List<StandAssignment> schedule(List<Tenant> tenants) {
        List<StandAssignment> assignments = new ArrayList<>();
        rejectedTenants.clear();

        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        List<Tenant> sortedTenants = new ArrayList<>(tenants);
        sortedTenants.sort(Comparator.comparingInt(Tenant::getEndTime));

        for (Tenant tenant : sortedTenants) {
            boolean assigned = false;

            for (int zone = 1; zone <= totalZones; zone++) {
                if (isSafe(tenant, zone, assignments)) {
                    assignments.add(new StandAssignment(tenant, zone));
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                rejectedTenants.add(tenant);
            }
        }

        return assignments;
    }

    private boolean isSafe(Tenant current, int zone, List<StandAssignment> assignments) {
        for (StandAssignment assignment : assignments) {
            Tenant other = assignment.getTenant();
            boolean timeOverlap = isTimeOverlap(current, other);

            if (timeOverlap) {
                if (assignment.getZoneNumber() == zone) {
                    return false; // Ditolak: 1 Stand fisik tidak bisa dipakai 2 tenant di jam yang sama
                }
                if (current.getCategory().equalsIgnoreCase(other.getCategory())) {
                    return false; // Ditolak: Kategori sama tidak boleh jualan barengan di jam yang sama
                }
            }
        }
        return true; 
    }

    private boolean isTimeOverlap(Tenant a, Tenant b) {
        return a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime();
    }

    public List<Tenant> getRejectedTenants() {
        return rejectedTenants;
    }
}