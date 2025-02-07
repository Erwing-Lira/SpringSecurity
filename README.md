# Spring Security Basic

## Information
User: `root`
Password: `root`
Role: `ADMIN`
Endpoints: `/v1/hello`, `/v1/hello-secured`, `/v1/hello-secured-two`

User: `root1`
Password: `root1`
Role: `USER`
Endpoints: `/v1/hello`, `/v1/hello-secured-two`

## Curls
Remember change the user in the Authentication
#### GET hello
```
curl --location 'http://localhost:8080/v1/hello' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### GET hello-secured
```
curl --location 'http://localhost:8080/v1/hello-secured' \
--header 'Authorization: Basic cm9vdDpyb290'
```
#### GET hello-secured-two
```
curl --location 'http://localhost:8080/v1/hello-secured-two' \
--header 'Authorization: Basic cm9vdDpyb290'
```
