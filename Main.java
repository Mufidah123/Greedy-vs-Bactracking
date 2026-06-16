import algorithms.BacktrackingScheduler;
import algorithms.GreedyScheduler;
import data.SampleData;
import java.util.List;
import java.util.Scanner;
import models.StandAssignment;
import models.Tenant;
import utils.TimeHelper;

public class Main {

    private static final int TOTAL_ZONES = 5;

    private static boolean greedyExecuted = false;
    private static boolean backtrackingExecuted = false;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Tenant> allTenants = SampleData.getTenants();

        List<StandAssignment> greedyResult = null;
        List<StandAssignment> backtrackingResult = null;

        int choice;

        do {

            System.out.println("\n=================================================");
            System.out.println("      SISTEM PENJADWALAN BAZAR RESTORAN");
            System.out.println("=================================================");

            System.out.println("Jumlah Zona      : " + TOTAL_ZONES);
            System.out.println("Jumlah Pendaftar : " + allTenants.size());

            System.out.println("\n1. Tampilkan Data Pendaftar");
            System.out.println("2. Jalankan Greedy Scheduler");
            System.out.println("3. Jalankan Backtracking Scheduler");
            System.out.println("4. Bandingkan Hasil");
            System.out.println("5. Keluar");

            System.out.print("\nPilihan : ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    showApplicants(allTenants);
                    waitEnter(scanner);
                    break;

                case 2:
                    greedyResult = runGreedy(allTenants);
                    greedyExecuted = true;
                    waitEnter(scanner);
                    break;

                case 3:
                    backtrackingResult = runBacktracking(allTenants);
                    backtrackingExecuted = true;
                    waitEnter(scanner);
                    break;

                case 4:
                    showComparison(
                            greedyResult,
                            backtrackingResult
                    );
                    waitEnter(scanner);
                    break;

                case 5:
                    System.out.println("\nTerima kasih!");
                    break;

                default:
                    System.out.println("\nPilihan tidak valid!");
                    waitEnter(scanner);
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void showApplicants(
            List<Tenant> tenants
    ) {

        System.out.println("\n=================================================");
        System.out.println("                  DATA PENDAFTAR");
        System.out.println("=================================================");

        System.out.printf(
                "%-3s %-25s %-18s %-12s %-12s%n",
                "ID",
                "Nama Tenant",
                "Kategori",
                "Mulai",
                "Selesai"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Tenant tenant : tenants) {

            System.out.printf(
                    "%-3d %-25s %-18s %-12s %-12s%n",
                    tenant.getId(),
                    tenant.getName(),
                    tenant.getCategory(),
                    TimeHelper.format(
                            tenant.getStartTime()
                    ),
                    TimeHelper.format(
                            tenant.getEndTime()
                    )
            );
        }
    }

    private static List<StandAssignment> runGreedy(
            List<Tenant> tenants
    ) {

        System.out.println("\n=================================================");
        System.out.println("                GREEDY SCHEDULER");
        System.out.println("=================================================");

        GreedyScheduler greedy =
                new GreedyScheduler();

        List<StandAssignment> result =
                greedy.schedule(tenants);

        int accepted = result.size();
        int rejected = tenants.size() - accepted;

        System.out.println("Tenant Diproses : " + tenants.size());
        System.out.println("Tenant Diterima : " + accepted);
        System.out.println("Tenant Ditolak  : " + rejected);

        System.out.println("\nDaftar Tenant Diterima:");

        for (StandAssignment assignment : result) {

            System.out.println(
                    "- "
                            + assignment.getTenant()
                                    .getName()
            );
        }

        System.out.println("\nGreedy selesai.");

        return result;
    }

    private static List<StandAssignment> runBacktracking(
            List<Tenant> tenants
    ) {

        System.out.println("\n=================================================");
        System.out.println("             BACKTRACKING SCHEDULER");
        System.out.println("=================================================");

        BacktrackingScheduler scheduler =
                new BacktrackingScheduler(
                        tenants,
                        TOTAL_ZONES
                );

        List<StandAssignment> result =
                scheduler.allocate();

        if (result == null) {

            System.out.println("\nTidak ditemukan solusi.");

            return null;
        }

        int accepted = result.size();
        int rejected = tenants.size() - accepted;

        System.out.println("Tenant Diproses : " + tenants.size());
        System.out.println("Tenant Diterima : " + accepted);
        System.out.println("Tenant Ditolak  : " + rejected);

        System.out.println("\nDaftar Tenant Diterima:");

        for (StandAssignment assignment : result) {

            System.out.println(
                    "- "
                            + assignment.getTenant()
                                    .getName()
            );
        }

        System.out.println("\nBacktracking selesai.");

        return result;
    }

    private static void showComparison(
            List<StandAssignment> greedyResult,
            List<StandAssignment> backtrackingResult
    ) {

        System.out.println("\n=================================================");
        System.out.println("                PERBANDINGAN HASIL");
        System.out.println("=================================================");

        if (!greedyExecuted) {

            System.out.println(
                    "\nGreedy belum dijalankan!"
            );

            return;
        }

        if (!backtrackingExecuted) {

            System.out.println(
                    "\nBacktracking belum dijalankan!"
            );

            return;
        }

        System.out.println("\n========== HASIL GREEDY ==========");

        printZoneResult(greedyResult);

        System.out.println("\n======= HASIL BACKTRACKING =======");

        if (backtrackingResult == null) {

            System.out.println(
                    "\nTidak ditemukan solusi."
            );

            return;
        }

        printZoneResult(backtrackingResult);

        System.out.println("\n========== RINGKASAN ==========");

        System.out.println(
                "Greedy       : "
                        + greedyResult.size()
                        + " tenant diterima"
        );

        System.out.println(
                "Backtracking : "
                        + backtrackingResult.size()
                        + " tenant diterima"
        );

        if (backtrackingResult.size()
                > greedyResult.size()) {

            System.out.println(
                    "\nBacktracking menghasilkan solusi lebih baik."
            );

        } else if (backtrackingResult.size()
                < greedyResult.size()) {

            System.out.println(
                    "\nGreedy menghasilkan solusi lebih baik."
            );

        } else {

            System.out.println(
                    "\nKeduanya menghasilkan jumlah tenant yang sama."
            );
        }
    }

    private static void printZoneResult(
                List<StandAssignment> assignments
        ) {

        for (int zone = 1;
                zone <= TOTAL_ZONES;
                zone++) {

                System.out.println(
                        "\nZona "
                                + (char) ('A' + zone - 1)
                );

                System.out.println(
                        "--------------------------------------------------------------------------------"
                );

                System.out.printf(
                        "%-25s %-18s %-20s %-20s%n",
                        "Tenant",
                        "Kategori",
                        "Mulai",
                        "Selesai"
                );

                System.out.println(
                        "--------------------------------------------------------------------------------"
                );

                boolean found = false;

                for (StandAssignment assignment : assignments) {

                if (assignment.getZoneNumber() == zone) {

                        Tenant tenant =
                                assignment.getTenant();

                        System.out.printf(
                                "%-25s %-18s %-20s %-20s%n",
                                tenant.getName(),
                                tenant.getCategory(),
                                TimeHelper.format(
                                        tenant.getStartTime()
                                ),
                                TimeHelper.format(
                                        tenant.getEndTime()
                                )
                        );

                        found = true;
                }
                }

                if (!found) {

                System.out.println(
                        "(Kosong)"
                );
                }
        }
        }

    private static void waitEnter(
            Scanner scanner
    ) {

        System.out.print(
                "\nTekan ENTER untuk kembali ke menu..."
        );

        scanner.nextLine();
        scanner.nextLine();
    }
}