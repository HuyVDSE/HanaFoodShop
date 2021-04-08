CREATE DATABASE FoodShop

USE FoodShop

CREATE TABLE tblAccount(
	userId varchar(50) primary key,
	password varchar(50),
	fullname nvarchar(150)
	role bit
)
 alter table tblAccount
 alter column password varchar(100)

CREATE TABLE tblCategory(
	CaId int IDENTITY(1,1) primary key,
	CategoryName nvarchar(50)
)

CREATE TABLE tblProduct(
	itemId int IDENTITY(1,1) primary key,
	name nvarchar (250),
	image varchar(100),
	description nvarchar(250),
	price real,
	createDate date,
	updateDate date,
	quantity int,
	status bit,
	CaId int foreign key references tblCategory(CaId)
)


CREATE TABLE tblOrder(
	orderId varchar(20) primary key,
	orderDate datetime,
	total real,
	userId varchar(50) foreign key references tblAccount(userId)
)

CREATE TABLE tblOrderDetail(
	orderId varchar(20) foreign key references tblOrder(orderId),
	itemId int foreign key references tblProduct(itemId),
	primary key (orderId, itemId),
	quantity int
)

ALter table tblOrderDetail
Add total real

CREATE TABLE tblPaymentMethod(
	methodId varchar(20) primary key,
	methodName nvarchar(50)
)

ALTER TABLE tblOrder
ADD methodId varchar(20) foreign key references tblPaymentMethod(methodId)

ALTER TABLE tblLog
ALTER COLUMN updateDate datetime

INSERT INTO tblLog(itemId, updateDate, userId) 
VALUES (1, '2020-01-10', 'hoangvd')

SELECT userId, password, fullname, role
FROM tblAccount
WHERE userId = 'huyvd' AND password = '123'

SELECT itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.categoryName 
FROM tblProduct p, tblCategory c
WHERE p.CaId = c.CaId
AND quantity > 0
AND status = 0
GROUP BY itemId, name, image, description, price, createDate, updateDate, quantity, quantity, status, p.caId, c.categoryName
ORDER BY createDate DESC
OFFSET 20 ROWS
FETCH NEXT 20 ROWS ONLY

SELECT COUNT(itemId) as TotalProduct
FROM tblProduct
WHERE quantity > 0 AND status = 0

select caId, categoryName
from tblCategory

SELECT itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.categoryName 
FROM tblProduct p, tblCategory c
WHERE p.CaId = c.CaId
	AND quantity > 0
	AND name like '%gà%'
	AND price between 50000 and 150000
	AND c.CaId = 2
	AND status = 1
GROUP BY itemId, name, image, description, price, createDate, updateDate, quantity, status, p.caId, c.categoryName
ORDER BY createDate DESC
OFFSET 0 ROWS
FETCH NEXT 20 ROWS ONLY

SELECT COUNT(itemId) as TotalProduct
FROM tblProduct p, tblCategory c
WHERE p.CaId = c.CaId
	AND quantity > 0
	AND name like '%g%'
	AND price between 50000 and 150000
	AND c.CaId = 2
	AND status = 1

UPDATE tblProduct
SET status = 1

UPDATE tblProduct
SET updateDate = '2020-12-31' , userIdUpdate = 'hoangvd'
WHERE itemId = 2

SELECT itemId, name
FROM tblProduct
WHERE status = 0

SELECT updateDate
FROM tblLog

ALTER TABLE tblProduct
ALTER COLUMN updateDate date

ALTER TABLE tblProduct
DROP COLUMN userId

ALTER TABLE tblProduct
ADD userIdUpdate varchar(50) foreign key references tblAccount(userId)

SELECT name, image, description, price, quantity, createDate, updateDate, status, CaId
FROM tblProduct
WHERE itemId = 1

SELECT name
FROM tblProduct
WHERE itemId = 10
AND quantity > 0
AND status = 1

insert tblOrder(orderId, orderDate, total, userId) 
values('1', '20210116 10:56:44', 2000, 'huyvd')

update tblProduct
set quantity = 150
where itemId = 1

select name
from tblProduct
where itemId = 1 and quantity > 0 and status = 1

SELECT orderId, orderDate, total, userId, methodName
FROM tblOrder o join tblPaymentMethod p on o.methodId = p.methodId
WHERE orderDate between '2021-01-17' and '2021-01-17 23:59:59'
AND userId = 'huyvd'

SELECT p.name, p.price, od.quantity, od.total
FROM tblOrderDetail od join tblProduct p on od.itemId = p.itemId
WHERE orderId = '170121-041215-798773'

UPDATE tblOrderDetail
SET total = (p.price * od.quantity)
FROM tblProduct p join tblOrderDetail od on p.itemId = od.itemId