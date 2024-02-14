build:
	chmod +x ./mvnw
	./mvnw clean install

run: build
	docker-compose up

stop:
	docker-compose down

test: build
	./mvnw test