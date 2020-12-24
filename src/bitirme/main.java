package bitirme;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;

import java.io.*;

import java.util.*;

//import org.apache.catalina.tribes.group.interceptors.TwoPhaseCommitInterceptor.MapEntry;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import bitirme.Tokenizer;

public class main {
	public static void main(String[] args) throws IOException {
//		combineTrainingFiles("TestDataSet/negative", "combinedNegativeTest.txt");
//		combineTrainingFiles("TestDataSet/positive", "combinedPositiveTest.txt");
//		combineTrainingFiles("TestDataSet/neutral", "combinedNeutralTest.txt");
		maxentTestInputCreator();
//		maxentInputCreator();
		
//		combineTrainingFiles("negative", "combinedNegative.txt");
//		combineTrainingFiles("positive", "combinedPositive.txt");
//		combineTrainingFiles("neutral", "combinedNeutral.txt");
		//calculateWithDictionary();
		//MaxEnt m = new MaxEnt();
		//MaxEnt e = new MaxEnt();
		//e.MaximumEnthropy();
		//calculateWithOurDatabase();
		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in)); System.out.print(
		 * "Enter Keyword to Search \n"); String s = br.readLine();
		 * takeTweetsFromConsole(s); br.close()
		 */

		//readRates();

	}
	
	public static void maxentTestInputCreator() throws IOException{
		BufferedReader pos = new BufferedReader(new InputStreamReader(new FileInputStream("combinedPositiveTest.txt"), "UTF-8"));
		StringBuilder posBuild = new StringBuilder();
		String temppos;
		while ((temppos = pos.readLine()) != null) {
			posBuild.append(temppos);
		}
		String positiveDic = posBuild.toString();
		//positiveDic  = positiveDic.replaceAll("[^a-zA-Z ]", "");
		positiveDic = positiveDic.replaceAll("[!'\"<>.,]", "");
		//positiveDic.replaceAll("[(\")<>]", "");
		String[] PositiveWordDictionary = positiveDic.split(" ENDOFTWEET ");
		ArrayList<String> PWD = new ArrayList<>();
		ArrayList<String> NeuWD = new ArrayList<>();
		ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : PositiveWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "1\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			PWD.add(clearedTweet);
			String tokenizedPos = Tokenizer.tokenIterator(clearedTweet);
			PWD.add(tokenizedPos+"\n");
			
		}
		FileWriter f = new FileWriter("combinedPositiveMaxent_ver2.txt");
		for (String string : PWD) {
			f.write(string);
		}
		f.close();
		BufferedReader neu = new BufferedReader(new InputStreamReader(new FileInputStream("combinedNeutralTest.txt"), "UTF-8"));
		StringBuilder neutralBuild = new StringBuilder();
		String tempneutral;
		while ((tempneutral = neu.readLine()) != null) {
			neutralBuild.append(tempneutral);
		}
		String neuDictionary = neutralBuild.toString();
		neuDictionary = neuDictionary.replaceAll("[!'\"<>.,]", "");
		String[] neuWordDictionary = neuDictionary.split(" ENDOFTWEET ");
		//ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : neuWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "0\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			NeuWD.add(clearedTweet);
			String tokenizedNeu = Tokenizer.tokenIterator(clearedTweet);
			NeuWD.add(tokenizedNeu+"\n");
		}
		FileWriter f1 = new FileWriter("combinedNeutralMaxent_ver2.txt");
		for (String string : NeuWD) {
			f1.write(string);
		}
		f1.close();
		BufferedReader neg = new BufferedReader(new InputStreamReader(new FileInputStream("combinedNegativeTest.txt"), "UTF-8"));
		StringBuilder negativeBuild = new StringBuilder();
		String temp;
		while ((temp = neg.readLine()) != null) {
			negativeBuild.append(temp);
		}
		String NegativeDictionary = negativeBuild.toString();
		NegativeDictionary = NegativeDictionary.replaceAll("[!'\"<>.,]", "");
		String[] NegativeWordDictionary = NegativeDictionary.split(" ENDOFTWEET ");
		//ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : NegativeWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "2\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			NWD.add(clearedTweet);
			String tokenizedNeg = Tokenizer.tokenIterator(clearedTweet);
			NWD.add(tokenizedNeg+"\n");
		}
		
		FileWriter f2 = new FileWriter("combinedNegativeMaxent_ver2.txt");
		for (String string : NWD) {
			f2.write(string);
		}
		f2.close();
	}
	
	public static void maxentInputCreator() throws IOException{
		BufferedReader pos = new BufferedReader(new InputStreamReader(new FileInputStream("combinedPositive.txt"), "UTF-8"));
		StringBuilder posBuild = new StringBuilder();
		String temppos;
		while ((temppos = pos.readLine()) != null) {
			posBuild.append(temppos);
		}
		String positiveDic = posBuild.toString();
		//positiveDic  = positiveDic.replaceAll("[^a-zA-Z ]", "");
		positiveDic = positiveDic.replaceAll("[!'\"<>.,]", "");
		//positiveDic.replaceAll("[(\")<>]", "");
		String[] PositiveWordDictionary = positiveDic.split(" ENDOFTWEET ");
		ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : PositiveWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "1\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			NWD.add(clearedTweet);
			String tokenizedPos = Tokenizer.tokenIterator(clearedTweet);
			NWD.add(tokenizedPos+"\n");
			
		}
		
		BufferedReader neu = new BufferedReader(new InputStreamReader(new FileInputStream("combinedNeutral.txt"), "UTF-8"));
		StringBuilder neutralBuild = new StringBuilder();
		String tempneutral;
		while ((tempneutral = neu.readLine()) != null) {
			neutralBuild.append(tempneutral);
		}
		String neuDictionary = neutralBuild.toString();
		neuDictionary = neuDictionary.replaceAll("[!'\"<>.,]", "");
		String[] neuWordDictionary = neuDictionary.split(" ENDOFTWEET ");
		//ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : neuWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "0\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			NWD.add(clearedTweet);
			String tokenizedNeu = Tokenizer.tokenIterator(clearedTweet);
			NWD.add(tokenizedNeu+"\n");
		}
		
		BufferedReader neg = new BufferedReader(new InputStreamReader(new FileInputStream("combinedNegative.txt"), "UTF-8"));
		StringBuilder negativeBuild = new StringBuilder();
		String temp;
		while ((temp = neg.readLine()) != null) {
			negativeBuild.append(temp);
		}
		String NegativeDictionary = negativeBuild.toString();
		NegativeDictionary = NegativeDictionary.replaceAll("[!'\"<>.,]", "");
		String[] NegativeWordDictionary = NegativeDictionary.split(" ENDOFTWEET ");
		//ArrayList<String> NWD = new ArrayList<>();
		
		for (String string : NegativeWordDictionary) {
			String[] tweet = string.split(" ");
			String clearedTweet = "2\t";
			for (String string2 : tweet) {
				
				if(string2.startsWith("http") || string2.startsWith("RT") || string2.startsWith("@") || string2.startsWith("#"))
					continue;
				clearedTweet+= string2+" ";
			}
			
			NWD.add(clearedTweet);
			String tokenizedNeg = Tokenizer.tokenIterator(clearedTweet);
			NWD.add(tokenizedNeg+"\n");
		}
		
		FileWriter f = new FileWriter("combinedForMaxent_ver2.txt");
		for (String string : NWD) {
			f.write(string);
		}
		f.close();
	}
