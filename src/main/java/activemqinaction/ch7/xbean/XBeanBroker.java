package activemqinaction.ch7.xbean;

import activemqinaction.ch6.Publisher;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class XBeanBroker {

 public static void main(String[] args) throws Exception {
     if (args.length == 0) {
      System.err.println("Please define a configuration file!");
      return;
     }
     String config = args[0];
     System.out.println("Starting broker with the following configuration: " + config);
     System.setProperty("activemq.base", System.getProperty("user.dir"));
     
     FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(config);
  
     Publisher publisher = new Publisher();
     for (int i = 0; i < 100; i++) {                                 
       publisher.sendMessage(new String[]{"JAVA", "IONA"});               
     }                                                                    
  
 }

}

