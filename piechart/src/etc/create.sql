CREATE TABLE IF NOT EXISTS SalesValue
(
	id				BIGSERIAL PRIMARY KEY,
	productId		INT(32) NOT NULL,
	value			INT(32) NOT NULL,
	percentage		DOUBLE(32)
);
