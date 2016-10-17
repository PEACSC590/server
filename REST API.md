# REST API Documentation

**POST** Authentication
`/login`

The login tries a username and password through NTLM authentication on Exeter Outlook mail server outlook.com/owa/exeter.edu and tests for a GET 200 response, which means that the credentials are valid. Only the username is taken as output.

Input: Body
```json
{
  "login": [string],
  "password": [string]
}
```

Output: JSON
```json
{
  "valid": [boolean],
  "userID": [int]
}
```

**POST** upload
`/upload`

The upload functionality allows users to input an item through POST, which will eventually be stored in the Item entity of the database. This allows new products to be listed for sale.

Input: Body
```json
{
  "userID" : [int],
  "description" : [string],
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}
```

Output: JSON
```json
{
  "success": [boolean],
  "redirect": [url]
}
```

**GET** List Item
`/list-item:itemID`

The list-item by ID page retrieves item information for a specific item ID, from the database to display to the user. This will be used when buyers click on a specific item for more information.

Input: query

```
{
  "itemID” : [int]
}
```

Output: JSON
```json
{
  "userID" : [int],
  "description" : [string],
  "price" : [double],
  "tags" : [array[string]],
  "image" : [jpg, bmp, png]
}
```

**GET** All Items
`/list-items`

The all-items function lists all of the items in the database that are available to be bought. The function retrieves all items and displays them in a grid form. This will be used in the "buy" tab of our dashboard.


Input: Body
```json
{
  
}
```

Output: JSON
```json
{
  "items" : [array[item]]
}
```




**GET** List Items
`/list-items`

The list-items function will list all the items with a specific parameter, retrieving all the items and displaying them similarly to all-items. This will be used for our sort filters used by buyers as they browse the items.

Input: Body
```json
{
  "userID" : [int],
  "price" : [double],
  "tags" : [array[string]]
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

The buy function allows a user to purchase an item they have previously seen, activating a time window for the seller to confirm the purchase. The username of the current session buys the item and this item increases the count of a buyer's limit on pending purchases.

Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int]
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

This function marks an item as sold in the database after the seller has confirmed that they have sold the item to the buyer.

Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int]
}
```

Output: JSON
```json
{
  "status" : [string] “sold”,
  "buyerItemCount" : [int],
  "counter" : [int]
}
```

**POST** Cancel Pending Sale
`/cancel-pending-sale`

This function cancels the pending sale of an item if the seller chooses that the item will not be sold to the buyer or if the counter has risen to 3+ days. The function should remove the item from the pending sale database and return it to the active products database.

Input: JSON
```json
{
  "itemID" : [int],
  "userID" : [int]
}
```

Output: JSON
```json
{
  "success" : [boolean],
  "buyerItemCount" : [int],
  "counter" : [int],
  "status" : [string] null
}
```

**POST** Unlist 
`/unlist`

This function cancels the  sale of an item if the seller chooses that the item will not be sold anymore. The function should remove the item from the sale database and return it hide it unless the buyer wants to relist it.

Input: JSON
```json
{
  "itemID" : [int]
}
```

Output: JSON
```json
{
  "success" : [boolean],
  "status" : [string] sold
}
```

**POST** Ban
`/ban`

This function bans a user who has been acting inappropriately. The function should toggle the "banned" boolean associated to a username to True.

Input: JSON
```json
{
  "userID" : [int]
}
```

Output: JSON
```json
{
  "ban" : [string] “banned”
}
```

**GET** My Items
`/my-items`

This function generates a list of a user's personal items that he or she is selling or is buying, retrieving the desired items from the database.

Input: Body
```json
{
  "userID" : "..."
}
```

Output: JSON
```json
{
  "items" : [array[item]]
}
```
