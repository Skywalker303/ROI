package Resource;

/**
 * Created by Андрей on 30.07.2017.
 */
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Data.Address;
import Data.User;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/User")
public class UserService {

    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;

    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = this.findAllUsers();
            if (users == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getUser(@PathParam("id") String userId) {
        User user = null;

        try {
            user = findById(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getUserbyEmail(@PathParam("email") String userEmail) {
        User user = null;

        try {
            user = findByEmail(userEmail);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @GET
    @Path("{birthday}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User getUserbyBirthday(@PathParam("birthday") Date userBirthday) {
        User user = null;

        try {
            user = findByBirthday(userBirthday);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createUser(User user) {

        try {
            long userId = persist(user);
            return Response.created(URI.create("/" + userId)).build();
        } catch (Exception e) {

            throw new WebApplicationException(e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("id") String userId,
                                   User user) {

        try {
            User oldUser = findById(userId);
            if (oldUser == null) {
                // return a not found in http/web format
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                long userIdUpdate = merge(user);
                return Response.ok().status(200).build(); //return a seeOther code
            }
        } catch (WebApplicationException e) {
            throw new WebApplicationException(e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") String userId) {
        try {
            if (!remove(userId)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Response.ok().status(200).build();
    }

    private long persist(User user) {

        try {
            Address address = user.getAddress();
            em.persist(address);
            em.persist(user);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user.getId();
    }

    private long merge(User user) {

        try {
            Address address = user.getAddress();
            em.merge(address);
         } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user.getId();
    }

    private User findById(String userId) {
        User user = null;
        try {
            int i = Integer.parseInt(userId);
            user = em.find(User.class, i);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    private User findByEmail(String userEmail) {
        User user = null;
        try {
            user = em.find(User.class, userEmail);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    private User findByBirthday(Date userBirthday) {
        User user = null;
        try {
            user = em.find(User.class, userBirthday);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return user;
    }

    private List<User> findAllUsers() {
        List<User> customers = new ArrayList<>();
        try {
            customers = (List<User>) em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return customers;
    }

    private boolean remove(String customerId) {
        User user;
        try {
            int i = Integer.parseInt(customerId);
            user= em.find(User.class, i);
            Address address = user.getAddress();
            em.remove(address);
            em.remove(user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
