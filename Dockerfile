FROM tomcat:11.0.4-jdk17

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/myapp.war /usr/local/tomcat/webapps/myapp.war

EXPOSE 8080
CMD ["catalina.sh", "run"]