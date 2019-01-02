package SampleCodes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLToArffFiles {
	public static void main(String[] args) {

		Connection conn;

		List<Integer> personPK = new ArrayList<Integer>();
		List<Integer> personID = new ArrayList<Integer>();

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3307/mitclean_5_train?&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"username", "password");

			Statement myStat = conn.createStatement();

			ResultSet rs = myStat.executeQuery("select * from mitclean_1_20_person.persons;");

			while (rs.next()) {
				personID.add(Integer.parseInt(rs.getString(1)));
				personPK.add(Integer.parseInt(rs.getString(2)));
			}

			for (int i = 0; i < 20; i++) {

				for (int j = 1; j <= 10; j++) {

					File file = new File(
							"C:\\Users\\fRttcr\\Desktop\\ANN\\CellTrainAll\\cell" + personID.get(i) + j + "t.arff");
					FileWriter fileWriter = new FileWriter(file, false);
					BufferedWriter bWriter = new BufferedWriter(fileWriter);

					ResultSet myRs = myStat.executeQuery("select * from cell" + personID.get(i) + j + "t");

					bWriter.write("@relation cell" + personID.get(i) + j + "t");
					bWriter.newLine();
					bWriter.newLine();
					bWriter.write("@attribute celltower_oid numeric");
					bWriter.newLine();
					bWriter.write(
							"@attribute dd {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}");
					bWriter.newLine();
					bWriter.write("@attribute md {1,2,3,4,5,6,7,8,9,10,11,12}");
					bWriter.newLine();
					bWriter.write("@attribute wdu {1,2,3,4,5,6,7}");
					bWriter.newLine();
					bWriter.write("@attribute stime numeric");
					bWriter.newLine();
					bWriter.write("@attribute duration numeric");
					bWriter.newLine();
					bWriter.write("@attribute class {yes,no}");
					bWriter.newLine();
					bWriter.newLine();
					bWriter.write("@data");
					bWriter.newLine();

					while (myRs.next()) {

						if (!file.exists()) {
							file.createNewFile();
						}

						bWriter.write(myRs.getString("celltower_oid") + "," + myRs.getString("dd") + ","
								+ myRs.getString("md") + "," + myRs.getString("wdu") + "," + myRs.getString("stime")
								+ "," + myRs.getString("duration") + "," + myRs.getString("class"));
						bWriter.newLine();
					}

					bWriter.close();
				}
			}
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}
}
