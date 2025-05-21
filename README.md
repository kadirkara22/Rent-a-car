# Rent-a-Car Yönetim Sistemi

Rent-a-Car Yönetim Sistemi, araç kiralama süreçlerini dijitalleştirerek hem müşterilere hem de yöneticilere kolaylık sağlamayı amaçlayan bir web platformudur. Kullanıcı dostu arayüzü ve güçlü backend altyapısıyla araç envanter yönetimi, rezervasyon, kiralama işlemleri gibi işlevleri tek bir sistemde birleştirir.

## Proje Kapsamı

### 👤 Kullanıcı Tipleri

#### Müşteri:
- Araç arama ve filtreleme
- Araç kiralama
- Kendi kişisel bilgilerini güncelleme
- Kiralama geçmişini görüntüleme

#### Admin:
- Araç ekleme, silme, güncelleme
- Araçları bakıma alma ve fiyat/konum ayarlama
- Rezervasyon yönetimi
- Kullanıcı yönetimi ve kullanıcıların kiralama geçmişlerini görüntüleme

### Fonksiyonellikler

#### Araç Envanter Yönetimi:
- Marka, model, fiyat ve durum bilgileriyle araç kaydetme, güncelleme ve listeleme

#### Rezervasyon Yönetimi:
- Müşteri rezervasyonlarının oluşturulması ve yönetimi

#### Kiralama Süreci:
- Araç teslim alma, kullanım ve teslim etme adımlarının takibi

### Kullanılan Teknolojiler

#### Backend
- Java Spring Boot – API geliştirme ve iş mantığı
- Hibernate ORM – Veritabanı işlemleri
- PostgreSQL – İlişkisel veritabanı
- JWT Authentication – Güvenli kullanıcı oturum yönetimi

#### Frontend
- Next.js – React tabanlı kullanıcı arayüzü
- Axios – API iletişimi
- Tailwind CSS (veya mevcutsa diğer stil kütüphaneleri)

### Güvenlik
- Kullanıcı kimlik doğrulaması JWT (JSON Web Token) ile sağlanmaktadır.
- Yetkilendirme sistemi ile müşteriler yalnızca kendi işlemlerine, adminler ise tüm sisteme erişebilir.

### Projenin Sağladığı Faydalar
✅ Zaman ve İşgücü Tasarrufu: Dijitalleşme sayesinde manuel hatalar en aza iner.  
✅ Kullanıcı Deneyimi: Kullanıcı dostu arayüz ile işlemler kolaylıkla gerçekleştirilir.  
✅ Güvenlik: JWT ile veri ve süreç güvenliği sağlanır.
