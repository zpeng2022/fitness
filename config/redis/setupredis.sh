# DO NOT OPEN THE PORT OF REDIS OUTSIDE OF DOCKER
# IT'S DANGEROUS. 
docker rm -f bgman-redis 
docker run -it -d --name bgman-redis \
	--network=fitnessNet \
	--restart=always \
	-v /root/bgman/redis/data/:/data  \
        -v /root/bgman/redis/config:/var/tmp \
	docker.io/redis:latest redis-server /var/tmp/redis.conf --appendonly yes

# -v /root/bgman/redis/config:/etc/redis.conf \ 
# redis-server /etc/redis.conf --appendonly yes

sleep 5
docker cp /usr/share/zoneinfo/Asia/Shanghai bgman-redis:/etc/localtime
docker exec bgman-redis bash -c 'echo "Asia/Shanghai" >> /etc/timezone'

sleep 10 
docker restart bgman-redis 
