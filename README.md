# stats

## Prerequisites:
1. Tomcat: Download tomcat from https://tomcat.apache.org/download-90.cgi
2. Maven: Download maven from https://maven.apache.org/download.cgi

## How to build:
1. Navigate to project directory stats/
2. Run "mvn clean install"

## How to run:
1. Navigate to tomcat/bin/ directory. Execute ./startup.sh
2. Copy stats.war file from stats/target and paste in under tomcat/webapps/
3. Tomcat will unpack the war and load the application
4. If your tomcat is running at port 8080, stats endpoints should be accessible from: http://localhost:8080/stats/flows
