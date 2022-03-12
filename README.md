# Simple JobSeekers Api using Jhipster Microservice

## Development & Runing The Application

To start your application in the dev profile, run:
```
./mvnw
```

## JHipster Registry
JHipster Registry it is an administration server, with dashboards to monitor and manage applications (localhost:8761)
```
docker-compose -f coffeesys/src/main/docker/jhipster-registry.yml up
```

## Supported Request Methods:
```HTTP
Method: GET
URL: http://localhost:8083/api/jsdata
```

```HTTP
Method: POST
URL: http://localhost:8083/api/jsdata
Body:
    {
        "gender": "Enter Gender Type Here",
        "region": "Enter The Region Here",
        "city": "Enter The City Here",
        "age": "Enter The Age Here, as Double!",
        "degree": "Enter The Degree Here",
        "major": "Enter The Major Here",
        "inistitute": "Enter The Inistitute Here"
    }
```

```HTTP
Method: DELETE
URL: http://localhost:8083/api/jsdata/{id} #id of the recored to delete
```
