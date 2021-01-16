# E-Reimbursement

A web application to submit a reimbursement form.

## Getting Started

Clone this project
```
git clone https://github.com/stefanusivan79/reimbursement-online.git
```


### Prerequisites

You need to install

```
MySQL
MinIO
Java 1.8
NodeJS with npm
```

### Installing


Front End

```
npm install
npm start
```

Back End

Set environment variable
```
MYSQL_HOST : [host]
MYSQL_PORT : [port]
MYSQL_DB : [database name]
MYSQL_USERNAME : [username]
MYSQL_PASSWORD : [password]

MINIO_HOST : [host]
MINIO_PORT : [port]
MINIO_SECURE : [secure]
MINIO_ACCESS_KEY : [access key]
MINIO_SECRET_KEY : [secret key]

SLACK_WEBHOOK_URL : [webhook url]
```

then run

```
mvn spring-boot:run
```


## Built With

* [Spring Boot](https://spring.io/projects/spring-boot)
* [React](https://reactjs.org/)
* [Jasper Reports](https://community.jaspersoft.com/project/jasperreports-library)

![Form Reimburse](https://res.cloudinary.com/silver-blaze/image/upload/v1610803893/ereimbursement/form_reimburse.png)

![List Reimburse](https://res.cloudinary.com/silver-blaze/image/upload/v1610804138/ereimbursement/list_reimburse.png)

![Dashboard](https://res.cloudinary.com/silver-blaze/image/upload/v1610803893/ereimbursement/dashboard.png)

![Report](https://res.cloudinary.com/silver-blaze/image/upload/v1610803893/ereimbursement/report.png)
