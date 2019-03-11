package rainfall;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;


import org.apache.commons.math3.distribution.NormalDistribution;


public class CsvParsers  {

	public CSVReader reader;
	public static CSVWriter writer;
	public String sourceFile;
	public String destFile;
	public static List<String[]> cvsData;
	public static List<Double> temperatureData;
	public static List<Integer> dayIndexData;
	public static List<Double> masterRainData;
	public static List<Integer> leadKeeper;
	
	public static List<RainData> rainData99;
	public static List<RainData> rainData00;
	public static List<RainData> rainData01;
	public static List<RainData> rainData02;
	public static List<RainData> rainData03;
	public static List<RainData> rainData04;
	public static List<RainData> rainData05;
	public static List<RainData> rainData06;
	public static List<RainData> rainData07;
	public static List<RainData> rainData08;
	public static List<RainData> rainData09;
	public static List<RainData> rainData10;
	public static List<RainData> rainData11;
	public static List<RainData> rainData12;
	public static List<RainData> rainData13;
	public static List<RainData> rainData14;
	public static List<RainData> rainData15;
	public static List<RainData> rainData16;
	public static List<RainData> rainData17;

	public static List<String> myDates01;
	public static List<String> myDates02;

	public static List<Date> dateHashMap;
	public static List<Quartz> mArray;
	public static List<Integer> indexKeeper;
	public static int kip = 0;

	public List<String> dateHolder;
	public List<String> leapDateHolder;
	public List<FrequencyMap> frequencyMap;
	public List<List<RainData>> rainDataHolder;
	private static NormalDistribution dist;
	
	private static String[] colData;
	public static NormalDistribution distNorm;

