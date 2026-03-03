# E-Shop Application

## Deskripsi

E-Shop adalah aplikasi web sederhana untuk manajemen produk yang dibangun menggunakan Spring Boot 3.5.10 dan Java 21. Aplikasi ini mendemonstrasikan operasi CRUD (Create, Read, Update, Delete) dengan menerapkan prinsip clean code dan praktik secure coding.

## Fitur

- **Tambah Produk** — Menambahkan produk baru dengan nama dan kuantitas
- **Lihat Daftar Produk** — Menampilkan semua produk dalam format tabel
- **Edit Produk** — Mengubah informasi produk yang sudah ada
- **Hapus Produk** — Menghapus produk dari daftar
- **Bootstrap UI** — Antarmuka pengguna yang responsif dan modern

## Teknologi yang Digunakan

| Teknologi | Versi |
|-----------|-------|
| Java | 21 |
| Spring Boot | 3.5.10 |
| Build Tool | Gradle 8.14.4 |
| Template Engine | Thymeleaf |
| UI Framework | Bootstrap |
| Annotations | Lombok |

## Cara Menjalankan

1. Clone repository ini
2. Masuk ke direktori proyek
3. Jalankan aplikasi
4. Buka browser: `http://localhost:8080/product/list`

---

## Reflection 1 — Clean Code dan Secure Coding

### Prinsip Clean Code yang Sudah Diterapkan

**Penamaan yang Bermakna**

Nama-nama kelas, method, dan variabel dibuat jelas dan deskriptif. Misalnya, `ProductController` langsung menunjukkan bahwa kelas ini mengatur request terkait produk. Method seperti `findById()` lebih mudah dipahami daripada `get()`, dan variabel seperti `productId`, `productName`, serta `productQuantity` bersifat self-explanatory.

**Single Responsibility Principle (SRP)**

Setiap kelas punya tanggung jawab yang spesifik:
- `Product` — berisi struktur data produk tanpa logika bisnis
- `ProductRepository` — fokus pada operasi penyimpanan data
- `ProductService` — mengurus logika bisnis
- `ProductController` — handle HTTP request dan response

**Function yang Kecil dan Fokus**

Method-method dibuat pendek dan melakukan satu hal saja. Contohnya, `create()` hanya menambahkan produk dan `findAll()` hanya mengambil semua produk.

