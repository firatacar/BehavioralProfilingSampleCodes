package SampleCodes;

import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import java.io.BufferedReader;

import java.io.FileReader;

import java.util.Arrays;
import java.util.List;

import weka.classifiers.functions.MultilayerPerceptron;

import java.io.File;

import jxl.*;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.Number;

public class JavaWekaForDataset {
	
	public static List<Integer> myUsers = Arrays.asList(29, 26, 66, 88, 77, 58, 8, 14, 7, 35, 10, 47, 71, 28, 97, 84,
			38, 72, 45, 91);

	// ACTIVITY TRAIN ALL
	public static void MULTILAYERPERCEPTRON_Activity_All_Train() throws Exception {
		BufferedReader reader;
		Instances traindata;
		WritableWorkbook workbook = null;
		WritableSheet sheet = null;

		WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
		arial10font.setColour(Colour.RED);
		WritableCellFormat arial10format_USER = new WritableCellFormat(arial10font);
		arial10format_USER.setWrap(true);

		WritableFont arial10font_3 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);

		WritableCellFormat arial10format_3 = new WritableCellFormat(arial10font_3);

		arial10format_3.setShrinkToFit(true);

		WritableFont arial10font_DETAIL = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
		arial10font_DETAIL.setColour(Colour.BLUE);
		WritableFont arial10format_DETAIL = new WritableFont(arial10font_DETAIL);

		NumberFormat fourdps = new NumberFormat("0.####");
		WritableCellFormat fourdpsFormat = new WritableCellFormat(arial10format_DETAIL, fourdps);
		fourdpsFormat.setWrap(true);

		int count1 = 0;
		int count2 = 0;

		workbook = Workbook.createWorkbook(new File(
				"C:\\Users\\fRttcr\\Desktop\\exceltest\\MULTILAYERPERCEPTRON\\ACTIVITY_TRAIN_ALL_MULTILAYERPERCEPTRON.xls"));

		for (int k = 0; k < 1; k++) {
			count1 = 0;
			count2 = 0;
			sheet = workbook.createSheet("All", k);
			for (int i = 0; i < 20; i++) {

				Label label = new Label(0, count1, myUsers.get(i).toString(), arial10format_USER);
				sheet.addCell(label);

				label = new Label(0, count1 + 1, "TOTAL", arial10format_3);
				sheet.addCell(label);

				label = new Label(0, count1 + 2, "INCLASS", arial10format_3);
				sheet.addCell(label);

				label = new Label(0, count1 + 3, "OUTCLASS", arial10format_3);
				sheet.addCell(label);

				for (int j = 1; j <= 10; j++) {
					reader = new BufferedReader(
							new FileReader("C:\\Users\\fRttcr\\Desktop\\ANN\\Train\\ActivityTrainAll\\activity"
									+ myUsers.get(i) + j + "t.arff"));
					traindata = new Instances(reader);
					reader.close();

					traindata.setClassIndex(traindata.numAttributes() - 1);

					Classifier cls = new MultilayerPerceptron();
					cls.buildClassifier(traindata);
					Evaluation eval = new Evaluation(traindata);
					eval.evaluateModel(cls, traindata);

					label = new Label(j, count2, myUsers.get(i).toString() + j, arial10format_USER);
					sheet.addCell(label);

					Number number = new Number(j, count2 + 1, eval.pctCorrect() / 100, fourdpsFormat); // Correctly
																										// classified
																										// instance
					sheet.addCell(number);

					number = new Number(j, count2 + 2, eval.truePositiveRate(0), fourdpsFormat); // Inclass
					sheet.addCell(number);

					number = new Number(j, count2 + 3, eval.trueNegativeRate(0), fourdpsFormat); // Outclass
					sheet.addCell(number);

					System.out.println("CHECK TO RUN MULTILAYERPERCEPTRON ACTIVITY TRAIN ALL");
				}

				count2 = count2 + 4;
				count1 = count1 + 4;

			}
		}
		
		workbook.write();
		workbook.close();
	}


	public static void main(String[] args) throws Exception {
		MULTILAYERPERCEPTRON_Activity_All_Train();
		//MULTILAYERPERCEPTRON_Activity_A_Train();
		//MULTILAYERPERCEPTRON_Activity_B_Train();
		//MULTILAYERPERCEPTRON_Activity_C_Train();
	}
}
