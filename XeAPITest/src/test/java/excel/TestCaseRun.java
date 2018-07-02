package excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestCaseRun {
     static String excel2010 = Common.STUDENT_INFO_XLSX_PATH;
     static String back=null;
	public static void main(String[] args) throws IOException{
	    CloseableHttpClient httpclient = HttpClients.createDefault();
		List<ReqParams> list1 = new ExcelUtil().readExcel(excel2010);
	    if (list1 != null) {
	        for (ReqParams param : list1) {
	        	String URL=param.getDomainName().concat(param.getUpath());
	        	//String casName=param.getCaseName();
	        	String Params=param.getRequestParam().replaceAll("\"", "\\\"");
	        	HttpPost httppost=new HttpPost(URL);
	      	    List<NameValuePair> params=new ArrayList<NameValuePair>();
	    	    params.add(new BasicNameValuePair("data",Params));
	    	    UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"UTF-8");
	    	    httppost.setEntity(entity);
	    	    CloseableHttpResponse httpres=httpclient.execute(httppost);
	    	    HttpEntity enres=httpres.getEntity();
	    	    back=EntityUtils.toString(enres,"UTF-8"); 
	    	    new CompareRes().compare(excel2010, param, back);
	    	    httpres.close();
	        }	        
	    }
	    System.out.println("1111");
	}

}
