docker compose -f "infra-docker-compose.yml" down;
docker volume rm    mysql-data;
docker volume rm    mysql-conf;
docker volume rm    mysql-salt-data;
docker volume rm    mysql-salt-conf;
docker volume rm    redis-data;
docker volume rm    redis-conf;
