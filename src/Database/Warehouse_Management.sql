create database QuanLy_Kho;
use QuanLy_Kho;
create table Product
(
    Product_Id     char(5) primary key,
    Product_name   varchar(150) not null unique,
    Manufacturer   varchar(200) not null,
    Created        date default (current_date()),
    Batch          smallint     not null,
    Quantity       int  default 0,
    Product_Status bit  default 1
);
create table Employee
(
    Emp_id        char(5) primary key,
    Emp_Name      varchar(100) not null unique,
    Birth_of_date date,
    Email         varchar(100) not null,
    Phone         varchar(100) not null,
    Address       text         not null,
    Emp_Status    smallint     not null
);
create table Account
(
    Acc_id     int primary key auto_increment,
    User_name  varchar(30) not null unique,
    Password   varchar(30) not null,
    Permission bit default 1,
    Emp_id     char(5)     not null unique,
    Acc_status bit default 1
);
create table Bill
(
    Bill_id        int primary key auto_increment,
    Bill_Code      varchar(10) not null,
    Bill_Type      bit         not null,
    Emp_id_created char(5)     not null,
    foreign key (Emp_id_created) references Employee (Emp_id),
    Created        date                 default (current_date()),
    Emp_id_auth    char(5),
    foreign key (Emp_id_auth) references Employee (Emp_id),
    Auth_date      date                 default (current_date()),
    Bill_Status    smallint    not null default 0
);


drop table Bill;
create table Bill_Detail
(
    Bill_Detail_id int primary key auto_increment,
    Bill_id        int     not null,
    foreign key (Bill_id) references Bill (Bill_id),
    Product_Id     char(5) not null,
    foreign key (Product_Id) references Product (Product_Id),
    Quantity       int     not null check ( Quantity > 0 ),
    Price          float   not null check ( Price > 0 )
);
drop table Bill_Detail;
# -------------------Procedure_Product-------------------
-- Lấy tất cả sản phẩm
delimiter &&
create procedure get_all_product(
)
begin
    select * from product limit 10;
end &&
-- Thêm mới 1 sản phẩm
delimiter &&
create procedure create_product(
    ProductId char(5),
    ProductName varchar(150),
    Manufacturers varchar(200),
    Batchs smallint
)
begin
    insert into Product(Product_Id, Product_name, Manufacturer, Batch)
    values (ProductId, ProductName, Manufacturers, Batchs);
end &&
-- Cập nhật sản phẩm
delimiter &&
create procedure update_product(
    ProductId char(5),
    ProductName varchar(150),
    Manufacturers varchar(200),
    Quantiti int,
    Batchs smallint,
    ProductStatus bit
)
begin
    update Product
    set Product_name   = ProductName,
        Manufacturer   = Manufacturers,
        Quantity       = Quantiti,
        Batch          = Batchs,
        Product_Status = ProductStatus
    where Product_Id = ProductId;
end &&
--
delimiter &&
create procedure get_product_by_id(
    ProductId char(5),
    out cnt_product int
)
begin
    set cnt_product = (select count(Product_Id) from Product where Product_Id = ProductId);
end &&

call get_product_by_id('Pb01', @something);
select @something;

-- Tìm kiếm theo tên
delimiter &&
drop procedure if exists find_product;
create procedure find_product(
    in ProductName varchar(150)
)
begin
    select * from Product where Product_name like concat('%', ProductName, '%') limit 10;
end &&
-- Cập nhật trạng thái sản phẩm
delimiter &&
drop procedure if exists update_status_product;
create procedure if not exists update_status_product(
    in ProductId char(5)
)
begin
    set @current_status = (select Product_Status from Product where Product_Id = ProductId);
    update Product
    set Product_Status = not @current_status
    where Product_Id = ProductId;
end &&
-- Check trung Id
delimiter &&
create procedure check_duplicate_idProduct(
    in productId char(5),
    out isDuplicate boolean
)
begin
    select count(*)
    into isDuplicate
    from Product
    where Product_Id = productId;
end &&
-- Check trùng name
delimiter &&
create procedure check_duplicate_nameProduct(
    in productName varchar(150),
    out isDuplicate boolean
)
begin
    select count(*)
    into isDuplicate
    from Product
    where Product_Name = productName;
end &&
# ---------------Employee----------------
# -------------------Validate------------
-- Check tên trùng
delimiter &&
create procedure check_duplicate_name(
    in empName varchar(100),
    out isDuplicate boolean
)
begin
    select count(*)
    into isDuplicate
    from Employee
    where Emp_Name = empName;
end &&
-- check mã trùng
delimiter &&
create procedure check_duplicate_id(
    in empId char(5),
    out isDuplicate boolean
)
begin
    select count(*)
    into isDuplicate
    from Employee
    where Emp_id = empId;
