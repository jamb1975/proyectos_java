package service;

import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.context.ApplicationScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adminlinux
 */
 @ApplicationScoped
 @DataSourceDefinition(
    name="java:global/env/jdbc/serviciosqr"   ,
    className="org.postgresql.ds.PGPoolingDataSource",     
    url="jdbc:postgresql://localhost:7777/serviciosqr",
    user="postgres",
    password="JAmbg172*"
            
)
public class DataProvider {
   
    
    
    
}
