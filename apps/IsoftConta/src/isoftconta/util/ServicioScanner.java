package isoftconta.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import isoftconta.Servicios;
import isoftconta.model.EmptyServicio;
import isoftconta.model.Area;
import isoftconta.FXServicioArea;
import java.util.function.BiConsumer;

/**
 * All the code related to classpath scanning, etc for samples.
 */
public class ServicioScanner {
    
    private static List<String> ILLEGAL_CLASS_NAMES = new ArrayList<>();
    static {
        ILLEGAL_CLASS_NAMES.add("/com/javafx/main/Main.class");
        ILLEGAL_CLASS_NAMES.add("/com/javafx/main/NoJavaFXFallback.class");
        ILLEGAL_CLASS_NAMES.add("/javafx/embed/swt/FXCanvas.class");
        ILLEGAL_CLASS_NAMES.add("/javafx/embed/swt/CustomTransfer.class");
        ILLEGAL_CLASS_NAMES.add("/javafx/embed/swt/SWTCursors.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/DoubleConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/SelectBinding.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/ContentBinding.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/ObjectConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/StringFormatter.class"); 
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/BidirectionalBinding.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/Logging.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/FloatConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/MapExpressionHelper.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/StringConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/IntegerConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/ExpressionHelperBase.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/LongConstant.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/ListExpressionHelper.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/ExpressionHelper.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/SetExpressionHelper.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/BidirectionalContentBinding.class");
        ILLEGAL_CLASS_NAMES.add("/com/sun/javafx/binding/BindingHelperObserver.class");
       ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/FloatBinding.class");
      ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/MapBinding.class");
     ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/BooleanBinding.class");
      ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/ListExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/IntegerBinding.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/FloatExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/NumberExpression.class");  
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/NumberExpressionBase.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/MapExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/Bindings.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/BooleanExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/LongExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/ObjectExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/SetExpression.class"); 
    ILLEGAL_CLASS_NAMES.add("/javafx/beans/binding/DoubleBinding.class"); 
    }
    
    private static Map<String, FXServicioArea> packageToAreaMap = new HashMap<>();
    static {
        System.out.println("Initialising FXSampler sample scanner...");
        System.out.println("\tDiscovering areas...");
        // find all areas on the classpath that expose a FXSamplerArea
        // service. These guys are our friends....
        ServiceLoader<FXServicioArea> loader = ServiceLoader.load(FXServicioArea.class);
        for (FXServicioArea servicioarea : loader) {
            final String areaName = servicioarea.getAreaNombre();
            final String basePackage = servicioarea.getServicioBasePackage();
            packageToAreaMap.put(basePackage, servicioarea);
            System.out.println("\t\tFound area '" + areaName + 
                    "', with sample base package '" + basePackage + "'");
        }
        
        if (packageToAreaMap.isEmpty()) {
            System.out.println("\tError: Did not find any areas!");
        }
    }
    
    private final Map<String, Area> areasMap = new HashMap<>();
    