//	public static void readRates() throws UnsupportedEncodingException, FileNotFoundException, IOException {
//		File[] allPositivefiles = new File("positive").listFiles();
//		BufferedReader inpositive = null;
//		StringBuilder sbpositive = new StringBuilder();
//
//		for (File f : allPositivefiles) {
//
//			if (f.getName().endsWith(".txt")) {
//				inpositive = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
//				;
//
//				String s = null;
//				while ((s = inpositive.readLine()) != null) {
//					sbpositive.append(s);
//					sbpositive.append("ENDTWEET");
//				}
//			}
//		}
//
//		inpositive.close();
//
//		String tweets = sbpositive.toString();
//
//		String oneTweet = "";
//
//		Map<String, Integer> PositiveWordNumbers = new HashMap<String, Integer>();
//		int i = 0;
//		PrintWriter writer = new PrintWriter("baseLine.txt");
//		while (tweets.length() > 0) {
//			int startOfTweet = 0;
//			int endOfTweet = tweets.indexOf("ENDTWEET");
//			oneTweet = tweets.substring(startOfTweet, endOfTweet);
//			oneTweet = oneTweet.toLowerCase();
//			ArrayList<String> words = new ArrayList<String>();
//			i++;
//			words = Tokenizer.tokenIterator(oneTweet);
//			System.out.println("positive =" + i + " =" + oneTweet);
//			for (String string : words) {
//				if (PositiveWordNumbers.containsKey(string)) {
//					PositiveWordNumbers.replace(string, PositiveWordNumbers.get(string) + 1);
//				} else {
//					PositiveWordNumbers.put(string, 1);
//				}
//			}
//			tweets = tweets.substring(tweets.indexOf("ENDTWEET") + 8);
//
//		}
//
//		File[] allNegativefiles = new File("negative").listFiles();
//		BufferedReader innegative = null;
//		StringBuilder sbnegative = new StringBuilder();
//
//		for (File f : allNegativefiles) {
//
//			if (f.getName().endsWith(".txt")) {
//				innegative = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
//				;
//
//				String s = null;
//				while ((s = innegative.readLine()) != null) {
//					sbnegative.append(s);
//					sbnegative.append("ENDTWEET");
//				}
//			}
//		}
//
//		innegative.close();
//
//		String negativetweets = sbnegative.toString();
//
//		String oneNegativeTweet = "";
//
//		Map<String, Integer> NegativeWordNumbers = new HashMap<String, Integer>();
//
//		// PrintWriter writer = new PrintWriter("baseLine.txt");
//		int j = 0;
//		while (negativetweets.length() > 0) {
//			int startOfTweet = 0;
//			int endOfTweet = negativetweets.indexOf("ENDTWEET");
//			oneNegativeTweet = negativetweets.substring(startOfTweet, endOfTweet);
//			oneNegativeTweet = oneNegativeTweet.toLowerCase();
//			ArrayList<String> words = new ArrayList<String>();
//			j++;
//			words = Tokenizer.tokenIterator(oneNegativeTweet);
//			System.out.println("negative =" + j + "=" + oneNegativeTweet);
//			for (String string : words) {
//				if (NegativeWordNumbers.containsKey(string)) {
//					NegativeWordNumbers.replace(string, NegativeWordNumbers.get(string) + 1);
//				} else {
//					NegativeWordNumbers.put(string, 1);
//				}
//			}
//			negativetweets = negativetweets.substring(negativetweets.indexOf("ENDTWEET") + 8);
//
//		}
//
//		File[] allNeutralfiles = new File("neutral").listFiles();
//		BufferedReader inNeutral = null;
//		StringBuilder sbNeutral = new StringBuilder();
//
//		for (File f : allNeutralfiles) {
//
//			if (f.getName().endsWith(".txt")) {
//				inNeutral = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
//				;
//
//				String s = null;
//				while ((s = inNeutral.readLine()) != null) {
//					sbNeutral.append(s);
//					sbNeutral.append("ENDTWEET");
//				}
//			}
//		}
//
//		inNeutral.close();
//
//		String neutraltweets = sbNeutral.toString();
//
//		String oneneutralTweet = "";
//
//		Map<String, Integer> neutralWordNumbers = new HashMap<String, Integer>();
//		int k = 0;
//		while (neutraltweets.length() > 0) {
//			int startOfTweet = 0;
//			int endOfTweet = neutraltweets.indexOf("ENDTWEET");
//			oneneutralTweet = neutraltweets.substring(startOfTweet, endOfTweet);
//			oneneutralTweet = oneneutralTweet.toLowerCase();
//			ArrayList<String> words = new ArrayList<String>();
//			k++;
//			words = Tokenizer.tokenIterator(oneneutralTweet);
//			System.out.println("neutral =" + k + "=" + oneneutralTweet);
//			for (String string : words) {
//				if (neutralWordNumbers.containsKey(string)) {
//					neutralWordNumbers.replace(string, neutralWordNumbers.get(string) + 1);
//				} else {
//					neutralWordNumbers.put(string, 1);
//				}
//			}
//			neutraltweets = neutraltweets.substring(neutraltweets.indexOf("ENDTWEET") + 8);
//
//		}
//		writer.close();
//		PrintWriter Oranlar = new PrintWriter("oranlar.txt");
//		for (String s : PositiveWordNumbers.keySet()) {
//			ArrayList<Double> val = new ArrayList<>();
//			int total = 0;
//			int numbersInPositive = PositiveWordNumbers.get(s);
//			int numbersInNeutral = 0;
//			int numbersInNegative = 0;
//			if (neutralWordNumbers.containsKey(s)) {
//				numbersInNeutral = neutralWordNumbers.get(s);
//				neutralWordNumbers.remove(s);
//			}
//			if (NegativeWordNumbers.containsKey(s)) {
//				numbersInNegative = NegativeWordNumbers.get(s);
//				NegativeWordNumbers.remove(s);
//			}
//			total = numbersInPositive + numbersInNegative + numbersInNeutral;
//			val.add(0, (double) numbersInNegative / (double) total);
//			val.add(1, (double) numbersInNeutral / (double) total);
//			val.add(2, (double) numbersInPositive / (double) total);
//			Oranlar.println(s + "|" + val.get(0) + "|" + val.get(1) + "|" + val.get(2));
//		}
//
//		for (String s : neutralWordNumbers.keySet()) {
//			ArrayList<Double> val = new ArrayList<>();
//			int total = 0;
//			int numbersInNeutral = neutralWordNumbers.get(s);
//			int numbersInNegative = 0;
//			if (NegativeWordNumbers.containsKey(s)) {
//				numbersInNegative = NegativeWordNumbers.get(s);
//				NegativeWordNumbers.remove(s);
//			}
//			total = numbersInNegative + numbersInNeutral;
//			val.add(0, (double) numbersInNegative / (double) total);
//			val.add(1, (double) numbersInNeutral / (double) total);
//			val.add(2, 0.0);
//			Oranlar.println(s + "|" + val.get(0) + "|" + val.get(1) + "|" + val.get(2));
//		}
//
//		for (String s : NegativeWordNumbers.keySet()) {
//			ArrayList<Double> val = new ArrayList<>();
//			int total = 0;
//			int numbersInNegative = NegativeWordNumbers.get(s);
//
//			total = numbersInNegative;
//			val.add(0, 1.0);
//			val.add(1, 0.0);
//			val.add(2, 0.0);
//			Oranlar.println(s + "|" + val.get(0) + "|" + val.get(1) + "|" + val.get(2));
//		}
//		Oranlar.close();
//	}
	public static void combineTrainingFiles(String directory, String outputName) throws NumberFormatException, IOException{
		File[] allPositivefiles = new File(directory).listFiles();
		BufferedReader inpositive = null;
		StringBuilder sbpositive = new StringBuilder();

		for (File f : allPositivefiles) {

			if (f.getName().endsWith(".txt")) {
				inpositive = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
				;

				String s = null;
				while ((s = inpositive.readLine()) != null) {
					sbpositive.append(s);
					sbpositive.append(" ENDOFTWEET ");
				}
			}
		}

		inpositive.close();
		FileWriter f = new FileWriter(outputName);
		f.write(sbpositive.toString());
		f.close();
	}


