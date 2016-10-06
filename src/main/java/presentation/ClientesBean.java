/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import business.clientes.boundary.ClientesManager;
import business.clientes.entities.Clientes;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author cbustamante
 */
@Model
public class ClientesBean {
    @Inject
    ClientesManager clientesMgr;
    
    private List<Clientes> listaClientes;
    private Clientes cliente;
    
    @PostConstruct
    public void init()
    {
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
    
    
    
    
}