end &&
-- Lấy tất cả sản phẩm
delimiter &&
drop procedure if exists get_all_employee;
create procedure get_all_employee(
)
begin
    select *
    from employee
    order by Emp_Name
    limit 10;
end &&
-- Thêm mới 1 sản phẩm
delimiter &&
create procedure create_employee(
    empId char(5),
    empName varchar(100),
    empBirt DATE,
    emails varchar(100),
    phones varchar(100),
    addres text,
    empStatus smallint
)
begin
    insert into Employee(Emp_id, Emp_Name, Birth_of_date, Email, Phone, Address, Emp_Status)
    values (empId, empName, empBirt, emails, phones, addres, empStatus);
end &&
-- Cập nhật sản phẩm
delimiter &&
create procedure update_employee(
    empId char(5),
    empName varchar(100),
    empBirt DATE,
    emails varchar(100),
    phones varchar(100),
    addres text,
    empStatus smallint
)
begin
    update Employee
    set Emp_Name      = empName,
        Birth_of_date = empBirt,
        Email         = emails,
        Phone         = phones,
        Address       = addres,
        Emp_Status    = empStatus
    where Emp_id = empId;
end &&
--
delimiter &&
create procedure get_employee_by_id(
    empId char(5),
    out cnt_employee int
)
begin
    set cnt_employee = (select count(Emp_Id) from Employee where Emp_id = empId);
end &&
--
-- Tìm kiếm theo tên
delimiter &&
drop procedure if exists find_employee;
create procedure find_employee(
    empName varchar(100)
)
begin
    select *
    from Employee
    where Emp_Name like concat('%', empName, '%')
    limit 10;
end &&
-- Cập nhật trạng thái sản phẩm
delimiter &&
drop procedure if exists update_status_employee;
create procedure if not exists update_status_employee(
    empId char(5),
    empStatus smallint
)
begin
    update Employee
    set Emp_Status = empStatus
    where Emp_id = empId;
end &&

delimiter &&
drop procedure if exists update_status_account_by_employee;
create procedure if not exists update_status_account_by_employee(
    empId char(5)
)
begin
    set @accountStatus = (select Acc_status from account where Emp_id = empId);
    update account
    set Acc_status = not @accountStatus
    where Emp_id = empId;
end &&
# ---------------Account---------------
-- Check trùng accname
delimiter &&
drop procedure if exists check_dulicate_nameAccount;
create procedure if not exists check_duplicate_nameAccount(
    in accName varchar(30),
    out isDuplicate boolean
)
begin
    set isDuplicate = false;
    select count(*)
    into isDuplicate
    from Account
    where User_name = accName;
end &&
-- Danh sách nhân viên
delimiter &&
create procedure get_all_status(
)
begin
    select * from Account;
end &&
-- Tạo tài khoản mới
delimiter &&
drop procedure if exists create_account;
create procedure if not exists create_account(
    accId int,
    userName varchar(30),
    passwords varchar(30),
    empId char(5)
)
begin
    insert into Account(Acc_id, User_name, Password, Emp_id)
    values (accId, userName, passwords, empId);
end &&
-- Cập nhật trạng thái tài khoản
delimiter &&
drop procedure if exists update_status_account;
create procedure if not exists update_status_account(
    accId int
)
begin
    set @current_status = (select Acc_status from Account where Acc_id = accId);
    update Account
    set Acc_status = not @current_status
    where Acc_id = accId;
end &&
-- Tìm kiếm
delimiter &&
drop procedure if exists findAccount;
create procedure if not exists findAccount(
    in findName varchar(30)
)
begin
    select A.*, E.Emp_Name
    from Account A
             join Employee E on A.Emp_id = E.Emp_id
    where A.User_name like concat('%', findName, '%')
       or E.Emp_Name like concat('%', findName, '%');
end &&
--
delimiter &&
create procedure get_account_by_id(
    accId int,
    out cnt_account int
)
begin
    set cnt_account = (select count(Acc_id) from Account where Acc_id = accId);
end &&

# --------------Login------------------
delimiter &&
create procedure login(
    username varchar(30),
    passwords varchar(30),
    out result int
)
begin
    set result = (select count(Acc_id) from Account where User_name = username and Password = passwords);
end &&
-- Check_login_status
delimiter &&
create procedure login_check_status(
    username varchar(30)
)
begin
    select Acc_status from Account where User_name = username;
end &&
-- check_permission
delimiter &&
create procedure check_permission(
    username varchar(30)
)
begin
    select Permission from Account where User_name = username;
end &&
# --------------------Phiếu nhập--------------------
-- Kiem tra mã nhân viên có tồn tại chưa
delimiter &&
drop procedure if exists createBillId;
create procedure if not exists createBillId(
    in empId char(5),
    out isExits boolean
)
begin
    DECLARE employeeId INT;
    SELECT COUNT(*) INTO employeeId FROM Employee WHERE Emp_id = empId;
    SET isExits = (employeeId > 0);
