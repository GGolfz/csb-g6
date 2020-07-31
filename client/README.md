# 06 Account Management
## Frontend (Vue)
- `/`
 > Home page to go to another page
- `/View`
 > View account page list the account that user own 
 > - Have Deposit, Withdraw, Tranfer, Payment
 > - See more button use to link to transaction page of each account
- `/Open`
 > Open account page for opening account
- `/Close`
 > Close account page for closing account
## Backend (Servlet)
### Get Account
> Get all accounts that user own
- **URL** : `/ShowAccountServlet`
- **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `None`
- **Body**:`None`
- **Functions Involved**:
 -	`getOwner(ownerID)`
 -	`getAccount(ownerID)`
 -	`getalreadydep(accountID)`
-	**Note**:`ownerID` get from session
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
 "id"  :  <String>,
 "name"  :  <String>,
 "lastname"  :  <String>,
 "accounts"  :  [
 {
 "accountID"  :  <String>,
 "accountType"  :  <String>,
 "balance"  :  <String>,
 "currency"  :  <String>,
 "fee"  :  <String>,
 "limit"  :  <String>,
 "interest"  :  <String>,
 "fixed"  :  <String>,
 "alreadydep"  :  <String>,
 "remain" : <String>
 }
 ]
 }
 ```
 **Note** : `accounts is JSON Array`
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  
 ### Get Owner
 > Get owner information
 - **URL** : `/GetOwnerServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `None`
- **Body**:`None`
- **Functions Involved**:
 -	`getOwnerInfo(ownerID)`
-	**Note**:`ownerID` get from session
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
 "id"  :  <String>,
 "citizenID"  :  <String>,
 "passportID"  :  <String>,
 "ownerName"  :  <String>,
 "ownerLastname"  :  <String>,
 "birthdate"  :  <String>,
 "gender"  :  <String>,
 "email"  :  <String>,
 "tel"  :  <String>,
 "occupation"  :  <String>
 }
 ```
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  
 ### Get Address
 > Get owner address
 - **URL** : `/GetAddressServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `None`
- **Body**:`None`
- **Functions Involved**:
 -	`getOwnerAddress(ownerID)`
-	**Note**:`ownerID` get from session
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
 "id"  :  <String>,
 "ownerName"  :  <String>,
 "ownerLastname"  :  <String>,
 "address"  : 
 {
 "addressNum"  :  <String>,
 "villageNum"  :  <String>,
 "building"  :  <String>,
 "soi"  :  <String>,
 "road"  :  <String>,
 "subDistrict"  :  <String>,
 "district"  :  <String>,
 "province"  :  <String>,
 "zipcode"  :  <String>
 }
 }
 ```
 **Note** : `address is JSONObject` 
  
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  
 ### Open Account
 > For open account
 - **URL** : `/OpenAccountServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
 {
 "id"  :  <String>,
 "citizenID"  :  <String>,
 "passportID"  :<String>,
 "ownerName"  :<String>,
 "ownerLastname"  :<String>,
 "birthdate"  :<String>,
 "email"  :<String>,
 "occupation"  :<String>,
 "tel"  :<String>,
 "gender"  :<String>,
 "addressNum"  :<String>,
 "villageNum"  :<String>,
 "soi"  :<String>,
 "road"  :<String>,
 "district"  :<String>,
 "subDistrict"  :<String>,
 "building"  :<String>>,
 "province"  :<String>,
 "zipcode"  :<String>,
 "type"  :<String>,
 "branch"  :<String>,
 "currency"  :<String>,
 "fixed"  :<String>,
 "month"  :<String>,
 "address"  :<String>
 }
 ```
- **Body**: `None`
- **Functions Involved**:
 -	``addAccount(accountID,id,accountType
,accountName,balance,type_id,
paymentlimit,currency)``
 - ``updateCustomerInfo(id,citizenID,
passportID,ownerName,ownerLastname,
birthdate,gender,email,tel,
occupation,address)``
 - ``getAccountAmount(accountType)``
#### Success Response
- **Status code** : `201`
- **Response body** :
````json
{
"accountID" : <String>,
"accountType" : <String>
}
````
  
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  
 ### Get Branches
 > Get all bank branch
 - **URL** : `/GetBranchsServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `None`
