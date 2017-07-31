package Controller;

/**
 * Created by Андрей on 30.07.2017.
 */

    import Data.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Named
@Stateless
public class UserBean {

    protected Client client;
    private User user;

    @PostConstruct
    private void init() {
    }

    @PreDestroy
    private void clean() {
    }

    public String createUser(User user) {
        if (user == null) {
            return "userError";
        }
        String navigation;
        client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/user/webapi/User")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(user, MediaType.APPLICATION_XML), Response.class);
        client.close();
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            navigation = "userCreated";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Could not create user."));
            navigation = "userError";
        }
        return navigation;
    }

    public String retrieveUser(String id) {
        String navigation;
        client = ClientBuilder.newClient();
        user = client.target("http://localhost:8080/user/webapi/User").path(id)
                .request(MediaType.APPLICATION_XML).get(User.class);
        client.close();
        if (user == null) {
            navigation = "userError";
        } else {
            navigation = "userRetrieved";
        }
        return navigation;
    }

    public List<User> retrieveAllUsers() {
        client = ClientBuilder.newClient();
        List<User> users = client.target("http://localhost:8080/user/webapi/User").path("all")
                .request(MediaType.APPLICATION_XML).get(new GenericType<List<User>>() {
                });
        client.close();
        return users;
    }

    public String editUser (User user) {
        if (user == null) {
            return "userError";
        }
        String navigation;
        client = ClientBuilder.newClient();

        Response response = client.target("http://localhost:8080/user/webapi/User").path(String.valueOf(user.getId()))
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(user, MediaType.APPLICATION_XML), Response.class);
        client.close();

        if (response.getStatus() == Status.OK.getStatusCode()) {
            navigation = "userEdited";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Could not edit user."));
            navigation = "userError";
        }

        return navigation;
    }

    public void deactivateUser(String id) {
        client = ClientBuilder.newClient();

        Response response = client.target("http://localhost:8080/customer/webapi/User").path(id)
                .request(MediaType.APPLICATION_XML)
                .delete(Response.class);
        client.close();

        if (response.getStatus() == Status.OK.getStatusCode()) {

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Could not delete user."));
        }
    }
    public void activateUser(String id) {
        client = ClientBuilder.newClient();

        Response response = (Response) client.target("http://localhost:8080/customer/webapi/User").path(id)
                .request(MediaType.APPLICATION_XML)
                .build(String.valueOf((Response.class)));
        client.close();

        if (response.getStatus() == Status.OK.getStatusCode()) {

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Could not delete user."));
        }
    }

    public User getUser() {
        return user;
    }

    public void deactivateUser() {
    }

    public void activateUser() {
    }
}


