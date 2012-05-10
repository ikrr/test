package jp.co.comp.site.dictionary;

public class ParserFactory {
	
	public static final int WEBLIO = 0;
	public static final int ALC = 1;
	
	public static HtmlParser create(int parserId){
		
		HtmlParser parser = null;
		
		switch(parserId){
		case WEBLIO:
			parser = new WeblioParser();
			break;
		case ALC:
			parser = new AlcParser();
			break;
		default :
			// default is weblio.
			parser = new WeblioParser();
			break;
		}
		
		return parser;
	}
	
}
