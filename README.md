## Summary

TUI DX Backend technical Test v2

The base project uses lombok, so you have to install it. You can use the following guide https://www.baeldung.com/lombok-ide

# Miquel Montoro Pilotes API by Saul Martinez

This API allows creating Clients, Create and Update Orders in order to allow the great Miquel Montoro
to prepare the right ammount of his delicius Pilotes.


## Table of Contents
* [Before Start](#Before Start)
* [Creating Clients](#Creating Clients)
* [Creating Orders](#Creating Orders)
* [Update Orders](#Update Orders)
* [Search Orders by Client](#Search Orders by Client)

## Before Start

This project uses Mapstructs in order to generate DTO to entity mapper so before try to run the project
a `mvn clean package` is needed. this is algo needed to generate .jar file deployed in the docker image.

## Creating Clients

Now that you have the API running the fist step is create a client.

Request:
```
{
"firstName": "string",
"lastName": "string",
"telephone": "string" (9 digit number)
}
```
Response:

Created Client
```
{
"clientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", (new client ID)
"firstName": "string",
"lastName": "string",
"telephone": "string"
}
```

## Creating Orders

Now that we have a client ID we can create orders.

Request:
```
{
  "clientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", (Here we need the existing client ID)
  "orderId": "", (this should be empty)
  "number": "string",
  "pilotes": 0, (Pilotes Orders can be 5,10 or 15 not other values are accepted)
  "orderTotal": 0,
  "orderTimestamp": "2021-10-26T18:47:11.839Z",
  "deliveryAddress": {
    "addressId": "", (this could be empty or have an existing adress ID)
    "street": "string",
    "postcode": "12345", (5 digit value)
    "city": "string",
    "country": "string"
  }
}
```

Response:

Created Order
```
{
"orderId": "702c9fc2-aed6-46b9-b934-e40ec327f469", (New Order ID)
"number": "string",
"pilotes": 5,
"orderTotal": 0,
"orderTimestamp": "2021-10-26T18:52:12.024+00:00",
"clientId": {
    "clientId": "b1e20e9e-c9f9-4be6-92e3-94bae4d28252",
    "firstName": "string",
    "lastName": "string",
    "telephone": "123456789"
},
"deliveryAddress": {
    "addressId": "1cdebb6a-67e4-423d-8471-75046c069e50",
    "street": "string",
    "postcode": "12345",
    "city": "string",
    "country": "string"
    }
}
```
## Update Orders

Orders can be updated 5 minutes after has been created.

Request:
```
{
  "clientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", (Here we need the existing client ID)
  "orderId": "702c9fc2-aed6-46b9-b934-e40ec327f469", (here the order ID is needed)
  "number": "string",
  "pilotes": 10, (Pilotes Orders can be 5,10 or 15 not other values are accepted)
  "orderTotal": 1000,
  "orderTimestamp": "2021-10-26T18:47:11.839Z",
  "deliveryAddress": {
    "addressId": "1cdebb6a-67e4-423d-8471-75046c069e50", (this could be empty or have an existing adress ID)
    "street": "string",
    "postcode": "67890", (5 digit value)
    "city": "string",
    "country": "string"
  }
}
```
Response
```
{
"orderId": "702c9fc2-aed6-46b9-b934-e40ec327f469",
"number": "string",
"pilotes": 10,
"orderTotal": 100,
"orderTimestamp": "2021-10-26T19:19:12.644+00:00",
"clientId": {
    "clientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "firstName": "string",
    "lastName": "string",
    "telephone": "123456789"
},
"deliveryAddress": {
    "addressId": "1cdebb6a-67e4-423d-8471-75046c069e50",
    "street": "string",
    "postcode": "67890",
    "city": "string",
    "country": "string"
}
}
```

## Search Orders by Client
the search of orders by client look for any order that contains complete or partially a
clients `fistName`, `lastName` or `telephone`.

Request:
Client data to search for orders
```
{
"firstName": "string",
"lastName": "string",
"telephone": "string"
}
```

Response:

List of Orders that match with the search criteria.
```
[
    {
        "orderId": "7f714237-4a70-4a7b-ba37-2bbfcf7967b8",
        "number": "string",
        "pilotes": 10,
        "orderTotal": 100,
        "orderTimestamp": "2021-10-26T19:19:12.644+00:00",
        "clientId": {
            "clientId": "46c0593b-ec4d-4680-938c-d8be56f501a7",
            "firstName": "string",
            "lastName": "string",
            "telephone": "123456789"
        },
        "deliveryAddress": {
            "addressId": "f55a8c25-f1f5-433f-ab24-c4c2978b62d8",
            "street": "string",
            "postcode": "67890",
            "city": "string",
            "country": "string"
        }
    }
]
```