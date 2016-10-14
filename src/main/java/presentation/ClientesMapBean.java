/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import business.clientes.entities.Clientes;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author cbustamante
 */
@Model
@RequestScoped
public class ClientesMapBean {

    private MapModel advancedModel;
    private String centerGeoMap = "-25.308987882217366, -57.581684589385986";
    private Marker marker;

    @Inject
    ClientesBean clientesBean;

    @PostConstruct
    public void init() {
        advancedModel = new DefaultMapModel();
        String iconUrl="http://www.myiconfinder.com/uploads/iconsets/32-32-7ab6c25395f71e65019ad7d593fd18d5.png";
        for (Clientes cliente : clientesBean.getListaClientes()) {
            LatLng coord1 = new LatLng(cliente.getLatitud(), cliente.getLongitud());
            advancedModel.addOverlay(new Marker(coord1, cliente.getRazonSocial(),  cliente.getLogoUrl(),iconUrl));
        }
        //Shared coordinates

//        LatLng coord2 = new LatLng(36.883707, 30.689216);
//        LatLng coord3 = new LatLng(36.879703, 30.706707);
//        LatLng coord4 = new LatLng(36.885233, 30.702323);
//
//        //Icons and Data
//        
//        advancedModel.addOverlay(new Marker(coord2, "Ataturk Parki", "ataturkparki.png"));
//        advancedModel.addOverlay(new Marker(coord4, "Kaleici", "kaleici.png", "http://maps.google.com/mapfiles/ms/micons/pink-dot.png"));
//        advancedModel.addOverlay(new Marker(coord3, "Karaalioglu Parki", "karaalioglu.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }
    

}
