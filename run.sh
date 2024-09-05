docker create network private
docker create network public
docker create network es-net
docker compose up -d

chmod +x cicd/create_gr_replica.sh