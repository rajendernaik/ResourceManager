package compute;

/*
 * This class is a soap interface implementation of the amazon ec2 requests like
 * start, stop, getid and getstate requests.
 * This class implements the newcastle instance interface.
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.amazon.aes.webservices.client.Jec2;
import com.amazon.aes.webservices.client.ReservationDescription;
import com.amazon.aes.webservices.client.TerminatingInstanceDescription;
import com.amazon.aes.webservices.client.ReservationDescription.Instance;
import com.foo.Bar;

public class AmazonNI implements NewcastleInstance {
	
	/*
	 * The logger class gets the logs... KEY and CERTIFICATE are used for authentication purposes..
	 * url is the url of the amazon ec2 wsdl to which the soap request is sent to...
	 */
	
	private static Logger log = Logger.getLogger(AmazonNI.class);
	public int newcasid;
	final String KEY = "H:\\ec2\\pk-2VYRRU7O72VXXWNWVGGTWSQ52LRD3G6A.pem";
    final String CERTIFICATE = "H:\\ec2\\cert-2VYRRU7O72VXXWNWVGGTWSQ52LRD3G6A.pem";
    URL url= null;
	
    /*
     * This is the constructor... which initialises the generic newcastle id, url
     * and the system properties which are required for the request to be sent
     * The other configuration is for the initialisation of the log4j system 
     */
	AmazonNI(int i)
	{
		try
		{
			newcasid=i;
			url = new URL("http://ec2.amazonaws.com/doc/2008-02-01/AmazonEC2.wsdl");
			System.getProperties().put("http.proxySet", "true");
		    System.getProperties().put("http.proxyHost", "wwwcache.ncl.ac.uk");
		    System.getProperties().put("http.proxyPort", "8080");
		    System.getProperties().put("http.proxyUser", "rajnaik");
		    System.getProperties().put("http.proxyPassword", "proxy-password");
		
		    BasicConfigurator.configure();
		    log.info("Entering Application");
		    Bar bar = new Bar();
		    bar.doIt();
		}
		catch(MalformedURLException mfue)
		{
			System.out.println(mfue);
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see compute.NewcastleInstance#start()
	 * In this function, first it initialises an object of Jec2 class and starts one instance of type m1.small
	 * and it requires some parameters mentioned below in the code
	 * after it gets started, prints the parameters of the instance..
	 */
	public boolean start() throws Exception{
		Jec2 ec2 = new Jec2( url, KEY, CERTIFICATE);
		
		//run instances
        List<String> params1 = new ArrayList<String>();
        ReservationDescription res = ec2.runInstances("ami-03a0446a", 1, 1, params1, null, "raj-keypair", "rajnaik", null, null, null, null, null, null);
        	log.info("Instances");  
        	@SuppressWarnings("unused")
			String instanceId = "";   
            if (res.instances != null) {
                    for (Instance inst : res.instances) {
                            log.info("\t"+inst.imageId+"\t"+inst.dnsName+"\t"+inst.state+"\t"+inst.keyName);
                            instanceId = inst.instanceId;                       
                    }
              }
			return true;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see compute.NewcastleInstance#getId()
	 * this class returns the generic newcastle id 
	 */	

	public int getId() throws Exception{
		// TODO Auto-generated method stub
		return newcasid;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see compute.NewcastleInstance#getState()
	 * This function is used to get the state of the instance.. whether it is running, pending or terminated
	 * It loops through the instances which you acquire from the reservation description
	 * It returns null if the instance is not found..
	 */
	public String getState() throws Exception {
		// TODO Auto-generated method stub

	    Jec2 ec2 = new Jec2( url, KEY, CERTIFICATE);
		
	    //describe instances
        List<String> params2 = new ArrayList<String>();
        List<ReservationDescription> instances = ec2.describeInstances(params2);
        log.info("Instances");
        String[] instanceIds = new String[100];
        int i1=1;
        for (ReservationDescription res1 : instances) {
                log.info(res1.owner+"\t"+res1.resId);
                if (res1.instances != null) {
                        for (Instance inst : res1.instances) {
                        	//log.info("\t"+inst.imageId+"\t"+inst.dnsName+"\t"+inst.state+"\t"+inst.keyName);
                                if(i1==newcasid){
                        		instanceIds[i1] = inst.instanceId;
                                System.out.println(instanceIds[i1]);
                                return inst.state;
                                }
                                i1++;
                        }
                }  
        }
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see compute.NewcastleInstance#stop()
	 * This function is used to terminate the required instance
	 * It loops through the instances which you acquire from the reservation description
	 * It returns true if it
	 */
	public boolean stop() throws Exception{
		// TODO Auto-generated method stub
	         
	    Jec2 ec2 = new Jec2( url, KEY, CERTIFICATE);
		
	    //describe instances
	    
        List<String> params2 = new ArrayList<String>();
        List<ReservationDescription> instances = ec2.describeInstances(params2);
        log.info("Instances");
        String[] instanceIds = new String[1];
        int i1=1;
        for (ReservationDescription res1 : instances) {
                log.info(res1.owner+"\t"+res1.resId);
                if (res1.instances != null) {
                        for (Instance inst : res1.instances) {
                        	//log.info("\t"+inst.imageId+"\t"+inst.dnsName+"\t"+inst.state+"\t"+inst.keyName);
                               	if(i1==newcasid){
                        		instanceIds[0] = inst.instanceId;
                                System.out.println(instanceIds[0]);
                                List<TerminatingInstanceDescription> instances1 = ec2.terminateInstances(instanceIds);
                                log.info("terminating");
                               	}
                                i1++;
                        }
                }  
        }return true;
	}
}