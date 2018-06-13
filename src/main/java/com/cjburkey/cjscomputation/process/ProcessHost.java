package com.cjburkey.cjscomputation.process;

import java.util.LinkedList;
import java.util.Queue;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.computer.ComputerCore;

public abstract class ProcessHost {
    
    private Queue<Process> processes = new LinkedList<>();
    
    public void appendProcess(Process process) {
        processes.add(process);
    }
    
    // Returns whether or not the process queue is now empty
    public boolean executeNext() {
        Process process = processes.poll();
        if (process == null) {
            return false;
        }
        process.onCall((ComputerCore) this);
        return processes.size() > 0;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((processes == null) ? 0 : processes.hashCode());
        return result;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ProcessHost other = (ProcessHost) obj;
        if (processes == null) {
            if (other.processes != null) {
                return false;
            }
        } else if (!processes.equals(other.processes)) {
            return false;
        }
        return true;
    }
    
}