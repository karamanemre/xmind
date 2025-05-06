# XMind Projesi

Bu proje, PostgreSQL ve Elasticsearch ile entegre edilen temel bir Spring Boot uygulamasını içerir. Ayrıca, uygulama başlatıldığında varsayılan kullanıcıların (admin ve user) oluşturulmasını sağlayan bir `CommandLineRunner` bileşeni bulunur.

## Docker Yapılandırması

Sağlanan `docker-compose.yml` dosyası, PostgreSQL ve Elasticsearch olmak üzere iki hizmeti yapılandırır.

### PostgreSQL Hizmeti
- **Görüntü**: `postgres:16`
- **Kullanıcı Adı**: `xmind_user`
- **Şifre**: `xmind_password`
- **Veritabanı Adı**: `xmind_demo`
- **Portlar**: `5432:5432` portu hariciye açılır.

### Elasticsearch Hizmeti
- **Görüntü**: `docker.elastic.co/elasticsearch/elasticsearch:7.16.1`
- **Kullanıcı Adı**: `elastic_user`
- **Şifre**: `elastic_password_123`
- **Portlar**: `9200:9200` ve `9300:9300` portları hariciye açılır.

### Default Kullanıcı Bilgileri

- **Admin Kullanıcı**:
    - E-posta: `admin@admin.com`
    - Şifre: `123456`
    - Rol: `ADMIN`

- **Normal Kullanıcı**:
    - E-posta: `user@user.com`
    - Şifre: `123456`
    - Rol: `USER`

## Projeyi Çalıştırmak

1. **Hizmetleri Başlatın**: Docker Compose kullanarak PostgreSQL ve Elasticsearch hizmetlerini başlatmak için aşağıdaki komutu çalıştırın:
   ```bash
   docker-compose up -d
