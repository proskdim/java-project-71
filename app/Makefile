.PHONY: rebuild clean build run-dist

rebuild: clean install run-dist

run-dist:
	./build/install/app/bin/app

build:
	./gradlew build

install:
	./gradlew installDist

clean:
	./gradlew clean

checkstyle:
	./gradlew checkstyleMain

run:
	./gradlew run
