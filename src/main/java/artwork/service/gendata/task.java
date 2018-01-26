package artwork.service.gendata;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class task {

    public static void main(String[] args){

        InputStream in = null;
        try {
            String appId = "01907a24370e291c3494c6d185612066075a3cc81852ea977fe6949da5daa234";
            String urlStr = "https://api.unsplash.com/photos/random?client_id="+appId;

            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(1000);
            int length = conn.getContentLength();


            in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int total = 0, nRead;
            while ((nRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, nRead);
                total += nRead;
            }
            String json = new String(out.toByteArray());


            Gson gson = new Gson();
            //ReceivedGame list = gson.fromJson(json, ReceivedGame.class);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }
}
