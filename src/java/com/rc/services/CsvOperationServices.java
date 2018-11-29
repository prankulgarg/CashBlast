/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.services;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
@Service
public class CsvOperationServices {
    
    
     public boolean saveDataToFile(String number,String status,String registrationDate,String deActiveDate,String firstName,String lastName) throws IOException{
                       String csv = "DriverData.csv";
                        CSVWriter csvWriter = null;

         
          try{
               csvWriter = new CSVWriter(new FileWriter("//home//prankul//"+csv, true), ',', CSVWriter.NO_QUOTE_CHARACTER);
      // writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);

              System.out.println("csv writer" +csvWriter.toString() );
              
              String[] data={number,firstName,lastName,status,registrationDate,deActiveDate};
              csvWriter.writeNext(data);
              System.out.println("data successfully written  "+"  error "+csvWriter.checkError());
                      
          }catch(Exception e){
              e.printStackTrace();
          }
         finally{
                  csvWriter.close();
                  
                  }
          return csvWriter.checkError();
         
      }  
     
     
     
     
       public boolean saveConsentRequest(String number,String status,String registrationDate) throws IOException{
                       String csv = "consentData.csv";
  CSVWriter csvWriter = null;

          
          try{
               csvWriter = new CSVWriter(new FileWriter("//home//prankul//"+csv, true), ',', CSVWriter.NO_QUOTE_CHARACTER);

             
              System.out.println("file name ===>"+csv);
              String[] data={number,status,registrationDate};
              csvWriter.writeNext(data);
              System.out.println("data successfully written to saveConsentRequest "+"  error "+csvWriter.checkError());
                      
          }catch(Exception e){
              e.printStackTrace();
          }
          
           finally{
                  csvWriter.close();
                  
                  }
          return csvWriter.checkError();
      }  
    
    
        public String[] readDataFromFile(String number) throws IOException{
         String data[]=new String[10];
         FileReader fileReader= null;  
               CSVReader csvReader=null;

         try{
                fileReader=new FileReader("//home//prankul//DriverData.csv");  
               csvReader=new CSVReader(fileReader);
               String dataa[] = new String[10];
               
               while((dataa=csvReader.readNext())!=null){
                   if(number.equals(dataa[0])){
                       System.out.println("inseide read data loop");
                      data=dataa ;
                      System.out.println(data[0]+"   "+data[1]+"   "+data[2]+"   "   +data[3]);
                        break;
                   }
               }
               
               
         }catch(Exception e){
e.printStackTrace();
         }  
          finally{
                  csvReader.close();
                  fileReader.close();
                  }
         return data;
     }
      
       public String[] getLocationFromFile(String number){
         String data[]=new String[15];
         try{
               FileReader fileReader=new FileReader("//home//prankul//location.csv");  
               CSVReader csvReader=new CSVReader(fileReader);
               String dataa[]= new String[15];
               
               while((dataa=csvReader.readNext())!=null){
                   if(number.equals(dataa[0])){
                      data=dataa ;
                      System.out.println(data[0]+"   "+data[1]+"   "+data[2]+"   "   +data[3]);
                        break;
                   }
               }
               
               
         }catch(Exception e){
             
         }  
         return data;
     }
       
       
       
