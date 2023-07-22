WITH client_project_count AS (
    SELECT client.NAME, COUNT(project.ID) AS PROJECT_COUNT
    FROM client
    LEFT JOIN project ON client.ID = project.CLIENT_ID
    GROUP BY client.ID
)
SELECT NAME, PROJECT_COUNT
FROM client_project_count
WHERE PROJECT_COUNT = (SELECT MAX(PROJECT_COUNT) FROM client_project_count);



