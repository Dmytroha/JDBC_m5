WITH project_duration AS (
    SELECT ID, DATEDIFF('MONTH', START_DATE, FINISH_DATE) AS MONTH_COUNT
    FROM project
) SELECT project.ID, client.NAME, project_duration.MONTH_COUNT FROM project JOIN client ON project.CLIENT_ID = client.ID JOIN project_duration ON project.ID = project_duration.ID WHERE project_duration.MONTH_COUNT = (SELECT MAX(MONTH_COUNT) FROM project_duration);