- **Body**:`None`
- **Functions Involved**:
 -	`getBranch()`
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
 "branch"  : 
 [
 {
 "id"  :  <String>,
 "name":  <String>
 }
 ]
 }
 ```
**Note** : `branch is JSON Array`
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
 ### Change Payment Limit
 > Change payment limit
 - **URL** : `/ChangeLimitServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `Required`
 ```json
 "accountID"  :  <String>,
 "limit"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
 -	`changeLimit(accountID,limit)`
#### Success Response
- **Status code** : `201`
- **Response body** : `None`
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  
 ### Close Account 
 > Close Account 
 - **URL** : `/CloseAccountServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `Required`
 ```json
 "accountID"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
 -	`closeAccount(accountID)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{
"accountID" : <String>,
"balance" : <String>
}
````
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
 ### Deposit
 > Deposit
 - **URL** : `/DepositServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
 "owner_id"  : <String>,
 "account_id"  :  <String>,
 "amount"  : <String>,
 "note"  : <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `deposit(owner_id,account_id, amount, note)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{
"update" : <String>
}
````
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
 ### Get Payee Name
 > Get Payee Name
 - **URL** : `/GetPayeeNameServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `Required`
 ```json
 "payee_id"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
 -	`getPayeeName(payeeID)`
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
 "payeeName":  <String>
 }
 ```
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`

---
 ### Transfer
 > Transfer
 - **URL** : `/TransferServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
 "owner_id"  : <String>,
 "account_id"  :  <String>,
 "payee_id"  : <String>,
 "amount"  : <String>,
 "note"  : <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `transfer(owner_id, account_id, payee_id, amount, note)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{
"update" : <String>
}
````
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
  ### Withdraw
 > Withdraw
 - **URL** : `/WithdrawServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
 "owner_id"  : <String>,
 "account_id"  :  <String>,
 "amount"  : <String>,
 "note"  : <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `withdraw(owner_id, account_id, amount, note)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{
"update" : <String>
}
````
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
  ### Add Favorite Account
 > Add Favorite Account
 - **URL** : `/AddFavoriteServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
 "ownerID"  : <String>,
 "id"  :  <String>,
 "name"  : <String>,
 "color"  : <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `addFavorite(owner_id,accId,name,color)`
- `getFavorite(owner_id)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{ 
"content"  :  [
 {
"favid" : <String>,
"name"  :  <String>,
"color"  :  <String>,
"id"  :  <String>,
"text" : <String>
}
]
}
````
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
### Delete Favorite Account
 >  Delete Favorite Account
 - **URL** : `/DeleteFavoriteServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
"favid"  :  <String>,
"ownerId"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `deleteFavorite(favid)`
- `getFavorite(onwer_id)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{ 
"content"  :  [
 {
"favid" : <String>,
"name"  :  <String>,
"color"  :  <String>,
"id"  :  <String>,
"text" : <String>
}
]
}
````

#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
### Edit Favorite Account
 >  Edit Favorite Account
 - **URL** : `/EditFavoriteServlet`
 - **Method** : `POST`
- **Auth required**: `YES`
- **Parameters**: `required`
 ```json
"owner_id"  :  <String>,
"favid" : <String>,
"id"  :  <String>,
"name"  :  <String>,
"color"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
- `updateFavorite(favid,owner_id,id,name,color)`
- `getFavorite(owner_id)`
#### Success Response
- **Status code** : `201`
- **Response body** : 
````json
{ 
"content"  :  [
 {
"favid" : <String>,
"name"  :  <String>,
"color"  :  <String>,
"id"  :  <String>,
"text" : <String>
}
]
}
````
#### Error Response 
- **Status code** : `400`
- **Response body** : `none`
 ---
 ### Get Favorite Account
 > Get Favorite Account
 - **URL** : `/GetFavoriteServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `required`
