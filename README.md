PrismREST
========

PrismREST provides a simple REST interface to the Database of the Bukkit Plugin ['Prism'](http://discover-prism.com/).

# REST API

Base URL: http://example.com/PrismREST/v1

Path      | QueryParameters | Description
----------|------------------|--------------------
`/player`  | none | list of all logged players
----------|------------------|--------------------
`/enum`  | none | list of all possible ActionTypes and DeathTypes
----------|------------------|--------------------
`/kills`  | `type={pve,pvp}` | count of all kills 
----------|------------------|--------------------
`/kills/{player}`  | `type={pve,pvp}` | count of all kills for one player 
----------|------------------|--------------------
`/deaths`  | `type=<deathtype>` | count of all deaths, find possible types at `/enum`
----------|------------------|--------------------
`/deaths/{player}`  | `type=<deathtype>` | count of all deaths for one player (Hint: ':' is '%3A' in a URL)
----------|------------------|--------------------
`/scoreboard/kills`  | `type={pvp,pve}` `limit=<int>` | scoreboard with kills per player
----------|------------------|--------------------
`/block-place`  | none | count of all blocks ever placed
----------|------------------|--------------------
`/block-place/{player}`  | none | count of all blocks ever placed by player
----------|------------------|--------------------
`/block-break`  | none | count of all blocks ever broken
----------|------------------|--------------------
`/block-break/{player}`  | none | count of all blocks ever broken by player
----------|------------------|--------------------
`/scoreboard/block-place`  | `limit=<int>` | scoreboard for block placed per player
----------|------------------|--------------------
`/scoreboard/block-break`  | `limit=<int>` | scoreboard for block broken per player
----------|------------------|--------------------

# Config File

The config file get's generated in the working directory of your Application Server (e.g. JBOSS_HOME/bin for jboss) if it doesn't already exist. Set your database according to your 'config.yml' of Prism. Note: PrismREST doesn't work if you are using Prism with an SQLite database.

Example:
```

dbname =prism_schema
dbusername=prism
dbpassword=1234
hostname =localhost
dbport =3306

```
