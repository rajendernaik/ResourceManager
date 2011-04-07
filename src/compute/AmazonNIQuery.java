package compute;

/**
 * This class is a query interface implementation of the amazon ec2 requests like
 * start, stop, getid and getstate requests.
 * This class implements the newcastle instance interface.
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xerox.amazonws.ec2.Jec2;
import com.xerox.amazonws.ec2.ReservationDescription;
import com.xerox.amazonws.ec2.TerminatingInstanceDescription;
import com.xerox.amazonws.ec2.ReservationDescription.Instance;

public class AmazonNIQuery implements NewcastleInstance {
	
	/**
	 * This logfactory is used to get the logs....
	 * Awsaccesskeyid and SecretAccesskey are used for authentication purposes
	 */
	private static Log log = LogFactory.getLog(AmazonNIQuery.class);
	public int newcasid;
	final String AWSAccessKeyId = "aws-access-key-id";
    final String SecretAccessKey = "aws-secret-access-key";
    
    /*
     * This constructor is used to initialise the newcastle id
     */
    
    public AmazonNIQuery(int i) {
		// TODO Auto-generated constructor stub
    	newcasid=i;
	}
    
    /**
     * This function is used to get the generic id of the newcastle instance
     */
	public int getId() throws Exception {
		// TODO Auto-generated method stub
		return newcasid;
	}
	
	
	/**
	 * This function is used to get the state of the instance.. whether it is running,terminated or pending
	 * It loops through the instances which you acquire from the reservation description
	 * It returns null if the instance is not found..
	 */
	public String getState() throws Exception {
		// TODO Auto-generated method stub
		Jec2 ec2 = new Jec2(AWSAccessKeyId, SecretAccessKey);
        //describe instances
        List<String> params = new ArrayList<String>();
        List<ReservationDescription> instances = ec2.describeInstances(params);
        log.info("Instances");
        String[] instanceIds = new String[100];
        int i1=1;
        for (ReservationDescription res1 : instances) {
                log.info(res1.getOwner()+"\t"+res1.getReservationId());
                if (res1.getInstances() != null) {
                	int i=0;
                        for (Instance inst : res1.getInstances()) {
                                log.info("\t"+inst.getImageId()+"\t"+inst.getInstanceId()+"\t"+inst.getDnsName()+"\t"+inst.getState()+"\t"+inst.getKeyName());
                                if(i1==newcasid){
                            		instanceIds[i1] = inst.getInstanceId();
                                    System.out.println(instanceIds[i1]);
                                    return inst.getState();
                                    }
                                    i1++;
                        }
                }
                
        }
		return null;
	}
	
	/**
	 * This function is used to start an amazon ec2 instance by giving the required parameters mentioned in the code
	 * 
	 */
	public boolean start() throws Exception {
		// TODO Auto-generated method stub
		Jec2 ec2 = new Jec2(AWSAccessKeyId, SecretAccessKey);
        
        //run instances
        List<String> params1 = new ArrayList<String>();
         ReservationDescription res = ec2.runInstances("ami-03a0446a", 1, 1, params1, "teja", "teja-keypair");
         	log.info("Instances");  
         	@SuppressWarnings("unused")
				String instanceId = "";   
             if (res.getInstances() != null) {
                     for (Instance inst : res.getInstances()) {
                             log.info("\t"+inst.getImageId()+"\t"+inst.getDnsName()+"\t"+inst.getState()+"\t"+inst.getKeyName());
                             instanceId = inst.getInstanceId();
                     }
               }
		return true;
	}
	
	/**
	 * This function is used to terminate the required instance
	 * It loops through the instances which you acquire from the reservation description
	 * It returns true if it is terminated
	 */
	public boolean stop() throws Exception {
		// TODO Auto-generated method stub
		
		Jec2 ec2 = new Jec2(AWSAccessKeyId, SecretAccessKey);
		//describe instances
        List<String> params = new ArrayList<String>();
        List<ReservationDescription> instances = ec2.describeInstances(params);
        log.info("Instances");
        String[] instanceIds = new String[1];
        int i1=1;
        for (ReservationDescription res1 : instances) {
                log.info(res1.getOwner()+"\t"+res1.getReservationId());
                if (res1.getInstances() != null) {
                        for (Instance inst : res1.getInstances()) {
                                //log.info("\t"+inst.getImageId()+"\t"+inst.getInstanceId()+"\t"+inst.getDnsName()+"\t"+inst.getState()+"\t"+inst.getKeyName());
                        	if(i1==newcasid){
                        		instanceIds[0] = inst.getInstanceId();
                                System.out.println(instanceIds[0]);
                                List<TerminatingInstanceDescription> instances1 = ec2.terminateInstances(instanceIds);
                                log.info("terminating");
                               	}
                                i1++;
                        }
                }
          }return false;
	}
}