end &&
-- Kiểm tra mã sản phẩm
delimiter &&
drop procedure if exists createBillIdProduct;
create procedure if not exists createBillIdProduct(
    productId char(5),
    out isExists boolean
)
begin
    DECLARE prodcutIdExist INT;
    SELECT COUNT(*) INTO prodcutIdExist FROM Product WHERE Product_Id = productId;
    SET isExists = (prodcutIdExist > 0);
end &&

-- findAll
delimiter &&
drop procedure if exists get_all_receipt;
create procedure if not exists get_all_receipt(
)
begin
    select B.Bill_id,
           B.Bill_Code,
           B.Bill_Type,
           B.Emp_id_created,
           B.Created,
           B.Bill_Status,
           BD.Product_Id,
           BD.Quantity,
           BD.Price
    from Bill B
             inner join Bill_Detail BD
                        on B.Bill_id = BD.Bill_id;
end &&
-- Duyệt phiếu
delimiter &&
drop procedure if exists browse_bill;


create procedure if not exists browse_bill(
)
begin
    declare billType boolean;
    select B.Bill_id,
           B.Bill_Code,
           B.Bill_Type,
           B.Emp_id_auth,
           B.Auth_date,
           B.Bill_Status,
           BD.Product_Id,
           BD.Quantity,
           BD.Price
    from Bill B
             inner join Bill_Detail BD
                        on B.Bill_id = BD.Bill_id
    where Bill_Status = 2;
    update Bill
    set Bill_Status = 2
    where Bill_Status = 0;
    if billType = 1 then
        update Product p
            inner join Bill_Detail bd
            on p.Product_Id = bd.Product_Id
        set p.Quantity = p.Quantity + bd.Quantity
        where bd.Bill_id in (select Bill_id from Bill where Bill_Status = 2);
    else
        update Product p
            inner join Bill_Detail bd
            on p.Product_Id = bd.Product_Id
        set p.Quantity = p.Quantity - bd.Quantity
        where bd.Bill_id in (select Bill_id from Bill where Bill_Status = 2);
    end if;
end &&
--
delimiter &&
drop procedure if exists get_billDetail_receipt;
create procedure if not exists get_billDetail_receipt(
)
begin
    select * from Bill_Detail;
end &&
-- Create
delimiter &&
drop procedure if exists create_receipt;
create procedure if not exists create_receipt(
    billCode varchar(10),
    billType boolean,
    empIdCreated char(5),
    productId char(5),
    quantyti int,
    prices float
)
begin
    declare billId int;
    insert into Bill(Bill_Code, Bill_Type, Emp_id_created)
    values (billCode, billType, empIdCreated);
    set billId = LAST_INSERT_ID();
    insert into Bill_Detail(Bill_id, Product_Id, Quantity, Price)
    values (billId, productId, quantyti, prices);
end &&
-- Update
delimiter &&
drop procedure if exists update_receipt;
create procedure if not exists update_receipt(
    billId long,
    billCode varchar(10),
    empIdCreated char(5),
    billStatus int,
    productId char(5),
    quantyti int,
    prices float
)
begin
    update Bill
    set Bill_Code      = billCode,
        Emp_id_created = empIdCreated,
        Bill_Status    = billStatus
    where Bill_id = billId
       or Bill_Code = billCode;
    update Bill_Detail
    set Product_Id = productId,
        Quantity   = quantyti,
        Price      = prices
    where Bill_id = billId;
end &&
-- Tìm kiếm theo Id
delimiter &&
drop procedure if exists find_receipt;
create procedure if not exists find_receipt(
    idPar int,
    codePar varchar(10)
)
begin
    select Bill.Bill_id,
           Bill.Bill_Code,
           Bill.Bill_Type,
           Bill.Emp_id_created,
           Bill.Created,
           Bill.Bill_Status,
           Bill_Detail.Product_Id,
           Bill_Detail.Quantity,
           Bill_Detail.Price
    from Bill
             inner join Bill_Detail on Bill.Bill_id = Bill_Detail.Bill_id
    where Bill.Bill_Code = codePar
       or Bill.Bill_id = idPar;
end &&
--

select *
from Bill;
delimiter &&
drop procedure if exists get_receipt_by_id;
create procedure if not exists get_receipt_by_id(
    billId long,
    billCode varchar(10),
    out cnt_bill int
)
begin
    set cnt_bill = (select count(Bill_id) from Bill where Bill_id = billId or Bill_Code = billCode);
end &&


# -----------------Thống kê--------------------------------
-- Chi phí theo ngày tháng năm
create procedure get_date(
)
begin

end &&