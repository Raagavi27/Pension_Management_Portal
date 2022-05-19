FROM tomcat:9.0.62-jdk11-openjdk-slim-buster
RUN sed -i 's/port="8080"/port="8000"/' ${CATALINA_HOME}/conf/server.xml
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]