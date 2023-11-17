all: clean_lib build_lib compile build run

compile_lib:
	mvn -f ../ChaseLogic/pom.xml compile

build_lib:
	mvn -f ../ChaseLogic/pom.xml install

clean_lib:
	mvn -f ../ChaseLogic/pom.xml clean

compile:
	mvn -f pom.xml compile

build:
	mvn -f pom.xml package

run:
	java -jar target/Game-1.0-SNAPSHOT-jar-with-dependencies.jar --enemiesCount=10 --wallsCount=100 --size=30 --profile=production

clean: clean_lib
	rm -rf target