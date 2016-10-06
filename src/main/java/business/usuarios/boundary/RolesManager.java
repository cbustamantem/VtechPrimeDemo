/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.usuarios.boundary;

import business.dao.GenericImpl;
import business.usuarios.entities.Roles;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author cbustamante
 */
@Stateless
public class RolesManager  extends GenericImpl<Roles, Integer>{
    
    @Inject  
    private transient Logger logger; 

    public Roles getRolByName(String busquedaRol) {
        try {            
            List<Roles> listaRoles = this.getAll();
            for (Roles rol :listaRoles)
            {
                if (busquedaRol.trim().equals(rol.getDescripcion().trim()))
                {
                    return rol;
                }
            }
            return null;
        } catch (Exception ex) {
            logger.error("RolImpl.getRolByName ", ex);
            return null;
        }
    }

}
