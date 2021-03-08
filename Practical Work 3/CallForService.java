package com.company.service_for_file_transfer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface CallForService extends Remote {
    public void setFileName(FileSample fileSample,String name) throws RemoteException;
    public void appendContent(FileSample fileSample) throws RemoteException;
    public void closeFile(FileSample fileSample)throws RemoteException;
    public void setCreationTime(FileSample fileSample, Date date)throws RemoteException;
}
