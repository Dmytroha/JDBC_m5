WITH project_duration AS (
    SELECT ID, (datediff('MONTH', START_DATE, FINISH_DATE)) AS MONTH_COUNT
    FROM project
), project_cost AS (
    SELECT project.ID, client.NAME, SUM(worker.SALARY * project_duration.MONTH_COUNT) AS PRICE
    FROM project
    JOIN client ON project.CLIENT_ID = client.ID
    JOIN project_duration ON project.ID = project_duration.ID
    JOIN project_worker ON project.ID = project_worker.PROJECT_ID
    JOIN worker ON project_worker.WORKER_ID = worker.ID
    GROUP BY project.ID
)
SELECT NAME, PRICE
FROM project_cost
ORDER BY PRICE DESC;

