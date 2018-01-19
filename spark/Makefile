build: compile
	docker build . -t sdtdensimag/spark --no-cache

compile:
	export SBT_OPTS="-Xms512M -Xmx3536M -Xss1M  -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -XX:MaxPermSize=724M"; cd app; sbt assembly

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

test:
	./run_docker.sh Test arg0 arg1
