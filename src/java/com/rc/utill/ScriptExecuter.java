/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rc.utill;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author nmss
 */
public class ScriptExecuter {
        protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger
            .getLogger(ScriptExecuter.class.getName());

    protected final static boolean debug=true;


    public static class ExecResult extends LogOutputStream {
        private int exitCode;
        /**
         * @return the exitCode
         */
        public int getExitCode() {
            return exitCode;
        }

        /**
         * @param exitCode the exitCode to set
         */
        public void setExitCode(int exitCode) {
            this.exitCode = exitCode;
        }

        private final List<String> lines = new LinkedList<>();

        @Override
        protected void processLine(String line, int level) {
            lines.add(line);
        }

        public List<String> getLines() {
            return lines;
        }
    }

    /**
     * execute the given command
     * @param cmd - the command 
     * @param msisdn 
     * @param exitValue - the expected exit Value
     * @return the output as lines and exit Code
     * @throws Exception
     */
    public static ExecResult execCmd(String cmd,String msisdn, int exitValue) throws Exception {
        if (debug)
            LOGGER.log(Level.INFO,"running "+cmd);
        CommandLine commandLine = CommandLine.parse(cmd);
        
        
        DefaultExecutor executor = new DefaultExecutor();
        commandLine.addArgument(msisdn);
        executor.setExitValue(exitValue);
        ExecResult result =new ExecResult();
        executor.setStreamHandler(new PumpStreamHandler(result));
        result.setExitCode(executor.execute(commandLine));
        return result;
    }


}
