/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import business.clientes.entities.Clientes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author cbustamante
 */
@Model
@RequestScoped
public class ClientesChartBean {

    @Inject
    ClientesBean clientesBean;
    private Integer activos;
    private Integer inactivos;

    @PostConstruct
    public void init() {
        activos = inactivos = 0;
        listaActivos = new ArrayList<Boolean>();
        if (clientesBean.getListaClientes() != null || !clientesBean.getListaClientes().isEmpty()) {
            for (Clientes clientes : clientesBean.getListaClientes()) {
                if (clientes.getEstadoContractual()) {
                    activos++;
                    
                } else {
                    inactivos++;
                }

            }
        }
        createBarModels();
    }
    private BarChartModel barModel;
    private List<Boolean> listaActivos;

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries activos = new ChartSeries();
        
        activos.setLabel("Activos");
        activos.set("2016", this.activos);
        
        ChartSeries inactivos = new ChartSeries();
        inactivos.setLabel("Inactivos");
        inactivos.set("2016", this.inactivos);
        
        model.addSeries(activos);
        model.addSeries(inactivos);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Gráfica de estados");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Estado Contractual");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Cantidad Clientes");
        yAxis.setMin(0);
        if (activos > inactivos )
        {
            yAxis.setMax(activos*1.2);
        }
        else
        {
            yAxis.setMax(inactivos*1.2);
        }
        
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
    
    

    

}
