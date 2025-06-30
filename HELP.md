# Getting Started

Points of interest:
* CustomEntryWrapperUnitTest
* PaymentsControllerIntegrationTest

Post request example

requires postgres docker container

curl -d '{"id":999,"amount":1,"debtorIban":"dbt-iban","creditorIban":"ctr-iban","details":null,"currency":"USD","type":"TYPE_3","bic":"mand-bic"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/payment

