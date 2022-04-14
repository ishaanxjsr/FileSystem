import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
    public
    static void reader(File obj){
        Scanner read= null;
        try {
            read = new Scanner(obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.printf("%3s %10s %15s %10s", "index","Name","Quantity","Date");
        System.out.println();
        int i=1;
        while(read.hasNextLine()){
            String data=read.nextLine();
            String[] arrOfdata = data.split("#", 3);
            if (arrOfdata.length<3)
                continue;
            System.out.format("%d %15s %15s %10s", i, arrOfdata[0], arrOfdata[1], arrOfdata[2]);
            System.out.println();
            i++;
        }
        read.close();
    }
    static void writer(RandomAccessFile obj){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter item" );
        System.out.println("Enter Name");
        String name=sc.nextLine();
        System.out.println("Enter Quantity");
        String quantity=sc.nextLine();
        System.out.println("Enter date");
        String date=sc.next();
        try {
            while(obj.getFilePointer() < obj.length()){
                obj.readLine();
            }
            obj.writeBytes(name+"#"+quantity+"#"+date);
            obj.writeBytes(System.lineSeparator());
            obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void remover(RandomAccessFile raf){
        System.out.println("Enter Line no. You want to delete");
        Scanner sc=new Scanner(System.in);
        int line=sc.nextInt();
        try {
            if (line>raf.length()){
                System.out.println("Out of range");
            }
            int i=0;
            File tmpFile = new File("temp.txt");
            RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
            while(line<=raf.length() && raf.getFilePointer() < raf.length() ){
                String ln=raf.readLine();
                i++;
                if (i==line){
                    continue;
                }

                tmpraf.writeBytes(ln);
                tmpraf.writeBytes(System.lineSeparator());
            }
            raf.seek(0);
            tmpraf.seek(0);
            while (tmpraf.getFilePointer()
                    < tmpraf.length()) {
                raf.writeBytes(tmpraf.readLine());
                raf.writeBytes(System.lineSeparator());
            }
            raf.setLength(tmpraf.length());
            tmpraf.close();
            raf.close();
            tmpFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public static void main(String args[]){

        try {
            File Obj = new File("Filestore.txt");
            if (!Obj.exists()){
            Obj.createNewFile();}
            System.out.println("1.List All Items\n2.Add New Item\n3.Remove Existing Item" +
                    "\nPlease Enter your choice");
            Scanner sc=new Scanner(System.in);
            int ch=sc.nextInt();
            RandomAccessFile rdwt = new RandomAccessFile(Obj, "rwd");
            switch(ch){
                case 1: reader(Obj);
                        break;
                case 2:writer(rdwt);
                        break;
                case 3: remover(rdwt);
                        break;
                default:
                    System.out.println("please Enter a valid choice");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


}}
