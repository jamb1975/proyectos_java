/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author adminlinux
 */
public class UtilDate {
private static SimpleDateFormat  datetime=new SimpleDateFormat("kk");
private static SimpleDateFormat  datemin=new SimpleDateFormat("mm");
private static SimpleDateFormat  datedia=new SimpleDateFormat("dd");
private static SimpleDateFormat  datemes=new SimpleDateFormat("MM");
private static SimpleDateFormat  dateyear=new SimpleDateFormat("yyyy");
private static SimpleDateFormat  df=new SimpleDateFormat("yyyy-MM-dd");
private static SimpleDateFormat  df2=new SimpleDateFormat("dd-MM-yyyy");
private static SimpleDateFormat  dma=new SimpleDateFormat("dd/MM/yyyy");
private static SimpleDateFormat  dma2=new SimpleDateFormat("dd/MM/yyyy");
private static SimpleDateFormat  nombremesdiayear = new SimpleDateFormat(" MMMMM dd 'DEL' yyyy");
private static final SimpleDateFormat  fechahora=new SimpleDateFormat("yyyy-MM-dd HH:mm");
private static String fecha;
private static LocalDate ld;

public static LocalDate formatoyearmesdia(Date d_fecha) {
    df=null;
    df=new SimpleDateFormat("yyyy-MM-dd");
    fecha=df.format(d_fecha);
   return ld=LocalDate.parse(fecha);
}  
public static String  formatodiamesyear2(Date d_fecha) {
    fecha=df2.format(d_fecha);
   return fecha;
}  
public static String s_formatoyearmesdia(Date d_fecha) {
    dma=null;
     dma=new SimpleDateFormat("yyyy-MM-dd");
    fecha=dma.format(d_fecha);
   return fecha;
} 
public static String formatodiamesyear(Date d_fecha) {
    dma=null;
     dma=new SimpleDateFormat("dd/MM/yyyy");
    fecha=dma.format(d_fecha);
   return fecha;
} 
public static String fechainiciomes(Date d_fecha) throws ParseException
{
    df=new SimpleDateFormat("MM/yyyy");
    String sfecha="01/"+df.format(d_fecha);
    System.out.println("sfecha ini->"+sfecha);
    Date fromDate =dma2.parse(sfecha);
  //  fecha=dma2.format(fromDate);
    System.out.println("sfecha ini->"+fromDate);
    return sfecha;
}
public static String fechafinmes(Date d_fecha) throws ParseException
{
   df=new SimpleDateFormat("MM/yyyy");
   dma=new SimpleDateFormat("yyyy-MM-dd");
   //calcular dia delmes
   fecha=dma.format(d_fecha);
   ld=LocalDate.parse(fecha);
   String lastdaymonth=String.valueOf(calculardiasdelmes(ld.getYear(), (ld.getMonthValue()-1)));
   String sfecha=lastdaymonth+"/"+df.format(d_fecha);
   Date fromDate =dma2.parse(sfecha);
    return sfecha;
}
public static String  formatonombremesdiayear(Date d_fecha) {
    fecha=nombremesdiayear.format(d_fecha);
   return fecha;
} 
public static Date  colocarfechahora(Date d_fecha) throws ParseException {
  
    return d_fecha =fechahora.parse(String.valueOf(LocalDate.now().getYear())+"-"+String.valueOf(LocalDate.now().getMonthValue())+"-"+String.valueOf(LocalDate.now().getDayOfMonth())+ " " + String.valueOf(LocalTime.now().getHour())+ ":"+String.valueOf(LocalTime.now().getMinute()));
}       
public static int getdiafecha(Date d_fecha)
{
  fecha=datedia.format(d_fecha);
   
    return Integer.valueOf(fecha);
}
public static int getmesfecha(Date d_fecha)
{
  fecha=datemes.format(d_fecha);
   
    return Integer.valueOf(fecha);
}
public static int getyearfecha(Date d_fecha)
{
  fecha=dateyear.format(d_fecha);
   
    return Integer.valueOf(fecha);
}
public static int gethorafecha(Date d_fecha)
{
  fecha=datetime.format(d_fecha);
   
    return Integer.valueOf(fecha);
}
public static int getminutofecha(Date d_fecha)
{
  fecha=datemin.format(d_fecha);
   
    return Integer.valueOf(fecha);
}
public static String colocardiames(Date d_fecha,int mes,int dia) throws ParseException
{
   df=new SimpleDateFormat("yyyy");
   String s_dia="";
   String s_mes="";
   s_dia=String.valueOf(dia);
   s_mes=String.valueOf(mes);
   if(dia<10)
   {
       s_dia="0"+dia;
   }
   if(mes<10)
   {
       s_mes="0"+mes;
   }
  
   String sfecha=s_dia+"-"+s_mes+"-"+df.format(d_fecha);
   return sfecha;
}
public static int calculardiasdelmes(int year, int mes)
{
 switch(mes){
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if ( ((year%100 == 0) && (year%400 == 0)) ||
                        ((year%100 != 0) && (year%  4 == 0))   )
                    return 29;  // Año Bisiesto
                else
                    return 28;
            default:
                throw new java.lang.IllegalArgumentException(
                "El mes debe estar entre 0 y 11");
        }
}
}
