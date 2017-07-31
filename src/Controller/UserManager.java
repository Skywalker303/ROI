package Controller;

/**
 * Created by Андрей on 30.07.2017.
 */
import java.io.Serializable;
import java.util.List;
import Data.User;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;


@Model
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<User> users;
    @EJB
    private UserBean userBean;

    @PostConstruct
    private void init() {
        user = new User();
        setUsers(userBean.retrieveAllUsers());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<User> getUsers() {
        return userBean.retrieveAllUsers();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
