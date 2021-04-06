# MarketSystem_JDBC
design database about Market System.

using jdbc connect with mysql.

detail explanation is on JDBC_PROJECT.pdf


# Relations

List of attributes of each entity and relationship.

![image](https://user-images.githubusercontent.com/53031059/113729907-64c93900-9732-11eb-95fe-523acf0feb01.png)

# DDL, Entity Structure

    CREATE TABLE Users
      (userId    int(10) unsigned NOT NULL auto_increment,
      name      varchar(32) NOT NULL,
      address   varchar(32) NOT NULL,
      phone_number  varchar(20) NOT NULL,
      birthday  date DEFAULT NULL,
      access_history    date DEFAULT NULL,
      subscription_fee  int(20) DEFAULT 30000,
      unpaid_fee        int(20) DEFAULT 0,
      data_joined       int(20) NOT NULL DEFAULT 0,
      account_number    int(20) DEFAULT NULL,
      joining_day       date NOT NULL,
      due_day           date DEFAULT NULL,
      PRIMARY KEY (userId)
    );

    CREATE TABLE Providers (
      providerId int(10) unsigned NOT NULL AUTO_INCREMENT,
      name varchar(32) NOT NULL,
      address varchar(20) NOT NULL,
      account_number int(20) NOT NULL,
      phone_number varchar(20) NOT NULL,
      birthday date NOT NULL,
      joining_fee int(20) DEFAULT 30000,
      data_joined int(20) NOT NULL DEFAULT 0,
      unpaid_amount int(20) DEFAULT 0,
      providerProfit int(20) DEFAULT 0,
      joining_day date NOT NULL,
      due_day date DEFAULT NULL,
      PRIMARY KEY (providerId)
    );

    CREATE TABLE Items (
      itemId int(10) unsigned NOT NULL AUTO_INCREMENT,
      p_Id int(10) unsigned NOT NULL,
      FOREIGN KEY(p_Id) 
      REFERENCES Providers(providerId) ON UPDATE CASCADE ON DELETE CASCADE, 
      name varchar(32) NOT NULL,
      type varchar(32) NOT NULL,
      author varchar(32) DEFAULT NULL,
      size int(32) NOT NULL,
      last_updated date NOT NULL,
      local_storage_required int(32) NOT NULL,
      machine_architecture_required varchar(32) DEFAULT NULL,
      operating_systems_required varchar(32) DEFAULT NULL,
      language varchar(20) DEFAULT NULL,
      description varchar(5000) NOT NULL,
      downloaded_time int(32) DEFAULT 0,
      PRIMARY KEY (itemId)
    );

    create table Downloads (
      downloadId INT unsigned NOT NULL AUTO_INCREMENT,

      u_Id INT unsigned NOT NULL,
      FOREIGN KEY(u_Id) 
      REFERENCES Users(userId) ON DELETE CASCADE,

      i_Id INT unsigned NOT NULL,
      FOREIGN KEY(i_Id) 
      REFERENCES Items(itemId) on delete cascade,
      PRIMARY KEY (downloadId)
    );

    create table preDownloads
    (
       item_id INT unsigned NOT NULL,
       pre_down_id INT unsigned NOT NULL,

       primary key(item_id, pre_down_id),
       foreign key(item_id)  references Items(itemId) 
         on delete cascade,
       foreign key(pre_down_id) references Items(itemId) on delete cascade
    );

    create table AccessHistory
    (
       history_Id INT unsigned not null AUTO_INCREMENT,
       user_id INT unsigned NOT NULL,
       item_id INT unsigned NOT NULL,

       primary key(history_Id),
       foreign key(item_id)  references Items(itemId) 
         on delete cascade,
       foreign key(user_id) references Users(userId)
         on delete cascade
    );

    create table Manager(
      managerId INT NOT NULL DEFAULT 1,
      totalProfit INT DEFAULT 0,
      netProfit INT DEFAULT 0,
      PRIMARY KEY(managerId)
    ); 

# Triggers
    delimiter //
    create trigger update_downloads
      after insert on Downloads
      for each row
      begin
        update Users set data_joined = data_joined + 1
        where userId = new.u_Id;

        update Manager set totalProfit = totalProfit + 10000;
        update Manager set netProfit = totalProfit – 5000;

        update Providers set data_joined = data_joined + 1
        where providerId = 
        (select p_Id from Items where new.i_Id = Items.itemId);

        update Providers set providerProfit = providerProfit + 5000
        where providerId = 
        (select p_Id from Items where new.i_Id = Items.itemId);
      end //
    delimiter ;

    delimiter //
    create trigger update_user
      after insert on Users
      for each row
      begin
        update Manager set totalProfit = totalProfit + 30000;
        update Manager set netProfit = netProfit + 30000;
      end //
    delimiter ;

    delimiter //
    create trigger update_provider_Profits
      after insert on Providers
      for each row
      begin
        update Manager set totalProfit = totalProfit + 30000;
        update Manager set netProfit = netProfit + 30000;
      end //
    delimiter ;

# Events
    delimiter //
    create event if not exists update_due_day 
        on schedule every 1 day
        starts now()
        on completion preserve enable
        do
        begin
      update Providers
      set unpaid_amount = unpaid_amount + 30000
      where due_day = curdate();
      update Providers
      set due_day = date_add(due_day, interval 1 month)
      where due_day = curdate();
        end//
    delimiter ;

    delimiter //
    create event if not exists update_due_day_user 
        on schedule every 1 day
        starts now()
        on completion preserve enable
        do
        begin
      update Users
      set unpaid_fee = unpaid_fee + 30000
      where due_day = curdate();
      update Users
      set due_day = date_add(due_day, interval 1 month)
      where due_day = curdate();
        end//
    delimiter ;

DML QUERY running within Java Code...
It's on JDBC_PROJECT.pdf you can check it

