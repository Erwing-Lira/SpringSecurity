# Spring Security Basic

The token will be 2 minutes of duration before its expiration

## Information
User: `root`
Password: `root`
Role: `ADMIN`
Endpoints: `GET`, `POST`, `PUT`, `DELETE`, `PATCH`

User: `root1`
Password: `root`
Role: `USER`
Endpoints: `GET`, `POST`, `PUT`

User: `root2`
Password: `root`
Role: `INVITED`
Endpoints: `GET`

## Swagger
Visit the html page to see the documentation
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

Image:
<img width="1324" alt="Screenshot 2025-02-24 at 11 40 48 p m" src="https://github.com/user-attachments/assets/0ea12e76-bc7b-4e78-9508-2bdef41564a6" />

## Curls
### Public
Response will contain the acces token (JWT Token) fot the next endpoints in this public endpoints.
Login
```
curl --location 'http://localhost:8080/v1/auth/log-in' \
--header 'Content-Type: application/json' \
--data '{
    "username": "root",
    "password": "root"
}'
```

Sign up
```
curl --location 'http://localhost:8080/v1/auth/sign-up' \
--header 'Content-Type: application/json' \
--data '{
    "username": "root4",
    "password": "root",
    "roleRequest": {
        "rolesName": [
            "ADMIN"
        ]
    }
}'
```

### Private with token
Remember change the JWT_TOKEN value with the token which was in the public responses
#### GET
```
curl --location 'http://localhost:8080/v1/method/get' \
--header 'Authorization: Bearer JWT_TOKEN'
```
#### POST
```
curl --location --request POST 'http://localhost:8080/v1/method/post' \
--header 'Authorization: Bearer JWT_TOKEN'
```
#### PUT
```
curl --location --request PUT 'http://localhost:8080/v1/method/put' \
--header 'Authorization: Bearer JWT_TOKEN'
```
#### DELETE
```
curl --location --request DELETE 'http://localhost:8080/v1/method/delete' \
--header 'Authorization: Bearer JWT_TOKEN'
```
#### PATCH
```
curl --location --request PATCH 'http://localhost:8080/v1/method/patch' \
--header 'Authorization: Bearer JWT_TOKEN'
```
