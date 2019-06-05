FROM tomcat:9.0.14-jre8
MAINTAINER antazri.xyz

COPY ./webapp/target/webapp.war /usr/local/tomcat/webapps/webapp.war

ADD tomcat-users.xml /usr/local/tomcat/conf/

CMD ["catalina.sh", "run"]
