CREATE TABLE tallocid_bysect(
	Fsect INT UNSIGNED NOT NULL,
	Fid BIGINT UNSIGNED NOT NULL DEFAULT 1000,
	Falloc_size INT UNSIGNED NOT NULL DEFAULT 500,
	Fdesc VARCHAR(128) NOT NULL DEFAULT "",
	Fcreate_timestamp INT NOT NULL DEFAULT 0,
	Flastmodify_timestamp INT NOT NULL DEFAULT 0,
	PRIMARY KEY(Fsect)
);