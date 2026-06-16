package algorithms;

import java.util.ArrayList;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class BacktrackingScheduler {
    private final List<Tenant> tenants;
    private final int totalZones;
    private List<StandAssignment> bestSolution;
    private int bestCount;

    public BacktrackingScheduler(List<Tenant> tenants, int totalZones) {
        this.tenants = tenants;
        this.totalZones = totalZones;
    }

    public List<StandAssignment> allocate() {
        bestSolution = new ArrayList<>();
        bestCount = 0;

        int[] zoneAllocation = new int[tenants.size()];
        backtrack(0, zoneAllocation, 0);

        return bestSolution;
    }

    // Teknik Pruning (pemangkasan cabang)
    private void backtrack(int tenantIndex, int[] zoneAllocation, int acceptedCount) {
        int remainingTenants = tenants.size() - tenantIndex;
        if (acceptedCount + remainingTenants <= bestCount) {
            return; // Menghentikan jika sisa tenant tidak akan melampaui rekor terbaik
        }

        if (tenantIndex == tenants.size()) {
            if (acceptedCount > bestCount) {
                bestCount = acceptedCount;
                bestSolution = buildAssignments(zoneAllocation);
            }
            return;
        }

        for (int zone = 1; zone <= totalZones; zone++) {
            if (isSafe(tenantIndex, zone, zoneAllocation)) {
                zoneAllocation[tenantIndex] = zone;
                backtrack(tenantIndex + 1, zoneAllocation, acceptedCount + 1);
                zoneAllocation[tenantIndex] = 0; 
            }
        }
        backtrack(tenantIndex + 1, zoneAllocation, acceptedCount);
    }

    private List<StandAssignment> buildAssignments(int[] zoneAllocation) {
        List<StandAssignment> assignments = new ArrayList<>();
        for (int i = 0; i < tenants.size(); i++) {
            if (zoneAllocation[i] != 0) {
                assignments.add(new StandAssignment(tenants.get(i), zoneAllocation[i]));
            }
        }
        return assignments;
    }

    private boolean isSafe(int tenantIndex, int zone, int[] zoneAllocation) {
        Tenant currentTenant = tenants.get(tenantIndex);

        for (int i = 0; i < tenantIndex; i++) {
            if (zoneAllocation[i] == 0) continue;
            Tenant otherTenant = tenants.get(i);
            boolean timeOverlap = isTimeOverlap(currentTenant, otherTenant);

            if (timeOverlap) {
                if (zoneAllocation[i] == zone) return false; 
                if (currentTenant.getCategory().equalsIgnoreCase(otherTenant.getCategory())) return false; 
            }
        }
        return true;
    }

    private boolean isTimeOverlap(Tenant a, Tenant b) {
        return a.getStartTime() < b.getEndTime() && b.getStartTime() < a.getEndTime();
    }
}