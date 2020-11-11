/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.moduloFacturacion.bean;

import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javafx.collections.ObservableList;
/**
 *
 * @author Jorge Herrera Castillo
 */
public class Imprimir implements Printable{
    PrinterJob printerJob;
    ObservableList<FacturacionDetalleBackup> mensaje;   
    String Nit, nombreCliente, direccionCliente, fecha, totalLetras,totalFactura;
    public Imprimir(){
        super();
        printerJob=PrinterJob.getPrinterJob();
        printerJob.setPrintable(this);       
    }
    // Método que lanza el cuadro de diálogos de la impresora e imprime
    // imagen
    public void imprima(ObservableList<FacturacionDetalleBackup> mensaje, String Nit, String nombreCliente, String direccionCliente,String fecha,String totalLetras, String totalFactura){
    this.mensaje=mensaje;
    this.Nit = Nit;
    this.nombreCliente = nombreCliente;
    this.direccionCliente = direccionCliente;
    this.fecha = fecha;
    this.totalLetras = totalLetras;
    this.totalFactura = totalFactura;
     if(printerJob.printDialog()){
        try{
            printerJob.print();
        }catch(Exception PrinterException){
            PrinterException.printStackTrace();
        }        
        } 
    }
    // Método que traza la imagen para imprimir
    public void imprimir(Graphics2D g2d,PageFormat pf,int pagina){     
        g2d.drawString(fecha, 370, 75);
        g2d.drawString(nombreCliente, 65, 102);
        g2d.drawString(direccionCliente,70, 120);
        g2d.drawString(Nit,410 , 120);
            int ancho =30;
            int largo = 150;
            int anchoDesc = 65;
            int anchoValor = 480;
          for(int x=0; x< mensaje.size();x++){
              g2d.drawString(String.valueOf(mensaje.get(x).getCantidadBackup()),ancho, largo);
              g2d.drawString(mensaje.get(x).getProductoDesc()+"  "+String.valueOf(mensaje.get(x).getProductoPrecio()), anchoDesc, largo);
              g2d.drawString(String.valueOf(mensaje.get(x).getTotalParcialBackup()), anchoValor, largo);
              
              largo = largo+17;
          }
         g2d.drawString(totalLetras, 30, 310);
         g2d.drawString(totalFactura, 480, 310);
    }
    public int print(Graphics g,PageFormat pf,int pagina){
      Graphics2D g2d=(Graphics2D)g;
      g2d.translate(pf.getImageableX(),pf.getImageableY());     
            switch(pagina){
                case 0:
                imprimir(g2d,pf,pagina);
                return (PAGE_EXISTS);            
                default:
                return (NO_SUCH_PAGE);
            }   
    }          
}
