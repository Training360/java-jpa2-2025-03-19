# Employees alkalmazás

PostgreSQL indítása Dockerben:

```shell
docker run -d -e POSTGRES_DB=employees -e POSTGRES_USER=employees -e POSTGRES_PASSWORD=employees -p 5432:5432 --name employees-postgres postgres
```

Docker image-be csomagolás:

```shell
docker build -t employees-app .
```

Docker indítása:

```shell
docker run -d -p 8081:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/employees --name employees-app employees-app
```