//		/*String kelime = null;
//		while ((kelime = oranlar.readLine()) != null) {
//			StringBuilder oranString = new StringBuilder();
//			oranString.append(kelime);
//
//			String input = oranString.toString();
//			ArrayList<Double> ratesOfWord = new ArrayList<>();
//			String key = input.substring(0, input.indexOf("|"));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(0, Double.parseDouble(input.substring(0, input.indexOf("|"))));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(1, Double.parseDouble(input.substring(0, input.indexOf("|"))));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(2, Double.parseDouble(input));
//			rates.put(key, new ArrayList<>(ratesOfWord));
//
//		}
//		oranlar.close();*/
//		String tweets = sbpositive.toString();
//		//String oneTweet = "";
//		PrintWriter writer = new PrintWriter(outputName);
//		writer.print(tweets);
//		writer.close();
//	}
//	
//	public static void calculateWithOurDatabase()
//			throws UnsupportedEncodingException, FileNotFoundException, IOException {
//		File[] allPositivefiles = new File("negativeCombined").listFiles();
//		BufferedReader inpositive = null;
//		StringBuilder sbpositive = new StringBuilder();
//
//		for (File f : allPositivefiles) {
//
//			if (f.getName().endsWith(".txt")) {
//				inpositive = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
//				;
//
//				String s = null;
//				while ((s = inpositive.readLine()) != null) {
//					sbpositive.append(s);
//					sbpositive.append("ENDTWEET");
//				}
//			}
//		}
//
//		inpositive.close();
//
//		BufferedReader oranlar = new BufferedReader(new InputStreamReader(new FileInputStream("oranlar.txt"), "UTF-8"));
//
//		Map<String, ArrayList<Double>> rates = new HashMap<String, ArrayList<Double>>();
//
//		String kelime = null;
//		while ((kelime = oranlar.readLine()) != null) {
//			StringBuilder oranString = new StringBuilder();
//			oranString.append(kelime);
//
//			String input = oranString.toString();
//			ArrayList<Double> ratesOfWord = new ArrayList<>();
//			String key = input.substring(0, input.indexOf("|"));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(0, Double.parseDouble(input.substring(0, input.indexOf("|"))));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(1, Double.parseDouble(input.substring(0, input.indexOf("|"))));
//			input = input.substring(input.indexOf("|") + 1);
//			ratesOfWord.add(2, Double.parseDouble(input));
//			rates.put(key, new ArrayList<>(ratesOfWord));
//
//		}
//		oranlar.close();
//		String tweets = sbpositive.toString();
//		String oneTweet = "";
//		PrintWriter writer = new PrintWriter("baseLineOurDatabaseTest.txt");
//		int i = 0;
//		while (tweets.length() > 0) {
//			int startOfTweet = 0;
//			int endOfTweet = tweets.indexOf("ENDTWEET");
//			oneTweet = tweets.substring(startOfTweet, endOfTweet);
//			oneTweet = oneTweet.toLowerCase();
//			ArrayList<String> words = new ArrayList<String>();
//			i++;
//			words = Tokenizer.tokenIterator(oneTweet);
//			System.out.println("positive =" + i + " =" + oneTweet);
//			baseLine(oneTweet, words, writer, rates);
//			tweets = tweets.substring(tweets.indexOf("ENDTWEET") + 8);
//
//		}
//		writer.close();
//	}
//
//	private static void baseLine(String oneTweet, ArrayList<String> words, PrintWriter writer,
//			Map<String, ArrayList<Double>> wordValueMap) {
//		double result = 0;
//		double effect = 0;
//		int index = 0;
//		for (String word : words) {
//			if (wordValueMap.containsKey(word)) {
//
//				for (int i = 0; i < 3; i++) {
//					ArrayList<Double> values = new ArrayList<Double>();
//					values.addAll(wordValueMap.get(word));
//					if (values.get(i) > effect) {
//
//						effect = values.get(i);
//						index = i;
//					}
//				}
//			}
//			if (index == 0) {
//				result -= effect;
//			} else if (index == 2) {
//				result += effect;
//			}
//		}
//		if (result < 0) {
//			writer.println(oneTweet + "-> Negative");
//		} else if (result > 0) {
//			writer.println(oneTweet + "-> Positive");
//		} else {
//			writer.println(oneTweet + "-> Neutral");
//		}
//	}
//
//	public static void takeTweetsFromConsole(String keyword) throws IOException {
//		/*
//		 * if (args.length < 1) { System.out.println(
//		 * "java twitter4j.examples.search.SearchTweets [query]");
//		 * System.exit(-1); }
//		 */
//		BufferedWriter out = new BufferedWriter(new FileWriter("file1.txt"));
//		// Twitter twitter = new TwitterFactory().getInstance();
//		try {
//			int i = 209;
//			Query query = new Query(keyword);
//			QueryResult result;
//			do {
//				ConfigurationBuilder cb = new ConfigurationBuilder();
//				cb.setDebugEnabled(true).setOAuthConsumerKey("AefOjLks5ZoQuPJWAx1xouRYS")
//						.setOAuthConsumerSecret("sBi3mPem1MStK6g5OQp84buRpRweFiC92hZVZUqF2xVPifuTjD")
//						.setOAuthAccessToken("167362117-xmjT0vAQxNiO6aqUtsdYmp59gc3LpNZqAaIdtQv4")
//						.setOAuthAccessTokenSecret("6QJqBP7b2wFWDCqk63ydAa8EYzkkqFavOrpfDCCWsXpiZ");
//
//				TwitterFactory tf = new TwitterFactory(cb.build());
//				Twitter twitter = tf.getInstance();
//				result = twitter.search(query);
//				List<Status> tweets = result.getTweets();
//
//				String twit = "";
//
//				for (Status tweet : tweets) {
//
//					twit = " - " + tweet.getText();
//					out.write((i + 1) + ") ");
//					out.write(twit);
//					out.newLine();
//					System.out.println(twit);
//					// Tokenizer t = new Tokenizer();
//					// t.tokenizer(twit);
//					i++;
//				}
//
//			} while ((query = result.nextQuery()) != null);
//
//			out.close();
//			System.exit(0);
//		} catch (TwitterException te) {
//			te.printStackTrace();
//			System.out.println("Failed to search tweets: " + te.getMessage());
//			System.exit(-1);
//		}
//	}
//
//	public static Map<String, List<Double>> ExcelReader() {
//		try {
//			String file = "C:\\Users\\Oguzhan\\workspace\\bitirme\\STNsubset.xls";
//			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
//			HSSFWorkbook wb = new HSSFWorkbook(fs);
//			HSSFSheet sheet = wb.getSheetAt(0);
//			HSSFRow row;
//			HSSFCell cell;
//
//			int rows; // No of rows
//			rows = sheet.getPhysicalNumberOfRows();
//
//			int cols = 4; // No of columns
//
//			Map<String, List<Double>> mymap = new HashMap<String, List<Double>>();
//			ArrayList<Double> values = new ArrayList<Double>();
//			ArrayList<Double> temp = new ArrayList<Double>();
//
//			for (int r = 0; r < rows; r++) {
//
//				row = sheet.getRow(r);
//				values.clear();
//				if (row != null) {
//					TurkishMorphParser parser;
//					String[] kelimeler = row.getCell(0).toString().split(" , ");
//					parser = TurkishMorphParser.createWithDefaults();
//					ArrayList<String> lemmas = new ArrayList<>();
//
//					for (int c = 1; c < cols; c++) {
//						cell = row.getCell(c);
//						if (cell != null) {
//							values.add(Double.parseDouble(cell.toString()));
//
//						}
//					}
//					for (String s : kelimeler) {
//						List<MorphParse> parses = parser.parse(s);
//						if (parses.size() > 0) {
//
//							mymap.put(parses.get(0).getLemma(), new ArrayList<Double>(values));
//						} else {
//							mymap.put(s, new ArrayList<Double>(values));
//						}
//					}
//
//				}
//			}
//			PrintWriter writer = new PrintWriter("map.txt");
//			for (String s : mymap.keySet()) {
//
//				writer.println(s + "|" + mymap.get(s).get(0) + "|" + mymap.get(s).get(1) + "|" + mymap.get(s).get(2));
//
//			}
//			return mymap;
//		}
//
//		catch (Exception ioe) {
//
//			ioe.printStackTrace();
//			return null;
//		}
//	}
//
//	public static ArrayList<String> tokenizer(String tweet) {
//		ArrayList<String> tokens = new ArrayList<String>();
//		String oneWord = "";
//		// tweet = tweet.replaceAll("[.,']", "");
//		tweet = tweet.replaceAll("[!?>.,'<)(;\r:\n\t\"]", " ");
//		tweet = tweet.replace('ý', 'i');
//		tweet = tweet.replace('Ý', 'I');
//		tweet = tweet.replace('ü', 'u');
//		tweet = tweet.replace('Ü', 'U');
//		tweet = tweet.replace('ð', 'g');
//		tweet = tweet.replace('Ð', 'g');
//		tweet = tweet.replace('ö', 'o');
//		tweet = tweet.replace('Ö', 'O');
//		tweet = tweet.replace('Ç', 'C');
//		tweet = tweet.replace('ç', 'c');
//		tweet = tweet.replace('Þ', 'S');
//		tweet = tweet.replace('s', 's');
//
//		while (!tweet.isEmpty()) {
//			oneWord = tweet.substring(0, tweet.indexOf(" "));
//			if (!(oneWord.startsWith("#") || oneWord.startsWith("@") || oneWord.startsWith("https://")
//					|| oneWord.startsWith("http://"))) {
//				tokens.add(oneWord);
//			}
//			if (tweet.contains(" ")) {
//				tweet = tweet.substring(tweet.indexOf(" ") + 1);
//			} else {
//				tweet = "";
//			}
//
//		}
//		return tokens;
//	}
//
//	public static void calculateWithDictionary() throws IOException {
//		BufferedReader neg = new BufferedReader(new InputStreamReader(new FileInputStream("negativeNew.txt"), "UTF-8"));
//		StringBuilder negativeBuild = new StringBuilder();
//		String temp;
//		while ((temp = neg.readLine()) != null) {
//			negativeBuild.append(temp);
//		}
//		String NegativeDictionary = negativeBuild.toString();
//		String[] NegativeWordDictionary = NegativeDictionary.split("#n");
//		ArrayList<String> NWD = new ArrayList<>();
//		for (String string : NegativeWordDictionary) {
//			NWD.add(string);
//		}
//
//		BufferedReader pos = new BufferedReader(new InputStreamReader(new FileInputStream("positiveNew.txt"), "UTF-8"));
//		StringBuilder positiveBuild = new StringBuilder();
//		String tempp;
//		while ((tempp = pos.readLine()) != null) {
//			positiveBuild.append(tempp);
//		}
//		String PositiveDictionary = positiveBuild.toString();
//		String[] PositiveWordDictionary = PositiveDictionary.split("#n");
//		ArrayList<String> PWD = new ArrayList<>();
//		for (String string : PositiveWordDictionary) {
//			PWD.add(string);
//		}
//
//		File[] allPositivefiles = new File("TestDataSet/negative").listFiles();
//		BufferedReader inpositive = null;
//		StringBuilder sbpositive = new StringBuilder();
//
//		for (File f : allPositivefiles) {
//
//			if (f.getName().endsWith(".txt")) {
//				inpositive = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
//				;
//
//				String s = null;
//				while ((s = inpositive.readLine()) != null) {
//					sbpositive.append(s);
//					sbpositive.append("ENDTWEET");
//				}
//			}
//		}
//
//		inpositive.close();
//
//		String tweets = sbpositive.toString();
//		String oneTweet = "";
//		PrintWriter writer = new PrintWriter("baseLineDictionaryTest.txt");
//		int i = 0;
//		while (tweets.length() > 0) {
//			int startOfTweet = 0;
//			int endOfTweet = tweets.indexOf("ENDTWEET");
//			oneTweet = tweets.substring(startOfTweet, endOfTweet);
//			oneTweet = oneTweet.toLowerCase();
//			ArrayList<String> words = new ArrayList<String>();
//			i++;
//			words = Tokenizer.tokenIterator(oneTweet);
//			System.out.println("positive =" + i + " =" + oneTweet);
//
//			double result = 0;
//			for (String string : words) {
//				if (PWD.contains(string)) {
//					result++;
//				}
//				if (NWD.contains(string)) {
//					result--;
//				}
//			}
//
//			if (result < 0) {
//				writer.println(oneTweet + "-> Negative");
//			} else if (result > 0) {
//				writer.println(oneTweet + "-> Positive");
//			} else {
//				writer.println(oneTweet + "-> Neutral");
//			}
//
//			tweets = tweets.substring(tweets.indexOf("ENDTWEET") + 8);
//
//		}
//		writer.close();
//	}

}
