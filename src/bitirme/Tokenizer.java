package bitirme;

import zemberek.*;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.morphology.*;

import zemberek.core.turkish.*;

import com.google.common.base.Joiner;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.glass.ui.Application;
import com.sun.org.apache.xpath.internal.operations.Variable;

//import org.antlr.v4.runtime.Token;
import java.util.*;
import java.util.Map.Entry;

import zemberek.tokenizer.ZemberekLexer;
import zemberek.tokenizer.antlr.TurkishLexer;

import java.io.*;
import org.junit.Assert;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.antlr.v4.runtime.Token;
import org.apache.poi.*;

import zemberek.*;

public class Tokenizer {

	public void tokenizer(String twit) throws IOException {
		tokenIterator(twit);
		//System.out.println();
		//simpleTokenization();

		// String s = twit.substring(0, twit.indexOf(' '));
		// System.out.println(s);
		// if(twit.substring(twit.indexOf(' ')+1).contains(" "))
		// {
		// tokenizer(twit.substring(twit.indexOf(' ')+1));
		// }else{
		// System.out.println(twit.substring(twit.indexOf(' ')+1));
		// }

	}

	public static TurkishMorphParser parser;

	public static String tokenIterator(String tweet) throws IOException {
		// System.out.println("Low level tokenization iterator using Ant-lr
		// Lexer.");
		//parser = TurkishMorphParser.createWithDefaults();
		//parser.parse("kitabýmýzsa");
		// StemmingAndLemmatization(parser);
		ZemberekLexer lexer = new ZemberekLexer();

		String input = tweet.replaceAll("[^a-zA-Z0-9 ]", "");
		//System.out.println("Input = " + input);
		Iterator<Token> tokenIterator = lexer.getTokenIterator(input);
		ArrayList<String> StemmedTokens = new ArrayList<>();
		String stemmedTweet = "";
		while (tokenIterator.hasNext()) {
			Token token = tokenIterator.next();
			if(token.getText().startsWith("http"))
				continue;
			parser = TurkishMorphParser.createWithDefaults();
			List<MorphParse> parses = parser.parse(token.getText());
			if(parses.size()>0){
				//StemmedTokens.add(parses.get(0).getLemma());
				stemmedTweet += "s="+parses.get(0).getLemma()+" ";
			}
			else{
				//StemmedTokens.add(token.getText());
				stemmedTweet += "s="+token.getText()+" ";
			}
		}
		return stemmedTweet;
	}
	// public TurkishMorphParser parser;

	public void parse(String word) {
		System.out.println("Word = " + word);

		System.out.println("Parses: ");
		List<MorphParse> parses = parser.parse(word);
		for (MorphParse parse : parses) {
			System.out.println(parse.formatLong());
			System.out.println("\tStems = " + parse.getStems());
			System.out.println("\tLemmas = " + parse.getLemmas());
		}
	}

	public static void simpleTokenization() {
		// System.out.println("Simple tokenization returns a list of token
		// strings.");
		ZemberekLexer lexer = new ZemberekLexer();
		String input = "Ýstanbul'a, merhaba!";
		// System.out.println("Input = " + input);
		// System.out.println("Tokenization list = " +
		// Joiner.on("|").join(lexer.tokenStrings("Ýstanbul'a, merhaba!")));
	}

	/*
	 * public static void main(String[] args) { tokenIterator();
	 * System.out.println(); simpleTokenization(); }
	 */
}