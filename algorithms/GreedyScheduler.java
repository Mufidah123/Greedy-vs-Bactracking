package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import models.StandAssignment;
import models.Tenant;

public class GreedyScheduler {

    private static final int TOTAL_ZONES = 4;

    private final List<Tenant> rejectedTenants =
            new ArrayList<>();

    public List<StandAssignment> schedule(
            List<Tenant> tenants
    ) {

        List<StandAssignment> assignments =
                new ArrayList<>();

        rejectedTenants.clear();

        if (tenants == null || tenants.isEmpty()) {
            return assignments;
        }

        List<Tenant> sortedTenants =
                new ArrayList<>(tenants);

        sortedTenants.sort(
                Comparator.comparingInt(
                        Tenant::getEndTime
                )
        );

        for (Tenant tenant : sortedTenants) {

            boolean assigned = false;

            for (int zone = 1;
                 zone <= TOTAL_ZONES;
                 zone++) {

                if (isSafe(
                        tenant,
                        zone,
                        assignments
                )) {

                    assignments.add(
                            new StandAssignment(
                                    tenant,
                                    zone
                            )
                    );

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

    private boolean isSafe(
            Tenant current,
            int zone,
            List<StandAssignment> assignments
    ) {

        for (StandAssignment assignment
                : assignments) {

            if (assignment.getZoneNumber()
                    != zone) {
                continue;
            }

            Tenant other =
                    assignment.getTenant();

            boolean sameCategory =
                    current.getCategory()
                            .equalsIgnoreCase(
                                    other.getCategory()
                            );

            boolean timeOverlap =
                    isTimeOverlap(
                            current,
                            other
                    );

            if (sameCategory || timeOverlap) {
                return false;
            }
        }

        return true;
    }

    private boolean isTimeOverlap(
            Tenant a,
            Tenant b
    ) {

        return a.getStartTime()
                < b.getEndTime()
                &&
                b.getStartTime()
                < a.getEndTime();
    }

    public List<Tenant> getRejectedTenants() {
        return rejectedTenants;
    }
}