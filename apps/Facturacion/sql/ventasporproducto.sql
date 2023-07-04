select t.no_ident,
       t.nombre,
       count(f.id_fac) total_atencion,
       sum(f.totalamount) valor_mesas
    from terceros t,
         factura f
    where f.id_emp=t.id_ter
    and   f.facturadate>='2015-04-04' and  f.facturadate<='2015-04-05'    

    group by t.no_ident,t.nombre


  select sum(f.totalamount),
  f.id_fac,((select  sum(totalamount) from factura  
  where id_fac=f.id_fac  and credito=true group by id_fac),0)total_cred
  from factura f group by f.id_fac