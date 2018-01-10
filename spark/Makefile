build: compile
	docker build . -t sdtdensimag/spark --no-cache

compile:
	cd app; sbt assembly

push:
	docker login -u=sdtdensimag
	docker push sdtdensimag/spark

test:
	./run.sh localhost 9042 Test arg0 arg1
