#基础镜像通过java8来的
FROM openjdk:8
#挂载卷是临时目录
VOLUME /tmp
ADD ./bgman-0.0.1.jar xlFitnessBooking.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" >> /etc/timezone
RUN mkdir -p /var/bgman/upload
#暴露端口
EXPOSE 8088
# 执行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+08", "-jar","/xlFitnessBooking.jar"]
MAINTAINER YuTao LiDuoShi XuLei
