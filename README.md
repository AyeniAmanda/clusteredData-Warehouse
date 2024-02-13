# Clustered Data Warehouse

The Clustered Data Warehouse is an application that consists of REST API for handling foreign exchange deals details and persisting them in a database. this application helps users create, retrieve,
and handles foreign exchange information with proper error handling and validations.

# Requirements
To run this application, ensure you have the following prerequisites:

1 Docker installed.

2 Make utility installed.

Clone the repository using the following command:

`git clone https://github.com/AyeniAmanda/clusteredData-Warehouse.git
`

Navigate to the project directory:

`cd clustered-data-warehouse
`

Run the following command in the terminal (with Docker installed):

`docker-compose up or make`

* the application runs on port ```8080```
*swagger url is [swagger link](http://localhost:8080/swagger-ui/index.html)


## API Reference

All URIs are relative to *http://localhost:8080*


### Save Deals

| Method     | HTTP request      | Description | 
|------------|-------------------|-------------|
| [**Post**] | /api/deals/create | save deal   |  

Request Sample

```json
{
  "dealUniqueId": "10-80-aa",
  "orderingCurrencyISO": "USD",
  "toCurrencyISO": "EUR",
  "dealTimestamp": "2024-02-13T15:05:21.354Z",
  "amountInOrderingCurrency": 100.50
}
```

Response Sample

```json
{
    "message": "successful",
    "status": "OK",
    "data": {
        "dealUniqueId": "10-80-aa",
        "orderingCurrencyISO": "USD",
        "toCurrencyISO": "EUR",
        "dealTimestamp": 1707836721.354000000,
        "amountInOrderingCurrency": 100.50
    }
}
```
### Get a Deal by ID

| Method    | HTTP request                 | Description           | 
|-----------|------------------------------|-----------------------|
| [**Get**] |  /api/deals/{dealId} | get a particular deal |

Response Sample -> The endpoint requires the dealUniqueId as a pathVariable

```json
{
    "message": "successful",
    "status": "OK",
    "data": {
        "dealUniqueId": "123456",
        "orderingCurrencyISO": "USD",
        "toCurrencyISO": "EUR",
        "dealTimestamp": null,
        "amountInOrderingCurrency": 100.50
    }
}
```
### Get All Deals


| Method    | HTTP request    | Description   | 
|-----------|-----------------|---------------|
| [**Get**] | /api/deals/     | get all deals |

Response Sample -> The endpoint returns all deals and it allows pagination


```json
{
  "message": "FX deals retrieved successfully",
  "status": "OK",
  "data": [
    {
      "dealUniqueId": "123456",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": null,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "okay",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "clever",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "amanda",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "EFE",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "lool",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "vanessa",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    },
    {
      "dealUniqueId": "10-80-aa",
      "orderingCurrencyISO": "USD",
      "toCurrencyISO": "EUR",
      "dealTimestamp": 1707836721.354000000,
      "amountInOrderingCurrency": 100.50
    }
  ]
}
```