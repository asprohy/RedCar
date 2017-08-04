package connection;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class getTeamNum {

    public static int testConnent() throws Exception{

        String path = "http://192.168.1.178:8088/TServer/GetTeamNum";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            return 1;
        }
        return 2;
    }

    public static List<teamEntity> getLastInfo() throws Exception{

        String path = "http://192.168.1.178/TServer/GetTeamNum";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream input = conn.getInputStream();
            return JSONParse(input);
        }
        return null;
    }

    private static List<teamEntity> JSONParse(InputStream input) throws Exception {
        List<teamEntity> list = new ArrayList<teamEntity>();
        byte[] data = StreamTool.read(input);
        String stringInfo = new String(data);
        JSONArray jsonArray = new JSONArray(stringInfo);
        for(int i = 0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            teamEntity info = new teamEntity(jsonObject.getInt("id"));
            list.add(info);
        }
        return list;
    }
}
