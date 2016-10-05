# PEAbay

## Import the Repo into Eclipse
1. *`$`* `git clone https://github.com/PEACSC590/server.git`
2. **In Eclipse:**
*File > Import...*
*Existing Maven Projects*
Browse for parent directory of the project; select the project
Eclipse should automatically connect the project to git as well as install the project dependencies through Maven

## Run the Server Locally
(>) (the green arrow)
*Run as... Maven install*
*`$`* `heroku local:start`

## Deploy to Heroku
Push to branch **production**; Heroku deploys this branch automatically
