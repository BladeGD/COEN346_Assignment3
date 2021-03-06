import java.util.concurrent.Semaphore;

public class Proc extends Thread implements Comparable<Proc> {
	protected String name;
    protected int arrivalTime;
    protected int readyTime;
    protected int burstTime;
    protected int remainderTime;
    protected int quantumTime;
    protected int waitTime;
    protected Boolean hasCPU;
    protected Boolean isFinished;

    public static void main(String args[]){
        Proc p = new  Proc("P1", 1, 1, 1, 1, false, false);
        System.out.println(p);
    }

    public Proc (String n, int arrT, int rdyT, int brT, int remT, Boolean h, Boolean f){
        name = n;
        arrivalTime = arrT;
        readyTime = rdyT;
        burstTime = brT;
        remainderTime = remT;
        waitTime = 0;
        if (remT*0.1 > 1) {
            quantumTime = (int) Math.ceil(remT*0.1);
        } 
        else {
            quantumTime = 1;
        }
        hasCPU = h;
        isFinished = f;
		
      //Launch Thread
        this.start();
    }
    
    public void obtainCPU(Semaphore mutex) {
	    	try {
	    		  mutex.acquire();
	    		  try {
	    			setRemainderTime(getRemainderTime()-getQuantumTime());
	    		  } finally {
	    		    mutex.release();
	    		  }
	    	} 
	    	catch(InterruptedException ie) {
	    		  // ...
	    	}
    	}
    

    public Proc(Proc p){
        this(p.name, p.arrivalTime, p.readyTime, p.burstTime, p.remainderTime, p.hasCPU, p.isFinished);
    }
    
    public void run() {
    }
    

    public void setReadyTime(int input) {
        readyTime = input;
    }
	

    public void setRemainderTime(int input) {
        remainderTime = input;
        setQuantumTime();
    }

    public void setQuantumTime() {
        if (remainderTime*0.1 > 1) {
            double qT = (double)remainderTime *0.1;
            quantumTime = (int)(Math.ceil(qT));
        } 
        else {
            quantumTime = 1;
        }
    }

    public String getProcessName() {
        return this.name;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public int getReadyTime() {
        return this.readyTime;
    }

    public int getRemainderTime() {
        return this.remainderTime;
    }

    public int getQuantumTime() {
        return this.quantumTime;
    }

    @Override
    public int compareTo(Proc x) {

        if (this.remainderTime != x.getRemainderTime()) {
            return Integer.compare(this.getRemainderTime(), x.getRemainderTime());
        }
        else {
            return Integer.compare(this.getArrivalTime(), x.getArrivalTime());
        }
    }

    
    @Override
    public String toString() {
        return String.format("Name: %s\n arrivalTime: %d\n readyTime: %d\n burstTime: %d\n remainderTime: %d\n hasCPU: %s\n isFinished: %s\n", 
            name, arrivalTime, readyTime, burstTime, remainderTime, String.valueOf(hasCPU), String.valueOf(isFinished));
    }
}