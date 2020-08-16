CREATE DATABASE beers_db;

USE [beers_db];
CREATE TABLE beers_table (
	id integer PRIMARY KEY AUTOINCREMENT,
	name varchar,
	tagline varchar,
	description varchar,
	alcohol_volume varchar,
	bitterness varchar,
	food_pairing varchar,
	available varchar
);
