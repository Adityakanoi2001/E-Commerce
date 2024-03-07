# Mail Microservice API Documentation

## 1. Send Email for Order API

## Endpoint
`POST http://localhost:8000/MailMicroservice/MailMicroserviceController/sendEmailForOrder`

## Description
This API endpoint is used to send an email with a bill receipt for an order.

## Request

- **Method:** POST
- **Headers:**
    - Content-Type: application/json
- **Body:**

  ```json
  {
    "consigneeName": "string",
    "consignorName": "string",
    "customerEmail": "string",
    "customerPhoneNumber": "string",
    "deliveryCharges": 0,
    "deliveryPartnerName": "string",
    "discount": 0,
    "dropAddress": "string",
    "dropDate": "string",
    "dropTime": "string",
    "goodsNature": "string",
    "invoiceDate": "string",
    "invoiceNumber": "string",
    "netFare": 0,
    "pickupAddress": "string",
    "pickupDate": "string",
    "pickupTime": "string",
    "subTotal": 0,
    "totalAmount": 0
  }
