/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import business.clientes.boundary.ClientesManager;
import business.clientes.entities.Clientes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author cbustamante
 */
@Model
@SessionScoped
public class ClientesBean implements Serializable{

    @Inject
    private transient Logger logger;

    @Inject
    ClientesManager clientesMgr;
    private boolean bloquearBotones;
    private List<Clientes> listaClientes;
    private Clientes cliente;


    @PostConstruct
    public void init() {
        cliente = new Clientes();
        listaClientes = clientesMgr.getAll();
    }

    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public boolean isBloquearBotones() {
        return bloquearBotones;
    }

    public void setBloquearBotones(boolean bloquearBotones) {
        this.bloquearBotones = bloquearBotones;
    }

    /***
     * Metodo para registrar a los clientes
     * @return 
     */
    public String registrarCliente() {
        try {

            if (null != cliente) {

                if (cliente != null) {
                    if (null == cliente.getClienteId()) {
                        cliente = clientesMgr.add(cliente);
                        listaClientes.add(cliente);
                    } else {
                        cliente = clientesMgr.update(cliente);
                        listaClientes = clientesMgr.getAll();
                    }
                }
                RequestContext.getCurrentInstance().execute("PF('dlgCliAdd').hide()");
                if (null == cliente) {
                    mensajeEmergente("Ocurrió un error al intentar guardar la cliente ", TiposMensajes.ERROR);
                }
                return "clientes";
            }

        } catch (Exception e) {
            mensajeEmergente("Ocurrió un error al intentar guardar la cliente ", TiposMensajes.ERROR);
            
            logger.error("Problemas al registrar a la cliente", e);
        }

        return null;
    }

    public void borrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
//            DaoFactory.getUsuarios().delete(cliente);
//            listaEmpresas = DaoFactory.getUsuarios().getAll();
        } catch (Exception e) {
            mensajeEmergente("Ocurrió un error al intentar guardar el cliente ", TiposMensajes.ERROR);
            logger.error("Problemas al insertar el cliente", e);
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.cliente = (Clientes) event.getObject();
        this.bloquearBotones = false;
//        RequestContext.getCurrentInstance().update("cliente-form:dlgCliAdd");
    }

    public void actionClean(ActionEvent actionEvent) {
        this.cliente = new Clientes();
        RequestContext.getCurrentInstance().update("cliente-form:dtCliente");
    }

    private void mensajeEmergente(String mensaje, TiposMensajes tipoMensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(tipoMensaje.toString(), mensaje));

        }
    }


    enum TiposMensajes {
        ERROR,
        INFO
    }

    
  
    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    

    
}
