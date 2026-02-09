# E-Shop Application

**Advanced Programming - Modul 1: Coding Standards**

## Deskripsi 

E-Shop adalah aplikasi web sederhana untuk manajemen produk yang dibangun menggunakan Spring Boot 3.5.10 dan Java 21. Aplikasi ini mendemonstrasikan operasi CRUD (Create, Read, Update, Delete) dengan menerapkan prinsip clean code dan praktik secure coding.

## Fitur

- **Tambah Produk**: Menambahkan produk baru dengan nama dan kuantitas
- **Lihat Daftar Produk**: Menampilkan semua produk dalam format tabel
- **Edit Produk**: Mengubah informasi produk yang sudah ada
- **Hapus Produk**: Menghapus produk dari daftar
- **Bootstrap UI**: Antarmuka pengguna yang responsif dan modern

## Teknologi yang Digunakan

- **Java**: 21 
- **Spring Boot**: 3.5.10
- **Build Tool**: Gradle 8.14.4
- **Template Engine**: Thymeleaf
- **UI Framework**: Bootstrap
- **Annotations**: Lombok 

## Cara Menjalankan

1. Clone repository ini
2. Masuk ke direktori proyek
3. Jalankan aplikasi:
4. Buka browser: `http://localhost:8080/product/list`

## Reflection: Penerapan Clean Code dan Secure Coding

### 1. Prinsip Clean Code yang Sudah Diterapkan

#### Penamaan yang Bermakna
Saya menggunakan nama-nama yang jelas dan deskriptif untuk kelas, method, dan variabel. Misalnya, `ProductController` langsung menunjukkan bahwa kelas ini mengatur request terkait produk, bukan nama generik seperti `Manager` atau `Handler`. Method seperti `findById()` juga lebih mudah dipahami daripada `get()` atau `retrieve()`. Begitu juga dengan nama variabel seperti `productId`, `productName`, dan `productQuantity` yang self-explanatory sehingga orang lain bisa langsung paham tanpa perlu baca dokumentasi.

#### Single Responsibility Principle (SRP)
Setiap kelas dalam proyek ini punya tanggung jawab yang spesifik. Kelas `Product` hanya berisi struktur data produk tanpa logika bisnis apapun. `ProductRepository` fokus pada operasi penyimpanan data seperti tambah, cari, update, dan hapus. `ProductService` mengurus logika bisnis, sementara `ProductController` hanya handle HTTP request dan response. Pemisahan ini bikin kode lebih mudah di-maintain karena kalau ada perubahan di satu area, kita tahu persis harus ubah di kelas mana.

#### Function yang Kecil dan Fokus
Method-method yang saya buat relatif pendek dan melakukan satu hal saja. Contohnya method `create()` hanya menambahkan produk, `findAll()` hanya mengambil semua produk, dan seterusnya. Tidak ada method yang melakukan terlalu banyak hal sekaligus yang bisa bikin bingung.

#### DRY (Don't Repeat Yourself)
Saya menghindari duplikasi kode dengan menggunakan layer service yang bisa dipanggil dari berbagai controller. Logika bisnis tidak tersebar di berbagai tempat, tapi terpusat di service layer. Ini juga memudahkan kalau nanti ada perubahan logika, cukup ubah di satu tempat.

### 2. Secure Coding Practices yang Sudah Diterapkan

#### Validasi Input di Frontend
Di form HTML, saya menggunakan atribut `required` untuk memastikan user tidak bisa submit form kosong. Untuk input quantity, ada validasi `min="0"` supaya user tidak bisa input angka negatif. Meskipun ini bukan security yang sempurna, setidaknya bisa mencegah input yang jelas-jelas salah.

#### Generate ID di Server-Side
Product ID di-generate menggunakan UUID di server, bukan dari input user. Ini penting supaya user tidak bisa manipulasi ID atau membuat ID yang bentrok dengan produk lain. User tidak punya kontrol atas ID yang dibuat sistem.

