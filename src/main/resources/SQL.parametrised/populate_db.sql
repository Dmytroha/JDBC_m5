
INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES (?, ?, ?, ?);

INSERT INTO client (NAME) VALUES (?);

INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES (?, ?, ?);

INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES (?, ?);


