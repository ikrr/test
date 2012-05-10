package jp.co.comp.site.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeblioParser extends HtmlParser {
	
	public WeblioParser(){
		super();
		htmlPath = "http://ejje.weblio.jp/content/";
		encoding = "UTF-8";
	}

	@Override
	protected void trimTags() {
		
		// regular expression is acting funny.
		
		String regex;
		String temp = mHtmlText.toString();
		Matcher match;

		regex = "<div class=level0>";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll(System.getProperty("line.separator"));
		
		regex = "<span class=KejjeYrJp>";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll("RETURN_TAG");
		
		regex = "<span class=KejjeYrEn>";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll("RETURN_TAG RETURN_TAG");

		regex = "</*[a-z]*[^>]*";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll("");

		regex = ">*";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll("");

		regex = "&#39;";
		match = Pattern.compile(regex).matcher(temp);
		temp = match.replaceAll("'");

		mRetText = temp;

	}

	@Override
	protected void extractDefinition(BufferedReader br) {
		
		String line;
		boolean isStarted = false;
		String startArticleTag = "<div class=level0>";
		String endArticleTag1 = "<div class=subCatCtWrp>";
		String endArticleTag2 = "<div class=kijiFoot>";
		
		try{
			while((line = br.readLine()) != null){
				
				if(!isStarted){
					if(line.contains(startArticleTag)){
						isStarted = true;
					}
					else if(line.contains("nrCnt")){
						mHtmlText.append("NOT found.");
						break;
					}
					else{
						continue;
					}
				}
				else if(line.contains(endArticleTag1)){
					break;
				}
				else if(line.contains(endArticleTag2)){
					break;
				}
				
				mHtmlText.append(line);
			}
		}
		catch(IOException e){
			System.out.println(e);
		}

	}
	
	@Override
	public String simplifyText(String text){
		
		// you can edit the text here as you like.
		
		//String regexBracket1 = "〔.*〕";
		//String regexBracket2 = "〈.*〉";
		String regexBracket3 = "【.*】";
		String regexBracket4 = "［.*］";
		String regexSyllable = "音節.*発音記号.*";
		
		//Pattern patternBracket1 = Pattern.compile(regexBracket1);
		//Pattern patternBracket2 = Pattern.compile(regexBracket2);
		Pattern patternBracket3 = Pattern.compile(regexBracket3);
		Pattern patternBracket4 = Pattern.compile(regexBracket4);
		Pattern patternSyllable = Pattern.compile(regexSyllable);
		
		Matcher match;
		String temp = text;
		
		//match = patternBracket1.matcher(temp);
		//temp = match.replaceAll("");
		
		//match = patternBracket2.matcher(temp);
		//temp = match.replaceAll("");
		
		match = patternBracket3.matcher(temp);
		temp = match.replaceAll("");
		
		match = patternBracket4.matcher(temp);
		temp = match.replaceAll("");
		
		match = patternSyllable.matcher(temp);
		temp = match.replaceAll("");
		
		return temp;
	}
	
	@Override
	public String[] getTitleAndDescription(String text){
		String[] ret = null;
		
		if(text.contains("用例")){
			Matcher match = Pattern.compile("RETURN_TAG").matcher(text);
			ret = match.replaceAll(System.getProperty("line.separator")).split("用例");
		}
		else if(text.length() > 1){
			ret = new String[2];
			ret[0] = text;
			ret[1] = "NO example.";
		}
		else {
			ret = new String[2];
			ret[0] = "";
			ret[1] = "";
		}
		
		return ret;
	}

}