#### HTTP Method yang Tepat
Untuk operasi delete, saya menggunakan POST method bukan GET. Ini penting karena GET seharusnya hanya untuk membaca data, bukan mengubah state. Kalau pakai GET untuk delete, ada risiko CSRF (Cross-Site Request Forgery) dimana link jahat bisa menghapus data tanpa sepengetahuan user.

#### Konfirmasi untuk Aksi Destruktif
Sebelum menghapus produk, ada dialog konfirmasi JavaScript yang muncul. Ini mencegah user tidak sengaja menghapus data penting hanya karena salah klik.

#### Dependency Injection
Saya menggunakan `@Autowired` untuk inject dependency, bukan instantiate langsung dengan keyword `new`. Ini membuat kode lebih loosely coupled dan lebih mudah untuk testing karena dependency bisa di-mock.

### 3. Masalah yang Ditemukan dan Cara Perbaikannya

#### Tidak Ada Validasi di Backend
Masalahnya sekarang, validasi hanya ada di frontend (HTML). Ini berbahaya karena user yang paham teknis bisa bypass validasi frontend dengan tools seperti Postman atau curl, lalu mengirim data yang invalid langsung ke server. 

Solusinya, tambahkan validasi di service layer

#### Tidak Ada Error Handling
Kalau user coba edit atau delete produk yang tidak ada (misalnya ID salah atau produk sudah dihapus), aplikasi bisa crash dengan NullPointerException. Ini pengalaman user yang buruk dan juga bisa expose informasi sistem yang seharusnya tidak terlihat.

Lebih baik lagi kalau ada halaman error khusus atau flash message yang memberitahu user bahwa produk tidak ditemukan.

#### Penyimpanan Data yang Tidak Persisten
Saat ini data disimpan di ArrayList yang berarti semua data hilang setiap kali aplikasi di-restart. Ini mungkin tidak masalah untuk pembelajaran atau demo, tapi untuk aplikasi production jelas tidak bisa diterima.

Solusi jangka panjangnya pakai database seperti PostgreSQL atau MySQL dengan Spring Data JPA. Untuk development, bisa pakai H2 database yang embedded.

#### Tidak Ada Logging
Aplikasi saat ini tidak punya logging sama sekali. Kalau terjadi error atau bug di production, akan sangat susah untuk debugging karena tidak ada jejak apa yang terjadi.

Logging membantu monitoring aplikasi dan troubleshooting ketika ada masalah.

#### Magic Strings
Di controller, ada beberapa string yang hardcoded seperti `"redirect:list"` atau `"CreateProduct"`. Kalau nanti nama view berubah, harus cari satu-satu di semua tempat yang menggunakan string itu.

Lebih baik define sebagai konstanta, dengan cara ini kalau ada perubahan cukup ubah di satu tempat.

### 4. Kesimpulan

Dari pengalaman mengerjakan modul ini, saya belajar bahwa clean code bukan hanya soal kode yang "berjalan", tapi kode yang mudah dibaca, dipahami, dan di-maintain oleh orang lain (atau diri sendiri di masa depan). Prinsip seperti SRP dan DRY memang terdengar sederhana tapi impact-nya besar dalam jangka panjang.

Untuk secure coding, saya jadi lebih aware bahwa validasi frontend saja tidak cukup. User dengan niat jahat atau bahkan user yang tidak sengaja bisa menyebabkan masalah kalau server tidak punya validasi sendiri. Error handling juga penting bukan hanya untuk user experience tapi juga untuk mencegah information disclosure.

Yang paling penting, saya menyadari bahwa kode yang saya tulis sekarang masih jauh dari sempurna. Masih banyak improvement yang bisa dilakukan seperti menambahkan unit test, menggunakan database yang proper, dan implementasi logging yang baik. Tapi setidaknya foundation-nya sudah benar dengan menerapkan clean code principles dan basic secure coding practices.

## Author

Jenisa Bunga - 2406431334
Fakultas Ilmu Komputer, Universitas Indonesia


## 📅 Timeline Pengembangan

- **Modul 1**: Implementasi CRUD dengan clean code principles