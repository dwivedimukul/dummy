package dummy;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class dummy {
	public static void main(String[] args) {
		String inline = "";

		try {
			URL url = new URL(
					"https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=12.9623455,77.6373747;12.9353915,77.611746;13.0107953,77.5526921;12.9647254,77.719147&destinations=12.9623455,77.6373747;12.9353915,77.611746;13.0107953,77.5526921;12.9647254,77.719147&travelMode=driving&key=AhT3nVgSlv14w5u2GLYkCrCJm1VWDkBeEGHpG4JFNb13vgktN7OIJEr-5KZZrZah");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " + responsecode);
			Double[][] distanceMatrix=new Double[4][4];

			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				System.out.println("\nJSON Response in String format");
				System.out.println(inline);
				sc.close();
			}

			JSONParser parse = new JSONParser();
			JSONObject jobj = (JSONObject) parse.parse(inline);
			JSONArray jsonarr_1 = (JSONArray) jobj.get("resourceSets");
			JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(0);
			JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("resources");
			JSONObject jsonobj_3 = (JSONObject) jsonarr_2.get(0);
			JSONArray jsonarr_3 = (JSONArray) jsonobj_3.get("results");
			for (int j = 1; j < jsonarr_3.size(); j++) {
				JSONObject jsonobj_2 = (JSONObject) jsonarr_3.get(j);
				int str_data1 = ((Long) jsonobj_2.get("destinationIndex")).intValue();
				System.out.println(str_data1);
				int str_data2 = ((Long) jsonobj_2.get("originIndex")).intValue();
				System.out.println(str_data2);
				Long str_data3 = (Long) jsonobj_2.get("totalWalkDuration");
				System.out.println(str_data3);
				try {
					Double str_data4 = (Double) jsonobj_2.get("travelDistance");
					System.out.println(str_data4);
					Double str_data5 = (Double) jsonobj_2.get("travelDuration");
					System.out.println(str_data5);
					if (str_data1 != str_data2) {
						distanceMatrix[str_data1][str_data2] = str_data4;
						distanceMatrix[str_data2][str_data1]=str_data4;}
					else {
						distanceMatrix[str_data1][str_data1] = null;}
				} catch (Exception e) {
					Long str_data4 = (Long) jsonobj_2.get("travelDistance");
					//System.out.println(str_data4);
					//Long str_data5 = (Long) jsonobj_2.get("travelDuration");
					//System.out.println(str_data5);

				}
				System.out.println("\n");

			}
			for(int i=0;i<distanceMatrix.length;i++) {
				for(int j=0;j<distanceMatrix.length;j++)
				System.out.println(i+" "+ j+" "+distanceMatrix[i][j]);
			}
			conn.disconnect();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}