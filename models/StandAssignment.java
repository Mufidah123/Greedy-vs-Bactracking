package models;

public class StandAssignment {
    private Tenant tenant;
    private int zoneNumber;

    public StandAssignment(Tenant tenant, int zoneNumber) {
        this.tenant = tenant;
        this.zoneNumber = zoneNumber;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getZoneName() {
        return "Zona " + (char) ('A' + zoneNumber - 1);
    }

    @Override
    public String toString() {
        return tenant.getName() + " -> " + getZoneName();
    }
}