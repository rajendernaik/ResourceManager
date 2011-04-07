package compute;

/*
 * Newcastle Instance is an interface for the both AmazonNI and AmazonNIQuery classes
 * It gives us the generic sense of a newcastle instance
 */
import java.util.Calendar;


public interface NewcastleInstance 
{	
	/**
	 * Gets the identifier of the instance.
	 * @return
	 * @throws Exception
	 */
	public int getId()throws Exception;
	/**
	 * gets the state of the newcastle instance... running, pending or terminated
	 */
	public String getState()throws Exception;
	
	/**
	 * stops a newcastle instance
	 * @return
	 * @throws Exception
	 */
	public boolean stop() throws Exception;
	
	/**
	 * starts a newcastle instance
	 * @return
	 * @throws Exception
	 */
	public boolean start() throws Exception;
	
	
}
