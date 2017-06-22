package nasser_alqtami.andrody.com.plus;

import android.util.Log;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Abboudi_Aliwi on 23.06.2017.
 * Website : http://andrody.com/
 * our channel on YouTube : https://www.youtube.com/c/Andrody2015
 * our page on Facebook : https://www.facebook.com/andrody2015/
 * our group on Facebook : https://www.facebook.com/groups/Programming.Android.apps/
 * our group on Whatsapp : https://chat.whatsapp.com/56JaImwTTMnCbQL6raHh7A
 * our group on Telegram : https://t.me/joinchat/AAAAAAm387zgezDhwkbuOA
 */

public class RssFeed {

	private static String TAG_CHANNEL = "channel";
	private static String TAG_TITLE = "title";
	private static String TAG_ITEM = "item";
	private static String TAG_ENCLOSER="enclosure";
	private static String ATTR_URL="url";
	 
	public NodeList getRSSFeedItems(String rss_url) {
		String rss_feed_xml;
		rss_feed_xml = this.getXmlFromUrl(rss_url);
		if (rss_feed_xml != null) {
			try {
				Document doc = this.getDomElement(rss_feed_xml);
				NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
				Element e = (Element) nodeList.item(0);

				NodeList items = e.getElementsByTagName(TAG_ITEM);

				return items;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public Data getResult(NodeList node,int i){
		try{			
			Element e1 = (Element) node.item(i);
			String title = this.getValue(e1, TAG_TITLE);
			String icon_url = getMediaUrlContent(e1);


			if(icon_url!=null){
				Data wItem = new Data();
				wItem.setTitle(title);
				wItem.setUrl(icon_url);
				return wItem;
			}
		}catch(Exception e){
			
		}
		return null;
	}
	
	private String getMediaUrlContent(Element e){		
		NodeList nList=e.getElementsByTagName(TAG_ENCLOSER);
		Element enclosureElement =(Element)nList.item(0);		
		return enclosureElement.getAttribute(ATTR_URL);
	}
	
	
	private String getXmlFromUrl(String url) {
		String xml = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			String charset=null;
			if(httpEntity.getContentType()!=null){
				HeaderElement values[] = httpEntity.getContentType().getElements();
				if (values.length > 0) {
					NameValuePair param = values[0].getParameterByName("charset");
					if (param != null) {
					charset = param.getValue();
					}
				}
			}
			Log.i("charset", ""+charset);
			xml = EntityUtils.toString(httpEntity,charset);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xml;
	}

	public Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setEncoding("UTF-8");
			is.setCharacterStream(new StringReader(xml));
			doc = (Document) db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}
		
	public final String getElementValue(Node elem) {

		if (elem != null) {
			return elem.getTextContent();
		}
		return "";
	}
	
	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}
}
