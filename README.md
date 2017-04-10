# _off-the-leash_

#### _off-the-leash, 04-10-2017_

#### By _**Janek Brandt, Chris Finney, Jahan Walsh and Grace Stuart**_

## Description
_Example text for the description of the project_


## Specifications

| Behavior                   | Input Example     | Output Example    |
| -------------------------- | -----------------:| -----------------:|



## Setup/Installation Requirements

* _Clone the repository_
* _Run the command 'gradle run'_
* _Open browser and go to localhost:4567_

Database creation.
* `CREATE DATABASE leash;`
* From the terminal `psql < leash.sql`

If the above does not work, use these commands to create the DB tables.
* `\c leash;`
* `CREATE TABLE parks (id serial PRIMARY KEY, name varchar, location varchar, size varchar, fenced boolean, small boolean);`
* `CREATE TABLE users (id serial PRIMARY KEY, name varchar);`
* `CREATE TABLE dogs (id serial PRIMARY KEY, userId int, name varchar, gender varchar, altered boolean, breed varchar);`
* `CREATE TABLE reviews (id serial PRIMARY KEY, userId int, parkId int, title varchar, review varchar, rating int);`
* `CREATE TABLE checkins (id serial PRIMARY KEY, userId int, parkId int, checkin timestamp, checkout timestamp);`


### License

Copyright (c) 2017 **_Janek Brandt, Chris Finney, Jahan Walsh and Grace Stuart_**

This software is licensed under the MIT license.
