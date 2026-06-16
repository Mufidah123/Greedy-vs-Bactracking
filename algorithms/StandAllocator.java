package algorithms;

import java.util.ArrayList;
import java.util.List;

import models.Tenant;
import models.StandAssignment;

public class StandAllocator {

    private final List<Tenant> tenants;
    private final int totalZones;

    public StandAllocator(List<Tenant> tenants, int totalZones) {
        this.tenants = tenants;
        this.totalZones = totalZones;
    }

    public List<StandAssignment> allocate() {

        int[] zoneAllocation = new int[tenants.size()];

        if (backtrack(0, zoneAllocation)) {

            List<StandAssignment> assignments = new ArrayList<>();

            for (int i = 0; i < tenants.size(); i++) {
                assignments.add(
                    new StandAssignment(
                        tenants.get(i),
                        zoneAllocation[i]
                    )
                );
            }

            return assignments;
        }

        return null;
    }

    private boolean backtrack(int tenantIndex,
                              int[] zoneAllocation) {

        if (tenantIndex == tenants.size()) {
            return true;
        }

        for (int zone = 1; zone <= totalZones; zone++) {

            if (isSafe(tenantIndex, zone, zoneAllocation)) {

                zoneAllocation[tenantIndex] = zone;

                if (backtrack(tenantIndex + 1, zoneAllocation)) {
                    return true;
                }

                // BACKTRACKING
                zoneAllocation[tenantIndex] = 0;
            }
        }

        return false;
    }

    private boolean isSafe(int tenantIndex,
                           int zone,
                           int[] zoneAllocation) {

        Tenant currentTenant = tenants.get(tenantIndex);

        for (int i = 0; i < tenantIndex; i++) {

            Tenant otherTenant = tenants.get(i);

            boolean sameCategory =
                    currentTenant.getCategory()
                            .equalsIgnoreCase(otherTenant.getCategory());

            boolean sameZone =
                    zoneAllocation[i] == zone;

            if (sameCategory && sameZone) {
                return false;
            }
        }

        return true;
    }
}