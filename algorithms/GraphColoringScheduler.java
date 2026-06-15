package algorithms;
import java.util.*;
import models.StandAssignment;
import models.Tenant;

public class GraphColoringScheduler {
    private static final int MAX_ZONES = 5;

    public List<StandAssignment> schedule(List<Tenant> tenants) {
        List<StandAssignment> assignments = new ArrayList<>();
        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        int n = tenants.size();

        Map<Tenant, List<Tenant>> adjList = new HashMap<>();
        Map<Tenant, Integer> degrees = new HashMap<>();

        for (Tenant t : tenants) {
            adjList.put(t, new ArrayList<>());
            degrees.put(t, 0);
        }

        for (int i = 0; i < n; i++) {
            Tenant t1 = tenants.get(i);
            for (int j = i + 1; j < n; j++) {
                Tenant t2 = tenants.get(j);
                if (Math.max(t1.getStartTime(), t2.getStartTime()) < Math.min(t1.getEndTime(), t2.getEndTime())) {
                    adjList.get(t1).add(t2);
                    adjList.get(t2).add(t1);
                    degrees.put(t1, degrees.get(t1) + 1);
                    degrees.put(t2, degrees.get(t2) + 1);
                }
            }
        }

        List<Tenant> sortedTenants = new ArrayList<>(tenants);
        sortedTenants.sort((a, b) -> degrees.get(b) - degrees.get(a));

        Map<Tenant, Integer> tenantColorMap = new HashMap<>();

        for (Tenant tenant : sortedTenants) {
            Set<Integer> neighborColors = new HashSet<>();
            for (Tenant neighbor : adjList.get(tenant)) {
                if (tenantColorMap.containsKey(neighbor)) {
                    neighborColors.add(tenantColorMap.get(neighbor));
                }
            }

            int color = 1;
            while (neighborColors.contains(color)) {
                color++;
            }

            tenantColorMap.put(tenant, color);
        }

        for (Tenant tenant : tenants) {
            int zoneNumber = tenantColorMap.get(tenant);
            if (zoneNumber <= MAX_ZONES) {
                assignments.add(new StandAssignment(tenant, zoneNumber));
            } else {
                System.out.println(">>> [Graph Color] Tenant Ditolak (Tidak Muat): " + tenant.getName());
            }
        }

        return assignments;
    }
}