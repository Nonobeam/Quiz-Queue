docker create network private
docker create network public
docker compose up -d

chmod +x cicd/create_gr_replica.sh