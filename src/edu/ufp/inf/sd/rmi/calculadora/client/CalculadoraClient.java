package edu.ufp.inf.sd.rmi.calculadora.client;

import edu.ufp.inf.sd.rmi.calculadora.server.CalculadoraRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui S. Moreira
 * @version 3.0
 */
public class CalculadoraClient {

    /**
     * Context for connecting a RMI client to a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private CalculadoraRI helloWorldRI;

    public static void main(String[] args) {
        if (args != null && args.length < 2) {
            System.err.println("usage: java [options] edu.ufp.sd.calculadora.server.CalculadoraClient <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else {
            //1. ============ Setup client RMI context ============
            CalculadoraClient hwc = new CalculadoraClient(args);
            //2. ============ Lookup service ============
            hwc.lookupService();
            //3. ============ Play with service ============
            hwc.playService();
        }
    }

    public CalculadoraClient(String args[]) {
        try {
            //List ans set args
            printArgs(args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(CalculadoraClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private Remote lookupService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to lookup service @ {0}", serviceUrl);
                
                //============ Get proxy to HelloWorld service ============
                helloWorldRI = (CalculadoraRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return helloWorldRI;
    }
    
    private void playService() {
        try {
            //============ Call HelloWorld remote service ============
            float resultado=0.0f;
            resultado=this.helloWorldRI.calculo(10, '-', 20);
            if(resultado==-11111)
            {
                System.out.println("\nConta Inválida");
            }
            else{
                System.out.println("\nResultado->"+resultado);
            }

          //  Logger.getLogger(this.getClass().getName()).log(Level.INFO, "goint to finish, bye. ;)");
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printArgs(String args[]) {
        for (int i = 0; args != null && i < args.length; i++) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "args[{0}] = {1}", new Object[]{i, args[i]});
        }
    }
}
