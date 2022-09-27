FROM openjdk:8
VOLUME /tmp
ADD ./bgman-0.0.1.jar fitnessBooking.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" >> /etc/timezone
EXPOSE 8088
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+08", "-jar","/fitnessBooking.jar"]
MAINTAINER YuTao LiDuoShi XuLei