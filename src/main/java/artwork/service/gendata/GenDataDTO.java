package artwork.service.gendata;

import artwork.domain.Multimedia;
import artwork.domain.User;

import java.util.List;

public class GenDataDTO {

    private List<User> users;
    private List<Multimedia> multimedia;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }
}
