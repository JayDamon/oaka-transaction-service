package contracts

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            request {
                method 'GET'
                url '/v1/transactions/total?year=2017&month=1&transactionTypeId=2&budgetIds=10&budgetIds=11&budgetIds=12&budgetIds=13&budgetIds=14&budgetIds=15&budgetIds=26&budgetIds=16&budgetIds=17&budgetIds=18&budgetIds=27&budgetIds=19&budgetIds=28&budgetIds=29&budgetIds=30'
            }
            response {
                status 200
                body('{"transactionType":"Expense","total":380.51}')
                headers {
                    contentType('application/json')
                }
            }
        },
        Contract.make {
            request {
                method 'GET'
                url '/v1/transactions/total?year=2017&month=1&transactionTypeId=1&budgetIds=20'
            }
            response {
                status 200
                body('{"transactionType":"Income","total":2000.00}')
                headers {
                    contentType('application/json')
                }
            }
        },
        Contract.make {
            request {
                method 'GET'
                url '/v1/transactions/total?year=2017&month=1&transactionTypeId=2&budgetIds=1&budgetIds=2&budgetIds=3&budgetIds=4&budgetIds=5&budgetIds=6&budgetIds=21&budgetIds=22'
            }
            response {
                status 200
                body('{"transactionType":"Expense","total":264.55}')
                headers {
                    contentType('application/json')
                }
            }
        },
        Contract.make {
            request {
                method 'GET'
                url '/v1/transactions/total?year=2017&month=1&transactionTypeId=1&budgetIds=23'
            }
            response {
                status 200
                body('{"transactionType":"Income","total":0}')
                headers {
                    contentType('application/json')
                }
            }
        }
        ,
        Contract.make {
            request {
                method 'GET'
                url '/v1/transactions/total?year=2017&month=1&transactionTypeId=2&budgetIds=24&budgetIds=7&budgetIds=8&budgetIds=9&budgetIds=25'
            }
            response {
                status 200
                body('{"transactionType":"Expense","total":593.45}')
                headers {
                    contentType('application/json')
                }
            }
        }
]
