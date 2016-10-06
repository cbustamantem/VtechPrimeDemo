/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.usuarios.boundary;

import business.dao.GenericImpl;
import business.usuarios.entities.Usuarios;
import javax.ejb.Stateless;

/**
 *
 * @author cbustamante
 */
@Stateless
public class UsuariosManager  extends GenericImpl<Usuarios, Integer>{
//    
//     public Empresas getEmpresa(Usuarios usuario) {
//        try {
//            if (null == usuario.getEmpresaId()) {
////                 usuario = getById(this.usuarioId.getUsuarioId().longValue());
//                usuario.setEmpresaId(usuario.getEmpresaId());
//            }
//            return empresa;
//        } catch (Exception ex) {
//            return null;
//        }
//
//    }
}
