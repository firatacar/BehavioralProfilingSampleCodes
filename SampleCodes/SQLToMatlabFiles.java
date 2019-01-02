package SampleCodes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLToMatlabFiles {
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

					File file = new File("C:\\Users\\fRttcr\\Desktop\\ANN2\\Train\\ActivityTrainAll\\activity"
							+ personID.get(i) + j + "t.data");
					FileWriter fileWriter = new FileWriter(file, false);
					BufferedWriter bWriter = new BufferedWriter(fileWriter);

					ResultSet myRs = myStat.executeQuery("select * from activity" + personID.get(i) + j + "t");

					bWriter.write("5");
					bWriter.newLine();
					bWriter.write("#n dd md wdu stime duration");
					bWriter.newLine();

					while (myRs.next()) {

						if (!file.exists()) {
							file.createNewFile();
						}

						bWriter.write(myRs.getString("dd") + "\t" + myRs.getString("md") + "\t" + myRs.getString("wdu")
								+ "\t" + myRs.getString("stime") + "\t" + myRs.getString("duration") + "\t"
								+ myRs.getString("class"));
						bWriter.newLine();
					}

					bWriter.close();
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
