FROM adoptopenjdk/openjdk8:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=drownedtears_vk_bot
ENV BOT_TOKEN=5419083950:AAF-tOAcS4RhDjaoKP55wfopmPtr1pu-H28
ENV BOT_DB_USERNAME=vktb_db_user
ENV BOT_DB_PASSWORD=vktb_db_password
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-jar", "app.jar"]