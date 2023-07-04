/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.DTOFactura;
import modelo.EntidadesStatic;

/**
 *
 * @author adminlinux
 */
public class ConexionPosgresql {
    private static Connection connection;
    public static void connectDatabasePosgresql(String host, String port, String database,
            String user, String password) {
        
        String url = "";
        try {
            // We register the PostgreSQL driver
            // Registramos el driver de PostgresSQL
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
             connection = null;
            url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            // Database connect
            // Conectamos con la base de datos
            connection = DriverManager.getConnection(
                    url,
                    user, password);           
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "TEST OK" : "TEST FAIL");
        } catch (java.sql.SQLException sqle) { 
            System.out.println("Error al conectar con la base de datos de PostgreSQL (" + url + "): " + sqle);
        }
   
    }
    
    public static List<DTOFactura> getfacturasporprefijoyfecha(String fromdate,String todate,String prefijo) throws SQLException
    {
          System.out.println("fechas->"+fromdate+" "+todate+" pref2->"+prefijo.substring(0, 1));
       
       if(prefijo.substring(0, 1).equals("A")|| prefijo.substring(0, 1).equals("P") || prefijo.substring(0, 1).equals("T")&& !prefijo.equalsIgnoreCase("TODOS"))
        {
            
           String queryfacturas="select f.facturadate, f.prefijo,f.no_factura,f.totalamount,ge.nit,ge.nombre," +
                            " ge.direccion,ge.telefono," +
                            " (select sum(cuotamoderadora) from facturaitem where factura_id=f.id)cuotamoderadora," +
                            " (select sum(copago) from facturaitem where factura_id=f.id)copago" +
                            " from factura f, gen_eapb ge " +
                           "  where f.geneapb_id=ge.id and to_char(f.facturadate,'yyyy-mm-dd') >='"+fromdate+"'"+
                          "   and to_char(f.facturadate,'yyyy-mm-dd') <='"+todate+"'  and f.prefijo='"+prefijo+"' order by f.facturadate";
           Statement stm_queryfacturas=connection.createStatement();
           ResultSet rs_queryfacturas=stm_queryfacturas.executeQuery(queryfacturas);
           EntidadesStatic.li_dtoFacturas=null;
           EntidadesStatic.li_dtoFacturas=new ArrayList<>();
           while(rs_queryfacturas.next())
           {
             DTOFactura dTOFactura=null;
             dTOFactura=new DTOFactura();
             dTOFactura.setCopago(rs_queryfacturas.getBigDecimal("copago")==null?BigDecimal.ZERO:rs_queryfacturas.getBigDecimal("copago"));
             dTOFactura.setCuotamoderadora(rs_queryfacturas.getBigDecimal("cuotamoderadora")==null?BigDecimal.ZERO:rs_queryfacturas.getBigDecimal("cuotamoderadora"));
             dTOFactura.setFechaelaboracion(rs_queryfacturas.getDate("facturadate"));
             dTOFactura.setPrefijo(rs_queryfacturas.getString("prefijo"));
             dTOFactura.setNo_factura(rs_queryfacturas.getLong("no_factura"));
             dTOFactura.setTotalamount(rs_queryfacturas.getBigDecimal("totalamount"));
             String[] _asnit=rs_queryfacturas.getString("nit").split("-");
             dTOFactura.setNo_ident(_asnit[0]);
             try{
             dTOFactura.setDigitoverificacion(_asnit[1]);
             }catch(Exception e)
             {
                 dTOFactura.setDigitoverificacion("0"); 
             }
             dTOFactura.setNombre(rs_queryfacturas.getString("nombre"));
             dTOFactura.setDireccion(rs_queryfacturas.getString("direccion"));
             dTOFactura.setTelefono(rs_queryfacturas.getString("telefono"));
             EntidadesStatic.li_dtoFacturas.add(dTOFactura);
           
           }
           
        }
        else
         if(prefijo.substring(0, 1).equals("E") && !prefijo.equalsIgnoreCase("TODOS"))
         {
            String q="select f.facturadate, f.prefijo,f.no_factura,f.totalamount," +
"			 gp.documento,gp.primer_nombre," +
"			 gp.segundo_nombre,gp.primer_apellido,gp.segundo_apellido," +
"			 gp.telefono,gp.direccion," +
"	(select sum(cuotamoderadora) from facturaitem where factura_id=f.id)cuotamoderadora," +
"	(select sum(copago) from facturaitem where factura_id=f.id)copago" +
"	 from factura f, gen_personas gp " +
"	 where f.gen_personas_id=gp.id and to_char(f.facturadate,'yyyy-mm-dd') >='"+fromdate+"'" +
"	  and to_char(f.facturadate,'yyyy-mm-dd') <='"+todate+"'  and f.prefijo='"+prefijo+"'"+"  order by f.facturadate";
           Statement stm_queryfacturas=connection.createStatement();
           ResultSet rs_queryfacturas=stm_queryfacturas.executeQuery(q);
           EntidadesStatic.li_dtoFacturas=null;
           EntidadesStatic.li_dtoFacturas=new ArrayList<>();
           while(rs_queryfacturas.next())
           {
             DTOFactura dTOFactura=null;
             dTOFactura=new DTOFactura();
             dTOFactura.setCopago(rs_queryfacturas.getBigDecimal("copago"));
             dTOFactura.setCuotamoderadora(rs_queryfacturas.getBigDecimal("cuotamoderadora"));
             dTOFactura.setFechaelaboracion(rs_queryfacturas.getDate("facturadate"));
             dTOFactura.setPrefijo(rs_queryfacturas.getString("prefijo"));
             dTOFactura.setNo_factura(rs_queryfacturas.getLong("no_factura"));
             dTOFactura.setTotalamount(rs_queryfacturas.getBigDecimal("totalamount"));
             dTOFactura.setNo_ident(rs_queryfacturas.getString("documento"));
             dTOFactura.setNombre(rs_queryfacturas.getString("primer_nombre")+" "+ rs_queryfacturas.getString("segundo_nombre"));
             dTOFactura.setDireccion(rs_queryfacturas.getString("direccion"));
             dTOFactura.setTelefono(rs_queryfacturas.getString("telefono"));
             EntidadesStatic.li_dtoFacturas.add(dTOFactura);
           
           }          
        }else
         if(prefijo.equals("TODOS"))
         {
            String q="select f.facturadate, f.prefijo,f.no_factura,f.totalamount,ge.nit,ge.nombre," +
"ge.direccion as dir_eapb,ge.telefono as tel_eapb,gp.documento,gp.primer_nombre," +
"gp.segundo_nombre,gp.primer_apellido,gp.segundo_apellido," +
"gp.telefono,gp.direccion," +
"(select sum(cuotamoderadora) from facturaitem where factura_id=f.id)cuotamoderadora," +
"(select sum(copago) from facturaitem where factura_id=f.id)copago" +
" from factura f FULL outer join gen_eapb ge on  f.geneapb_id=ge.id " +
" FULL outer join gen_personas gp on  f.gen_personas_id=gp.id" +
" where to_char(f.facturadate,'yyyy-mm-dd') >='"+fromdate+"'" +
"  and to_char(f.facturadate,'yyyy-mm-dd') <='"+todate+"'  order by f.facturadate,f.prefijo,f.no_factura";
           Statement stm_queryfacturas=connection.createStatement();
           ResultSet rs_queryfacturas=stm_queryfacturas.executeQuery(q);
           EntidadesStatic.li_dtoFacturas=null;
           EntidadesStatic.li_dtoFacturas=new ArrayList<>();
           while(rs_queryfacturas.next())
           {
             DTOFactura dTOFactura=null;
             dTOFactura=new DTOFactura();
             dTOFactura.setCopago(rs_queryfacturas.getBigDecimal("copago"));
             dTOFactura.setCuotamoderadora(rs_queryfacturas.getBigDecimal("cuotamoderadora"));
             dTOFactura.setFechaelaboracion(rs_queryfacturas.getDate("facturadate"));
             dTOFactura.setPrefijo(rs_queryfacturas.getString("prefijo"));
             dTOFactura.setNo_factura(rs_queryfacturas.getLong("no_factura"));
             dTOFactura.setTotalamount(rs_queryfacturas.getBigDecimal("totalamount"));
              if(rs_queryfacturas.getString("documento")!=null)
             {     
             dTOFactura.setNo_ident(rs_queryfacturas.getString("documento"));
             dTOFactura.setNombre(rs_queryfacturas.getString("primer_nombre")+" "+ rs_queryfacturas.getString("segundo_nombre"));
             dTOFactura.setPrimer_apellido(rs_queryfacturas.getString("primer_apellido"));
             dTOFactura.setSegundo_apellido(rs_queryfacturas.getString("segundo_apellido"));
             dTOFactura.setDireccion(rs_queryfacturas.getString("direccion"));
             dTOFactura.setTelefono(rs_queryfacturas.getString("telefono"));
             }
             else
             {
                dTOFactura.setNo_ident(rs_queryfacturas.getString("nit")); 
                dTOFactura.setNombre(rs_queryfacturas.getString("nombre"));
                dTOFactura.setDireccion(rs_queryfacturas.getString("dir_eapb"));
                dTOFactura.setTelefono(rs_queryfacturas.getString("tel_eapb"));
              
             }
             EntidadesStatic.li_dtoFacturas.add(dTOFactura);
           
           }          
        }
       
        connection.close();
        connection=null;
        return EntidadesStatic.li_dtoFacturas;
    }
            
}
