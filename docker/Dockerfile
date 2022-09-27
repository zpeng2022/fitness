FROM openjdk:8
VOLUME /tmp
ADD ./bgman-0.0.1.jar fitnessBooking.jar
EXPOSE 8088
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fitnessBooking.jar"]
MAINTAINER YuTao LiDuoShi XuLei