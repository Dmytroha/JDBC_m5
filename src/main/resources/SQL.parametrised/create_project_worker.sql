CREATE TABLE IF NOT EXISTS project_worker (
    PROJECT_ID INTEGER NOT NULL,
    WORKER_ID INTEGER NOT NULL,
    PRIMARY KEY (PROJECT_ID, WORKER_ID),
    FOREIGN KEY (PROJECT_ID) REFERENCES project(ID),
    FOREIGN KEY (WORKER_ID) REFERENCES worker(ID)
);
