package com.xx.uppcl.beans;


import com.xx.uppcl.pojo.CalculatorPojo;
import com.xx.uppcl.services.DBServices;

import com.xx.uppcl.utils.Utils;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.faces.bi.component.chart.UIDataItem;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.ModelUtils;

public class ConsumptionCalculator {

    public String appliances = "";
    public String watt = "";
    public String equipments = "";
    public String hoursUse = "";
    public String daysUse = "";
    public ArrayList<SelectItem> equipmentsList = new ArrayList<SelectItem>();
    public ArrayList<SelectItem> wattageList = new ArrayList<SelectItem>();
    public double sumTotalLoad = 0;    
    public double sumTotalConsumption = 0;
    Utils utils = new Utils();
    
    Map<String, Float> updatedAppliances = new HashMap<String, Float>();
    Map<String, Float> finalMapAppliances = new HashMap<String, Float>();

    public void setAppliances(String appliances) {
        this.appliances = appliances;
    }

    public String getAppliances() {
        return appliances;
    }

    public void setWatt(String watt) {
        this.watt = watt;
    }

    public String getWatt() {
        return watt;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setHoursUse(String hoursUse) {
        this.hoursUse = hoursUse;
    }

    public String getHoursUse() {
        return hoursUse;
    }

    public void setDaysUse(String daysUse) {
        this.daysUse = daysUse;
    }

    public String getDaysUse() {
        return daysUse;
    }

    public void setEquipmentsList(ArrayList<SelectItem> equipmentsList) {
        this.equipmentsList = equipmentsList;
    }

    public ArrayList<SelectItem> getEquipmentsList() {
        DBServices ds = new DBServices();
        if (equipmentsList.size() == 0) {
            List<String> list = new ArrayList<String>();
            list = ds.getEquipmentList();
            for (String value : list) {
                equipmentsList.add(new SelectItem(value));
            }
        }
        return equipmentsList;
    }

    public void setWattageList(ArrayList<SelectItem> wattageList) {
        this.wattageList = wattageList;
    }

    public ArrayList<SelectItem> getWattageList() {
        return wattageList;
    }

    public ConsumptionCalculator() {
        super();
    }

    ArrayList<CalculatorPojo> calcList = new ArrayList<CalculatorPojo>();

    public void setCalcList(ArrayList<CalculatorPojo> calcList) {
        this.calcList = calcList;
    }


    public ArrayList<CalculatorPojo> getCalcList() {
       return calcList;
    }

    public void deleteCurrentRecord(ActionEvent actionEvent) {
        Object index = ADFContext.getCurrent()
                                 .getPageFlowScope()
                                 .get("deleteID");
        if (index != null) {
            System.err.println("Deleting, Index is.." + Integer.parseInt(index.toString()));
            calcList.remove(Integer.parseInt(index.toString()));
            
            if(calcList.size() > 0){
               AdfFacesContext.getCurrentInstance().getPageFlowScope().put("data", getPieChartData());
            }else{
                //reset graph
                List<UIDataItem> dataItems = new ArrayList<UIDataItem>();
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("data", dataItems);
                AdfFacesContext.getCurrentInstance().getPageFlowScope().put("dataSize", dataItems.size());
            }
        } else {
            System.err.println("Index is NA.." + index);
        }
        refreshPage();
    }

    public void onClickAdd(ActionEvent actionEvent) {
    if(checkRequiredValues()){
        
        double wattage = watt != null ? Double.parseDouble(watt) : 0;
        double equip = equipments != null ? Double.parseDouble(equipments) : 0;
        double hours = hoursUse != null ? Double.parseDouble(hoursUse) : 0;
        double days = daysUse != null ? Double.parseDouble(daysUse) : 0;
            
        double totalCons = wattage * equip;
        double totConsumption = (wattage * hours * days)/1000;
            
        DecimalFormat df = new DecimalFormat("###.##"); 
        String totalConsSt = df.format(totalCons);
        String totConsumptionSt = df.format(totConsumption);

        System.err.println("appliances= " + appliances + ", watt= " + watt + ", equipments= " + equipments +
                            ", hoursUse=" + hoursUse + ", daysUse=" + daysUse+", totalCons="+totalConsSt+", totConsumption="+totConsumptionSt);
            
        calcList.add(new CalculatorPojo(appliances, watt, equipments, hoursUse, daysUse, totalConsSt, totConsumptionSt));
        if(calcList.size() > 0){
           AdfFacesContext.getCurrentInstance().getPageFlowScope().put("data", getPieChartData());
        }else{
            //reset graph
            List<UIDataItem> dataItems = new ArrayList<UIDataItem>();
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("data", dataItems);
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("dataSize", dataItems.size());
        }
        this.resetForm();
    } 
}

    public void onSelectAppliances(ValueChangeEvent vcl) {
        wattageList.clear();
        if(vcl.getNewValue() != null){
            DBServices ds = new DBServices();
            List<String> list = new ArrayList<String>();
            list = ds.getWattageByEquipment(vcl.getNewValue().toString());
            for (String value : list) {
                wattageList.add(new SelectItem(value));
            } 
        } 
    }

    public void setSumTotalLoad(double sumTotalLoad) {
        this.sumTotalLoad = sumTotalLoad;
    }

    public double getSumTotalLoad() {    
        sumTotalLoad = 0;
        if(calcList!=null && calcList.size()>0){
            for(int i=0; i<calcList.size(); i++){
                CalculatorPojo calPojo = calcList.get(i);
                double total = calPojo.getTotalLoad() != null ? Double.parseDouble(calPojo.getTotalLoad()) : 0;
                sumTotalLoad = sumTotalLoad + total;
            }
        }
        DecimalFormat df = new DecimalFormat("###.##");
        String sumTotLoad = df.format(sumTotalLoad);
        sumTotalLoad = Double.parseDouble(sumTotLoad);
        return sumTotalLoad;
    }

    public void setSumTotalConsumption(double sumTotalConsumption) {
        this.sumTotalConsumption = sumTotalConsumption;
    }

    public double getSumTotalConsumption() {
        sumTotalConsumption = 0;
        if(calcList!=null && calcList.size()>0){
            for(int i=0; i<calcList.size(); i++){
                CalculatorPojo calPojo = calcList.get(i);
                double total = calPojo.getTotalConsumption() != null ? Double.parseDouble(calPojo.getTotalConsumption()) : 0;
                sumTotalConsumption = sumTotalConsumption + total;
            }
        } 
        DecimalFormat df = new DecimalFormat("###.##");
        String sumTotLoad = df.format(sumTotalConsumption);
        sumTotalConsumption = Double.parseDouble(sumTotLoad);
        return sumTotalConsumption;
    }

    private void resetForm() {
        appliances ="";
        watt ="";
        equipments ="";
        hoursUse ="";
        daysUse =""; 
        refreshPage();
    }
    
    private void refreshPage(){
        FacesContext fctx = FacesContext.getCurrentInstance();
        String refreshpage = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, refreshpage);
        UIV.setViewId(refreshpage);
        fctx.setViewRoot(UIV);
    }

