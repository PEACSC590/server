# PEAbay

## Import the Repo into Eclipse
1. **In command line:** `git clone https://github.com/PEACSC590/server.git`
2. **In Eclipse:** *File > Import...*
3. *Existing Maven Projects*
4. Browse for parent directory of the project; select the project

(Eclipse should automatically connect the project to git as well as install the project dependencies through Maven)

## Run the Server Locally
1. **In Eclipse:** (>) (the green arrow)
2. *Run as... Maven install*
3. **In command line:** `heroku local:start`

## Deploy to Heroku
Push to branch **production**; Heroku deploys this branch automatically