        public String[] readConsentStatus(String number) throws IOException{
         String data[]=null;
        FileReader fileReader=null;
               CSVReader csvReader=null;
         try{
               fileReader=new FileReader("//home//prankul//consentData.csv");  
           csvReader=new CSVReader(fileReader);
               String dataa[];
               
               while((dataa=csvReader.readNext())!=null){
                   if(number.equals(dataa[0])){
                      data=dataa ;
                      System.out.println(data[0]+"   "+data[1]+"   "+data[2]+"   "   );
                        break;
                   }
               }
               
               
         }catch(Exception e){
             
         }  
         finally{
                  csvReader.close();
                  fileReader.close();
                  }
         return data;
     }
       public  boolean updateCSV(String number,String status) throws IOException {
           LocalDate date = LocalDate.now();
          DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          String text = date.format(formatters);
         CSVWriter writer = null;  
          CSVReader reader = null;
         try{
        File inputFile = new File("//home//prankul//DriverData.csv");

        // Read existing file
        reader = new CSVReader(new FileReader(inputFile), ',');
        List<String[]> csvBody = reader.readAll();
        // get CSV row column and replace with by using row and column
        
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            for(int j=0; j<strArray.length; j++){
                if(strArray[j].equals(number)){
                  //String to be replaced
                    csvBody.get(i)[j+3] = status; //Target replacement
                    csvBody.get(i)[j+5]=text;
                }
            }
        }
        reader.close();

        // Write to CSV file which is open
     //  writer = new CSVWriter(new FileWriter(inputFile), ',');
      
       writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
       writer.writeAll(csvBody);
        writer.flush();
        writer.close();
          }catch(Exception e){
              
          }
          finally{
                  writer.close();
                  reader.close();
                  }
          return writer.checkError();
    }
       
       public  boolean updateConsent(String number,String status,String date) throws IOException {
           
         
         CSVWriter writer = null;  
          CSVReader reader = null;
         try{
        File inputFile = new File("//home//prankul//consentData.csv");

        // Read existing file
        reader = new CSVReader(new FileReader(inputFile), ',');
        List<String[]> csvBody = reader.readAll();
        // get CSV row column and replace with by using row and column
        
        for(int i=0; i<csvBody.size(); i++){
            String[] strArray = csvBody.get(i);
            for(int j=0; j<strArray.length; j++){
                if(strArray[j].equals(number)){
                  //String to be replaced
                    csvBody.get(i)[j+1] = status; //Target replacement
                    csvBody.get(i)[j+2]=date;
                }
            }
        }
        reader.close();

        // Write to CSV file which is open
     //  writer = new CSVWriter(new FileWriter(inputFile), ',');
      
       writer = new CSVWriter(new FileWriter(inputFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
       writer.writeAll(csvBody);
        writer.flush();
        writer.close();
          }catch(Exception e){
              
          }
          finally{
                  writer.close();
                  reader.close();
                  }
          return writer.checkError();
    }
       
 public Boolean deleteLine(String msisdn) throws FileNotFoundException, IOException{
     File inputFile = new File("//home//prankul//DriverData.csv");
File tempFile = new File("//home//prankul//myTempFile.txt");

BufferedReader reader = new BufferedReader(new FileReader(inputFile));
BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

String lineToRemove = msisdn;
String currentLine;

while((currentLine = reader.readLine()) != null) {
    // trim newline when comparing with lineToRemove
    String trimmedLine = currentLine.trim();
    if(trimmedLine.equals(lineToRemove)) continue;
    writer.write(currentLine + System.getProperty("line.separator"));
}
writer.close(); 
reader.close(); 
boolean successful = tempFile.renameTo(inputFile);
     
   return successful;
 }      
      
  public boolean  removeLineFromFile( String lineToRemove) {
boolean checkFlag = false;
    try {
       // String file  = "//home//prankul//DriverData.csv";
      //File inFile =  new File("//home//prankul//DriverData.csv");

      File file = new File("//home//prankul//DriverData.csv");

    CSVReader csvFileReader = new CSVReader(new FileReader(file));
boolean checkflag = false;
    List<String[]> list = csvFileReader.readAll();

    for (int i = 0; i < list.size(); i++) {
        String[] filter = list.get(i);
        if (filter[0].equalsIgnoreCase(lineToRemove)) {
           checkFlag = true;
            list.remove(i);
        }
    }
    csvFileReader.close();
    CSVWriter csvOutput = new CSVWriter(new FileWriter(file));

    csvOutput.writeAll(list);
    csvOutput.flush();

    csvOutput.close();

    }
    catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  
  return checkFlag;
  }

       
}
