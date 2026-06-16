package data;

import java.util.ArrayList;
import java.util.List;
import models.Tenant;

public class ExtremeData {
    public static List<Tenant> getTenants() {
        List<Tenant> tenants = new ArrayList<>();
        
        // Tenant Utama (Durasi sewa panjang)
        tenants.add(new Tenant("Tenant Minuman Besar", "Minuman", 8, 48));
        tenants.add(new Tenant("Tenant Makanan Besar", "Makanan", 8, 48));
        tenants.add(new Tenant("Tenant Kriya Besar", "Kerajinan", 8, 48));
        
        // Greedy akan rakus mengambil ini karena selesai paling cepat, 
        // padahal tenant ini memblokir tenant besar di atas karena konflik kategori.
        tenants.add(new Tenant("Es Teh", "Minuman", 10, 15));
        tenants.add(new Tenant("Kue", "Makanan", 10, 15));
        tenants.add(new Tenant("Pernik", "Kerajinan", 10, 15));
        tenants.add(new Tenant("Baju", "Pakaian", 10, 15));

        return tenants;
    }
}