# _off-the-leash_

#### _off-the-leash, 04-10-2017_

#### By _**<a href="https://github.com/janek-b">Janek Brandt</a>, <a href="https://github.com/finneywhat">Chris Finney</a>,<a href="https://github.com/jwalsh370">Jahan Walsh</a> and <a href="https://github.com/gstuart">Grace Stuart</a>**_

## Description
_Example text for the description of the project_


## Specifications

| Behavior                   | Input Example     | Output Example    |
| -------------------------- | -----------------:| -----------------:|



## Setup/Installation Requirements

* _Clone the repository_
* _Create a google maps api key_
* _Add the api key as an environment variable_ `export MAPS_KEY="YOUR_API_KEY"`
* _Run the command 'gradle run'_
* _Open browser and go to localhost:4567_


Database creation.
* `CREATE DATABASE leash;`
* From the terminal `psql < leash.sql`

If the above does not work, use these commands to create the DB tables.
* `\c leash;`
* `CREATE TABLE parks (id serial PRIMARY KEY, name varchar, location varchar, size varchar, fenced boolean, small boolean, upVote int, downVote int, lat double precision, lng double precision);`
* `CREATE TABLE users (id serial PRIMARY KEY, name varchar);`
* `CREATE TABLE dogs (id serial PRIMARY KEY, userId int, name varchar, gender varchar, altered boolean, breed varchar);`
* `CREATE TABLE reviews (id serial PRIMARY KEY, userId int, parkId int, title varchar, content varchar);`
* `CREATE TABLE checkins (id serial PRIMARY KEY, userId int, parkId int, checkin timestamp, checkout timestamp);`
* `CREATE TABLE votes (id serial PRIMARY KEY, userId int, parkId int, direction varchar);`


### License

Copyright (c) 2017 **_Janek Brandt, Chris Finney, Jahan Walsh and Grace Stuart_**

This software is licensed under the MIT license.