    /**
     * Gets the list of sample classes to load
     *
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Map<String, Area> discoverSamples() {
        Class<?>[] results = new Class[] { };
        
        try {
        	  results = loadFromPathScanning();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (Class<?> servicioClass : results) {
            if (! Servicios.class.isAssignableFrom(servicioClass)) continue;
            if (servicioClass.isInterface()) continue;
            if (Modifier.isAbstract(servicioClass.getModifiers())) continue;
//            if (Sample.class.isAssignableFrom(EmptySample.class)) continue;
            if (servicioClass == EmptyServicio.class) continue;
            
            Servicios servicios = null;
            try {
                servicios = (Servicios)servicioClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (servicios == null || ! servicios.isVisible()) continue;
            
            

            final String packageName = servicioClass.getPackage().getName();
            
            for (String key : packageToAreaMap.keySet()) {
                if (packageName.contains(key)) {
                    final String prettyAreaName = packageToAreaMap.get(key).getAreaNombre();
                    
                    Area area;
                    if (! areasMap.containsKey(prettyAreaName)) {
                        area = new Area(prettyAreaName, key);
                        area.setWelcomePage(packageToAreaMap.get(key).getWelcomePage());
                        areasMap.put(prettyAreaName, area);
                    } else {
                        area = areasMap.get(prettyAreaName);
                    }
                    
                    area.addServicio(packageName, servicios);
                }
            }
        }
        
        return areasMap;
    } 

    /**
     * Scans all classes.
     *
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private Class<?>[] loadFromPathScanning() throws ClassNotFoundException, IOException {
        final List<File> dirs = new ArrayList<>();
        final List<File> jars = new ArrayList<>();
        
        // scan the classpath
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = "";
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println("url->"+url.getPath());
            if (url.toExternalForm().contains("/jre/")) continue;
            
            // Only "file" and "jar" URLs are recognized, other schemes will be ignored.
            String protocol = url.getProtocol().toLowerCase();
            if ("file".equals(protocol)) {
                dirs.add(new File(url.getFile()));
            } else if ("jar".equals(protocol)) {
                String fileName = new URL(url.getFile()).getFile();
                
                // JAR URL specs must contain the string "!/" which separates the name
                // of the JAR file from the path of the resource contained in it, even
                // if the path is empty.
                int sep = fileName.indexOf("!/");
                if (sep > 0) {
                    jars.add(new File(fileName.substring(0, sep)));
                }
            }
        }

        // and also scan the current working directory
        final Path workingDirectory = new File("").toPath();
        scanPath(workingDirectory, dirs, jars);
        
        // process directories first, then jars, so that classes take precedence
        // over built jars (it makes rapid development easier in the IDE)
        final Set<Class<?>> classes = new LinkedHashSet<>();
        for (File directory : dirs) {
            classes.addAll(findClassesInDirectory(directory));
        }
        for (File jar : jars) {
            String fullPath = jar.getAbsolutePath();
            if (fullPath.endsWith("jfxrt.jar")) continue;
            classes.addAll(findClassesInJar(new File(fullPath)));
        }
        
        return classes.toArray(new Class[classes.size()]);
    }

    private void scanPath(Path workingDirectory, final List<File> dirs, final List<File> jars) throws IOException {
        Files.walkFileTree(workingDirectory, new SimpleFileVisitor<Path>() {
            @Override public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                final File file = path.toFile();
                final String fullPath = file.getAbsolutePath();
                final String name = file.toString();
                
                if (fullPath.endsWith("jfxrt.jar") || name.contains("jre")) {
                    return FileVisitResult.CONTINUE;
                }
                
                if (file.isDirectory()) {
                    dirs.add(file);
                } else if (name.toLowerCase().endsWith(".jar")) {
                    jars.add(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(Path file, IOException ex) {
                System.err.println(ex + " Skipping...");
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private List<Class<?>> findClassesInDirectory(File directory) throws IOException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + directory.getAbsolutePath());
            return classes;
        }
        
        processPath(directory.toPath(), classes);
        return classes;
    }

    private List<Class<?>> findClassesInJar(File jarFile) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
 
        Map<String, Class<?>> env = new HashMap<>();
        if (!jarFile.exists()) {
            System.out.println("Jar file does not exist here: " + jarFile.getAbsolutePath());
            return classes;
        }
        
        FileSystem jarFileSystem = FileSystems.newFileSystem(jarFile.toPath(),ArrayList.class.getClassLoader());
        //env.forEach((k,v)-> classes.add(v));
        processPath(jarFileSystem.getPath("/"), classes);
        return classes;
    }
    
    private void processPath(Path path, final List<Class<?>> classes) throws IOException {
        final String root = path.toString();
        
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = file.toString();
                
                if (name.endsWith(".class") &&  name.contains("/isoftconta/servicios/"))  {
                    
                    // remove root path to make class name correct in all cases
                    name = name.substring(root.length());
                    
                    Class<?> clazz = processClassName(name);
                    if (clazz != null) {
                        classes.add(clazz);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(Path file, IOException ex) {
                System.err.println(ex + " Skipping...");
                return FileVisitResult.CONTINUE;
            }
        });        
    }

    private Class<?> processClassName(final String name) {
        String className = name.replace("\\", ".");
        className = className.replace("/", ".");
        
        // some cleanup code
        if (className.contains("$")) {
            // we don't care about samples as inner classes, so 
            // we jump out
            return null;
        }
        if (className.contains(".bin")) {
            className = className.substring(className.indexOf(".bin") + 4);
            className = className.replace(".bin", "");
        }
        if (className.startsWith(".")) {
            className = className.substring(1);
        }
        if (className.endsWith(".class")) {
            className = className.substring(0, className.length() - 6);
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Throwable e) {
            // Throwable, could be all sorts of bad reasons the class won't instantiate
         System.out.println("ERROR: Class name: " + className);
          System.out.println("ERROR: Initial filename: " + name);
      //   e.printStackTrace();
        }
        return clazz;
    } 
}
