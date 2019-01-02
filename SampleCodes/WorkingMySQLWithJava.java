package SampleCodes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// For Train Data
public class WorkingMySQLWithJava {
	public static void main(String[] args) {

		Connection conn;

		List<Integer> personPK = new ArrayList<Integer>();
		List<Integer> personID = new ArrayList<Integer>();

		try {
			// To connect MySQL.
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/mitclean_5_train?&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"username", "password");
			Statement myStat = conn.createStatement();

			// To select persons.
			ResultSet rs = myStat.executeQuery("select * from mitclean_1_20_person.persons;");
			while (rs.next()) {
				personID.add(Integer.parseInt(rs.getString(1)));
				personPK.add(Integer.parseInt(rs.getString(2)));
			}

			for (int i = 0; i < 20; i++) {
				for (int j = 1; j <= 10; j++) {
					// Create new table for each person. Then insert "class" column for persons and insert yes data to selected person.
					myStat.execute("create table mitclean_5_train.activity" + personID.get(i) + j
							+ "t as select person_oid, dd, md,wdu, stime, duration from mitclean_4_train_c_d.activityspanclean"
							+ personPK.get(i) + "_" + personID.get(i) + "a_" + j + "c;");
					myStat.execute("alter table mitclean_5_train.activity" + personID.get(i) + j
							+ "t add column class varchar(5) default 'yes';");
					for (int k = 0; k < 20; k++) {
						if (i == k) {
							k++;

						}
						if (k < 20) {
							// Insert other persons data and set no data to out of the selected person.
							myStat.execute("insert into mitclean_5_train.activity" + personID.get(i) + j
									+ "t (person_oid, dd, md, wdu,stime,duration) select person_oid,dd,md,wdu,stime,duration from mitclean_4_train_c_d.activityspanclean"
									+ personPK.get(k) + "_" + personID.get(k) + "a_" + j + "d;");
							myStat.execute("update mitclean_5_train.activity" + personID.get(i) + j
									+ "t set class = 'no' where person_oid=" + personID.get(k) + ";");
						}
					}
					// After insert all data to new table then remove the "person_oid" column.
					myStat.execute("alter table mitclean_5_train.activity" + personID.get(i) + j
							+ "t drop column person_oid;");
				}

			}

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

}
