package com.infostretch.isosbb.util;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.GPRSInfo;
import net.rim.device.api.system.RadioInfo;


/**
*  Helper class to fetch country name by using the phone's mcc value.
 */
public class CountryCode 
{
    String MCC_to_CallingCode = "";
    public CountryCode() 
    {    
         MCC_to_CallingCode ="412,93#"+
                                            "276,355#"+
                                            "603,213#"+
                                            "544,1684#"+
                                            "213,376#"+
                                            "631,244#"+
                                            "365,1264#"+
                                            "344,1268#"+
                                            "722,54#"+
                                            "283,374#"+
                                            "363,297#"+
                                            "505,61#"+
                                            "232,43#"+
                                            "400,994#"+
                                            "364,1242#"+
                                            "426,973#"+
                                            "470,880#"+
                                            "342,1246#"+
                                            "257,375#"+
                                            "206,32#"+
                                            "702,501#"+
                                            "616,229#"+
                                            "350,1441#"+
                                            "402,975#"+
                                            "736,591#"+
                                            "218,387#"+
                                            "652,267#"+
                                            "724,55#"+
                                            "348,1284#"+
                                            "528,673#"+
                                            "284,359#"+
                                            "613,226#"+
                                            "642,257#"+
                                            "456,855#"+
                                            "624,237#"+
                                            "302,1#"+
                                            "625,238#"+
                                            "346,1345#"+
                                            "623,236#"+
                                            "622,235#"+
                                            "730,56#"+
                                            "460,86#"+
                                            "732,57#"+
                                            "654,269#"+
                                            "548,682#"+
                                            "712,506#"+
                                            "612,225#"+
                                            "219,385#"+
                                            "368,53#"+
                                            "280,357#"+
                                            "230,420#"+
                                            "630,243#"+
                                            "238,45#"+
                                            "638,253#"+
                                            "366,1767#"+
                                            "370,1809#"+
                                            "740,593#"+
                                            "602,20#"+
                                            "706,503#"+
                                            "627,240#"+
                                            "657,291#"+
                                            "248,372#"+
                                            "636,251#"+
                                            "750,500#"+
                                            "288,298#"+
                                            "542,679#"+
                                            "244,358#"+
                                            "208,33#"+
                                            "742,594#"+
                                            "547,689#"+
                                            "628,241#"+
                                            "607,220#"+
                                            "282,995#"+
                                            "262,49#"+
                                            "620,233#"+
                                            "266,350#"+
                                            "202,30#"+
                                            "290,299#"+
                                            "352,1473#"+
                                            "535,1671#"+
                                            "704,502#"+
                                            "611,224#"+
                                            "632,245#"+
                                            "738,592#"+
                                            "372,509#"+
                                            "708,504#"+
                                            "454,852#"+
                                            "216,36#"+
                                            "274,354#"+
                                            "404,91#"+
                                            "405,91#"+
                                            "510,62#"+
                                            "432,98#"+
                                            "418,964#"+
                                            "272,353#"+
                                            "425,972#"+
                                            "222,39#"+
                                            "338,1876#"+
                                            "440,81#"+
                                            "441,81#"+
                                            "416,962#"+
                                            "401,7#"+
                                            "639,254#"+
                                            "545,686#"+
                                            "467,850#"+
                                            "450,82#"+
                                            "419,965#"+
                                            "437,996#"+
                                            "457,856#"+
                                            "247,371#"+
                                            "415,961#"+
                                            "651,266#"+
                                            "618,231#"+
                                            "606,218#"+
                                            "295,423#"+
                                            "246,370#"+
                                            "270,352#"+
                                            "455,853#"+
                                            "294,389#"+
                                            "646,261#"+
                                            "650,265#"+
                                            "502,60#"+
                                            "472,960#"+
                                            "610,223#"+
                                            "278,356#"+
                                            "551,692#"+
                                            "340,596#"+
                                            "609,222#"+
                                            "617,230#"+
                                            "334,52#"+
                                            "550,691#"+
                                            "259,373#"+
                                            "212,377#"+
                                            "428,976#"+
                                            "297,382#"+
                                            "354,1664#"+
                                            "604,212#"+
                                            "643,258#"+
                                            "414,95#"+
                                            "649,264#"+
                                            "536,674#"+
                                            "429,977#"+
                                            "204,31#"+
                                            "362,599#"+
                                            "546,687#"+
                                            "530,64#"+
                                            "710,505#"+
                                            "614,227#"+
                                            "621,234#"+
                                            "534,1670#"+
                                            "242,47#"+
                                            "422,968#"+
                                            "410,92#"+
                                            "552,680#"+
                                            "423,970#"+
                                            "714,507#"+
                                            "537,675#"+
                                            "744,595#"+
                                            "716,51#"+
                                            "515,63#"+
                                            "260,48#"+
                                            "268,351#"+
                                            "330,1787#"+
                                            "427,974#"+
                                            "629,242#"+
                                            "647,262#"+
                                            "226,40#"+
                                            "250,7#"+
                                            "635,250#"+
                                            "356,1869#"+
                                            "358,1758#"+
                                            "308,508#"+
                                            "360,1784#"+
                                            "549,685#"+
                                            "292,378#"+
                                            "626,239#"+
                                            "420,966#"+
                                            "608,221#"+
                                            "220,381#"+
                                            "633,248#"+
                                            "619,232#"+
                                            "525,65#"+
                                            "231,421#"+
                                            "293,386#"+
                                            "540,677#"+
                                            "637,252#"+
                                            "655,27#"+
                                            "214,34#"+
                                            "413,94#"+
                                            "634,249#"+
                                            "746,597#"+
                                            "653,268#"+
                                            "240,46#"+
                                            "228,41#"+
                                           "417,963#"+
                                            "466,886#"+
                                            "436,992#"+
                                            "640,255#"+
                                            "520,66#"+
                                            "514,640#"+
                                            "615,228#"+
                                            "539,676#"+
                                            "374,1868#"+
                                            "605,216#"+
                                            "286,90#"+
                                            "438,993#"+
                                            "376,1649#"+
                                            "641,256#"+
                                            "234,44#"+
                                            "235,44#"+
                                            "255,380#"+
                                            "424,971#"+
                                            "430,971#"+
                                            "431,971#"+
                                            "310,1#"+
                                            "311,1#"+
                                            "748,598#"+
                                            "332,1340#"+
                                            "434,998#"+
                                            "541,678#"+
                                            "225,379#"+
                                            "734,58#"+
                                            "452,84#"+
                                            "543,681#"+
                                            "421,967#"+
                                            "645,260#"+
                                            "648,263#";

    //System.out.println(MCC_to_CallingCode);
    }
    
