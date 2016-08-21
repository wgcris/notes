package loop;

public class loop {

        public static void main(String[] args) {
                // TODO Auto-generated method stub
                int loop = 1;
                if(args.length>=1) {
                        System.out.println(args[0]);
                        loop = Integer.parseInt(args[0]);
                }
                for(int i=0;i<loop;i++){
                        System.out.println("start thread " + i);
                        new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        // TODO Auto-generated method stub
                                        int j=0;
                                        while(true){j++;}
                                }
                        }).start();
                }
        }
}