**DRY (Don't Repeat Yourself)**

Logika bisnis tidak tersebar di berbagai tempat, melainkan terpusat di service layer sehingga perubahan cukup dilakukan di satu tempat.

### Secure Coding Practices yang Sudah Diterapkan

**Validasi Input di Frontend**

Form HTML menggunakan atribut `required` dan validasi `min="0"` untuk mencegah input yang jelas-jelas tidak valid.

**Generate ID di Server-Side**

Product ID di-generate menggunakan UUID di server, bukan dari input user, sehingga user tidak bisa memanipulasi ID.

**HTTP Method yang Tepat**

Operasi delete menggunakan POST method, bukan GET, untuk mencegah risiko CSRF.

**Konfirmasi untuk Aksi Destruktif**

Dialog konfirmasi JavaScript muncul sebelum menghapus produk untuk mencegah penghapusan data yang tidak disengaja.

**Dependency Injection**

`@Autowired` digunakan untuk inject dependency sehingga kode lebih loosely coupled dan mudah di-test.

### Masalah yang Ditemukan dan Cara Perbaikannya

**Tidak Ada Validasi di Backend**

Saat ini validasi hanya ada di frontend, sehingga user yang paham teknis bisa bypass validasi dengan tools seperti Postman. Solusinya adalah menambahkan validasi di service layer.

**Tidak Ada Error Handling**

Jika user mencoba edit atau delete produk yang tidak ada, aplikasi bisa crash dengan `NullPointerException`. Solusinya adalah menambahkan halaman error khusus atau flash message yang informatif.

**Penyimpanan Data Tidak Persisten**

Data saat ini disimpan di ArrayList sehingga hilang setiap kali aplikasi di-restart. Solusi jangka panjangnya adalah menggunakan database seperti PostgreSQL atau MySQL dengan Spring Data JPA.

**Tidak Ada Logging**

Tanpa logging, debugging di production sangat sulit. Perlu ditambahkan logging untuk monitoring dan troubleshooting.

**Magic Strings**

String seperti `"redirect:list"` atau `"CreateProduct"` yang hardcoded sebaiknya didefinisikan sebagai konstanta agar perubahan cukup dilakukan di satu tempat.

### Kesimpulan

Clean code bukan hanya soal kode yang "berjalan", tetapi kode yang mudah dibaca, dipahami, dan di-maintain. Validasi frontend saja tidak cukup — server harus memiliki validasi sendiri. Masih banyak improvement yang bisa dilakukan seperti unit test, database yang proper, dan implementasi logging, tetapi foundation-nya sudah benar dengan menerapkan clean code principles dan basic secure coding practices.

---

## Reflection 2 — Unit Testing dan Code Coverage

### Pengalaman Unit Testing

Setelah menulis unit test, saya merasa lebih yakin terhadap kebenaran dan kestabilan aplikasi. Unit testing membantu memahami perilaku setiap method, memaksa berpikir lebih kritis terhadap kemungkinan bug, serta membuat proses refactoring lebih aman.

### Berapa Banyak Unit Test yang Sebaiknya Dibuat?

Tidak ada jumlah pasti. Bergantung pada kompleksitas class, setiap public method sebaiknya memiliki unit test yang mencakup:
- Skenario normal (happy path)
- Edge case atau boundary condition
- Skenario negatif dan penanganan error

Tujuan utama bukan memperbanyak jumlah test, melainkan memastikan seluruh perilaku penting telah terverifikasi.

### Bagaimana Memastikan Unit Test Sudah Cukup?

Code coverage (misalnya menggunakan JaCoCo) dapat membantu menunjukkan bagian kode mana yang sudah diuji. Namun, **100% code coverage tidak berarti kode bebas dari bug**. Selain coverage, pastikan juga:
- Assertion benar-benar memverifikasi perilaku yang diharapkan
- Edge case dan input tidak valid diuji
- Jalur exception ditangani dan diuji
- Test merepresentasikan skenario penggunaan nyata

Kualitas dan relevansi test jauh lebih penting daripada sekadar mencapai angka 100%.

### Kebersihan Kode pada Functional Test Suite

Pembuatan functional test suite baru dengan prosedur setup dan instance variable yang sama berpotensi menimbulkan masalah kebersihan kode:

**Potensi Masalah:**
- **Duplikasi Kode (DRY)** — Pengulangan kode setup di banyak test class meningkatkan beban pemeliharaan
- **Menurunnya Maintainability** — Perubahan konfigurasi berisiko menimbulkan inkonsistensi jika tidak semua class diperbarui
- **Pemisahan Tanggung Jawab yang Buruk** — Pencampuran kode infrastruktur test dengan logika pengujian membuat test sulit dipahami

**Saran Perbaikan:**
- **Base Functional Test Class** — Ekstrak logika setup ke abstract base class yang di-extend oleh setiap test suite
- **Page Object Pattern** — Representasikan setiap halaman web sebagai class terpisah yang menyimpan elemen dan aksi halaman
- **Helper/Utility Method** — Pindahkan operasi yang sering digunakan ke helper method agar test lebih ringkas

---

## Reflection 3 — CI/CD

### Code Quality Issues

Awalnya tidak ditemukan masalah signifikan karena kode tutorial sudah cukup rapi. Setelah mencoba menambahkan contoh pelanggaran seperti empty catch block, tools analisis berhasil mendeteksinya sebagai code quality issue. Perbaikan dilakukan dengan menambahkan penanganan error yang tepat di dalam blok catch.

### Evaluasi Implementasi CI/CD

Workflow yang dibuat sudah memenuhi konsep **Continuous Integration** karena setiap push langsung menjalankan build dan test secara otomatis. Proses deployment yang berjalan otomatis setelah build berhasil juga memenuhi konsep **Continuous Deployment**. Implementasi ini masih sederhana tetapi sudah menunjukkan konsep dasar CI/CD dengan baik.

### Deployment

Aplikasi berhasil di-deploy ke **Koyeb** (PaaS):

🌐 **Live URL:** https://narrow-harriett-eshop-module-bbef9481.koyeb.app/product/list

| Konfigurasi | Detail |
|-------------|--------|
| Platform | Koyeb Free Tier |
| Instance | 0.1 vCPU, 512 MB RAM |
| Region | Frankfurt, Germany |
| Builder | Docker |
| Auto-deploy | Enabled from main branch |

---

## Reflection 4 — SOLID Principles

### 1) Prinsip yang Diterapkan

**a. Single Responsibility Principle (SRP)**

Setiap kelas memiliki satu tanggung jawab yang jelas:
- `CarController` — menangani HTTP request dan response
- `CarServiceImpl` — menangani logika bisnis
- `CarRepositoryImpl` — menangani penyimpanan dan pengambilan data

**b. Open/Closed Principle (OCP)**

`CarService` dan `CarRepository` didefinisikan sebagai interface. Sistem dapat dikembangkan tanpa mengubah kode yang sudah ada. Contohnya, mengganti mekanisme penyimpanan dari in-memory ke database cukup dengan membuat implementasi baru dari `CarRepository`.

**c. Liskov Substitution Principle (LSP)**

`CarServiceImpl` mengimplementasikan `CarService`. `CarController` bergantung pada interface `CarService`, sehingga implementasi lain dapat menggantikan `CarServiceImpl` tanpa mengubah perilaku sistem.

**d. Interface Segregation Principle (ISP)**

Interface `CarService` hanya berisi method yang relevan dengan operasi Car: `create`, `findAll`, `findById`, `update`, dan `deleteCarById`. Klien tidak dipaksa bergantung pada method yang tidak digunakan.

**e. Dependency Inversion Principle (DIP)**

Modul tingkat tinggi tidak bergantung pada modul tingkat rendah, keduanya bergantung pada abstraksi:
- `CarController` → bergantung pada `CarService` (interface)
- `CarServiceImpl` → bergantung pada `CarRepository` (interface)

Spring Framework melakukan dependency injection sehingga implementasi dapat diganti tanpa mengubah struktur sistem.

### 2) Keuntungan Menerapkan SOLID

**Lebih Mudah Di-maintain**

Karena setiap kelas memiliki satu tanggung jawab, kesalahan dapat dilokalisasi dengan cepat. Misalnya, masalah pada pengambilan data cukup diperiksa di `CarRepositoryImpl`.

**Lebih Mudah Dikembangkan**

Dengan interface, penambahan fitur atau perubahan implementasi dapat dilakukan tanpa memodifikasi kode yang sudah ada, sehingga mengurangi risiko merusak fungsionalitas yang telah berjalan.

**Lebih Mudah Di-test**

Karena dependensi diarahkan pada abstraksi, unit testing dapat menggunakan mock object tanpa bergantung pada implementasi nyata seperti database.

**Lebih Fleksibel**

Implementasi baru dapat ditambahkan atau diganti tanpa mengubah kelas yang sudah ada.

### 3) Kerugian Tidak Menerapkan SOLID

**Sulit Di-maintain**

Jika satu kelas menangani controller, logika bisnis, dan akses data sekaligus, perubahan kecil dapat berdampak besar dan meningkatkan risiko kesalahan.

**Sulit Dikembangkan**

Tanpa interface, kelas yang langsung membuat instansiasi repository akan sulit dikembangkan. Perubahan mekanisme penyimpanan memerlukan modifikasi langsung pada kelas tersebut.

**Sulit Di-test**

Ketergantungan langsung pada implementasi konkret menyulitkan unit testing karena sistem harus dijalankan secara penuh.

**Desain yang Tidak Konsisten**

Tanpa desain yang baik, dapat terjadi ketidakkonsistenan seperti penamaan method yang tidak sesuai konteks, misalnya `deleteProductById` pada modul Car, yang membingungkan pengembang lain.

---

## Author

**Jenisa Bunga** — 2406431334  