# Spring Security Basic using Database

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

## Curls
Remember change the user in the Authentication
#### GET
```
curl --location 'http://localhost:8080/v1/auth/get' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### POST
```
curl --location --request POST 'http://localhost:8080/v1/auth/post' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### PUT
```
curl --location --request PUT 'http://localhost:8080/v1/auth/put' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### DELETE
```
curl --location --request DELETE 'http://localhost:8080/v1/auth/delete' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### PATCH
```
curl --location --request PATCH 'http://localhost:8080/v1/auth/patch' \
--header 'Authorization: Basic cm9vdDpyb290'
```

## Information in Database
<img width="650" alt="Screenshot 2025-02-07 at 4 06 41 p m" src="https://github.com/user-attachments/assets/61ec0c97-f040-415d-8ad6-5292401c863a" />
