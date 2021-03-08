package com.company.service_for_file_transfer;

import javax.swing.text.MaskFormatter;
import java.net.MalformedURLException;
import java.rmi.MarshalException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerFileStorage extends UnicastRemoteObject implements CallForService {
    private static final long serialVersionID = 1L;
    protected ServerFileStorage() throws RemoteException {
        super();
    }

    @Override
    public void setFileName(FileSample fileSample, String name) throws RemoteException {
        fileSample.setName(name);
    }

    @Override
    public void appendContent(FileSample fileSample) throws RemoteException {
        fileSample.getContent();
    }

    @Override
    public void closeFile(FileSample fileSample) throws RemoteException {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
        String dateConverted = simpleDateFormat.format(date.getTime());
        System.out.println(fileSample.getName()+" : "+dateConverted +"---"+" closed ");
    }

    @Override
    public void setCreationTime(FileSample fileSample, Date date) throws RemoteException {
        Calendar currentTime = Calendar.getInstance();
        fileSample.setDateCreation(currentTime.getTime());
    }
    public static void main(String []args){
        try{
            Naming.rebind("rmi:localhost:5000/file_transfer",new ServerFileStorage());
            System.out.println("Server is ready");
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}
