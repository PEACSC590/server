# PEAbay

Install [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), [Maven](https://maven.apache.org/download.cgi), and the [Heroku Toolbelt](https://toolbelt.heroku.com).

## Set up Server Locally

### Import the Repo into Eclipse
1. **In command line:** `git clone https://github.com/PEACSC590/server.git` - Clone the respository
2. **In Eclipse:** *File > Import...*
3. *Existing Maven Projects*
4. Browse for parent directory of the project; select the project (Eclipse should automatically connect the project to git as well as install the project dependencies through Maven)

### Set up Local Environmental Variables
1. **In command line:** `heroku config:get -a peabay MONGODB_DEV_URI -s >> .env` - Get the URI for connecting to the database using the *dev* account (this saves it in a .env file that Heroku Local can use) 

## Run the Server Locally

1. **In Eclipse:** Select the project in Package Explorer 
2. *Run as... Maven install*
3. **In command line:** In the project directory: `heroku local:start` - Start Heroku Local, which emulates Heroku running the code, on your own computer

## Deploy to Heroku
Push to branch **production**; Heroku deploys this branch automatically

1. **In command line:** In the project directory: `git checkout production` - Switch to the **production** branch
2. `git merge master` - Merge **master** into the current branch (**production**)
3. `git checkout master` - Switch back to **master**
