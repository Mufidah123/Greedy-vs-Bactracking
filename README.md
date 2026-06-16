# Greedy-vs-Bactracking
## Judul:
**Sistem Penjadwalan Otomatis Tenant Bazar Restoran Selama Seminggu Menggunakan Algoritma Greedy dan Backtracking**

## Masalah:
Restoran mengadakan bazar UMKM selama satu minggu (168 jam). Jumlah tenant yang mendaftar melebihi kapasitas stand yang tersedia. Selain masalah bentrokan waktu sewa, terdapat kendala persaingan pasar di mana tenant dengan kategori produk yang sama tidak boleh beroperasi secara bersamaan demi mencegah kanibalisasi penjualan.

## Kendala & Aturan Bisnis
* kapasitas stand terbatas yaitu berjumlah 4 stand.
* Satu stand fisik hanya boleh diisi oleh satu tenant pada satu waktu (tidak boleh ada dua tenant di stand yang sama secara bersamaan).
* Tenant dengan kategori produk yang sama tidak boleh beroperasi secara bersamaan di stand mana pun dalam area bazar.

### Peran Greedy (Activity Selection)
Algoritma Greedy digunakan untuk menyusun jadwal sewa dengan heuristik Earliest Finish Time. Tujuannya adalah memilih sebanyak mungkin tenant yang durasi sewanya tidak bertabrakan, memberikan solusi yang sangat cepat (efisien secara waktu) untuk pendaftar dalam jumlah banyak.

### Peran Backtracking (Graph Coloring)
Algoritma Backtracking digunakan untuk mencari solusi optimal global dalam alokasi stand. Algoritma ini mengeksplorasi seluruh kemungkinan kombinasi penempatan untuk memastikan jumlah tenant yang diterima maksimal, dengan mempertimbangkan kendala bentrokan waktu dan konflik kategori produk.

## Dataset / Data Dummy:

![Sample Data](data/Dataset.png)

_Pada versi prototype ini, data pendaftaran tenant masih menggunakan data dummy yang disimpan di SampleData.java. Data mencakup nama tenant, kategori produk, jam mulai, dan jam selesai (berbasis skala 168 jam selama satu minggu). Pada implementasi nyata, data tersebut dapat berasal dari form pendaftaran atau database_