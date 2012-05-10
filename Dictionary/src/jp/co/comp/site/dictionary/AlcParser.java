package jp.co.comp.site.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlcParser extends HtmlParser {
	
	public AlcParser(){
		super();
		htmlPath = "http://eow.alc.co.jp/search?q=";
		encoding = "UTF-8";
	}

	@Override
	void trimTags() {
		// TODO Auto-generated method stub
		// regular expression is acting funny.
		String regexReturn = "<li>";
		String regexStart = "</*[a-z]*[^>]*";
		String regexEnd = ">*";
		String regexApostrophe = "&#39;";
		//String regexBracket = "】" + System.getProperty("line.separator");

		Pattern patternBr = Pattern.compile(regexReturn);
		Pattern patternStart = Pattern.compile(regexStart);
		Pattern patternEnd = Pattern.compile(regexEnd);
		Pattern patternApostrophe = Pattern.compile(regexApostrophe);
		//Pattern patternBracket = Pattern.compile(regexBracket);
				
		String temp = mHtmlText.toString();
		Matcher match;

		match = patternBr.matcher(temp);
		temp = match.replaceAll(System.getProperty("line.separator"));

		match = patternStart.matcher(temp);
		temp = match.replaceAll("");

		match = patternEnd.matcher(temp);
		temp = match.replaceAll("");

		match = patternApostrophe.matcher(temp);
		//temp = match.replaceAll("'");

		mRetText = temp;

	}

	@Override
	void extractDefinition(BufferedReader br) {
		// TODO Auto-generated method stub

		String line;
		boolean isStarted = false;
		String startArticleTag = "<div class=searchwordfont>";
		String endArticleTag = "<div class=smallredfont>";
		
		try{
			while((line = br.readLine()) != null){
				
				if(!isStarted){
					if(line.contains(startArticleTag)){
						isStarted = true;
					}
					else{
						continue;
					}
				}
				else if(line.contains(endArticleTag)){
					break;
				}
				
				mHtmlText.append(line);
			}
		}
		catch(IOException e){
			System.out.println(e);
		}
		
		if(!isStarted){
			mHtmlText.append("NOT found.");
		}
	}

	@Override
	public String simplifyText(String text) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String[] getTitleAndDescription(String text){
		return null;
	}

}
