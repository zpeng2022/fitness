#!/bin/bash

currentTime=`date +"%Y-%m-%d-%H-%M-%S"`
echo ${currentTime}
docker exec mysql5.7 bash -c "mysqldump -uroot -pfitnessBooking_2022_hdu bgman > /data/bgman.sql"
docker cp mysql5.7:/data/bgman.sql ./backup/bgman_${currentTime}.sql
# docker cp mysql5.7:/data/bgman.sql ./bgman.sql
docker rm -f mysql5.7

docker run -it -d --name mysql5.7 \
	--network=fitnessNet \
	--restart=always \
	-p 13306:3306 \
	-v /root/bgman/mysql/data/:/var/lib/mysql  \
	-v /root/bgman/mysql/logs/:/var/log/mysql  \
	-e 'MYSQL_ROOT_PASSWORD=fitnessBooking_2022_hdu' \
	docker.io/mysql:5.7.29 --character-set-server=utf8 --collation-server=utf8_general_ci --lower_case_table_names=1


sleep 10
docker cp /usr/share/zoneinfo/Asia/Shanghai mysql5.7:/etc/localtime
docker exec mysql5.7  bash -c 'echo "Asia/Shanghai" >> /etc/timezone'
docker cp ./bgman.sql mysql5.7:/tmp/
# docker exec mysql5.7 bash -c "echo source /tmp/bgman.sql|mysql -uroot -pfitnessBooking_2022_hdu"
# this one not test.
# docker exec mysql5.7 bash -c "echo SET GLOBAL general_log = 'ON'; |mysql -uroot -pfitnessBooking_2022_hdu"


sleep 20 
docker restart mysql5.7
