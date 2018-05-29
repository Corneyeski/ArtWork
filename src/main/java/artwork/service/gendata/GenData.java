package artwork.service.gendata;

import artwork.domain.Multimedia;
import artwork.domain.User;
import artwork.domain.enumeration.Type;
import artwork.repository.MultimediaRepository;
import artwork.repository.UserRepository;
import artwork.security.SecurityUtils;
import artwork.service.UserService;
import artwork.service.gendata.entitiesPhoto.LinksRetrofit;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class GenData {

    @Autowired
    private UserRepository userRepository;

    public GenDataDTO genData(Long id, String password, User user){

        InputStream in = null;
        try {

            //List<User> users = genUsers(id,password);
            List<Multimedia> multimediaList = new ArrayList<>();
            List<String> links = new ArrayList<>();
            int i = 0;

            while(i++ < 1) {

                String appId = "01907a24370e291c3494c6d185612066075a3cc81852ea977fe6949da5daa234";
                String urlStr = "https://api.unsplash.com/photos/random?client_id=" + appId;

                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(2000);
                conn.setReadTimeout(1000);


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


                Multimedia m = saveImage(list.getLinks().getDownload());
                m.setUser(user);
                multimediaList.add(m);
            }

            GenDataDTO genDataDTO = new GenDataDTO();
            //genDataDTO.setUsers(users);
            genDataDTO.setMultimedia(multimediaList);

            links.forEach(System.out::println);

            return genDataDTO;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }

    public List<User> genUsers(Long id, String password){

        int i = 0;
        List<User> users = new ArrayList<>();
        do {
            i++;

            User user = new User();
            user.setActivated(true);
            user.setEmail("user" + id + "@user.com");
            user.setLogin("user" + id);
            user.setPassword(password);
            user.setPassword("user");
            user.setFirstName("user" + id);
            id++;

            users.add(user);
        }while(i <= 5);

        return users;
    }

    private static Multimedia saveImage(String imageUrl) throws IOException {
        Multimedia m = new Multimedia();
        m.setTotalValoration(5.0);
        m.setType(Type.PHOTO);
        m.setTitle("autoGen");

        URL url = new URL(imageUrl);

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

        return m;
    }
}
