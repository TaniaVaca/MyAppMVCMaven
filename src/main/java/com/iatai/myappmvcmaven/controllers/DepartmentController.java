package com.iatai.myappmvcmaven.controllers;

import com.iatai.ejbpersistencemaven.bussines.entities.Department;
import com.iatai.ejbpersistencemaven.exceptions.InfraestructureException;
import com.iatai.ejbpersistencemaven.persistence.dao.implement.DeparmentDaoImpl;
import com.iatai.myappmvcmaven.controllers.utils.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@javax.faces.bean.ManagedBean(name = "departmentController")
@ViewScoped
public class DepartmentController implements Serializable {

    private com.iatai.ejbpersistencemaven.bussines.entities.Department department;
    private List<com.iatai.ejbpersistencemaven.bussines.entities.Department> listDepartments = null;
    private DeparmentDaoImpl deparmentDaoImpl = new DeparmentDaoImpl();
    private boolean init;

    public void iniciarLista() {
        if (!init) {
            if (listDepartments == null) {
                    listDepartments = deparmentDaoImpl.getDepartmentsList();
            }
            init = true;
        }
    }

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public DepartmentController() {
    }

    public Department getDepartment() {
        if(department==null){
            department = new Department();
        }
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getListDepartments() {
        return listDepartments;
    }

    public void setListDepartments(List<Department> listDepartments) {
        this.listDepartments = listDepartments;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DeparmentDaoImpl getDeparmentDaoImpl() {
        return deparmentDaoImpl;
    }

    public com.iatai.ejbpersistencemaven.bussines.entities.Department prepareCreate() {
        department = new com.iatai.ejbpersistencemaven.bussines.entities.Department();
        initializeEmbeddableKey();
        return department;
    }

    public void create() {
        try {
            deparmentDaoImpl.createEntity(department);
            if (!JsfUtil.isValidationFailed()) {
                listDepartments = null;    // Invalidate list of items to trigger re-query.
            }
        } catch (InfraestructureException ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        try {
            deparmentDaoImpl.updateEntity(department);
        } catch (InfraestructureException ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroy() {
        try {
            deparmentDaoImpl.deleteEntity(department, department.getId());
            if (!JsfUtil.isValidationFailed()) {
                department = null; // Remove selection
                listDepartments = null;    // Invalidate list of items to trigger re-query.
            }
        } catch (InfraestructureException ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(deparmentDaoImpl.getDepartmentsList(), true);
    }
}
