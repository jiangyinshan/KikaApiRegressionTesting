package Test;

public class SleepSort {
    public static void sleepSort(int[] array){
        for(int num : array){
            new Thread(()->{
                try{
                    Thread.sleep(num);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(num);
            }).start();
        }
    }
    public static void main(String[] args){
        int[] array ={10,50,20,50,130,170,300,200};
        sleepSort(array);
    }
}
