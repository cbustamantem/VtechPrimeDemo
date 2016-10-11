package presentation;

import business.usuarios.boundary.RolesManager;
import business.usuarios.boundary.UsuariosManager;
import business.usuarios.controller.UsuariosController;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import business.usuarios.entities.Roles;
import business.usuarios.entities.Usuarios;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import org.apache.log4j.Logger;

@Model
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private transient Logger logger;

    @Inject
    UsuariosManager um;

    @Inject
    UsuariosController uc;

    @Inject
    RolesManager rolesManager;

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean loggedIn;
    private Usuarios usuario;
    private Roles rol;

    @PostConstruct
    private void init() {
        logger.info("Inicio de LoginBean");
        usuario = new Usuarios();
        username="invitado";
        password="invitado";
    }

    /**
     * Método para cerrar Sesión
     *
     * @return
     */
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.jsf");
        } catch (Exception e) {
        }
        
        logger.info("Cerró la sessión");
        loggedIn = false;
        return "login";
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Método para iniciar sessión
     *
     * @return
     * @throws java.io.IOException
     */
    public String login() throws IOException {
        usuario = uc.authenticate(username, password);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        loggedIn = null != usuario ;
        
        sessionMap.put("loggedIn", loggedIn);

        if (loggedIn) {
            logger.info("Se inició sesión como " + username);

            sessionMap.put("userLoggedIn", usuario);

            this.rol = rolesManager.getRolByName(usuario.getRoles().getDescripcion());
            RequestContext.getCurrentInstance().update("loginPage");
            FacesContext.getCurrentInstance().getExternalContext().redirect("secure/home.jsf");
            return "home";
        } else {
            loggedIn = false;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de acceso", "El usuario o contraseña incorrecta");
            FacesContext.getCurrentInstance().addMessage(null, message);
            logger.error("Se intentó ingresar como " + username);
            username = null;
            password = null;
            RequestContext.getCurrentInstance().update("loginPage");
            return "login";
        }
    }

    /**
     * Método que redirecciona al home de la aplicación en caso de que el
     * usuario este correctamente autenticado, de lo contrario la regla de
     * acceso aplicada a travez de loginFilter hace que se redireccione al
     * login.jsf
     *
     * @throws IOException
     */
    public void redirect() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("secure/home.xhtml");
    }

    public boolean getPermisoPantalla(String roles) {
        if (null != roles && !roles.equalsIgnoreCase("")) {
            if (null != this.rol) {
                if (this.rol.getDescripcion().trim().equals(roles.trim())) {
                    return true;
                } else {
                    return false;
                }
//                String pantallaView[] = roles.split(",");
//                List<Pantallas> pantallas = rol.getPantallasList();
//                if (pantallas != null) {
//                    for (String ps : pantallaView) {
//                        for (Pantallas p : pantallas) {
//                            if (p.getDescPantalla().trim().equals(ps.trim())) {
//                                return true;
//                            }
//                        }
//                    }
//                }
            }
        }
        return false;
    }

    //Getters && Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public LoginBean getInstance() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return (LoginBean) fc.getApplication().getELResolver().getValue(fc.getELContext(), null, "loginBean");
    }

}
