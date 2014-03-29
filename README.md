PrismREST
========

PrismREST provides a simple REST interface to the Database of the Bukkit Plugin ['Prism'](http://discover-prism.com/).

## How to deploy

In order to deploy the *.war package you need a JEE Application Server i.e. [JBoss](https://www.jboss.org/jbossas/downloads/) or [Tomcat](https://tomcat.apache.org/download-80.cgi). 


####For JBoss: 

1. Download & extract JBoss, from now on this folder is called 'JBOSS_HOME'
2. run 'standalone.sh', use 'screen' to run it in background
3. copy the 'PrismREST.war' into JBOSS_HOME/standalone/deployments/
4. JBoss should now auto-deploy the application, if the file 'PrismREST.war.deployed' gets created, JBoss succeded
5. If you run it the first time, ['prismrest.properties'](https://github.com/trichner/PrismREST/blob/master/README.md#config-file) will be generated in JBOSS_HOME/bin/
6. edit the config according to your Prism 'config.yml'
7. redeploy the application
8. Profit

#####Hints:
- JBoss has a webinterface at [localhost:9990](http://localhost:9990), it helps managing your deployments
- if you create the config file before deploying, you won't have to redeploy
- if you run into problems set the log level to 'DEBUG', edit your JBOSS_HOME/standalone/configuration/standalone.xml and set 'root-logger' to 'DEBUG'

## REST API

Base URL: http://example.com/PrismREST/v1

All queries use 'HTTP GET'.

Path      | QueryParameters | Description
----------|------------------|--------------------
`/player`  | none | list of all logged players
`/enum`  | none | list of all possible ActionTypes and DeathTypes
`/kills`  | `type={pve,pvp}` | count of all kills 
`/kills/{player}`  | `type={pve,pvp}` | count of all kills for one player 
`/deaths`  | `type=<deathtype>` | count of all deaths, find possible types at `/enum`
`/deaths/{player}`  | `type=<deathtype>` | count of all deaths for one player (Hint: ':' is '%3A' in a URL)
`/scoreboard/kills`  | `type={pvp,pve}` `limit=<int>` | scoreboard with kills per player
`/block-place`  | none | count of all blocks ever placed
`/block-place/{player}`  | none | count of all blocks ever placed by player
`/block-break`  | none | count of all blocks ever broken
`/block-break/{player}`  | none | count of all blocks ever broken by player
`/scoreboard/block-place`  | `limit=<int>` | scoreboard for block placed per player
`/scoreboard/block-break`  | `limit=<int>` | scoreboard for block broken per player

#### Some Examples:

Scoreboard with all PvP kills:
`http://example.com/PrismREST/v1/scoreboard/kills?type=pvp`

Count of 'death by zombie':
`http://example.com/PrismREST/v1/deaths?type=mob%3Azombie`

Count of all blocks ever mined:
`http://example.com/PrismREST/v1/block-break`


## Config File

The config file get's generated in the working directory of your Application Server (e.g. JBOSS_HOME/bin for jboss) if it doesn't already exist. Set your database according to your 'config.yml' of Prism. Note: PrismREST doesn't work if you are using Prism with an SQLite database.

Example:
```

dbname =prism_schema
dbusername=prism
dbpassword=1234
hostname =localhost
dbport =3306

```
