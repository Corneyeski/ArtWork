package artwork.service.gendata;

import artwork.domain.Multimedia;
import artwork.domain.enumeration.Type;
import artwork.repository.MultimediaRepository;
import artwork.service.gendata.entitiesPhoto.LinksRetrofit;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Task {

    public static Multimedia genData(){

        InputStream in = null;
        try {

            List<String> links = new ArrayList<>();
            int i = 0;
            while(i++ < 1) {

                String appId = "01907a24370e291c3494c6d185612066075a3cc81852ea977fe6949da5daa234";
                String urlStr = "https://api.unsplash.com/photos/random?client_id=" + appId;

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
                LinksRetrofit list = gson.fromJson(json, LinksRetrofit.class);

                links.add(list.getLinks().getDownload());
            }

            String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
            String destinationFile = "image.jpg";

            links.forEach(System.out::println);

            return saveImage(links.get(0),destinationFile);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }

    public static Multimedia saveImage(String imageUrl, String destinationFile) throws IOException {
        /*URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        Multimedia m = new Multimedia();
        m.setTotalValoration(5.0);
        m.setImage(b);
        m.setType(Type.PHOTO);
        m.setTitle("prueba");

        is.close();
        os.close();

        File file = new File("image.jpg");
        if(file.exists()){
            try {
                BufferedImage bufferedImage=ImageIO.read(file);
                ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", byteOutStream);
                m.setImage( byteOutStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        Multimedia m = new Multimedia();
        m.setTotalValoration(5.0);
        m.setType(Type.PHOTO);
        m.setTitle("prueba2");

        URL url = new URL(imageUrl);
        //BufferedImage image = ImageIO.read(url);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream inputStream = url.openStream()) {
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        }
        m.setImage(output.toByteArray());
        m.setImageContentType("image/jpeg");

        /*

        ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteOutStream);

        m.setImage(byteOutStream.toByteArray());*/

        return m;
    }
}
