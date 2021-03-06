# REST API Documentation

## Definitions

### Item

```js
{
  "name": [string],
  "description": [string],
  "price": [number],
  "tags": [string[]],
  "imageURL": [string]
}
```

This is used e.g. for the `item` attribute in the upload input.

### Item document in the items collection

```js
{
  "itemID": [string],
  "userID": [string],
  "name": [string],
  "description": [string],
  "price": [number],
  "tags": [string[]],
  "imageURL": [string],
  "status": [status],
  "dateBought": [number] // null if never bought
}
``` 

### status
i.e. Item status

A string with a value of `"listed"`, `"pending"`, `"sold"`, `"hidden"`, or `"illegal"`.

`"hidden"` is different from `"sold"` in that items with the `"hidden"` status can be listed again, while items with the `"sold"` status cannot.

`"illegal"` is returned when an item upload is unsuccessful (not only for the cause of it being illegal to upload it).

This attribute is on each item in the *Items* collection.

### filter

A string containing JSON that the server will use to query the *Items* collection.

e.g.: `"{"price": {"$lt": 100}}"` to query for items with `price` less than $100.

## Endponumbers

**POST** Authentication
`/login`

The login tries a username and password through NTLM authentication on Exeter Outlook mail server outlook.com/owa/exeter.edu and tests for a GET 200 response, which means that the credentials are valid.

An access token is generated and saved in the user's document in *Users*, and this token given in the output of this function for the client to save in local storage. This will be necessary for any POST requests made using the user's id.

Input: Body
```js
{
  "userID": [string],
  "password": [string]
}
```

Output: JSON
```js
{
  "success": [boolean],
  "userToken": [string]
}
```

**POST** Logging Out
`/logout`

Log the user out by their username.

The user's user token must be provided to verify that it is actually the user.

When the client receives "true" for "success", it should remove the userToken from the localStorage and redirect the browser to the homepage.

Input: Body
```js
{
  "userID": [string],
  "userToken": [string]
}
```

Output: JSON
```js
{
  "success": [boolean]
}
```

**POST** upload
`/upload`

The upload functionality allows users to input an item through POST, which will eventually be stored as a document in the *Items* collection in the database. This allows new products to be listed for sale.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "userID" : [string],
  "userToken": [string],
  "item": [Item]
}
```

Output: JSON
```js
{
  "status": [status], // "listed" if successful; else, "illegal"
}
```

**GET** getItems
`/list-item?itemID=[number]`

The list item by ID page retrieves item information for a specific item ID, from the database to display to the user. This will be used when buyers click on a specific item for more information.

Input: query string

Output: JSON
```js
{
  "item": [Item]
}
```

**GET** buyableItems
`/buyable-items`

The all-items function lists all of the items in the database that are available to be bought. The function retrieves all items and displays them in a grid form. This is to be used to display items in the "buy" tab of the dashboard.

This only displays items with `status == "listed"`.

Input: query status == "listed"

Output: JSON
```js
{
  "items" : [Item[]]
}
```

**GET** Search Items
`/search?q=[string]&filter=[filter]`

The search-items function will list all the items related to the given text search and that match the parameters given in the `filter` attribute. This will be used for our search bar on the products for sale page.

Input: body
```js
{
  "searchby" : [string]
}

Output: JSON
```js
{
  "items" : [Item[]]
}
```

**POST** Buy
`/buy`

The buy function allows a user to purchase an item, activating a time window for the seller to confirm the purchase. The username of the current session buys the item and the buyer's bought items count increases.

The date the request is received is stored with the item in *Items*.

If the buyer's current `pendingPurchases` count is greater than the limit, `status` will given as `"listed"`.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "itemID" : [string],
  "buyerID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "status" : [status], // "pending", if successful; else, "listed"
  "numPendingPurchases" : [number],
  "dateBought" : [string] // set to the milliseconds since the unix epoch at the time the request is receieved; null if status=="listed"
}
```

**POST** Sell
`/sell`

This function marks an item as sold in the database after the seller has confirmed that they have sold the item to the buyer.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "itemID" : [string],
  "buyerID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "status" : [status], // "sold", if successful; else, "pending"
}
```

**POST** Cancel Pending Sale
`/cancel-pending-sale`

This function cancels the pending sale of an item if the buyer decides that he no numberer wants to buy the item.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "itemID" : [string],
  "buyerID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "status" : [status], // the status of the item now; "listed", if successful; else, "pending"
  "numPendingPurchases" : [number]
}
```

**POST** Refuse Sale
`/refuse-sale`

This function cancels the pending sale of an item if the seller chooses that the item will not be sold to the buyer. The function should remove the item from the pending sale database and return it to the active products database.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "itemID" : [string],
  "buyerID" : [string],
  "sellerID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "status" : [status], // the status of the item now; "listed", if successful; else, "pending"
}
```

**POST** Unlist 
`/unlist`

This function cancels the sale of an item if the seller chooses that the item will not be listed anymore. The function should remove the item from the items collection and hide it until the buyer wants to relist it.

The user's user token must be provided to verify that it is actually the user.

Input: Body
```js
{
  "itemID" : [string],
  "sellerID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "status" : [status], // "hidden", if successful; else, "listed"
}
```

<!-- let's keep this as a manual database edit for now, just because we haven't made any system for admins -->
<!--**POST** Ban
`/ban`

This function bans a user who has been acting inappropriately. The function should toggle the "banned" boolean associated to a username to True.

Input: JSON
```js
{
  "userID" : [string]
}
```

Output: JSON
```js
{
  "ban" : [string] â€œbannedâ€�
}
```-->

**GET** My Items
`/my-items`

This function generates a list of a user's personal items that he or she is selling or is buying, retrieving the desired items from the database.

(This uses the same method(s) as `/search` internally)

Input: Body
```js
{
  "userID" : [string],
  "userToken" : [string]
}
```

Output: JSON
```js
{
  "items" : [Item[]]
}
```

**POST** Refresh Items
`/refresh`

This function runs asynchronously, which means that this function will be run periodically. Refresh items goes through all items with status 'pending'. If the item has been in the database for more than three days, or whatever the period of inactivity is, then the item should be cancelled because the seller could be inactive.


Input: Body
```js
{
 
}
```

Output: JSON
```js
{
  "status" : [status], // "successful", if successful; else, "failed"
}
