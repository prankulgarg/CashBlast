/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.services;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.rc.dao.GameServiceDao;

/**
 *
 * @author Admin
 */

@Service
public class ConsentTimer {

    public ConsentTimer() {
        
         /*      TimerTask repeatedTask = new TimerTask() {
        public void run() {
            System.out.println("Task performed on " + new Date());
        }
    };
    Timer timer = new Timer("Timer");
     
    long delay  = 1000L ;
    long period = 1000L * 60L;
    timer.scheduleAtFixedRate(repeatedTask, delay, period);
   */
    }
    
    
    
 @Autowired
    public GameServiceDao celtickService;
    @Scheduled(cron = "*/30 * * * * *")
	public void schedulePayment() {
            System.out.println("timer running");
//          TimerTask repeatedTask = new TimerTask() {
//        public void run() {
//            System.out.println("Task performed on " + new Date());
//        }
//    };
//    Timer timer = new Timer("Timer");
//     
//    long delay  = 1000L;
//    long period = 1000L * 60L;
//    timer.scheduleAtFixedRate(repeatedTask, delay, period);
            String consentStatus = null;
      List<Integer> pendingConsentMsISDN = celtickService.getPendeingConsent(); 
      for (Integer msisdn :pendingConsentMsISDN ){
          //scriptcall
          celtickService.setConsentStatus(consentStatus, msisdn);
          if (consentStatus.equalsIgnoreCase("approve")){
              // send approve to channel 
              
          }
          if (consentStatus.equalsIgnoreCase("diApprove")){
              // send diApprove to channel 
              
          }
          if (consentStatus.equalsIgnoreCase("pending")){
              // send pending to channel 
              
          }
          
      }

}
}