package jp.co.comp.site.dictionary;

import java.io.*;
import java.net.*;

abstract public class HtmlParser {

	// doesn't use threads.
	protected StringBuilder mHtmlText;
	protected String mRetText;
	
	protected String htmlPath;
	protected String encoding;
		
	public HtmlParser(){
		mHtmlText = new StringBuilder();
	}
		
	abstract void trimTags();
	abstract void extractDefinition(BufferedReader br);
	abstract public String simplifyText(String text);
	abstract String[] getTitleAndDescription(String text);
		
	public String getRetText(String searchWord){
		fetchHtmlTextFromWeb(searchWord, 0);
		return getRetText();
	}
		
	public String getRetText(){
		trimTags();
		return mRetText;
	}
	
	public String[] getRetTextArray(){
		return getRetText().split(System.getProperty("line.separator"));
	}
	
	public String[] getRetTextArray(String searchWord){
		return getRetText(searchWord).split(System.getProperty("line.separator"));
	}
		
	public String getHtmlText(){
		return mHtmlText.toString();
	}
		
	public void fetchHtmlTextFromWeb(String searchWord, int asIs){
			
		// clear.
		mHtmlText.setLength(0);
			
		try{
			URL url = new URL(htmlPath + searchWord);
			URLConnection uc = url.openConnection();

			BufferedInputStream bis = new BufferedInputStream(uc.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(bis, encoding));
			
			
			if(asIs > 0) {
				String line;
				try{
					while((line = br.readLine()) != null){
						mHtmlText.append(line);
					}
				}
				catch(IOException e){
					System.out.println(e);
				}
			}
			else {
				extractDefinition(br);
			}
			
				
			br.close();
			bis.close();
		}
		catch(MalformedURLException e){
			System.out.println("Invalid URL.");
		}
		catch(UnknownHostException e){
			System.out.println("Couldn't find the web site.");
		}
		catch(IOException e){
			System.out.println(e);
		}
			
	}
}
