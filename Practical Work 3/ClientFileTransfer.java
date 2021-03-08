package com.company.service_for_file_transfer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;

public class ClientFileTransfer {
    public static void main(String[] args){
        try {
            CallForService sample = (CallForService) Naming.lookup("rmi://localhost:5000/file_transfer");
            FileSample sampleFile = new FileSample("rmi.txt","Not things is here", Calendar.getInstance().getTime());
            sample.setFileName(sampleFile,"");
            sample.appendContent(sampleFile);
            sample.closeFile(sampleFile);
            sample.setCreationTime(sampleFile,Calendar.getInstance().getTime());
            System.out.println(sample.toString());
        }catch (NotBoundException e){
            e.printStackTrace();
        }catch (RemoteException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}
