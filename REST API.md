# REST API Documentation

**POST** Authentication
`/login`
The login tries a username and password through NTLM authentication on Exeter Outlook mail server outlook.com/owa/exeter.edu and tests for a GET 200 response, which means that the credentials are valid. Only the username is taken as output.
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

The upload functionality allows users to input an item through POST, which will eventually be stored in the Item entity of the database.
Input: Body
```json
{
  "userID" : "...",
  "description" : "...",
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}
```

Output: JSON
```json
{
  "success": [boolean],
  "redirect": "...url"
}
```

**GET** List Item
`/list-item:itemID`

The list-item by ID page retrieves item information for a specific item ID, from the database to display to the user.

Input: query

```
{
  "itemID” : [int],
}
```

Output: JSON
```json
{
  "userID" : "...",
  "description" : "...",
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}
```

**GET** List Items
`/list-items`
Use for Search. Assumes “status” = null.

The list-items function lists all of the items in the database that are available to be bought. The function retrieves all items and displays them in a grid form.

Input: Body
```json
{
  "userID" : "...",
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

The buy function allows a user to purchase an item they have previously seen. The username of the current session buys the item and this item increases the count of a buyer's limit on pending purchases.
Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int],
}
```

Output: JSON
```json
{
  "status" : [string] “pending”,
  "buyerItemCount" : [int],
  "counter" : [int]
}
```

**POST** Sell
`/sell`


Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int],
}
```

Output: JSON
```json
{
  "status" : [string] “sold”,
  "buyerItemCount" : [int]
}
```

**POST** Cancel Sale
`/cancel-sale`

This function cancels the sale of an item if the seller chooses that the item will not be sold anymore. The function should remove the item from the item database.
Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int],
}
```

Output: JSON
```json
{
  "success" : [boolean]
}
```

**POST** Ban
`/ban`

This function bans a user who has been acting inappropriately. The function should toggle the "banned" boolean associated to a username to True.
Input: JSON
```json
{
  "userID" : [int],
}
```

Output: JSON
```json
{
  "ban" : [string] “banned”,
}
```

**GET** My Items
`/my-items`

This function generates a list of a user's personal items that he or she is selling or is buying, retrieving the desired items from the database.
Input: Body
```json
{
  "userID" : "...",
}
```

Output: JSON
```json
{
  "items" : [array[item]]
}
```
