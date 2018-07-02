package excel;

import commons.AnalyJson;
import excel.ExcelUtil;
public class CompareRes {

	int actResuNo=12;
	int passNo=11;
	
	public  void compare(String path,ReqParams reqparam,String actResu){
		String recode=AnalyJson.getJsonValue(actResu,"code");
		ExcelUtil.writeCell(path,Integer.parseInt(reqparam.getNoRow()),actResuNo,actResu);
		if(reqparam.getExpResult().trim().equals(recode)){
			ExcelUtil.writeCell(path,Integer.parseInt(reqparam.getNoRow()),passNo,"pass");
		}else{
			ExcelUtil.writeCell(path,Integer.parseInt(reqparam.getNoRow()),passNo,"fail");
		}
	}
}