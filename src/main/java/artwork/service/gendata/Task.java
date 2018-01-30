package artwork.service.gendata;

import artwork.domain.Multimedia;
import artwork.domain.enumeration.Type;
import artwork.service.gendata.entitiesPhoto.LinksRetrofit;
import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Task {

    public List<Multimedia> genData(){

        InputStream in = null;
        try {

            List<Multimedia> multimediaList = new ArrayList<>();
            List<String> links = new ArrayList<>();
            int i = 0;
            while(i++ < 10) {

                String appId = "01907a24370e291c3494c6d185612066075a3cc81852ea977fe6949da5daa234";
                String urlStr = "https://api.unsplash.com/photos/random?client_id=" + appId;

                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(1000);


                in = url.openStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int nRead;
                while ((nRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, nRead);
                }
                String json = new String(out.toByteArray());


                Gson gson = new Gson();
                LinksRetrofit list = gson.fromJson(json, LinksRetrofit.class);

                links.add(list.getLinks().getDownload());

                multimediaList.add(saveImage(list.getLinks().getDownload()));
            }

            links.forEach(System.out::println);

            return multimediaList;
        }
        catch (Exception e) {
            System.err.println(e);
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }

    private static Multimedia saveImage(String imageUrl) throws IOException {
        Multimedia m = new Multimedia();
        m.setTotalValoration(5.0);
        m.setType(Type.PHOTO);
        m.setTitle("autoGen");

        URL url = new URL(imageUrl);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        m.setImage(output.toByteArray());
        m.setImageContentType("image/jpeg");

        return m;
    }
}
