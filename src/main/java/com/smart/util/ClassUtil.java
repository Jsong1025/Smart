package com.smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author jsong
 *
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className , false);
    }
    public static Class<?> loadClass(String className , boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className , isInitialized , getClassLoader());
        } catch (ClassNotFoundException e){
            LOGGER.error("load class failure" , e);
            throw new RuntimeException(e);
        }
        return  cls;
    }

    /**
     * 获取指定包下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url != null){
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")){
                        //如果是文件
                        String packagePath = url.getPath().replaceAll("%20" , " ");
                        addClass(classSet , packagePath , packageName);
                    } else if (protocol.equals("jar")){
                        //Jar包
                        JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                        if (jarURLConnection != null){
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")){
                                        String className = jarEntryName.substring(0 , jarEntryName.lastIndexOf(".")).replaceAll("/" , ".");
                                        doAddClass(classSet , className);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception e){
            LOGGER.error("get class set failure" , e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    /**
     * 加载包下的所有类
     */
    private static void addClass(Set<Class<?>> classSet , String packagePath , String packageName){
        //查找路径下所有字节码文件和文件夹
        File[] files = new File(packagePath).listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                // 以.class结尾的字节码文件 或者 文件夹
                return (file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            }
        });

        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                //文件
                String className = fileName.substring(0 , fileName.lastIndexOf("."));   //去除.clss文件后缀
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet , className);
            } else {
                //文件夹
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    //包名路径
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    //包名
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet , subPackagePath , subPackageName);
            }
        }
    }

    /**
     * 加载类，并放入classSet中
     */
    private static void doAddClass(Set<Class<?>> classSet , String className){
        Class<?> cls = loadClass(className , false);
        classSet.add(cls);
    }

}
