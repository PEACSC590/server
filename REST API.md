# REST API Documentation

**POST** Authentication
`/login`

Input: Body
```json
{
  "login": "...",
  "password": "..."
}
```

Output: JSON
```json
{
  "valid": [boolean],
  "username": "..."
}
```


**POST** upload
`/upload`

Input: Body
```json
{
  "sellerID" : "...",
  "description" : "...",
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}

Output: JSON
```json
{
  "success": [boolean],
  "redirect": "...url"
}
```

**GET** List Item
`/list-item:itemID`

Input: query

```
{
  “itemID” : [int],
}
```

Output: JSON
```json
{
  "sellerID" : "...",
  "description" : "...",
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}
```

**GET** List Items
`/list-items`
Use for Search. Assumes “status” = null.

Input: Body
```json
{
  "sellerID" : "...",
  "price" : [double],
  "tags" : [array[string]],
}
```

Output: JSON
```json
{
  "items" : [array[item]]
}
```

**POST** Buy
`/buy`

Input: JSON
```json
{
  “itemID" : [int],
}
```

Output: JSON
```json
{
  “status” : [string] “pending”,
}
```

**POST** Sell
`/sell`

Input: JSON
```json
{
  “itemID" : [int],
}
```

Output: JSON
```json
{
  “status” : [string] “sold”,
}
```
