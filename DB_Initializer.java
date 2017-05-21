package database;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.Statement;


public class DB_Initializer {

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		String  url="jdbc:mysql://localhost/schoolData" ; 
		Connection conn = null;
		Statement stmt;
		String username = "root";
		String password = "Adsf!234";
		//ResultSet resSet;
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception exc){
			System.out.println ( exc.getMessage() );
		}
		
		try{
			conn = DriverManager.getConnection(url,username,password);
		}
		catch(Exception exc)
		{
			System.out.println ( exc.getMessage() ); 
		}
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS pass_makes");
			stmt.executeUpdate("DROP TABLE IF EXISTS res_has");
			stmt.executeUpdate("DROP TABLE IF EXISTS trip_has");
			stmt.executeUpdate("DROP TABLE IF EXISTS has_driver" );
			stmt.executeUpdate("DROP TABLE IF EXISTS checks");
			stmt.executeUpdate("DROP TABLE IF EXISTS pass_complaints");
			stmt.executeUpdate("DROP TABLE IF EXISTS trip" );
			stmt.executeUpdate("DROP TABLE IF EXISTS reviews" );
			stmt.executeUpdate("DROP TABLE IF EXISTS wallet");
			stmt.executeUpdate("DROP TABLE IF EXISTS transaction");
			stmt.executeUpdate("DROP TABLE IF EXISTS reservations" );
			stmt.executeUpdate("DROP TABLE IF EXISTS checkpoint");
			stmt.executeUpdate("DROP TABLE IF EXISTS route" );
			stmt.executeUpdate("DROP TABLE IF EXISTS vehicle");
			stmt.executeUpdate("DROP TABLE IF EXISTS driver");
			stmt.executeUpdate("DROP TABLE IF EXISTS passenger" );
			stmt.executeUpdate("DROP TABLE IF EXISTS user");
			stmt.executeUpdate("DROP TABLE IF EXISTS customer_service");
			stmt.executeUpdate("DROP TABLE IF EXISTS user_login" );
			stmt.executeUpdate("DROP TABLE IF EXISTS driver_owns" );
			
			
			/*
			stmt.executeUpdate("DROP INDEX checkpoint_name_index ON checkpoint" );
			stmt.executeUpdate("DROP INDEX checkpoint_coords_index ON checkpoint" );
			stmt.executeUpdate("DROP INDEX user_name_index ON user" );
			stmt.executeUpdate("DROP INDEX user_sname_index ON user" );
			*/
			
			stmt.executeUpdate("DROP VIEW IF EXISTS credentials_view" );
			stmt.executeUpdate("DROP VIEW IF EXISTS wallet_view" );
			stmt.executeUpdate("DROP VIEW IF EXISTS transactions_view" );
			stmt.executeUpdate("DROP VIEW IF EXISTS trip_revenue_view" );
			stmt.executeUpdate("DROP VIEW IF EXISTS vehicles_check_view" );
			
			/*stmt.executeUpdate("DROP TRIGGER IF EXISTS resupdater" );
			stmt.executeUpdate("DROP TRIGGER IF EXISTS withdraw_updater" );
			stmt.executeUpdate("DROP TRIGGER IF EXISTS trip_cancellation_updater" );*/
			
			stmt.executeUpdate("DROP PROCEDURE IF EXISTS transfer_money" );
			stmt.executeUpdate("DROP PROCEDURE IF EXISTS register_user" );
			stmt.executeUpdate("DROP PROCEDURE IF EXISTS accept_reservation" );
			stmt.executeUpdate("DROP PROCEDURE IF EXISTS deny_reservation" );
			
			System.out.println("Existing tables dropped!");
			
			//user_login
			stmt.executeUpdate("CREATE TABLE user_login"
					+ "(user_id 			int PRIMARY KEY AUTO_INCREMENT"
					+ " username 		VARCHAR(20) NOT NULL UNIQUE,"
					+ " email 			VARCHAR(20) NOT NULL,"
					+ " password 		VARCHAR(20) NOT NULL )ENGINE=INNODB");
			
			//  USER	
			stmt.executeUpdate("CREATE TABLE user" 
					+ "(user_id 				int PRIMARY KEY,"
					+ " name 				VARCHAR(20) NOT NULL,"
					+ " surname 			VARCHAR(20) NOT NULL,"
					+ " age 				int,"
					+ " gender 				VARCHAR(10),"
					+ " phone_number 		int,"
					+ " birth_year 			int,"
					+ "FOREIGN KEY(user_id) references user_login(user_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	CUSTOMER SERVICE	
			stmt.executeUpdate("CREATE TABLE customer_service("
					+ " user_id 			int PRIMARY KEY,"
					+ " FOREIGN KEY(user_id) REFERENCES user_login(user_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	PASSENGER	
			stmt.executeUpdate("CREATE TABLE passenger" 
					+ "(user_id 				int PRIMARY KEY,"
					+ " FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	DRIVER	
			stmt.executeUpdate("CREATE TABLE driver" 
					+ "(user_id int PRIMARY KEY,"
					+ " driver_license_number 				int,"
					+ " driver_license_type 				VARCHAR(20),"
					+ " driver_license_expiration_y			int,"
					+ " driver_license_expiration_m			int,"
					+ " FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	VEHICLE	
			stmt.executeUpdate("CREATE TABLE vehicle"
					+ "(v_id 					int,"
					+ "	manufacturer			varchar(20),"
					+ " model 					varchar(20), "
					+ "	color					varchar(20),"
					+ "	plate_number			varchar(20),"
					+ "	primary key(v_id) ) ENGINE=INNODB");
			
			//	WALLET
			stmt.executeUpdate("Create table wallet("
					+ "	user_id			int,"
					+ "	balance			int,"
					+ "	primary key(user_id),"
					+ "	foreign key(user_id) references user(user_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	TRANSACTION	
			stmt.executeUpdate("Create table transaction("
					+ "	transaction_id		int,"
					+ "	sender_id			int,"
					+ "	receiver_id			int,"
					+ "	amount				int,"
					+ "	status				varchar(10),"
					+ "	date_of_submission 	varchar(10),"	//Can make it date
					+ "	primary key(transaction_id),"
					+ "	foreign key(sender_id) references passenger(user_id) ON DELETE CASCADE,"
					+ "	foreign key(receiver_id) references driver(user_id) ON DELETE CASCADE)ENGINE=INNODB");

			//	ROUTE
			stmt.executeUpdate("Create table route("
					+ "	r_id		int,"
					+ "	primary key(r_id) )");

			//	CHECKPOINT	
			stmt.executeUpdate("Create table checkpoint("
					+ "	cp_id			int,"
					+ "r_id				int, "
					+ "location_name	varchar(20), "
					+ "location_lat		int, "
					+ "location_lon		int, "
					+ "Price			int,"
					+ "ETA_hour			int, "
					+ "ETA_min			int,"
					+ "primary key(cp_id),"
					+ "foreign key(r_id) references route(r_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	RESERVATIONS	
			stmt.executeUpdate("Create table reservations("
					+ "	reservation_id 		int,"
					+ "	user_id				int,"
					+ "	enter_checkpoint	int, "
					+ " exit_checkpoint		int,"
					+ " Cost				int,"
					+ " status				varchar(10),"
					+ "	primary key(reservation_id ),"
					+ " foreign key(user_id) references passenger(user_id) ON DELETE CASCADE,"
					+ "	foreign key(enter_checkpoint) references checkpoint(cp_id) ON DELETE CASCADE,"
					+ "	foreign key(exit_checkpoint) references checkpoint(cp_id) ON DELETE CASCADE)ENGINE=INNODB");
			
			//	REVIEWS	
			stmt.executeUpdate("Create table reviews("
					+ "	review_id		int,"
					+ "	user_id			int, "
					+ " rating			int, "
					+ " primary key(review_id ),"
					+ "	foreign key(user_id) references passenger(user_id))ENGINE=INNODB");	//made some changes to initial report
			
			//	TRIP	
			stmt.executeUpdate("Create table trip("
					+ "	trip_id					int,"
					+ "	time_of_departure_h		int, "
					+ " time_of_departure_m		int, "
					+ " status 					varchar(20), "
					+ " primary key(trip_id ))");
						
			//	PASS_COMPLAINTS	
			stmt.executeUpdate("Create table pass_complaints("
					+ "	pass_id			int, "
					+ " service_id				int, "
					+ " subject 		varchar(10), "
					+ " message      	varchar(20), "
					+ " time			int, "
					+ "	primary key( pass_id, service_id ),"
					+ " FOREIGN KEY(pass_id) REFERENCES passenger(user_id),"
					+ " FOREIGN KEY(service_id) REFERENCES customer_service(user_id) )ENGINE=INNODB");

			//	CHECKS	
			stmt.executeUpdate("Create table checks("
					+ "	reservation_id		int, "
					+ " transaction_id		int, "
					+ "	primary key(reservation_id,transaction_id),"
					+ " FOREIGN KEY(reservation_id) REFERENCES reservations(reservation_id),"
					+ " FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id))ENGINE=INNODB");
			
			//	RES_HAS	
			stmt.executeUpdate("Create table res_has("
					+ "	reservation_id				int, "
					+ " review_id					int, "
					+ "	primary key(reservation_id, review_id),"
					+ "	foreign key(reservation_id) references reservations(reservation_id),"
					+ " foreign key(review_id) 		references reviews(review_id))ENGINE=INNODB");
			
			// DRIVER_OWNS
			stmt.executeUpdate("Create table driver_owns("
					+ "	user_id				int, "
					+ " v_id				int, "
					+ " primary key(user_id, v_id), "
					+ "	foreign key(user_id) references driver(user_id),"
					+ " foreign key(v_id) references vehicle(v_id))ENGINE=INNODB");
			
			//	TRIP_HAS	
			stmt.executeUpdate("Create table trip_has("
					+ "	r_id				int, "
					+ " trip_id 			int, "
					+ "	primary key(r_id, trip_id ),"
					+ "	foreign key(r_id)      references route(r_id),"
					+ " foreign key( trip_id ) references trip(trip_id))ENGINE=INNODB");
			
			//	HAS_DRIVER
			stmt.executeUpdate("Create table has_driver("
					+ "	user_id				int, "
					+ " trip_id 			int, "
					+ "	primary key(user_id, trip_id ),"
					+ "	foreign key(user_id) references driver(user_id),"
					+ " foreign key(trip_id) references trip(trip_id))ENGINE=INNODB");
			
			//	PASS_MAKES
			stmt.executeUpdate("CREATE TABLE pass_makes("
					+ "reservation_id		int,"
					+ "user_id				int,"
					+ "trip_id				int,"
					+ "PRIMARY KEY(reservation_id, user_id, trip_id),"
					+ "FOREIGN KEY(reservation_id) REFERENCES reservations(reservation_id),"
					+ "FOREIGN KEY(user_id) REFERENCES passenger(user_id),"
					+ "FOREIGN KEY(trip_id) REFERENCES trip(trip_id))ENGINE=INNODB");	
			
			//INSERTS - For testing purposes
			/*
			//Users
			stmt.executeUpdate("INSERT INTO user" 
				    + "VALUES (000001, 'can', 'bayraktar', 21, 'male', '01112223344', '1870-03-02')" );
			stmt.executeUpdate("INSERT INTO user" 
				    + "VALUES (000002, 'erin', 'avllazagaj', 18, 'male', '56667778899', '1999-07-8')" );
			stmt.executeUpdate("INSERT INTO user" 
				    + "VALUES (000003, 'utku', 'uckun', 57, 'male', '26667775599', '1963-01-01')" );
			stmt.executeUpdate("INSERT INTO user" 
				    + "VALUES (000004, 'ege', 'yosunkaya', 21, 'male', '78884446677', '1996-03-03')" );
			stmt.executeUpdate("INSERT INTO user" 
				    + "VALUES (000012, 'bahar', 'candan', 19, 'female', '43332221199', '1998-02-24')" );
			
			//Customer service
			stmt.executeUpdate("INSERT INTO customer_service" 
				    + "VALUES (000012)" );
			
			//Passenger
			stmt.executeUpdate("INSERT INTO passenger" 
				    + "VALUES (000001)" );
			stmt.executeUpdate("INSERT INTO passenger" 
				    + "VALUES (000002)" );
			stmt.executeUpdate("INSERT INTO passenger" 
				    + "VALUES (000003)" );
			
			//Driver
			stmt.executeUpdate("INSERT INTO driver" 
				    + "VALUES (000004, 444333222, 'B', 2019, 06)" );
			
			//Vehicle
			stmt.executeUpdate("INSERT INTO vehicle" 
					+ "VALUES (000001, 'ford', 'mustang', 'black', '06 ESD 234')" );	
			
			//Wallets
			stmt.executeUpdate("INSERT INTO wallet" 
				    + "VALUES (000001, 100)" );
			stmt.executeUpdate("INSERT INTO wallet" 
				    + "VALUES (000002, 200)" );
			stmt.executeUpdate("INSERT INTO wallet" 
				    + "VALUES (000003, 300)" );
			stmt.executeUpdate("INSERT INTO wallet" 
				    + "VALUES (000004, 400)" );
			stmt.executeUpdate("INSERT INTO wallet" 
				    + "VALUES (000012, 500)" );
			*/
			//INDICES
			
			//Checkpoint name index
			stmt.executeUpdate("CREATE INDEX checkpoint_name_index USING BTREE ON checkpoint(location_name);");
			
			//Checkpoint coords index
			stmt.executeUpdate("CREATE INDEX checkpoint_coords_index USING BTREE ON checkpoint"
					+ "(location_lat,location_lon);");
			
			//User name index
			stmt.executeUpdate("CREATE INDEX user_name_index USING BTREE ON user(name);");
			
			//User surname index
			stmt.executeUpdate("CREATE INDEX user_sname_index USING BTREE ON user(surname);");
		
			
			
			
			// VIEWS 
			
			//Credentials view
			stmt.executeUpdate("CREATE VIEW credentials_view AS (SELECT * FROM user_login)");
			
			//Wallet view
			stmt.executeUpdate("CREATE VIEW wallet_view AS (SELECT * FROM wallet)");
			
			//Transactions view
			stmt.executeUpdate("CREATE VIEW transactions_view AS (SELECT * FROM transaction)");
			
			//Trip Revenue view
			stmt.executeUpdate("CREATE VIEW trip_revenue_view AS (SELECT sum(cost) FROM reservations NATURAL "
					+ "JOIN pass_makes GROUP BY trip_id)");
			
			//Vehicles_Check view
			
			stmt.executeUpdate("CREATE VIEW vehicles_check_view AS (SELECT count(*) FROM vehicle NATURAL JOIN "
					+ "driver_owns GROUP BY user_id)");
			
			//TRIGGERS
			
			//resupdater - syntax error near (ON AFTER UPDATE)
			/*
			stmt.executeUpdate("CREATE TRIGGER resupdater ON AFTER UPDATE OF trip ON free_seats"
					+ "UPDATE reservations SET status=�cancelled� WHERE reservation_id IN"
					+ "(SELECT * FROM trip_res WHERE trip_res.trip_id = trip_id);"
					+ "END;");*/
			
			//withdraw_updater - syntax error near (ON wallet AFTER UPDATE OF wallet)
			/*
			stmt.executeUpdate("CREATE TRIGGER withdraw_updater ON wallet AFTER UPDATE OF wallet ON balance "
					+ "REFERENCING NEW ROW AS nrow "
					+ "REFERENCING OLD ROW AS orow "
					+ "WHEN ( nrow.user_id IN (SELECT user_id FROM driver)) "
					+ "BEGIN "
					+ "DECLARE total_debt INTEGER DEFAULT 0; "
					+ "(SELECT sum(cost) INTO total_debt FROM reservations NATURAL JOIN "
					+ "trip_res NATURAL JOIN has_dirver WHERE user_id = nrow.user_id AND "
					+ "reservations.status NOT EQUAL �completed�); "
					+ "IF (nrow.balance-orow.balance < total_debt) "
					+ "ROLLBACK; "
					+ "END IF; "
					+ "END;");*/
			
			//trip_cancellation_updater - syntax error near(ON wallet AFTER UPDATE OF)
			/*
			stmt.executeUpdate("CREATE TRIGGER trip_cancellation_updater ON wallet AFTER UPDATE OF trip ON "
					+ "status "
					+ "REFERENCING NEW ROW AS nrow "
					+ "REFERENCING OLD ROW AS orow "
					+ "WHEN ( nrow.status == �cancelled�) "
					+ "BEGIN "
					+ "FOR EACH ROW r IN (SELECT * FROM reservations WHERE reservation_id IN "
					+ "(SELECT * FROM trip_res WHERE trip_id = nrow.trip_id) ) "
					+ "DECLARE pass_id; "
					+ "DECLARE driv_id; "
					+ "SELECT user_id INTO (pass_id) FROM pass_makes WHERE "
					+ "reservation_id = r.reservation_id; "
					+ "SELECT user_id INTO (driv_id) FROM has_driver WHERE trip_id = "
					+ "nrow.trip_id; "
					+ "UPDATE reservations SET status = �cancelled� WHERE "
					+ "reservations_id = r.reservations_id; "
					+ "CALL transfer_money(driv_id,pass_id,r.cost); "
					+ "END FOR; "
					+ "END; ");*/
			
			//PROCEDURES
			
			//transfer_money - syntax error near(@send_uid @recv_uid)
			/*
			stmt.executeUpdate("CREATE PROCEDURE transfer_money (@send_uid INT,@recv_uid INT,@amount INT) "
					+ "AS "
					+ "BEGIN "
					+ "IF(amount <0) "
					+ "RETURN; "
					+ "END IF; "
					+ "DECLARE send_wid; "
					+ "DECLARE send_bal; "
					+ "DECLARE recv_wid; "
					+ "DECLARE recv_bal; "
					+ "SELECT wallet_id INTO (send_wid) FROM wallet NATURAL JOIN has_wallet "
					+ "WHERE user_id = send_uid; "
					+ "SELECT wallet_id INTO (recv_wid) FROM wallet NATURAL JOIN has_wallet "
					+ "WHERE user_id = recv_uid; "
					+ "SELECT balance INTO (send_bal)FROM wallet WHERE wallet_id = send_wid; "
					+ "IF(send_bal < amount) "
					+ "RETURN; "
					+ "END IF; "
					+ "START TRANSACTION; "
					+ "UPDATE wallet SET balance =balance-amount WHERE wallet_id = "
					+ "send_wid; "
					+ "UPDATE wallet SET balance =balance+amount WHERE wallet_id = "
					+ "recv_wid; "
					+ "INSERT INTO transactions VALUES(NULL,send_wid,recv_wid,amount, "
					+ "�pending�, NOW()); "
					+ "COMMIT; "
					+ "END; "
					+ "END");*/
			/*
			//register_user - syntax error near @passenger
			stmt.executeUpdate("CREATE PROCEDURE register_user (@passenger BOOLEAN, @email "
					+ "VARCHAR(250),@username VARCHAR(100),@password VARCHAR(30), @first "
					+ "VARCHAR(50),@last VARCHAR(50), @dob DATE) "
					+ "AS "
					+ "BEGIN "
					+ "DECLARE uid; "
					+ "START TRANSACTION; "
					+ "SELECT AUTO_INCREMENT INTO uid FROM INFORMATION_SCHEMA.TABLES "
					+ "WHERE TABLE_SCHEMA = 'DBNAME' AND TABLE_NAME = 'user_login'; "
					+ "INSERT INTO user_login VALUES (uid,username,email,password); "
					+ "END; "
					+ "INSERT INTO user VALUES(uid,first,last,NULL,NULL,dob); "
					+ "IF (passenger) "
					+ "INSERT INTO passenger VALUES(uid); "
					+ "ELSE "
					+ "INSERT INTO driver VALUES(uid,NULL,NULL,NULL,NULL); "
					+ "END IF; "
					+ "END; ");*/
			/*
			stmt.executeUpdate("CREATE PROCEDURE accept_reservation (@res_id INT) "
					+ "AS "
					+ "BEGIN "
					+ "UPDATE reservations SET state = �accepted� WHERE reservation_id = res_id; "
					+ "UPDATE transaction SET state = �completed� WHERE transaction_id = (SELECT "
					+ "DISTINCT transaction_id FROM checks WHERE reservation_id = res_id); "
					+ "END; ");*/
			/*
			stmt.executeUpdate("CREATE PROCEDURE deny_reservation (@res_id INT,@driv_id) "
					+ "AS "
					+ "BEGIN "
					+ "DECLARE trans_id; "
					+ "DECLARE c; "
					+ "SELECT DISTINCT transaction_id INTO (trans_id) FROM checks WHERE "
					+ "reservation_id = res_id; "
					+ "SELECT cost INTO (c) FROM reservations WHERE reservation_id = res_id; "
					+ "UPDATE reservations SET status= �denied� WHERE reservation_id=res_id; "
					+ "UPDATE trip SET free_seats = free_seats + 1 WHERE trip_id = (SELECT "
					+ "DISTINCT trip_id FROM trip_res WHERE reservation_id = res_id); "
					+ "UPDATE transaction SET state = �denied� WHERE transaction_id = "
					+ "trans_id; "
					+ "UPDATE wallet SET balance = balance + c WHERE wallet_id = (SELECT "
					+ "sender_id FROM transactions WHERE transaction_id = trans_id); "
					+ "UPDATE wallet SET balance = balance - c WHERE wallet_id = (SELECT "
					+ "receiver_id FROM transactions WHERE transaction_id = trans_id); "
					+ "END; ");*/
			
			//driver economy revenue needed
			/*
			stmt.executeUpdate("SELECT name,surname,age,gender "
					+ "FROM user "
					+ "WHERE user_id IN "
					+ "( SELECT user_id, count(*) AS travels "
					+ "FROM has_driver "
					+ "GROUP BY user_id "
					+ "SORT BY travels DESC "
					+ "LIMIT 0,10 "
					+ "); ");*/
		}
		catch(Exception exc){	 
			System.out.println ( exc.getMessage() );
		}
				
		System.out.println("Done Initializing!");		
		/*try{
			stmt = conn.createStatement();
			resSet = stmt.executeQuery("select * from customer");
			System.out.println("Customer:");
			while(resSet.next()){
				System.out.println(resSet.getString("cid") + ", " + resSet.getString("name") 
								+ ", " + resSet.getString("bdate") + ", " + resSet.getString("address")
								+ ", " + resSet.getString("city") + ", " + resSet.getString("nationality"));
			}
			System.out.println ("\nAccount:");
			resSet = stmt.executeQuery("select * from account");
			while(resSet.next()){	
				System.out.println(resSet.getString("aid") + ", " + resSet.getString("branch")
						+ ", " + resSet.getFloat("balance")+ ", " + resSet.getString("openDate"));
			}
			System.out.println ("\nOwns:");
			resSet = stmt.executeQuery("select * from owns");
			while(resSet.next()){	
				System.out.println(resSet.getString("cid") + ", " + resSet.getString("aid"));
			}
			System.out.println ();
		}catch(Exception exc){
			 System.out.println ( exc.getMessage() ); 
		}*/
	}

}
