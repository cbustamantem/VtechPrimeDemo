/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import business.clientes.boundary.ClientesManager;
import business.clientes.entities.Clientes;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Overlay;

/**
 *
 * @author cbustamante
 */
@Model
public class ClientesBean {

    @Inject
    private transient Logger logger;

    @Inject
    ClientesManager clientesMgr;
    private boolean bloquearBotones;
    private List<Clientes> listaClientes;
    private Clientes cliente;
    private MapModel geoModel;
    private int zoom;
    private String centerGeoMap;
    

    @PostConstruct
    public void init() {
        cliente = new Clientes();
        listaClientes = clientesMgr.getAll();
        centerGeoMap=  "-25.308987882217366, -57.581684589385986";
        zoom=11;
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

    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
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

                    return "clientes";
                }
                RequestContext.getCurrentInstance().execute("PF('dlgEmpAdd').hide()");
                if (null == cliente) {
                    mensajeEmergente("Ocurrió un error al intentar guardar la cliente ", TiposMensajes.ERROR);
                    return "clientes";

                }
            }

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Error",
                    "Ocurrió un error al intentar guardar la cliente "));
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
        RequestContext.getCurrentInstance().update("empresa-form:dtEmpresa");
    }

    public void actionClean(ActionEvent actionEvent) {
        //addMessage("Welcome to Primefaces!!");
        this.cliente = new Clientes();
        RequestContext.getCurrentInstance().update("pregunta-form:dtUsuario");
    }

    private void mensajeEmergente(String mensaje, TiposMensajes tipoMensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
//            DaoFactory.getUsuarios().delete(cliente);
//            listaEmpresas = DaoFactory.getUsuarios().getAll();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(tipoMensaje.toString(), mensaje));

        }
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
    

    enum TiposMensajes {
        ERROR,
        INFO
    }

    
    
    public void onStateChange(StateChangeEvent event) {
        try {
            LatLngBounds bounds = event.getBounds();

            this.zoom = event.getZoomLevel();
            logger.info("Zoom: " + zoom);

        } catch (Exception e) {
            logger.error("onStateChange ", e);
        }

//        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Zoom Level", String.valueOf(zoomLevel)));
//        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Center", event.getCenter().toString()));
//        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "NorthEast", bounds.getNorthEast().toString()));
//        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "SouthWest", bounds.getSouthWest().toString()));
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();

        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();

            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        }
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();

        try {

            addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Ubicación Seleccionada", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
            FacesContext context = FacesContext.getCurrentInstance();
            ClientesBean encuesta = context.getApplication().evaluateExpressionGet(context, "#{clientesBean}", ClientesBean.class);
            logger.info("Asignando marcador:");
            cliente.setLatitud(latlng.getLat());
            cliente.setLongitud(latlng.getLng());            
            if (zoom < 16) {
                zoom = 16;
            }

            logger.info("Asignando marcador:2");
            if (geoModel.getMarkers().isEmpty()) {
                logger.info("Creando marcador:" + latlng.getLat() + " - " + latlng.getLng());
                //Overlay ubcacion = geoModel.findOverlay("ubicacion");
                Overlay ubcacion = new Marker(new LatLng(latlng.getLat(), latlng.getLng()), "ubicacion");
                geoModel.addOverlay(ubcacion);
            } else {
                logger.info("Actualizando marcador:" + latlng.getLat() + " - " + latlng.getLng());
                //Overlay ubcacion = geoModel.findOverlay("ubicacion");
                this.geoModel.getMarkers().clear();
                //ubcacion  = new Marker(new LatLng(latlng.getLat(), latlng.getLng() ),"ubicacion" );
                //this.geoModel.addOverlay(new Marker(new LatLng(latlng.getLat(), latlng.getLng() ),"ubicacion" ));
                Overlay ubcacion = new Marker(new LatLng(latlng.getLat(), latlng.getLng()), "ubicacion");
                geoModel.addOverlay(ubcacion);
            }
            centerGeoMap = latlng.getLat() + "," + latlng.getLng();

        } catch (Exception ex) {
            logger.error("OnPointSelect ", ex);
        }

    }

    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    

    public String registrar() {
        try {
            if (geoModel.getMarkers().isEmpty()) {
                FacesContext context = FacesContext.getCurrentInstance();

                context.addMessage(null, new FacesMessage("Atención!", "Deberá seleccionar una ubicación para continuar "));

                return "";
            } else {

                return "encuesta";

            }

        } catch (Exception e) {
            return "";
        }

    }
}
