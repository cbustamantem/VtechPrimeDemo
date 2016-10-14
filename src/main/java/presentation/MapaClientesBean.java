/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
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
public class MapaClientesBean {

    private MapModel geoModel;
    private int zoom;
    private String centerGeoMap;

    @Inject
    private transient Logger logger;

    @PostConstruct
    public void init() {
        centerGeoMap = "-25.308987882217366, -57.581684589385986";
        zoom = 11;
        geoModel = new DefaultMapModel();
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

    public void onStateChange(StateChangeEvent event) {
        try {
            LatLngBounds bounds = event.getBounds();

            this.zoom = event.getZoomLevel();
            logger.info("Zoom: " + zoom);

        } catch (Exception e) {
            logger.error("onStateChange ", e);
        }
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
            ClientesBean clientesBean = context.getApplication().evaluateExpressionGet(context, "#{clientesBean}", ClientesBean.class);
            logger.info("Asignando marcador:");
            clientesBean.getCliente().setLatitud(latlng.getLat());
            clientesBean.getCliente().setLongitud(latlng.getLng());
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
            RequestContext.getCurrentInstance().update("form-add:clienteGr");

        } catch (Exception ex) {
            logger.error("OnPointSelect ", ex);
        }

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

    public void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
}
