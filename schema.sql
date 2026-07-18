CREATE DATABASE IF NOT EXISTS atmdb;
USE atmdb;

CREATE TABLE Accounts (
    AccountNumber INT PRIMARY KEY,
    FullName VARCHAR(100),
    FatherName VARCHAR(100),
    DOB VARCHAR(30),
    Pin INT,
    Education VARCHAR(50),
    Occupation VARCHAR(100),
    Phone VARCHAR(30),
    Address VARCHAR(255),
    Balance INT DEFAULT 0,
    IsActive BOOLEAN DEFAULT TRUE,
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Deposits (
    DepositID INT PRIMARY KEY AUTO_INCREMENT,
    AccountNumber INT,
    Amount INT,
    DepositDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AccountNumber) REFERENCES Accounts(AccountNumber)
);

CREATE TABLE Withdraws (
    WithdrawID INT PRIMARY KEY AUTO_INCREMENT,
    AccountNumber INT,
    Amount INT,
    WithdrawDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AccountNumber) REFERENCES Accounts(AccountNumber)
);

CREATE TABLE Transactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    AccountNumber INT,
    TransactionType VARCHAR(20),
    Amount INT,
    BalanceAfter INT,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AccountNumber) REFERENCES Accounts(AccountNumber)
);

CREATE TABLE Admins (
    AdminID INT PRIMARY KEY AUTO_INCREMENT,
    Username VARCHAR(50) UNIQUE,
    Password VARCHAR(100),
    FullName VARCHAR(100),
    IsActive BOOLEAN DEFAULT TRUE
);

-- Default admin for the shared Login page (username / password)
INSERT INTO Admins (Username, Password, FullName, IsActive)
VALUES ('admin', 'admin123', 'System Admin', TRUE)
ON DUPLICATE KEY UPDATE Username = Username;
