import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.io.*;

public class db_proj {
    private void init(){
        StringBuilder p_init = new StringBuilder();

        p_init.append("\n");
        p_init.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");
        p_init.append("-------------------------------------------------------------StudentId: 2016310526------------------------------------------------------------\n");
        p_init.append("----------------------------------------------------------------Name: Kimchanho---------------------------------------------------------------\n");
        p_init.append("-------------------------------------------------------------Database Term Project------------------------------------------------------------\n");
        p_init.append("----------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.println(p_init);

        StringBuilder menu = new StringBuilder();
        

        menu.append("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        menu.append("-------------------------------------------------------------------Main Menu------------------------------------------------------------------\n");
        menu.append("1. User Mode\n");
        menu.append("2. Provider Mode\n");
        menu.append("3. Manager Mode\n");
        menu.append("4. Quit\n");
        menu.append("----------------------------------------------------------------------------------------------------------------------------------------------");


        while(true){
            System.out.println(menu);
            try{
                System.out.print("select: ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int choice = 0;
                String input = br.readLine();
                choice = Integer.parseInt(input);
                switch(choice){
                    case 1:
                        select_userMode();
                        break;
                    case 2:
                        select_providerMode();
                        break;
                    case 3:
                        select_managerMode();
                        break;
                    case 4:
                        System.out.println("Good bye~");
                        System.exit(0);
                }
            }catch(Exception e){
                System.out.println("Insert Proper Input");
            }
        }
    }
    
    String program = "program";
    String videoclip = "videoclip";
    String soundclip = "soundclip";
    String document = "document";
    String image = "image";
    String user_id_input = "";
    String provider_id_input = "";
    int uid = 0;
    int pid = 0;


    public void select_userMode() throws IOException{
        StringBuilder userMode = new StringBuilder();

        userMode.append("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        userMode.append("-------------------------------------------------------------------User Mode------------------------------------------------------------------\n");
        userMode.append("1. User Enrollment\n");
        userMode.append("2. User Delete\n");
        userMode.append("3. Show Items\n");
        userMode.append("4. Sign up\n");
        userMode.append("5. Sign out\n");
        userMode.append("6. Downloads Item\n");
        userMode.append("7. Pay Subscription Fee\n");
        userMode.append("8. Return to Main menu\n");
        userMode.append("-----------------------------------------------------------------------------------------------------------------------------------------------\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println(userMode);
            try{                
                System.out.print("select: ");
                int choice = 0;
                String input = br.readLine();
                choice = Integer.parseInt(input);

                switch(choice){
                    case 1:
                        user_enroll();
                        break;
                    case 2:
                        user_delete();
                        break;
                    case 3:
                        show_all_items();
                        break;
                    case 4:
                        user_signup();
                        break;
                    case 5:
                        user_signout();
                        break;
                    case 6:
                        downloads_items();
                        break;
                    case 7:
                        user_pay();
                        break;
                    case 8:
                        System.out.println("return to main menu");
                        uid =0;
                        return;
                }
            }catch(Exception e){
                System.out.println("Insert Proper Input");
            }
        }

    }
    public void user_enroll() throws IOException{

        System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        System.out.print("-----------------------------------------------------------------User Enroll------------------------------------------------------------------\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("name: ");
        String name = br.readLine();
        System.out.print("address: ");
        String address = br.readLine();
        System.out.print("phone number: ");
        String phone_number = br.readLine();
        System.out.print("account number: ");
        String account_number = br.readLine();
        System.out.print("today(for joining day): ");
        String joining_day = br.readLine();
        
        try(            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement();)
        {
            Class.forName ("com.mysql.jdbc.Driver"); 
 
            int result = stmt.executeUpdate("insert into Users(name, address, phone_number, account_number, joining_day) " +
                "values('"+name+"','"+address+"','"+phone_number+"','"+account_number+"','"+joining_day+"')");

            if(result >0)  System.out.println("Congratulations! Enrollment Success");
            else{
                System.out.println("Enrollment Failed");
                System.exit(0);
            }
            ResultSet rset = stmt.executeQuery("select userId from Users where name = '"+name+"'");
            while(rset.next()){
                uid = rset.getInt(1);
            }
            result = stmt.executeUpdate("update Users set due_day = date_add(joining_day, interval 1 month) where name = '"+name+"' and address ='" + address+"'");
            result = stmt.executeUpdate("update Users set unpaid_fee = 30000 where name = '"+name+"' and address ='" + address+"'");

            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
        }
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void user_delete() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(uid == 0){
            System.out.println("you need to SignUp first...");
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
            return;
        }
        
        System.out.print("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
        System.out.print("----------------------------------------------------------------User Unregister-----------------------------------------------------------------\n");
        
        try(            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement();)
        {
            Class.forName ("com.mysql.jdbc.Driver"); 
 
            int result = stmt.executeUpdate("delete from Users where userId = "+
                uid);

            if(result >0)  System.out.println("Good bye~ delete complete");
            else{
                System.out.println("Delete Failed");
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
        }
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_all_items() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(uid == 0){
            System.out.println("you need to SignUp first...");
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
            return;
        }

        int choice = 1;
        int input = 0;
        String category = "program";
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            while(true){
                System.out.print("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
                System.out.print("--------------------------------------------------------------------Item List-------------------------------------------------------------------\n");
                if(choice == 1){
                    category = "program";
                }
                else if(choice == 2){
                    category = "videoclip";
                }
                else if(choice == 3){
                    category = "soundclip";
                }
                else if(choice == 4){
                    category = "document";
                }
                else if(choice == 5){
                    category = "image";                    
                }
                else{
                    System.out.println("error...\n");
                }
                
                ResultSet rset = stmt.executeQuery(
                    "select*from Items where type = '"+category+"'" );
                System.out.printf("--------------------------------------------------------------------%8s-------------------------------------------------------------------\n",category);

                    
                System.out.printf("%10s %20s %10s %10s %15s %50s\n", "[item_id]", "[item_name]", "[item_type]", "[item_size]", "[storage_size]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %10s %10d %15d %50s\n", 
                               rset.getInt(1),
                               rset.getString(3),
                               rset.getString(4),
                               rset.getInt(6),
                               rset.getInt(8),
                               rset.getString(12)
                               );            
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------\n");
                System.out.println("1. prev");
                System.out.println("2. next");
                System.out.println("3. check more information");
                System.out.println("4. Quit");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------\n");
    
                System.out.print("select: ");
                String Input = br.readLine();
                input = Integer.parseInt(Input);
                if(input == 1){
                    if(choice >1){
                        choice -=1;
                    }
                    else{
                        System.out.println("first page");
                    }
                }
                else if(input == 2){
                    if(choice <5){
                        choice +=1;
                    }
                    else{
                        System.out.println("last page");
                    }
                }
                else if(input == 3){
                    System.out.print("check y if you want more information [y/n]: ");
                    String check = br.readLine();
        
                    if(check.equals("y") || check.equals("Y")){
                        System.out.print("which item you want to check(item id): ");
                        String item_id = br.readLine();
                        int i_id = Integer.parseInt(item_id);
                        show_detail_item(i_id, category);
                    }
                }
                else if(input == 4){
                    break;
                }
                else{
                    System.out.println("wrong input");
                    choice = 1;
                }
            }

            stmt.close();    
            conn.close();    

        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_detail_item(int item_number, String category) throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            ResultSet rset = stmt.executeQuery(
                    "select*from Items where type = '"+category+"' and itemId ='"+item_number+"'");
                    
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------\n");

            if(category.equals(program)){
                
                System.out.printf("%10s %20s %10s %15s %15s %15s %50s\n", "[item_id]", "[item_name]", "[item_size]", "[storage_size]", "[machine]", "[Os]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %10d %15d %15s %15s %50s\n", 
                                   rset.getInt(1),
                                   rset.getString(3),
                                   rset.getInt(6),
                                   rset.getInt(8),
                                   rset.getString(9),
                                   rset.getString(10),
                                   rset.getString(12)
                                   );            
                }
            }
            else if(category.equals(videoclip)){
                System.out.printf("%10s %20s %10s %15s %50s\n", "[item_id]", "[item_name]", "[item_size]", "[storage_size]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %10d %15d %50s\n", 
                                    rset.getInt(1),
                                    rset.getString(3),
                                    rset.getInt(6),
                                    rset.getInt(8),
                                    rset.getString(12)
                                    );            
                }
            }
            else if(category.equals(soundclip)){
                System.out.printf("%10s %20s %10s %15s %50s\n", "[item_id]", "[item_name]", "[item_size]", "[storage_size]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %10d %15d %50s\n", 
                                    rset.getInt(1),
                                    rset.getString(3),
                                    rset.getInt(6),
                                    rset.getInt(8),
                                    rset.getString(12)
                                    );            
                }
            }
            else if(category.equals(document)){
                System.out.printf("%10s %20s %15s %10s %15s %15s %50s\n", "[item_id]", "[item_name]", "[author]", "[item_size]", "[storage_size]", "[language]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %15s %10d %15d %15s %50s\n", 
                                    rset.getInt(1),
                                    rset.getString(3),
                                    rset.getString(5),
                                    rset.getInt(6),
                                    rset.getInt(8),
                                    rset.getString(11),
                                    rset.getString(12)
                                    );            
                }
            }
            else if(category.equals(image)){
                System.out.printf("%10s %20s %10s %15s %50s\n", "[item_id]", "[item_name]", "[item_size]", "[storage_size]", "[description]");
                while (rset.next()) {
                    System.out.printf("%10d %20s %10d %15d %50s\n", 
                                    rset.getInt(1),
                                    rset.getString(3),
                                    rset.getInt(6),
                                    rset.getInt(8),
                                    rset.getString(12)
                                    );            
                }
            }
            else{
                System.out.println("error...");
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------\n");
        
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
            
            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }

    }
    public void user_signup() throws IOException{
        
        System.out.print("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
        System.out.print("-------------------------------------------------------------------User Signup------------------------------------------------------------------\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("User id: ");
        user_id_input = br.readLine();
        uid = Integer.parseInt(user_id_input);

        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select name from Users where userId = '"+uid+"'");

                    while(rset.next()){
                        System.out.printf("welcome %10s ~\n", rset.getString(1));
                    }
            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }       
    public void user_signout(){
        if(uid == 0){
            System.out.println("SignUp first");
            return;
        }
        uid = 0;
        System.out.println("See you again~");
    } 
    public void downloads_items() throws IOException{
        if(uid == 0){
            System.out.println("you need to SignUp first");
            return;
        }

        StringBuilder download_item_menu = new StringBuilder();
        download_item_menu.append("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
        download_item_menu.append("------------------------------------------------------------------User Download-----------------------------------------------------------------\n");

        download_item_menu.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");
        download_item_menu.append("1. Show Item List with short description\n");
        download_item_menu.append("2. Check preDownload items\n");
        download_item_menu.append("3. Download items\n");
        download_item_menu.append("4. Quit\n");
        download_item_menu.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        while(true){
            System.out.println(download_item_menu);
            System.out.print("select: ");
            String Choice = br.readLine();
            choice = Integer.parseInt(Choice);
            switch(choice){
                case 1:
                    show_all_items();
                    break;
                case 2:
                    check_pre_down();
                    break;
                case 3:
                    user_down();
                    break;
                case 4:
                    return;
            }
        }
    }
    public void check_pre_down() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("which item you want to check: ");
        String Item_id = br.readLine();
        int item_id = Integer.parseInt(Item_id);
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            System.out.print("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
            System.out.print("---------------------------------------------------------------Check preDownloads---------------------------------------------------------------\n");
            ResultSet rset = stmt.executeQuery(
                    "select * from preDownloads where item_id = '"+item_id+"'");

            while(rset.next()){
                System.out.printf("recommend to down: %d\n", rset.getInt(2));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------\n");

            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    
    public void user_down() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(uid == 0){
            System.out.println("you need to SignUp first");
            return;
        }
        System.out.print("which item you want to Download(item id needed): ");
        String DownId = br.readLine();
        int down_id = Integer.parseInt(DownId);
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            int result = stmt.executeUpdate(
                    "insert into Downloads(u_Id, i_Id) values('"+uid+"','"+down_id+"')");
            if(result >0){
                System.out.println("download success!");
            }
            else{
                System.out.println("download fail...");
            }
            result = stmt.executeUpdate("update Items set downloaded_time = downloaded_time +1 where itemId = '"+down_id+"'");

            ResultSet rset = stmt.executeQuery("select size from Items where itemId = '" + down_id +"'");
            int size_ =0;
            while(rset.next()){
                size_ = rset.getInt(1);
            }

            size_ = size_*500;
            result = stmt.executeUpdate("update Users set unpaid_fee = unpaid_fee + '"+size_+"' where userId='"+uid+"'");
    
            if(result >0)  System.out.println("unpaid amount is increase " + size_);
            else{
                System.out.println("Pay Failed");
                System.exit(0);
            }

            rset = stmt.executeQuery("select p_Id from Items where itemId ='"+down_id+"'");
            int pid = 0;
            while(rset.next()){
                pid = rset.getInt(1);
            }
            result = stmt.executeUpdate("update Providers set providerProfit = providerProfit +'" + size_+"' where providerId ='"+pid+"'" );
            
            result = stmt.executeUpdate("insert into AccessHistory(user_id, item_id) values(" +uid+","+down_id+")");

            if(result >0)  System.out.println("AccessHistory updated");
            else{
                System.out.println("AccessHistory Updated Failed");
            }
            
            stmt.close();    
            conn.close();    
        }
                
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    
    public void user_pay() throws IOException{
        if(uid == 0){
            System.out.println("you need to SignUp first");
            return;
        }
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            
            System.out.print("-------------------------------------------------------------Welcome to Market Place------------------------------------------------------------\n");
            System.out.print("-------------------------------------------------------------------User PayFee------------------------------------------------------------------\n");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            ResultSet rset = stmt.executeQuery("select unpaid_fee from Users where userId ='"+uid+"'");
            int unpaid_fee=0;
            while(rset.next()){
                unpaid_fee = rset.getInt(1);
            }
            System.out.println("How much you want to pay? your unpaid fee is " +unpaid_fee);
            String payment = br.readLine();
            int pay = Integer.parseInt(payment);
            if(pay > unpaid_fee) {
                System.out.println("to much...");
                return;
            }

            int pay_left = unpaid_fee - pay;

            int result = stmt.executeUpdate("update Users set unpaid_fee = unpaid_fee - '"+pay+"' where userId='"+uid+"'");
    
            if(result >0)  System.out.println("unpaid amount is " + pay_left);
            else{
                System.out.println("Pay Failed");
                System.exit(0);
            }
            
            result = stmt.executeUpdate("update Manager set totalProfit = totalProfit + '" +pay+"'");
            result = stmt.executeUpdate("update Manager set netProfit = netProfit + '" +pay+"'");

            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }        
    }

    public void select_providerMode() throws IOException{
        StringBuilder providerMode = new StringBuilder();

        providerMode.append("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        providerMode.append("-----------------------------------------------------------------Provider Mode----------------------------------------------------------------\n");
        providerMode.append("1. Provider Enrollment\n");
        providerMode.append("2. Provider Delete\n");
        providerMode.append("3. Show your items\n");
        providerMode.append("4. Sign up\n");
        providerMode.append("5. Sign out\n");
        providerMode.append("6. Uploads item\n");
        providerMode.append("7. Pay fee to Manager\n");
        providerMode.append("8. Show my Profits\n");
        providerMode.append("9. Return to Main menu\n");
        providerMode.append("-----------------------------------------------------------------------------------------------------------------------------------------------\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println(providerMode);
            try{
                int choice = 0;
                System.out.print("select: ");
                String input = br.readLine();
                choice = Integer.parseInt(input);

                switch(choice){
                    case 1:
                        provider_enroll();
                        break;
                    case 2:
                        provider_delete();
                        break;
                    case 3:
                        show_provider_item();
                        break;
                    case 4:
                        provider_signup();
                        break;
                    case 5:
                        provider_signout();
                        break;
                    case 6:
                        upload_item();
                        break;
                    case 7:
                        provider_pay();
                        break;
                    case 8:
                        show_profit();
                        break;
                    case 9:
                        System.out.println("return to main menu");
                        pid =0;
                        return;
                }
            }catch(Exception e){
                System.out.println("Insert Proper Input");
            }
        }
    }
    public void provider_enroll() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        System.out.print("----------------------------------------------------------------Provider Enroll---------------------------------------------------------------\n");
        System.out.print("name: ");
        String name = br.readLine();
        System.out.print("address: ");
        String address = br.readLine();
        System.out.print("account number: ");
        String account_number = br.readLine();
        System.out.print("phone number: ");
        String phone_number = br.readLine();
        System.out.print("birthday: ");
        String birthday = br.readLine();
        System.out.print("today(for joining day): ");
        String joining_day = br.readLine();
        
        try(            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement();)
        {
            Class.forName ("com.mysql.jdbc.Driver"); 
 
            int result = stmt.executeUpdate("insert into Providers(name, address, account_number, phone_number, birthday, joining_day) " +
                "values('"+name+"','"+address+"','"+account_number+"','"+phone_number+"','"+birthday+"','"+joining_day+"')");

            if(result >0)  System.out.println("Congratulations! Enrollment Success");
            else{
                System.out.println("Enrollment Failed");
                System.exit(0);
            }
            ResultSet rset = stmt.executeQuery("select providerId from Providers where name = '"+name+"'");
            while(rset.next()){
                pid = rset.getInt(1);
            }

            result = stmt.executeUpdate("update Providers set due_day = date_add(joining_day, interval 1 month) where name = '"+name+"' and address ='" + address+"'");
            result = stmt.executeUpdate("update Providers set unpaid_amount = 30000 where name = '"+name+"' and address ='" + address+"'");

            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();

        }
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_provider_item() throws IOException{
        if(pid == 0){
            System.out.println("you need to SignUp first");
            return;
        }

        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select*from Items where p_Id = '"+pid+"'" );
            
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("-------------------------------------------------------------------Your Items-----------------------------------------------------------------\n");
                  
            System.out.printf("%10s %20s %10s %10s %15s %50s\n", "[item_id]", "[item_name]", "[item_type]", "[item_size]", "[storage_size]", "[description]");
            while (rset.next()) {
                System.out.printf("%10d %20s %10s %10d %15d %50s\n", 
                           rset.getInt(1),
                           rset.getString(3),
                           rset.getString(4),
                           rset.getInt(6),
                           rset.getInt(8),
                           rset.getString(12)
                           );            
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
                
            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }

    }
    public void provider_delete() throws IOException{
        if(pid == 0){
            System.out.println("you need to SignUp first");
            return;
        }

        try(            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement();)
        {
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("---------------------------------------------------------------Provider Unregister------------------------------------------------------------\n");
             
            Class.forName ("com.mysql.jdbc.Driver"); 
 
            int result = stmt.executeUpdate("delete from Providers where providerId = "+
                pid);

            if(result >0)  System.out.println("Good bye~ delete complete");
            else{
                System.out.println("Delete Failed");
                System.exit(0);
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        }
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void provider_signup() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        System.out.print("----------------------------------------------------------------Provider Signup---------------------------------------------------------------\n");
             
        System.out.print("Provider id: ");
        provider_id_input = br.readLine();
        pid = Integer.parseInt(provider_id_input);

        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select name from Providers where providerId = '"+pid+"'");

                    while(rset.next()){
                        System.out.printf("welcome %10s ~\n", rset.getString(1));
                    }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
        
            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void provider_signout() throws IOException{
        if(pid == 0){
            System.out.println("SignUp first");
            return;
        }
        pid = 0;
        System.out.println("See you again~");
    }
    public void upload_item() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        if(pid == 0){
            System.out.println("you need to SignUp first");
            return;
        }
        
        System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        System.out.print("----------------------------------------------------------------Provider Upload---------------------------------------------------------------\n");
        System.out.print("Item name: ");
        String name = br.readLine();
        System.out.print("Item type(program/document/image/videoclip/soundclip): ");
        String type = br.readLine();
        System.out.print("Item size: ");
        String _size = br.readLine();
        int size = Integer.parseInt(_size);
        int fee = size*500;
        System.out.print("today(xxxx-xx-xx): ");
        String last_updated = br.readLine();
        System.out.print("local storage needed: ");
        String _local = br.readLine();
        int local_storage = Integer.parseInt(_local);
        System.out.print("short description: ");
        String desc = br.readLine();

        System.out.print("author(if needed or NONE): ");
        String author = br.readLine();
        System.out.print("language(if needed or NONE): ");
        String language = br.readLine();
        System.out.print("machine needed(if needed or NONE): ");
        String machine = br.readLine();
        System.out.print("operating system needed(if needed or NONE): ");
        String os = br.readLine();


        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            int _item_id = 0;
            int result = stmt.executeUpdate(
                    "insert into Items(p_Id, name, type, author, size, last_updated, local_storage_required, machine_architecture_required, operating_systems_required, language, description) values('"
                    +pid+"','"+name+"','"+type+"','"+author+"','"+size+"','"+last_updated+"','"+local_storage+"','"+machine+"','"+os+"','"+language+"','"+desc+"')");
            
            result = stmt.executeUpdate("update Providers set unpaid_amount = unpaid_amount +'" + fee+"' where providerId ='"+pid+"'" );

            if(result >0){
                ResultSet rset = stmt.executeQuery(
                    "select itemId from Items where p_Id = '"+pid+"' and name = '"+name+"'");

                    while(rset.next()){
                        _item_id = rset.getInt(1);
                        System.out.printf("upload success, your item_id is %d ~\n", rset.getInt(1));
                    }
            }
            else{
                System.out.println("upload fail...");
            }

            System.out.print("Do you want to make predownload list? [y/n] :");
            String choice = br.readLine();

            if(choice.equals("y") || choice.equals("Y")){
                System.out.print("how much item you want to upload predownload: ");
                String _input = br.readLine();
                int num = Integer.parseInt(_input);
                for(int i =0; i <num; i++){
                    System.out.print("Enter predown item id: ");
                    String _id = br.readLine();
                    int id = Integer.parseInt(_id);
                    result = stmt.executeUpdate(
                        "insert into preDownloads(item_id, pre_down_id) values('"+_item_id+"','"+id+"')");
                }
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();

            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void provider_pay() throws IOException{
        if(pid == 0){
            System.out.println("you need to SignUp first");
            return;
        }

        
        try {
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("----------------------------------------------------------------Provider PayFee---------------------------------------------------------------\n");
       
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            ResultSet rset = stmt.executeQuery("select unpaid_amount from Providers where providerId ='"+pid+"'");
            int unpaid_fee=0;
            while(rset.next()){
                unpaid_fee = rset.getInt(1);
            }
            System.out.println("How much you want to pay? your unpaid fee is " +unpaid_fee);
            String payment = br.readLine();
            int pay = Integer.parseInt(payment);
            if(pay > unpaid_fee) {
                System.out.println("to much...");
                return;
            }

            int pay_left = unpaid_fee - pay;

            int result = stmt.executeUpdate("update Providers set unpaid_amount = unpaid_amount -'"+pay+"' where providerId='"+pid+"'");
    
            if(result >0)  System.out.println("unpaid fee is " + pay_left);
            else{
                System.out.println("Pay Failed");
                System.exit(0);
            }
            
            result = stmt.executeUpdate("update Manager set totalProfit = totalProfit + '" +pay+"'");
            result = stmt.executeUpdate("update Manager set netProfit = netProfit + '" +pay+"'");
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_profit() throws IOException{
        if(pid == 0){
            System.out.println("you need to SignUp first");
            return;
        }

        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 

            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("---------------------------------------------------------------Provider Profits---------------------------------------------------------------\n");

            ResultSet rset = stmt.executeQuery(
                    "select providerProfit from Providers where providerId = '"+pid+"'" );

            System.out.print("Your profits: ");                    
            while (rset.next()) {
                System.out.printf("%4d\n", 
                           rset.getInt(1)
                           );            
            }
            System.out.print("want to get more info? [y/n]: ");
            String _check;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            _check = br.readLine();
            if(_check.equals("Y") || _check.equals("y")){
                rset = stmt.executeQuery("select itemId, name, downloaded_time from Items where p_Id = '"+pid+"'");
                while(rset.next()){
                    int i_id =rset.getInt(1);
                    String name =rset.getString(2);
                    int d_time =rset.getInt(3);

                    System.out.printf("your item(itemId: %d) %10s downloaded %d time\n", i_id, name, d_time);
                }
            }
            
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();

            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }

    }

    String password = "1234";

    public void select_managerMode() throws IOException{
        StringBuilder managerMode = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Password...(for test use 1234)  :");
        String pw = br.readLine();
        if(!pw.equals(password)){
            System.out.println("Password Wrong...");
            return;
        }
        System.out.println("Access complete");

        managerMode.append("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
        managerMode.append("-----------------------------------------------------------------Manager Mode-----------------------------------------------------------------\n");

        managerMode.append("1. Show All Users\n");
        managerMode.append("2. Show All Providers\n");
        managerMode.append("3. Show All Downloads Info\n");
        managerMode.append("4. Show Access Info\n");
        managerMode.append("5. Show Profits\n");
        managerMode.append("6. Return to Main menu\n");
        managerMode.append("-----------------------------------------------------------------------------------------------------------------------------------------------\n");


        while(true){
            System.out.println(managerMode);
            try{
                int choice = 0;
                System.out.print("select: ");
                String input = br.readLine();
                choice = Integer.parseInt(input);

                switch(choice){
                    case 1:
                        show_all_users();
                        break;
                    case 2:
                        show_all_providers();
                        break;
                    case 3:
                        show_all_downinfo();
                        break;
                    case 4:
                        show_access_info();
                        break;
                    case 5:
                        show_profits();
                        break;
                    case 6:
                        System.out.println("return to main menu");
                        return;
                }
            }catch(Exception e){
                System.out.println("Insert Proper Input");
            }
        }
    }
    public void show_all_users() throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("----------------------------------------------------------------Show all Users----------------------------------------------------------------\n");
            ResultSet rset = stmt.executeQuery(
                    "select*from Users" );
                   
            System.out.printf("%10s %20s %20s %20s %20s %20s %20s\n", "[user_id]", "[user_name]", "[user_addr]", "[phone_num]", "[unpaid]", "[downloads]", "[account]");
            while (rset.next()) {
                System.out.printf("%10d %20s %20s %20s %20d %20d %20s\n", 
                           rset.getInt(1),
                           rset.getString(2),
                           rset.getString(3),
                           rset.getString(4),
                           rset.getInt(8),
                           rset.getInt(9),
                           rset.getString(10)
                           );            
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
                
            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_all_providers() throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select*from Providers" );
            
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("--------------------------------------------------------------Show all Providers--------------------------------------------------------------\n");
                   
            System.out.printf("%15s %20s %20s %20s %20s %20s %20s\n", "[provider_id]", "[provider_name]", "[provider_addr]", "[account]", "[phone_num]", "[downloads]", "[unpaid]");
            while (rset.next()) {
                System.out.printf("%15d %20s %20s %20s %20s %20d %20d\n", 
                           rset.getInt(1),
                           rset.getString(2),
                           rset.getString(3),
                           rset.getString(4),
                           rset.getString(5),
                           rset.getInt(8),
                           rset.getInt(9)
                           );            
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
                
            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_all_downinfo() throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select*from Downloads" );
            
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("--------------------------------------------------------------Show all Downloads--------------------------------------------------------------\n\n");
            
            
            System.out.print("-----------------------------------------------------------Show Users Download Info-----------------------------------------------------------\n");
            System.out.printf("%20s %20s\n", "[User_id]", "[Item_id]");
            while (rset.next()) {
                System.out.printf("%20d %20d\n", 
                           rset.getInt(2),
                           rset.getInt(3)
                           );            
            }

            rset = stmt.executeQuery("select itemId, downloaded_time from Items");
            System.out.print("-----------------------------------------------------------Show Items Download Info----------------------------------------------------------\n");
            System.out.printf("%20s %20s\n", "[Item_id]", "[Downloaded time]");
            while(rset.next()){
                System.out.printf("%20d %20d\n", 
                           rset.getInt(1),
                           rset.getInt(2)
                           );
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
        
                
            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public void show_access_info() throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            ResultSet rset = stmt.executeQuery(
                    "select item_id, count(item_id) from AccessHistory group by item_id" );
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("--------------------------------------------------------------Show Access History-------------------------------------------------------------\n\n");
               
            
            System.out.print("------------------------------------------------------------Show Access Item History-----------------------------------------------------------\n");
            System.out.printf("%20s %20s\n", "[Item_id]", "[Access_time]");
            while (rset.next()) {
                System.out.printf("%20d %20d\n", 
                           rset.getInt(1),
                           rset.getInt(2)
                           );            
            }
            rset = stmt.executeQuery("select providerId, name, data_joined from Providers");
            System.out.print("--------------------------------------------------------------Show Provider History-------------------------------------------------------------\n");
            
            System.out.printf("%20s %20s %20s\n", "[Item_id]","[name]","[downloaded]");
            while (rset.next()) {
                System.out.printf("%20d %20s %20d\n", 
                           rset.getInt(1),
                           rset.getString(2),
                           rset.getInt(3)
                           );            
            }
            
            System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------\n");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Do you want to delete providers with total 5 downloads or less? [y/n]: ");
            String choice = br.readLine();

            if(choice.equals("y") || choice.equals("Y")){
                System.out.print("Drop prividerId: ");
                choice = br.readLine();
                int del_id = Integer.parseInt(choice);

                int result = stmt.executeUpdate("delete from Providers where providerId ='"+del_id+"'");
                if(result >0)   System.out.println("drop success");
                else{
                    System.out.println("drop failed");
                }
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            br.readLine();
                
            stmt.close();    
            conn.close();    
            
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }

    }
    public void show_profits() throws IOException{
        try { 
            Class.forName ("com.mysql.jdbc.Driver"); 
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db2016310526", "2016310526", "qweasd12"); 
            Statement stmt = conn.createStatement(); 
            
            
            System.out.print("------------------------------------------------------------Welcome to Market Place-----------------------------------------------------------\n");
            System.out.print("--------------------------------------------------------------Show Manager Profit-------------------------------------------------------------\n\n");
            ResultSet rset = stmt.executeQuery(
                    "select*from Manager" );
                   
            System.out.printf("%20s %20s\n", "[Total_profit]", "[Net_profit]");
            while (rset.next()) {
                System.out.printf("%20d %20d\n", 
                           rset.getInt(2),
                           rset.getInt(3)
                           );            
            }
            System.out.print("---------------------------------------------------------Press Enter to continue-------------------------------------------------------------\n");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            br.readLine();
                
            stmt.close();    
            conn.close();    
        }        
        catch (SQLException sqle) {         
            System.out.println("SQLException : " + sqle);        
        }
        catch (Exception e) {         
            System.out.println("Exception : " + e);        
        }
    }
    public static void main(String[] args){

        new db_proj().init();
    }
}