	public void parseCSV(String sourceFile, String destFile) {
		this.sourceFile = sourceFile;
		this.destFile = destFile;

		
		try {
			reader = new CSVReader(new FileReader(sourceFile), '\t', '"', 2);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			writer = new CSVWriter(new FileWriter(destFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dateHashMap = new ArrayList<>();

		cvsData = new ArrayList<String[]>();
		temperatureData = new ArrayList<Double>();
		masterRainData = new ArrayList<>();
		
	   

		rainData99 = new ArrayList<>();
		rainData00 = new ArrayList<>();
		rainData01 = new ArrayList<>();
		rainData02 = new ArrayList<>();
		rainData03 = new ArrayList<>();
		rainData04 = new ArrayList<>();
		rainData05 = new ArrayList<>();
		rainData06 = new ArrayList<>();
		rainData07 = new ArrayList<>();
		rainData08 = new ArrayList<>();
		rainData09 = new ArrayList<>();
		rainData10 = new ArrayList<>();
		rainData11 = new ArrayList<>();
		rainData12 = new ArrayList<>();
		rainData13 = new ArrayList<>();
		rainData14 = new ArrayList<>();
		rainData15 = new ArrayList<>();
		rainData16 = new ArrayList<>();
		rainData17 = new ArrayList<>();
		rainDataHolder = new ArrayList<>();
		leadKeeper = new ArrayList<>();
		frequencyMap = new ArrayList<>();

		rainDataHolder.add(rainData99);
		rainDataHolder.add(rainData00);
		rainDataHolder.add(rainData01);
		rainDataHolder.add(rainData02);
		rainDataHolder.add(rainData03);
		rainDataHolder.add(rainData04);
		rainDataHolder.add(rainData05);
		rainDataHolder.add(rainData06);
		rainDataHolder.add(rainData07);
		rainDataHolder.add(rainData08);
		rainDataHolder.add(rainData09);
		rainDataHolder.add(rainData10);
		rainDataHolder.add(rainData11);
		rainDataHolder.add(rainData12);
		rainDataHolder.add(rainData13);
		rainDataHolder.add(rainData14);
		rainDataHolder.add(rainData15);
		rainDataHolder.add(rainData16);
	//	rainDataHolder.add(rainData17);
	
		
		 mArray = new ArrayList<>();

		indexKeeper = new ArrayList<>();

		dayIndexData = new ArrayList<>();

		myDates01 = new ArrayList<>();
		myDates02 = new ArrayList<>();
		dateHolder = new ArrayList<>();
		leapDateHolder = new ArrayList<>();
		
		String[] headerArray = new String[50];
		headerArray[0] = "The Dates";
		headerArray[1] = "Rain Data 1999";
		
		dateHolder.add("1-Jan-99");
		

		for (int i = 0; i < 18; i++) {
			String myYear = Integer.toString(i);
			String formated = String.format("%02d", i);
			String date = "1-Jan";
			String myDate = String.join("-", date, formated);
		
			dateHolder.add(myDate);
		}
		
		
		leapDateHolder.add("1-Mar-99");
		for (int i = 0; i < 18; i++) {
			String myYear = Integer.toString(i);
			String formated = String.format("%02d", i);
			String date = "1-Mar";
			String myDate = String.join("-", date, formated);
		
			
			leapDateHolder.add(myDate);
//			System.out.println(myDate);
		}
		
		
		
		
		int jumper = 0;
		for (int i =0; i < 17; i++) {
			
			{
			String formated = String.format("%02d", 00 + i);
			String newFormated = formated.substring(Math.max(formated.length() - 2, 0));
			String kdate = "Rain Data 20";
			String kmyDate = String.join("", kdate, newFormated);
			headerArray[jumper + 3] = kmyDate;
			jumper+=2;
			}
			
		}
		
		int num = headerArray.length;
		headerArray[42] = "Frequency";
		headerArray[43] = "Julian";
		headerArray[44] = "Log X";
		headerArray[45] = "Log X*Freq";
		headerArray[39] = "Mean";
		headerArray[40] = "Standard Deviation";
		headerArray[41] = "Probability";
		String[] xpliter = new String[5];
		String SubString;
		String[] nextLine;
		String date;
		String[] subdate;
		// int myDate;
		// int myMontth;
		// int myYear;
		int index = 0;
		int counter = 0;
		int count = 0;
		int myCount = 0;
		int k = 0;

		
		
		writer.writeNext(headerArray);

		try {
			while ((nextLine = reader.readNext()) != null) {
				count++;

				if (nextLine != null && count > 1) {

					SubString = Arrays.toString(nextLine).substring(1, (Arrays.toString(nextLine).length()) - 1);
					xpliter = SubString.split(",");
					if (xpliter.length != 0) {
						masterRainData.add((Double.parseDouble(xpliter[5])));
						myDates01.add(xpliter[0]);

						// writer.writeNext(xpliter);

						for (int i = 0; i < dateHolder.size(); i++) {
							if (xpliter[0].equals(dateHolder.get(i))) {

								String formated = String.format("%02d", 99 + i);
								String newFormated = formated.substring(Math.max(formated.length() - 2, 0));
								String kdate = "year";
								String kmyDate = String.join("", kdate, newFormated);

								Date newDate = new Date(kmyDate, counter);
							//	System.out.println("The Counter: " + counter + "         the Date: " + myDates01.get(counter));
								dateHashMap.add(newDate);

							}

						}
						
						for (int i = 0; i < leapDateHolder.size(); i++){
							if (xpliter[0].equals(leapDateHolder.get(i))){
								/* && myDates01.get(counter-1).equalsIgnoreCase(anotherString)// 28-Feb*/
								
								String myDate = myDates01.get(counter-1);
								String formattedDate = myDate.substring(0, myDate.length() - 3);
								if(formattedDate.equalsIgnoreCase("28-Feb")){
									leadKeeper.add(counter);
								}
							
								
							}
							
						}

					}
					counter++;
				}

			} // end of while loop


			int x =0; 
			for(Integer leapKeeper: leadKeeper ){
				myDates01.add(leapKeeper + x, "not leap");
				x++;
			}

			int z = 0;
			for (int i = 0; i < dateHashMap.size() - 1; i++) {
				int leapYear = 0;
				int offset = 0;
				int incrementOffset = 0;
				String formated = String.format("%02d", 99 + i);
				String newFormated = formated.substring(Math.max(formated.length() - 2, 0));
				int lastYearDigit = Integer.parseInt(newFormated);
				
				String kdate = "rainData";
				String kmyDate = String.join("", kdate, newFormated);
				
				if(lastYearDigit%4 == 0){
					leapYear = 1;
					
				}else{
					incrementOffset = leadKeeper.get(z);
					z++;
				}
			//	System.out.println("leaap year check: " + leapYear);

				feedData(rainDataHolder.get(i), dateHashMap.get(i).getValue(), dateHashMap.get(i + 1).getValue(), leapYear, incrementOffset);
				
				
			}
			
			List<RainData> myData = new ArrayList(); 
			
			int myIndex = 0;
			for(List<RainData> rainList : rainDataHolder) {
				
				
					myData = rainList;
		
					
			
				dateReaper(myData, index);
			//	System.out.println("--------------------------------------------------" + myIndex +"the size:  " + rainList.size());
				
				myIndex++;
			}
			int q = 0;
			for (Quartz triples : mArray) {
			//	System.out.print(triples.toString() + indexKeeper.get(q) + "  :   ");
			//	System.out.println();
				q++;
			}
		

			int cummulativeFrequency = 0;
			int dateIncrement = 0;
			int julian = 0;
		//	for(List<Double> rainHolder: rainDataHolder){// running 18 times  // the rainHolder is of size 18
			for (int i = 0; i <366; i++) {//rainHolder.size() 366
				
				colData = new String[46];
				List<Double> doubleData = new ArrayList<>();
				int j = 1;
				int w =2;
				int frequency = 0;
				for (List<RainData> rainList : rainDataHolder) {// another 18 runs one iteration feeds row data // 	another 365
					//System.out.println("the new size:  " + rainList.size());
					colData[0] = myDates01.get(dateIncrement);
					colData[j] = Double.toString(rainList.get(i).getRaindata());
					doubleData.add(rainList.get(i).getRaindata());
					if(!rainList.get(i).isThreshold()){
						colData[w] = "    --";
					}else{
					colData[w] = String.valueOf(rainList.get(i).isThreshold());
					frequency++;
					}
//					System.out.println("the j: " + j);
					j+=2;
					w+=2;
				}
				
				
				Double mean = findMean(doubleData);
				Double sd = findSd(doubleData);
				colData[42] = Double.toString(frequency);
				colData[43] = Double.toString(dateIncrement);
				colData[44] = Double.toString(Math.log(dateIncrement));
				colData[45] = Double.toString(Math.log(dateIncrement)*frequency);
				writer.writeNext(colData);

				if(dateIncrement > 59 && dateIncrement < 128 ){
					
					frequencyMap.add(new FrequencyMap(dateIncrement, frequency));
				}
				
				
				dateIncrement++;
			}
			
			int cumFreq = getCumFreq(frequencyMap);
			double getLnMean = getLnMean(frequencyMap);
			double getSd = getSd(frequencyMap);
			distNorm = new NormalDistribution(getLnMean, getSd);
			 double inter = distNorm.inverseCumulativeProbability(0.9);
			 int juliandate = (int) Math.exp(inter); 
			 
			 System.out.println(inter);
			
			String jDate = myDates01.get(juliandate);
			 System.out.println(juliandate + "   " +  jDate);
		
		
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private double getSd(List<FrequencyMap> frequencyMap2) {
		double sum = 0;
		double julian = 0;
		double cumFreq = getCumFreq(frequencyMap2) + 1;
		double mean = getLnMean(frequencyMap2);
		//Math.sqrt(sum)/(sdInput.size() - 1)		
		for(FrequencyMap freq: frequencyMap2){
			
			julian = Math.log(freq.getJulian());
//			System.out.println("Julian    " + (julian- mean) +"      "+ mean);

			sum += Math.pow(Math.abs(julian - mean), 2);			
		}
		System.out.println("SD    " + sum/cumFreq);
		return Math.sqrt(sum/(cumFreq));
		
		
	}

	private double getLnMean(List<FrequencyMap> frequencyMap2) {
		List<Double> convertToLnList = new ArrayList<>();
		double julian = 0;
		int frequency = 0;
		double lnAverage = 0;
		double cumFreq = getCumFreq(frequencyMap2) + 1;
		for(FrequencyMap freq: frequencyMap2){
			julian = Math.log(freq.getJulian());
			frequency = freq.getFrequency();
			lnAverage += julian * frequency;
			convertToLnList.add(julian);	
			
		}
		System.out.println("The cumSum : " + lnAverage/cumFreq);
		//224 cumsum
		return lnAverage/cumFreq;
		
	}

	private int getCumFreq(List<FrequencyMap> frequencyMap2) {
		int cummulativeFrequency = 0;
		for(FrequencyMap freq: frequencyMap2){
			cummulativeFrequency += freq.getFrequency();
			
		}
		return (cummulativeFrequency - 1);
	}

	int dateIndex = 0;
	public void feedData(List<RainData> myRainData, int start, int end, int leapYear, int offset) {
		int span = end -start; 
	//	System.out.println("The startspan : "  + start + "The end " + end + "   Offfset: " + offset + "  the real index  " + ( offset - start));
		for (int i = start; i < end; i++) {
			dateIndex++;
			myRainData.add(new RainData(masterRainData.get(i), false));
			myDates02.add(myDates01.get(i));

		}
		
		if(leapYear == 0){
			RainData empty = new RainData(0.0, false);
		//	System.out.println("If leap year function check: NOT a leap year");

			myRainData.add(59, empty);
//			myDates01.add(start + 59, "not leap");
			
		}
//		for (int i = 0; i < 365; i++) {
//			System.out.println("inner data: " + myDates02.get(i) + "         the rain " + myRainData.get(i) );
//		}


	}

	public Double findMean(List<Double> input){
		Double y = 0.0; 
		
		for(Double inputData: input){
			y += inputData;
		}
		return y/input.size();
		
	}
	public  Double findSd(List<Double> sdInput){
		double sum = 0; 
		double mean = findMean(sdInput);
		//Math.sqrt(sum)/(sdInput.size() - 1)		
		for(Double i: sdInput){
			sum += Math.pow(Math.abs(i - mean), 2);			
		}
		
		return Math.sqrt(sum/(sdInput.size()));
		
		
	}

	public static List<String> getMyDates01() {
		return myDates01;
	}

	public static void setMyDates01(List<String> myDates01) {
		CsvParsers.myDates01 = myDates01;
	}

	public CSVReader getReader() {
		return reader;
	}

	public void setReader(CSVReader reader) {
		this.reader = reader;
	}

	public CSVWriter getWriter() {
		return writer;
	}

	public void setWriter(CSVWriter writer) {
		this.writer = writer;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getDestFile() {
		return destFile;
	}

	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}

	public static List<String[]> getCvsData() {
		return cvsData;
	}

	public static void setCvsData(List<String[]> cvsData) {
		CsvParsers.cvsData = cvsData;
	}

	public static List<Double> getTemperatureData() {
		return temperatureData;
	}

	public static void setTemperatureData(List<Double> temperatureData) {
		CsvParsers.temperatureData = temperatureData;
	}

	public static List<Integer> getDayIndexData() {
		return dayIndexData;
	}

	public static void setDayIndexData(List<Integer> dayIndexData) {
		CsvParsers.dayIndexData = dayIndexData;
	}


	public static double averageTemperature(ArrayList<Double> tempData) {
		double average;
		double accumulator = 0;
		int counter = 0;
		for (Double temp : tempData) {
			counter++;
			accumulator += temp;
		}

		average = accumulator / counter;
		return average;

	}

	public static Double[] getTemperatureArray() {
		Double[] y = new Double[temperatureData.size()];
		y = temperatureData.toArray(y);

		return y;

	}
	public void getProb(List<Double> dataLn){

		 double mean = findMean(dataLn);
		 double sd  = findSd(dataLn);
	//	 System.out.println( sd + "          " + mean + "  " + dataLn.size());
	
		 
		 distNorm = new NormalDistribution(mean, sd);
		 double inter = distNorm.inverseCumulativeProbability(0.99);
	//	 System.out.println(inter);
	//	 System.out.println(Math.exp(inter));
	}
	public static void dateReaper(List<RainData> rainList , int index) {

		
		for (int i = 3; i < rainList.size(); i++) {
			
//			System.out.println("  first-number: " + array[i - 3] + "   second-number: " + array[i - 2]
//					+ "  third-number: " + array[i - 1] + "  fourth-number" + array[i] + "  sum: =  "
//					+ (array[i - 3] + array[i - 2] + array[i - 1] + array[i]));
			if ((rainList.get(i - 3).getRaindata() + rainList.get(i - 2).getRaindata() + rainList.get(i - 1).getRaindata() + rainList.get(i).getRaindata()) >= 40) {
				mArray.add(new Quartz(rainList.get(i - 3).getRaindata(), rainList.get(i - 2).getRaindata(), rainList.get(i - 1).getRaindata(),rainList.get(i ).getRaindata()));
				indexKeeper.add(i- 1);
				rainList.get(i).setThreshold(true);
			
				i += 3;
		

			}
			
//			writer.writeNext(colData);
		
		
		}

	}
	
	
	
	


}
