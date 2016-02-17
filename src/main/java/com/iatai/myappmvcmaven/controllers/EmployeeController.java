package com.iatai.myappmvcmaven.controllers;

import com.iatai.ejbpersistencemaven.exceptions.InfraestructureException;
import com.iatai.ejbpersistencemaven.persistence.dao.implement.EmployeeDaoImpl;
import com.iatai.myappmvcmaven.controllers.utils.JsfUtil;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "employeeController")
@ViewScoped
public class EmployeeController implements Serializable {

    private com.iatai.ejbpersistencemaven.bussines.entities.Employee employee;
    private List<com.iatai.ejbpersistencemaven.bussines.entities.Employee> listEmployees = null;
    private EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
    private boolean init;

    public void iniciarLista() {
        if (!init) {
            if (listEmployees == null) {
                    listEmployees = employeeDaoImpl.getEmployeesList();
            }
            init = true;
        }
    }

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public EmployeeController() {
    }

    public com.iatai.ejbpersistencemaven.bussines.entities.Employee getEmployee() {
        return employee;
    }

    public void setEmployee(com.iatai.ejbpersistencemaven.bussines.entities.Employee employee) {
        this.employee = employee;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EmployeeDaoImpl getEmployeeDaoImpl() {
        return employeeDaoImpl;
    }

    public com.iatai.ejbpersistencemaven.bussines.entities.Employee prepareCreate() {
        employee = new com.iatai.ejbpersistencemaven.bussines.entities.Employee();
        initializeEmbeddableKey();
        return employee;
    }

    public List<com.iatai.ejbpersistencemaven.bussines.entities.Employee> getListEmployees() {
        return listEmployees;
    }

     public void create() {
        try {
            employeeDaoImpl.createEntity(employee);
            if (!JsfUtil.isValidationFailed()) {
            listEmployees = null;    // Invalidate list of items to trigger re-query.
            }
        } catch (InfraestructureException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

     public void update() {
        try {
            employeeDaoImpl.updateEntity(employee);
        } catch (InfraestructureException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

     public void destroy() {
        try {
            employeeDaoImpl.deleteEntity(employee, employee.getId());     
            if (!JsfUtil.isValidationFailed()) {
            employee = null; // Remove selection
            listEmployees = null;    // Invalidate list of items to trigger re-query.
            }
        } catch (InfraestructureException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
