package compute;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.amazon.aes.webservices.client.Jec2;
import com.amazon.aes.webservices.client.ReservationDescription;
import com.amazon.aes.webservices.client.ReservationDescription.Instance;
import com.foo.Bar;

public class ResourceManager1 {
	
	private static Log log = LogFactory.getLog(ResourceManager1.class);
	
	public List<NewcastleInstance> getAllInstances() throws Exception {
		
	    List<NewcastleInstance> newcasinstances = new ArrayList<NewcastleInstance>();
	    NewcastleInstance nI = new AmazonNI(1);
	    NewcastleInstance nI2 = new AmazonNIQuery(2); 
	    newcasinstances.add(nI);
	    newcasinstances.add(nI2);
	    
	    int index=1000;
	    long[] times = new long[index];
	    long[] times1 = new long[index];
	  
	    // to calculate time for starting the instance by query interface
	    //take start time before making the request
	    long sum=0;long sum1=0;
	    
	 for(int i=0;i<index;i++){ 
	    Date startTime = new java.util.Date();

	    // convert start time to milliseconds
	    long startTimeMS = startTime.getTime();

	    // format start time to display timestamp
	    Format formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	    String s = formatter.format(startTime);

	    System.out.println("startTime " + s + " [" + startTimeMS + " ms] " +
	          "-startinstancebysoapinterface - start");
	    
	    //nI.start();
	    System.out.println(nI.getState());
	    //nI.stop();
	    //nI.getId();
	    
	    //take end time immediately
	    Date endTime = new java.util.Date();
	    long endTimeMS = endTime.getTime();     // convert endTime to milliseconds
	    long runTime = endTimeMS - startTimeMS;
	    String t = formatter.format(endTime);   // format endTime for timestamp
	    
	    System.out.println("endTime " + t + " [" + endTimeMS + " ms] " +  "-startinstancebysoapinterface - end");
	    System.out.println("[Execution time] " + (runTime) + " ms"); 
		times[i]=runTime;
		sum = sum + times[i];
		
		//to calculate time for the soap interface
	    Date startTime1 = new java.util.Date();

	    // convert start time to milliseconds
	    long startTimeMS1 = startTime1.getTime();

	    // format start time to display timestamp
	    Format formatter1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	    String s1 = formatter1.format(startTime1);

	    System.out.println("startTime " + s1 + " [" + startTimeMS1 + " ms] " +
	          "-startinstancebyqueryinterface - start");
	    
	    //nI2.start();
	    System.out.println(nI2.getState());
	    //nI2.stop();
	    //nI2.getId();
	    
	    Date endTime1 = new java.util.Date();
	    long endTimeMS1 = endTime1.getTime();     // convert endTime to milliseconds
	    long runTime1 = endTimeMS1 - startTimeMS1;
	    String t1 = formatter1.format(endTime1);   // format endTime for timestamp

	    System.out.println("endTime " + t1 + " [" + endTimeMS1 + " ms] " +  "-startinstancebyqueryinterface - end");
	    System.out.println("[Execution time] " + (runTime1) + " ms");
	    times1[i]=runTime1;
	    sum1 = sum1 + times1[i];
	   }
	   long avg = sum / index; 
	   long avg1 = sum1 / index;
	   System.out.println("Average time for state by soap is " + avg + " ms");
	   System.out.println("Average time for state by query is " + avg1 + " ms");
	   
	   FileWriter fw = new FileWriter("H:\\ec2\\soapdata.txt");
	   FileWriter fw1 = new FileWriter("H:\\ec2\\querydata.txt");
	   BufferedWriter x = new BufferedWriter(fw);
	   BufferedWriter y = new BufferedWriter(fw1);
	   for(int c = 0 ; c < index ; c++)
	   {
		  x.write(Integer.toString(c));
		  x.write("\t");
	      x.write(Long.toString(times[c]));
	      x.write("\r\n");
	      y.write(Integer.toString(c));
		  y.write("\t");
	      y.write(Long.toString(times1[c])+ "\r\n");
	   }
	   
	   x.close();
	   y.close();
	   fw.close();
	   fw1.close();
	   
	  /*  for(Iterator<NewcastleInstance> i = newcasinstances.iterator();i.hasNext();)
	    {
	    	NewcastleInstance new_cas = i.next();
	    	System.out.println(new_cas.getId()+new_cas.getState());
	    }*/
	 return newcasinstances;
	}
}
