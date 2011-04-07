package com.foo;

/*This class is just for the initialisation of the log4j
 * I used this based on http://logging.apache.org/log4j/1.2/manual.html
 * u can refer the above link...
 */

 import org.apache.log4j.Logger;
 import org.apache.log4j.*;

 public class Bar {
   static Logger logger = Logger.getLogger(Bar.class);

   public void doIt() {
     logger.debug("Did it again!");
   }
 }