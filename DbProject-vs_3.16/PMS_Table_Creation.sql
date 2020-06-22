CREATE TABLE SystemUser
(
Uid int identity(101,1) primary key not null,
UserName varchar(20) not null,
userPassword varchar(100) not null,
Role varchar(10) not null,
Phone varchar(15),
Address varchar(100),
)

CREATE TABLE Company
(
Cid int identity(201,1) primary key not null,
CompanyName varchar(50) not null,
Address varchar(100) not null,
ContactNumber varchar(15) not null,
)

CREATE TABLE Generic
(
GId int identity(301,1) primary key not null,
Category varchar(20) not null,
Uses varchar(100),
)

CREATE TABLE Medicine
(
Mid int identity(1001,1) primary key not null,
MedicineName varchar(100) not null,
Gid int not null FOREIGN KEY REFERENCES Generic(Gid),
Cid int not null FOREIGN KEY REFERENCES Company(Cid),
MedicineType varchar(50),
Amount varchar(20), 
)

CREATE TABLE Purchase
(
Pid int identity(401,1) primary key not null,
Mid int not null FOREIGN KEY REFERENCES Medicine(Mid),
CostPrice float not null,
SellingPrice float not null,
Quantity int not null,
)

CREATE TABLE Inventory
(
IId int identity(601,1) primary key not null,
Mid int not null FOREIGN KEY REFERENCES Medicine(Mid),
Stock int not null,
ExpireDate date,
)

CREATE TABLE SellingDetails
(
SId int identity(801,1) primary key not null,
Uid int not null FOREIGN KEY REFERENCES SystemUser(Uid),
CustomerName varchar(50) not null,
SellingDate date,
TotalPrice float not null,
)

CREATE TABLE Records
(
RId int identity(1,1) primary key not null,
Uid int not null FOREIGN KEY REFERENCES SystemUser(Uid),
Mid int not null FOREIGN KEY REFERENCES Medicine(Mid),
Sid int not null FOREIGN KEY REFERENCES SellingDetails(Sid),
ItemName varchar(100) not null,
)

CREATE TABLE BuyingDetails
(
BId int identity(1,1) primary key not null,
Uid int not null FOREIGN KEY REFERENCES SystemUser(Uid),
Pid int not null FOREIGN KEY REFERENCES Purchase(Pid),
Status varchar(50) DEFAULT 'Success',
)

INSERT INTO SystemUser(UserName,userPassword,Role,Phone,Address)
VALUES('admin','admin','Manager','123456789','AUST,Tejgaon,Dhaka')


SELECT * FROM SellingDetails
SELECT * FROM SystemUser
SELECT * FROM Records
SELECT * FROM Purchase
SELECT * FROM Medicine
SELECT * FROM Inventory
SELECT * FROM Generic
SELECT * FROM Company
SELECT * FROM BuyingDetails


