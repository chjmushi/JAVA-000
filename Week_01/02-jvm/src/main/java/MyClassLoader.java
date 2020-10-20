import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author: xiayiyang
 * @date: 2020-10-18 13:05
 * @description: week01-01-jvm 自定义类加载器
 * @date 2020-10-18 13:07
 **/
public class MyClassLoader extends ClassLoader {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MyClassLoader( String path) {
        this.path = path;
    }

    public static void main(String[] args) throws  Exception {
        MyClassLoader myClassLoader = new MyClassLoader("src/main/resources/");
        Class<?> clazz = myClassLoader.loadClass("Hello");
        Object hello = clazz.newInstance();
        Method helloMethod = clazz.getDeclaredMethod("hello", null);
        helloMethod.invoke(hello,null);

    }


    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private byte[] loadByte(String name) throws Exception {
        FileInputStream fis = new FileInputStream( path + name + ".xlass");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        decode(data);
        return data;
    }

    private byte[] decode(byte[] data) {
        for(int i = 0; i < data.length; i++) {
            data[i] = (byte) (255 - data[i]);
        }
        return data;
    }
}
