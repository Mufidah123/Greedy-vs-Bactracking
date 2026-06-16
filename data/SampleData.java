package data;
import java.util.ArrayList;
import java.util.List;
import models.Tenant;

public class SampleData {

    public static List<Tenant> getTenants() {
        List<Tenant> tenants = new ArrayList<>();

        tenants.add(new Tenant("Coffee Fore","Minuman",
            8,18
        ));
        tenants.add(new Tenant("Coffee Markom","Minuman",
            10,42
        ));

        tenants.add(new Tenant("Burger Aldi Taher","Makanan",
            8,66
        ));
        tenants.add(new Tenant("Burger Enaak!","Makanan",
            32,90
        ));

        tenants.add(new Tenant("Dessert Manisku","Dessert",
            56,90
        ));
        tenants.add(new Tenant("Dessert Heaven","Dessert",
            80,114
        ));

        tenants.add(new Tenant("Bakso Bakar","Makanan",
            104,138
        ));
        tenants.add(new Tenant("Bakso Panggang","Makanan",
            106,162
        ));

        tenants.add(new Tenant("Seafood Sari Laut","Makanan Laut",
            128,162
        ));
        tenants.add(new Tenant("Seafood Bahari","Makanan Laut",
            130,162
        ));

        tenants.add(new Tenant("Craft A","Kerajinan",
            8,162
        ));
        tenants.add(new Tenant("Craft B","Kerajinan",
            32,162
        ));

        // Data Pengeco
        tenants.add(new Tenant("Ayam Geprek Sultan","Makanan",
            18,30
        ));
        tenants.add(new Tenant("Es Teh Jumbo","Minuman",
            20,28
        ));
        tenants.add(new Tenant("Pancake Premium","Dessert", 
            8,48
        ));
        tenants.add(new Tenant("Jus Buah Segar","Minuman", 
            8,18
        ));
        tenants.add(new Tenant("Kebab Turki","Makanan",
            8,58
        ));
        tenants.add(new Tenant("Nusa Rajut","Kerajinan",
            18,32
        ));

        return tenants;
    }
}