# ExpenseTracker

REQUIREMENTS 
============
Java,
Intellij IDEA (Any IDE),
Postman,
PostgreSQL,
PostgresDocker,


DOCKER
======
-> Install docker desktop
-> Pull an image
	
	docker pull postgres

-> Run DB

	docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d -p 5432:5432 postgres
	docker container ls

	/* For the first time */	
	docker cp expensetracker_db.sql postgresdb:/
	docker container exec -it postgresdb bash
	docker container exec -it postgresdb psql -U postgres
	\connect expensetrackerdb
	select * from et_users;

	/* For the succesive time */
	run Postgres in docker
	docker container exec -it postgresdb psql -U postgres
	\connect expensetrackerdb
  
  
