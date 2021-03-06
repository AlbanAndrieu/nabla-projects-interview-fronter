cls

call setenv.bat
REM OK call mvn org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=jetty9x -Ddatabase=derby > deploy.log 2>&1
REM OK call mvn clean install org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=jetty9x -Ddatabase=derby > deploy.log 2>&1
REM TODO call mvn clean install org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn clean install tomcat7:run -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM OK clean mvn tomcat7:redeploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby -Dtomcat.username=admin -Dtomcat.password=microsoft > deploy.log 2>&1
REM OK call mvn clean tomcat7:redeploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn clean install cargo:undeploy cargo:deploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn org.codehaus.cargo:cargo-maven2-plugin:deployer-deploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM check result at http://192.168.0.29:8280/manager/html
REM OK call mvn jetty:run-war -Psample,run-its -Dserver=jetty9x -Ddatabase=derby -Djetty.port=9090 > deploy.log 2>&1
REM WAS OK call mvn org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its,arq-jbossas-managed -Dserver=jboss7x > deploy.log 2>&1

REM TO TEST mvn clean install -Dlog4j.configuration=log4j.properties -Dlog4j.debug=true -Prun-its,arq-weld-ee-embedded -Dtest=LoanServiceITest > deploy.log 2>&1

REM TODO mvn -U -B clean install -Dsurefire.useFile=false -Psample,jacoco,integration,run-its,arq-jetty-embedded -Djacoco.outputDir=/workspace/users/albandri30/nabla-project-interview-fronter/gui/target -Ddatabase=derby -Dserver=jetty7x -Dwebdriver.chrome.driver=/var/lib/chromedriver -Dwebdriver.base.url=http://localhost:9090 -Dlog4j.configuration=log4j.properties -Dlog4j.debug=true -Darquillian=arq-jetty-embedded -Darquillian.launch=arq-jetty-embedded  > install.log
mvn clean install -Dserver=jetty9x -Dwebdriver.chrome.driver=/var/lib/chromedriver -Dwebdriver.base.url=http://localhost:9090  > install.log

REM build with mvn clean install -Psample,run-its -Dserver=jetty9x
REM OK but not with integration test call java -jar target/dependency/jetty-runner.jar --port 9090 target/*.war

REM OK mvn -U -B clean install -Dsurefire.useFile=false -Psample,jacoco,integration,run-its,arq-weld-ee-embedded -Djacoco.outputDir=/workspace/users/albandri30/nabla-project-interview-fronter/gui/target -Ddatabase=derby -Dserver=jboss7x -Dwebdriver.chrome.driver=/var/lib/chromedriver -Dwebdriver.base.url=http://localhost:8180 -Dlog4j.configuration=log4j.properties -Dlog4j.debug=true -Darquillian=arq-weld-ee-embedded -Darquillian.launch=arq-weld-ee-embedded > install.log 2>&1

REM OK TO RUN GATLING TEST mvn clean install gatling:execute -Pgatling
REM OK TO RUN SERVER mvn clean install org.codehaus.cargo:cargo-maven2-plugin:run -Dserver=jetty9x > deploy.log 2>&1
REM Inside Jconsole --> Remote connection to : service:jmx:rmi://localhost:1099/jndi/rmi://localhost:1099/jmxrmi
REM Inside VisualVM --> Add jmx connection : service:jmx:rmi://127.0.0.1:1099/jndi/rmi://127.0.0.1:1099/jmxrmi
REM do not forget to replace localhost by 127.0.0.1 and to deactivate proxy

pause