```json
"onwer_id"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
 -	`getFavorite(owner_id)`
#### Success Response
- **Status code** : `200`
- **Response body** :
 ```json
 {
"content"  :  [
 {
"favid" : <String>,
"name"  :  <String>,
"color"  :  <String>,
"id"  :  <String>,
"text" : <String>
}
]
 }
 ```
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
  ### Get Transaction History
 > Get Transaction History
 - **URL** : `/GetTransactionHistoryServlet`
 - **Method** : `GET`
- **Auth required**: `YES`
- **Parameters**: `required`
```json
"accountID"  :  <String>
 ```
- **Body**:`None`
- **Functions Involved**:
-`getTransactionHistory(accountID)`
#### Success Response
- **Stutus code** : `200`
- **Response body** :
```json
"Date": <String>, 
"currency" : <String>, 
"amount" : <String>, 
"type" : <String>,
"account_id" : <String>,
"owner_id" : <String>,
"payee_id" : <String>,
"note" : <String>
```
#### Error Response 
- **Status code** : `403`
- **Response body** : `none`
 ---
 
## Function (Model)
### config(request,response)
- **Description** : config cors for response
- **Package** : Controller.CORS
### getFavorite(ownerID)
- **Description** : get all favorite from database
- **Package** : Model.QueryModel
### getBranch()
- **Description** : get all branch from database
- **Package** : Model.QueryModel
### getUpdate()
- **Description** : get current time for update data
- **Package** : Model.QueryModel
### getOwner(ownerID)
- **Description** : get owner name and lastname from database
- **Package** : Model.QueryModel
### getAccount(ownerID)
- **Description** : get all account that owner own from database
- **Package** : Model.QueryModel
### getOwnerInfo(ownerID)
- **Description** : get all infomation of owner from database
- **Package** : Model.QueryModel
### getOwnerAddress(ownerID)
- **Description** : get owner address from database
- **Package** : Model.QueryModel
### getAccountAmount(accountType)
- **Description** : get number of account in type that you sent via parameter
- **Package** : Model.QueryModel
### updateCustomerInfo(`customerInfo`)
- **Description** : update customerInfo to database
- **Package** : Model.QueryModel
### changeLimit(accountID,limit)
- **Description** : change payment limit for account
- **Package** : Model.QueryModel
### addAccount(`accountInfo`)
- **Description** : insert new account to database
- **Package** : Model.QueryModel
### closeAccount(`accountID`)
- **Description** : calculate balance (including interest) set account balance to 0 and account status to be false 
- **Package** : Model.QueryModel
### getPayeeName(`payee_id`)
- **Description** : get payee name and lastname from database
- **Package** : Model.QueryModel
### getRemain(`accountID`)
- **Description** : get remain from database
- **Package** : Model.QueryModel
### getalreadydep(`accountID`)
- **Description** : get the deposit in this month for fixed deposit account
- **Package** : Model.QueryModel
### getcreateTransaction(`transactionInfo`)
- **Description** : crate transaction to database
- **Package** : Model.QueryModel
### deposit(`depositandwithdrawInfo`)
- **Description** : crate deposit transaction in the database
- **Package** : Model.QueryModel
### withdraw(`depositandwithdrawInfo`)
- **Description** : crate withdraw transaction in the database
- **Package** : Model.QueryModel
### transfer(`transferInfo`)
- **Description** : transfer money from account 1 to 2
- **Package** : Model.QueryModel
### checkAccountStatus(`account_id`)
- **Description** : check the status of the account from database
- **Package** : Model.QueryModel
### checkMatchCurrency(`account_id, payee_id`)
- **Description** : check the currency of the account from database
- **Package** : Model.QueryModel


transferInfo :
````json
 "owner_id" : <String>,
 "account_id" : <String>,
 "payee_id" :<String>,
 "amount" :<String>,
 "note" :<String>
````

depositandwithdrawInfo :
````json
 "owner_id" : <String>,
 "account_id" : <String>,
 "amount" :<String>,
 "note" :<String>
````

transactionInfo :
````json
 "owner_id" : <String>,
 "account_id" : <String>,
 "transaction_type" :<String>,
 "amount" :<String>,
 "payee_id" :<String>,
 "note" :<String>,
 "update" :<String>
````

customerInfo :
````json
 "id" : <String>,
 "citizenID" : <String>,
 "passportID" :<String>,
 "ownerName" :<String>,
 "ownerLastname" :<String>,
 "birthdate" :<String>,
 "email" :<String>,
 "occupation" :<String>,
 "tel" :<String>,
 "gender" :<String>,
 "address" :<String>
````

accountInfo :
````json
 "accountID" : <String>,
 "ownerID" : <String>,
 "accountType" :<String>,
 "name" :<String>,
 "balance" :<String>,
 "accountTypeID" :<String>,
 "payment_limit" :<String>,
 "occupation" :<String>,
 "currency" :<String>
````