	/* Countries MCC codes. */
	private static final int Afghanistan = 412;
	private static final int Albania = 276;
	private static final int Algeria = 603;
	private static final int Andorra = 213;
	private static final int Angola = 631;
	private static final int Antigua_and_Barbuda = 344;
	private static final int Australia = 505;
	private static final int Austria = 232;
	private static final int Belgium = 206;
	private static final int Bolivia = 736;
	private static final int Brazil = 724;
	private static final int Cameroon = 624;
	private static final int Cananda = 302;
	private static final int Chile = 730;
	private static final int China = 460;
	private static final int Denmark = 238;
	private static final int Finland = 244;
	private static final int France = 208;
	private static final int Germany = 262;
	private static final int Greece = 202;
	private static final int Hungary = 216;
	private static final int APN_HongKong = 454;
	private static final int Iceland = 274;
	private static final int India = 404;
	private static final int Ireland = 272;
	private static final int Italy = 222;
	private static final int Japan = 440;
	private static final int Malta = 278;
	private static final int Mauritius = 617;
	private static final int APN_Malaysia = 502;
	private static final int APN_Mexico = 334;
	private static final int Netherlands = 204;
	private static final int APN_NewZealand = 530;
	private static final int Norway = 242;
	private static final int Poland = 260;
	private static final int Portugal = 268;
	private static final int PuertoRico = 330;
	private static final int Romania = 226;
	private static final int Russia = 250;
	private static final int Singapore = 525;
	private static final int SouthAfrica = 655;
	private static final int Spain = 214;
	private static final int Sweden = 240;
	private static final int Switzerland = 228;
	private static final int APN_Turkey = 286;
	private static final int UnitedKingdom = 234;
	private static final int UnitedStatesofAmerica = 310;
	private static final int APN_Vietnam = 452;
    
	
	/**
	 * Fetches the Country string from the mcc  data retrived from RadioInfo.
	 * @return String APN 
	 * @throws Exception 
	 */
	public static String getCountryName()  {
		try{
		int num = RadioInfo.getNumberOfNetworks();
		for (int i = 0; i < num; i++) {
			int mcc = RadioInfo.getMCC(i);
			String countryName = getCountryNameFromCode(mcc);
			if(countryName != null){
				return countryName;
			}
		}
		}catch (Exception e) {
		}
		return null;
	}
	
