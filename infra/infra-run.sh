docker compose -f "infra-docker-compose.yml" --env-file ./env/.env down;
docker network create inner;
docker volume create 	mysql-data;
docker volume create 	mysql-conf;
docker volume create 	mysql-salt-data;
docker volume create 	mysql-salt-conf;
docker volume create 	redis-data;
docker volume create 	redis-conf;
docker compose -f "infra-docker-compose.yml" --env-file ./env/.env up -d;
