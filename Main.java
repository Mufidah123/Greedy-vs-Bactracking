import algorithms.BacktrackingScheduler;
import algorithms.GreedyScheduler;
import data.ExtremeData;
import data.SampleData;
import java.util.List;
import java.util.Scanner;
import models.StandAssignment;
import models.Tenant;

public class Main {
    private static final int TOTAL_ZONES = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Tenant> tenants = SampleData.getTenants();

        while (true) {
            System.out.println("\n=====================================================");
            System.out.println("   SISTEM PENJADWALAN STAND BAZAR RESTO (1 MINGGU)   ");
            System.out.println("=====================================================");
            System.out.println("\n0. Keluar");
            System.out.println("1. Tampilkan Data Pendaftar");
            System.out.println("2. Jalankan Algoritma Greedy (Activity Selection)");
            System.out.println("3. Jalankan Algoritma Backtracking (Graph Coloring)");
            System.out.println("4. Bandingkan Hasil Keduanya (Detail Jadwal & Waktu)");
            System.out.println("5. Uji Kasus Ekstrem (Demonstrasi Kelemahan)");
            
            System.out.print("\nPilih menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: 
                    printTenantsAsTable(tenants);
                    pressEnterToContinue(scanner);
                    break;
                case 2: 
                    runGreedy(tenants);
                    pressEnterToContinue(scanner);
                    break;
                case 3: 
                    runBacktracking(tenants);
                    pressEnterToContinue(scanner);
                    break;
                case 4: 
                    compareResults(tenants);
                    pressEnterToContinue(scanner);
                    break;
                case 5: 
                    runExtremeCase();
                    pressEnterToContinue(scanner);
                    break;
                case 0:
                    System.out.println("Keluar dari program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
                    pressEnterToContinue(scanner);
            }
        }
    }

    // Fungsi jeda sebelum kembali ke menu
    private static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\nTekan Enter untuk kembali ke menu...");
        scanner.nextLine();
    }

    private static String formatTime(int hours) {
        String[] days = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        int dayIndex = (hours) / 24;
        int timeOfDay = (hours) % 24;
        
        if (dayIndex >= 7) {
            dayIndex = 6;
            timeOfDay = 24;
        }
        return String.format("%s %02d:00", days[dayIndex], timeOfDay);
    }

    private static void printTenantsAsTable(List<Tenant> tenants) {
        System.out.println("\n=== DATA PENDAFTAR TENANT ===");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("| %-22s | %-13s | %-18s | %-18s|\n", "Nama Tenant", "Kategori", "Waktu Mulai", "Waktu Selesai");
        System.out.println("-----------------------------------------------------------------------------------");
        for (Tenant t : tenants) {
            System.out.printf("| %-22s | %-13s | %-18s | %-18s|\n", 
                t.getName(), t.getCategory(), formatTime(t.getStartTime()), formatTime(t.getEndTime()));
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    private static void runGreedy(List<Tenant> tenants) {
        System.out.println("\n=== Menjalankan Algoritma Greedy ===");
        GreedyScheduler scheduler = new GreedyScheduler(TOTAL_ZONES);
        List<StandAssignment> assignments = scheduler.schedule(tenants);
        printSummary(tenants, assignments, "Greedy");
    }

    private static void runBacktracking(List<Tenant> tenants) {
        System.out.println("\n=== Menjalankan Algoritma Backtracking ===");
        BacktrackingScheduler scheduler = new BacktrackingScheduler(tenants, TOTAL_ZONES);
        List<StandAssignment> assignments = scheduler.allocate();
        printSummary(tenants, assignments, "Backtracking");
    }

    private static void printSummary(List<Tenant> allTenants, List<StandAssignment> assignments, String algorithmName) {
        int processed = allTenants.size();
        int accepted = assignments.size();
        int rejected = processed - accepted;

        System.out.println("\nJumlah Tenant Diproses : " + processed);
        System.out.println("Jumlah Tenant Diterima : " + accepted);
        System.out.println("Jumlah Tenant Ditolak  : " + rejected);
        
        System.out.println("\nDaftar Tenant yang Diterima :");
        if (assignments.isEmpty()) {
            System.out.println("(Tidak ada tenant yang diterima)");
        } else {
            for (StandAssignment sa : assignments) {
                System.out.println(">> " + sa.getTenant().getName());
            }
        }
    }

    private static void compareResults(List<Tenant> tenants) {
        System.out.println("\n=== KOMPARASI HASIL PENJADWALAN ===");

        long startGreedy = System.nanoTime();
        GreedyScheduler greedy = new GreedyScheduler(TOTAL_ZONES);
        List<StandAssignment> greedyResult = greedy.schedule(tenants);
        long timeGreedy = System.nanoTime() - startGreedy;

        long startBacktrack = System.nanoTime();
        BacktrackingScheduler backtrack = new BacktrackingScheduler(tenants, TOTAL_ZONES);
        List<StandAssignment> backtrackResult = backtrack.allocate();
        long timeBacktrack = System.nanoTime() - startBacktrack;

        System.out.println("\n[1] ALOKASI JADWAL STAND - GREEDY");
        printAssignments(greedyResult);

        System.out.println("\n[2] ALOKASI JADWAL STAND - BACKTRACKING");
        printAssignments(backtrackResult);

        System.out.println("\n=== ANALISA WAKTU EKSEKUSI ===");
        System.out.printf("Greedy       : %d Tenant diterima (%.4f ms)\n", greedyResult.size(), (timeGreedy / 1000000.0));
        System.out.printf("Backtracking : %d Tenant diterima (%.4f ms)\n", backtrackResult.size(), (timeBacktrack / 1000000.0));
    }

    private static void runExtremeCase() {
        System.out.println("\n=== UJI KASUS DATASET EKSTREM ===");
        List<Tenant> extremeTenants = ExtremeData.getTenants();
        
        System.out.println("\n[Data Pendaftar Tenant]");
        printTenantsAsTable(extremeTenants);

        GreedyScheduler greedy = new GreedyScheduler(TOTAL_ZONES);
        List<StandAssignment> greedyResult = greedy.schedule(extremeTenants);

        BacktrackingScheduler backtrack = new BacktrackingScheduler(extremeTenants, TOTAL_ZONES);
        List<StandAssignment> backtrackResult = backtrack.allocate();

        System.out.println("\n[Hasil Penjadwalan: ALGORITMA GREEDY]");
        System.out.println("Total Dijadwalkan : " + greedyResult.size() + " Tenant\n");
        printAssignments(greedyResult);

        System.out.println("\n[Hasil Penjadwalan: ALGORITMA BACKTRACKING]");
        System.out.println("Total Dijadwalkan : " + backtrackResult.size() + " Tenant\n");
        printAssignments(backtrackResult);
    }

    private static void printAssignments(List<StandAssignment> assignments) {
        if (assignments.isEmpty()) {
            System.out.println("Tidak ada tenant yang berhasil dijadwalkan.");
            return;
        }

        for (int zone = 1; zone <= TOTAL_ZONES; zone++) {
            System.out.println(">> STAND " + zone + " <<");
            boolean found = false;
            for (StandAssignment sa : assignments) {
                if (sa.getZoneNumber() == zone) {
                    Tenant t = sa.getTenant();
                    System.out.println("• " + t.getName() + " (" + t.getCategory() + ")");
                    System.out.println("  " + formatTime(t.getStartTime()) + " s.d " + formatTime(t.getEndTime()));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("   (Kosong)");
            }
            System.out.println(); // Spasi antar stand
        }
    }
}