	private static String getCountryNameFromCode(int mcc) throws Exception {
	
			String url = null;
			try {
				mcc = Integer.parseInt(Integer.toHexString(mcc));
			} catch (Exception e) {
			}
	
			switch (mcc) {
			case Spain:
				url = "Spain";
				break;
			case UnitedKingdom:
			case 235:
					url = "United Kingdom";
				break;
			case Germany:
					url = "Germany";
				break;
			case Italy:
				url = "Italy";
				break;
			case UnitedStatesofAmerica:
			case 311:
			case 316:
			case 313:
			//case 204:
				url = "United States of America";
				break;
			case India:
			case 405:
				url = "India";
				break;
			case 505:
				url = "Australia";
				break;
			case Austria:
				url = "Austria";
				break;
			case APN_Turkey:
				url = "Turkey";
				break;
			case APN_HongKong:
				url = "Hong Kong";
				break;
			case Netherlands:
			case 362:
				url = "Netherlands";
				break;
			case Chile:
				url = "Chile";
				break;
			case 460:
				url = "China";
				break;
			case APN_Mexico:
				url = "Mexico";
				break;
			case Cananda:
				url = "Canada";
				break;
			case France:
				url = "France";
				break;
			case Singapore:
				url = "Singapore";
				break;
			case APN_Malaysia:
				url = "Malaysia";
				break;
			case Brazil:
				url = "Brazil";
				break;
			case APN_Vietnam:
				url = "Vietnam";
				break;
			case Ireland:
				url = "Ireland";
				break;
			case APN_NewZealand:
				url = "New Zealand";
				break;
	
			default:
				break;
			}
			return url;
		}
	
    public String getCountryCallingCode(){
       int code_d = GPRSInfo.getHomeMCC();
       System.out.println("Code = "+code_d);
       if(code_d>=0){
                  String code = Integer.toHexString(code_d);
                  System.out.println("Code = "+code);

            int pos1 = MCC_to_CallingCode.indexOf(code+",");
            if(pos1 >=0){
                  pos1 = MCC_to_CallingCode.indexOf(",",pos1);
                  int pos2 = MCC_to_CallingCode.indexOf('#',pos1);
                  String code1 = MCC_to_CallingCode.substring(pos1+1,pos2);
                  System.out.println("Calling Code = "+code1);
                  return code1;
            }
       }
       return "";
    }
    
    public static String getVendorName()
    {
      return  DeviceInfo.getManufacturerName();
    }
    public static String getPhoneModel()
    {
        return DeviceInfo.getDeviceName();
    }
}
