# Aplikasi CRUD Operasi Aritmatika (Spring Boot + PostgreSQL)

Aplikasi web kalkulator dan manajemen riwayat operasi aritmatika (`+`, `*`, `-`, `/`) berbasis **Spring Boot**, **Spring Data JPA**, **Thymeleaf**, dan **PostgreSQL**.

## Fitur Utama
1. **Form Operasi Aritmatika**:
   - Input dua bilangan (`bilangan1` dan `bilangan2`) serta dropdown operator (`+`, `*`, `-`, `/`).
   - Tombol **Result** untuk menghitung hasil kalkulasi dan menyimpannya secara otomatis ke database PostgreSQL.
2. **Tabel Database `hasil_aritmatika`**:
   - Menampilkan kolom `bilangan1`, `operator`, `bilangan2`, dan `result` langsung di bawah form.
   - **Edit**: Mengubah nilai `bilangan1`, `operator`, dan `bilangan2` serta menghitung ulang `result` di database.
   - **Delete**: Menghapus baris data dari tampilan web sekaligus dari database PostgreSQL.
3. **Auto-Create Database & Tabel**:
   - Aplikasi dilengkapi dengan [`DatabaseAutoCreator`](src/main/java/com/aritmatika/demo/config/DatabaseAutoCreator.java) yang otomatis membuat database `aritmatika_db` apabila belum ada di PostgreSQL lokal, serta `spring.jpa.hibernate.ddl-auto=update` yang otomatis membuat tabel `hasil_aritmatika`.

---

## Cara Menjalankan Proyek (Bagi Siapa Saja yang Clone dari GitHub)

1. Pastikan layanan **PostgreSQL** lokal sudah berjalan di komputer (`localhost:5432`).
2. Sesuaikan `username` dan `password` PostgreSQL lokal Anda di file [`src/main/resources/application.properties`](src/main/resources/application.properties):
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/aritmatika_db
   spring.datasource.username=fuad
   spring.datasource.password=LautLaos
   ```
3. Jalankan aplikasi (database `aritmatika_db` dan tabel `hasil_aritmatika` akan dibuat otomatis saat aplikasi dijalankan):
   ```bash
   ./mvnw spring-boot:run
   ```
4. Buka browser dan kunjungi:
   - **URL**: `http://localhost:8080`

---

## Struktur Tabel Database (`hasil_aritmatika`)
| Nama Kolom | Tipe Data | Keterangan |
| :--- | :--- | :--- |
| `id` | `BIGSERIAL` (PK) | Primary Key otomatis |
| `bilangan1` | `DOUBLE PRECISION` | Bilangan pertama |
| `operator` | `VARCHAR(10)` | Simbol operator (`+`, `*`, `-`, `/`) |
| `bilangan2` | `DOUBLE PRECISION` | Bilangan kedua |
| `result` | `DOUBLE PRECISION` | Hasil operasi aritmatika |
