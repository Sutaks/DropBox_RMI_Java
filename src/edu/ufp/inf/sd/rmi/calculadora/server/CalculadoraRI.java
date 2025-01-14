package edu.ufp.inf.sd.rmi.calculadora.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>Title: Projecto SD</p>
 * <p>Description: Projecto apoio aulas SD</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: UFP </p>
 * @author Rui Moreira
 * @version 1.0
 */
public interface CalculadoraRI extends Remote {
    public void print(String msg) throws RemoteException;
    public float calculo(float a, char tipo,float b) throws RemoteException;
}
