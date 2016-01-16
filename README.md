# auction
Android application for selling and buying stuff.

# API docs

POST api/users
- Supports optional fields like `firstName`, `lastName`, `email` etc.

```
{
    "username": "pesho",
    "password": "testtest"
}
```

GET api/categories
- Returns all available categories on the server
- The products array contains only ids of products
- Requires auth

```
[
  {
    "_id": "5699ccb82307b58816f64b27",
    "name": "Cars",
    "products": []
  },
  {
    "_id": "5699ccb82307b58816f64b2a",
    "name": "Books",
    "products": []
  },
  {
    "_id": "5699ccb82307b58816f64b29",
    "name": "Pets",
    "products": []
  }
]
```

POST api/categories/:id/products
- Adds a product to the category with id `:id`.
- Requires auth

Request
```
{
    "name": "JavaScript: The Good parts",
    "price": 200,
    "currentPrice": 10,
    "imageUrl": "http://akamaicovers.oreilly.com/images/9780596517748/cat.gif",
    "description": "Quite new, just read it and don't want it anymore"
}
```
Response
```
{
  "name": "JavaScript: The Good parts",
  "price": 200,
  "currentPrice": 10,
  "imageUrl": "http://akamaicovers.oreilly.com/images/9780596517748/cat.gif",
  "description": "Quite new, just read it and don't want it anymore",
  "postedBy": "pesho",
  "categoryId": "5699ccb82307b58816f64b2a",
  "_id": "5699cf913543a9ff16237cb4",
  "bids": [],
  "postedDate": "2016-01-16T05:05:21.239Z"
}
```

GET api/categories/:id/products
- Returns an array with detailed product info for the given category
- Requires auth

```
{
  "categoryId": "5699ccb82307b58816f64b2a",
  "products": [
    {
      "_id": "5699cf913543a9ff16237cb4",
      "name": "JavaScript: The Good parts",
      "price": 200,
      "currentPrice": 10,
      "imageUrl": "http://akamaicovers.oreilly.com/images/9780596517748/cat.gif",
      "description": "Quite new, just read it and don't want it anymore",
      "postedBy": "pesho",
      "categoryId": "5699ccb82307b58816f64b2a",
      "bids": [],
      "postedDate": "2016-01-16T05:05:21.239Z"
    }
  ]
}
```

GET api/products/:id
- Returns detailed info about the product with id `:id`
- Requires auth

```
{
  "_id": "5699cf913543a9ff16237cb4",
  "name": "JavaScript: The Good parts",
  "price": 200,
  "currentPrice": 10,
  "imageUrl": "http://akamaicovers.oreilly.com/images/9780596517748/cat.gif",
  "description": "Quite new, just read it and don't want it anymore",
  "postedBy": "pesho",
  "categoryId": "5699ccb82307b58816f64b2a",
  "bids": [],
  "postedDate": "2016-01-16T05:05:21.239Z"
}
```

GET api/users/:username/products
- Returns an array of the products `:username` posted
- Requires auth

```
[
  {
    "_id": "5699cf913543a9ff16237cb4",
    "name": "JavaScript: The Good parts",
    "price": 200,
    "currentPrice": 10,
    "imageUrl": "http://akamaicovers.oreilly.com/images/9780596517748/cat.gif",
    "description": "Quite new, just read it and don't want it anymore",
    "postedBy": "pesho",
    "categoryId": "5699ccb82307b58816f64b2a",
    "bids": [],
    "postedDate": "2016-01-16T05:05:21.239Z"
  }
]
```

POST api/products/:id
- Posts a bid on a product
- Requires auth

Response (for auth with 'pesho')

```
{
  "error": "You cannot bid on products that you posted."
}
```

Response for other user

```
{
  "amount": 30,
  "productId": "5699cf913543a9ff16237cb4",
  "bidBy": "gosho",
  "_id": "5699d65ed128be701ac2cb52",
  "bidDate": "2016-01-16T05:34:22.187Z"
}
```

GET api/products/:id/bids
- Returns an array of detailed bid info about a product
- Requires auth

```
[
  {
    "_id": "5699d65ed128be701ac2cb52",
    "amount": 30,
    "productId": "5699cf913543a9ff16237cb4",
    "bidBy": "gosho",
    "bidDate": "2016-01-16T05:34:22.187Z"
  }
]
```
