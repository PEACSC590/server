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
  "valid": [boolean]
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
itemID // database's id for desired item
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

**POST** List Items
`/list-items`

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
  "items" : [array[item]]
}
```
