#------------------#
# Git Docker image #
#------------------#
FROM alpine/git as clone 

# Set the working directory
WORKDIR /app

# Add Json lastest SHA commit to check update for the clone cmd
ADD https://api.github.com/repos/Kybox/KyLibrary2/git/refs/heads/master /app

# Clone the Github repository
RUN git clone -b master https://github.com/Kybox/KyLibrary2.git

# -------------------#
# Maven Docker image #
#--------------------#
FROM maven:3.6-jdk-8-alpine as builder

# Set the working directory
WORKDIR /app

# Copy all data from the cloned repository to the working directory
COPY --from=clone /app/KyLibrary2 /app

# Build the project
RUN mvn clean package

#---------------------#
# Tomcat Docker image #
#---------------------#
FROM tomcat:9.0-jre8

# Add JNDI for the database connection
COPY tomcat/conf/context.xml /usr/local/tomcat/conf/context.xml
COPY tomcat/conf/server.xml /usr/local/tomcat/conf/server.xml

# Add a user access to the tomcat manager GUI
COPY tomcat/conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

# Copy all WAR files to the Tomcat webapps directory
COPY --from=builder /app/client/target/KyLibrary.war /usr/local/tomcat/webapps/KyLibrary.war
COPY --from=builder /app/webservice/target/KyLibrary#WebService.war /usr/local/tomcat/webapps/KyLibrary#WebService.war
COPY --from=builder /app/batch/target/KyLibrary#WebService#Batch.war /usr/local/tomcat/webapps/KyLibrary#WebService#Batch.war