    private boolean checkRequiredValues() { 
        int count=0;
        if("".equals(appliances)){
            showErrorMessage("You must select value for Appliances");
            count++;
        }
        if("".equals(watt)){
            showErrorMessage("You must select value for Watt");
            count++;
        }
        if("".equals(equipments)){
            showErrorMessage("You must select value for No Of Equipments");
            count++;
        }
        if("".equals(hoursUse)){
            showErrorMessage("You must select value for Hour Use/Day");
            count++;
        }
        if("".equals(daysUse)){
            showErrorMessage("You must select value for Days Use/Month");
            count++;
        }
        if(count>0){
            return false;
        }
        return true;
    }
    private void showErrorMessage(String msg) {
        FacesMessage message = new FacesMessage(msg); message.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, message);
    }
    
    public CollectionModel getPieChartData() {
        List<UIDataItem> dataItems = new ArrayList<UIDataItem>();
        
        updatedAppliances = new HashMap<String, Float>();
        finalMapAppliances = new HashMap<String, Float>();
        
        System.out.println("Lize size --> "+calcList.size());
        if(calcList != null && calcList.size() > 0){
            int count = 0;
            for(CalculatorPojo calcData : calcList){
                //create new updated list with specific appliances name like ac, tv, etc..
                forACAppliance(calcData, count);
                forTVAppliance(calcData, count);
                forLampAppliance(calcData, count);
                forComputerAppliance(calcData, count);
                forCoolerAppliance(calcData, count);
                forIronAppliance(calcData, count);
                forFanAppliance(calcData, count);
                forAttaChakkiAppliance(calcData, count);
                forHeaterAppliance(calcData, count);
                forGeyserAppliance(calcData, count);
                forInverterAppliance(calcData, count);
                forMicrowaveAppliance(calcData, count);
                forGrinderAppliance(calcData, count);
                forMotorAppliance(calcData, count);
                forRadioAppliance(calcData, count);
                forFridgeAppliance(calcData, count);
                forTosterAppliance(calcData, count);
                forLightAppliance(calcData, count);
                forWashingAppliance(calcData, count);
                count++;
            }
        }
        
        //total consumption based on appliances
        addAppliance();
        
        if(finalMapAppliances.size() > 0){
            for (Map.Entry<String,Float> entry : finalMapAppliances.entrySet()){
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                UIDataItem item = new UIDataItem();
                item.setValue(entry.getValue());
                item.setSeries(entry.getKey());
                dataItems.add(item);
            }         
            
        }
        
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("dataSize", dataItems.size());
        return ModelUtils.toCollectionModel(dataItems);
    }
    
    public void forACAppliance(CalculatorPojo calcData, int count){
        String appliance = "AC";
        
        //check AC appliance and total ac value
        if("AC 1 Ton".equals(calcData.getAppliances()) || "AC 1.5 Ton".equals(calcData.getAppliances()) ||
           "AC 2 Ton".equals(calcData.getAppliances()) || "Split AC".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forTVAppliance(CalculatorPojo calcData, int count){
        String appliance = "Television";
        
        //check TV appliance and total ac value
        if("Big Television".equals(calcData.getAppliances()) || "Small Television".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forLampAppliance(CalculatorPojo calcData, int count){
        String appliance = "Lamp";
        
        //check Lamp appliance and total ac value
        if("CFL Lamp".equals(calcData.getAppliances()) || "Regular Lamp".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
        
    }
    
    public void forComputerAppliance(CalculatorPojo calcData, int count){
        String appliance = "Computer";
        
        //check Comp appliance and total ac value
        if("Computer".equals(calcData.getAppliances()) || "Computer with Printer".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forCoolerAppliance(CalculatorPojo calcData, int count){
        String appliance = "Cooler";
        
        //check Cooler appliance and total ac value
        if("Desert Cooler".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forIronAppliance(CalculatorPojo calcData, int count){
        String appliance = "Iron";
        
        //check Iron appliance and total ac value
        if("Electric Iron".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forFanAppliance(CalculatorPojo calcData, int count){
        String appliance = "Fan";
        
        //check Fan appliance and total ac value
        if("Exhaust Fan".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
        
        if("Fan".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forAttaChakkiAppliance(CalculatorPojo calcData, int count){
        String appliance = "Atta Chakki";
        
        //check Atta Chakki appliance and total ac value
        if("House Atta Chakki".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forHeaterAppliance(CalculatorPojo calcData, int count){
        String appliance = "Heater";
        
        //check Heater appliance and total ac value
        if("Immersion Heater".equals(calcData.getAppliances()) || "Room Heater".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forGeyserAppliance(CalculatorPojo calcData, int count){
        String appliance = "Geyser";
        
        //check Geyser appliance and total ac value
        if("Instant Geyser".equals(calcData.getAppliances()) || "Water Heater- Geyser".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forInverterAppliance(CalculatorPojo calcData, int count){
        String appliance = "Inverter";
        
        //check Inverter appliance and total ac value
        if("Inverter".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forMicrowaveAppliance(CalculatorPojo calcData, int count){
        String appliance = "Microwave";
        
        //check Microwave appliance and total ac value
        if("Microwave Oven".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forGrinderAppliance(CalculatorPojo calcData, int count){
        String appliance = "Grinder";
        
        //check Grinder appliance and total ac value
        if("Mixer Cum Grinder".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forMotorAppliance(CalculatorPojo calcData, int count){
        String appliance = "Motor";
        
        //check Motor appliance and total ac value
        if("Pump Motor".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forRadioAppliance(CalculatorPojo calcData, int count){
        String appliance = "Radio";
        
        //check Radio appliance and total ac value
        if("Radio".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forFridgeAppliance(CalculatorPojo calcData, int count){
        String appliance = "Refrigerator";
        
        //check Fridge appliance and total ac value
        if("Refrigerator 165 litres".equals(calcData.getAppliances()) || "Refrigerator 210 litres".equals(calcData.getAppliances())
           || "Refrigerator 300 litres".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forTosterAppliance(CalculatorPojo calcData, int count){
        String appliance = "Toster";
        
        //check Toster appliance and total toster value
        if("Toaster".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forLightAppliance(CalculatorPojo calcData, int count){
        String appliance = "Light";
        
        //check Light appliance and total light value
        if("Tube Light".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void forWashingAppliance(CalculatorPojo calcData, int count){
        String appliance = "Washing Machine";
        
        //check Washing Machine appliance and total Washing Machine value
        if("Washing Machine".equals(calcData.getAppliances()) || "Washing Machine with Dryer".equals(calcData.getAppliances())){
            updatedAppliances.put(appliance+ "_" + count, Float.parseFloat(calcData.getTotalConsumption()));
        }
    }
    
    public void addAppliance(){
        Float totalACComsumption = 0.0f;
        Float totalTVComsumption = 0.0f;
        Float totalLampComsumption = 0.0f;
        Float totalCompComsumption = 0.0f;
        Float totalCoolerComsumption = 0.0f;
        Float totalIronComsumption = 0.0f;
        Float totalFanComsumption = 0.0f;
        Float totalChakkiComsumption = 0.0f;
        Float totalHeaterComsumption = 0.0f;
        Float totalGeyserComsumption = 0.0f;
        Float totalInverterComsumption = 0.0f;
        Float totalMicroComsumption = 0.0f;
        Float totalGrinderComsumption = 0.0f;
        Float totalMotorComsumption = 0.0f;
        Float totalRadioComsumption = 0.0f;
        Float totalfridgeComsumption = 0.0f;
        Float totalLightComsumption = 0.0f;
        Float totalTosterComsumption = 0.0f;
        Float totalWMComsumption = 0.0f;
        
        System.out.println("Updated Appliances size --> "+updatedAppliances.size());
        if(updatedAppliances.size() > 0){
            for(Map.Entry<String,Float> ac : updatedAppliances.entrySet()){
                if(ac.getKey().contains("AC_")){
                    totalACComsumption = totalACComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Television_")){
                    totalTVComsumption = totalTVComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Lamp_")){
                    totalLampComsumption = totalLampComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Computer_")){
                    totalCompComsumption = totalCompComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Cooler_")){
                    totalCoolerComsumption = totalCoolerComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Iron_")){
                    totalIronComsumption = totalIronComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Fan_")){
                    totalFanComsumption = totalFanComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Atta Chakki_")){
                    totalChakkiComsumption = totalChakkiComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Heater_")){
                    totalHeaterComsumption = totalHeaterComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Geyser_")){
                    totalGeyserComsumption = totalGeyserComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Inverter_")){
                    totalInverterComsumption = totalInverterComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Microwave_")){
                    totalMicroComsumption = totalMicroComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Grinder_")){
                    totalGrinderComsumption = totalGrinderComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Motor_")){
                    totalMotorComsumption = totalMotorComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Radio_")){
                    totalRadioComsumption = totalRadioComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Refrigerator_")){
                    totalfridgeComsumption = totalfridgeComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Toster_")){
                    totalTosterComsumption = totalTosterComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Light_")){
                    totalLightComsumption = totalLightComsumption + ac.getValue();   
                }else if(ac.getKey().contains("Washing Machine_")){
                    totalWMComsumption = totalWMComsumption + ac.getValue();   
                }
                
            }
        }
        
        finalMapAppliances.put("AC", totalACComsumption);
        finalMapAppliances.put("Television", totalTVComsumption);
        finalMapAppliances.put("Lamp", totalLampComsumption);
        finalMapAppliances.put("Computer", totalCompComsumption);
        finalMapAppliances.put("Cooler", totalCoolerComsumption);
        finalMapAppliances.put("Iron", totalIronComsumption);
        finalMapAppliances.put("Fan", totalFanComsumption);
        finalMapAppliances.put("Atta Chakki", totalChakkiComsumption);
        finalMapAppliances.put("Heater", totalHeaterComsumption);
        finalMapAppliances.put("Geyser", totalGeyserComsumption);
        finalMapAppliances.put("Inverter", totalInverterComsumption);
        finalMapAppliances.put("Microwave", totalMicroComsumption);
        finalMapAppliances.put("Grinder", totalGrinderComsumption);
        finalMapAppliances.put("Motor", totalMotorComsumption);
        finalMapAppliances.put("Radio", totalRadioComsumption);
        finalMapAppliances.put("Refrigerator", totalfridgeComsumption);
        finalMapAppliances.put("Toster", totalTosterComsumption);
        finalMapAppliances.put("Light", totalLightComsumption);
        finalMapAppliances.put("Washing Machine", totalWMComsumption);
        
        
    }
}
