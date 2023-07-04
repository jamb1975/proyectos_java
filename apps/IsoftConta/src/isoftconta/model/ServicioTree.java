package isoftconta.model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import isoftconta.Servicios;

public class ServicioTree {
    private TreeNode root;
    
    private int count = 0;

    public ServicioTree(Servicios rootServicios) {
        root = new TreeNode(null, null, rootServicios);
    }
    
    public TreeNode getRoot() {
        return root;
    }
    
    public Object size() {
        return count;
    }
    
    public void addServicio(String[] packages, Servicios servicios) {
        if (packages.length == 0) {
            root.addServicio(servicios);
            return;
        }
        
        TreeNode n = root;
        for (String packageName : packages) {
            if (n.containsChild(packageName)) {
                n = n.getChild(packageName);
            } else {
                TreeNode newNode = new TreeNode(packageName);
                n.addNode(newNode);
                n = newNode;
            }
        }
        
        if (n.packageName.equals(packages[packages.length - 1])) {
            n.addServicio(servicios);
            count++;
        }
    }
    
    @Override public String toString() {
        return root.toString();
    }
    

    public static class TreeNode {
        private final Servicios servicios;
        private final String packageName;
        
        private final TreeNode parent;
        private List<TreeNode> children;
        
        public TreeNode() {
            this(null, null, null);
        }
        
        public TreeNode(String packageName) {
            this(null, packageName, null);
        }
        
        public TreeNode(TreeNode parent, String packageName, Servicios servicios) {
            this.children = new ArrayList<>();
            this.servicios = servicios;
            this.parent = parent;
            this.packageName = packageName;
        }
        
        public boolean containsChild(String packageName) {
            if (packageName == null) return false;
            
            for (TreeNode n : children) {
                if (packageName.equals(n.packageName)) {
                    return true;
                }
            }
            return false;
        }
        
        public TreeNode getChild(String packageName) {
            if (packageName == null) return null;
            
            for (TreeNode n : children) {
                if (packageName.equals(n.packageName)) {
                    return n;
                }
            }
            return null;
        }
        
        public void addServicio(Servicios servicios) {
            children.add(new TreeNode(this, null, servicios));
        }
        
        public void addNode(TreeNode n) {
            children.add(n);
        }
        
        public Servicios getServicios() {
            return servicios;
        }
        
        public String getPackageName() {
            return packageName;
        }
        
        public TreeItem<Servicios> createTreeItem() {
            TreeItem<Servicios> treeItem = null;
            
            if (servicios != null) {
                treeItem = new TreeItem<Servicios>(servicios);
            } else if (packageName != null) {
                treeItem = new TreeItem<Servicios>(new EmptyServicio(packageName));
            }
            
            treeItem.setExpanded(true);
            
            // recursively add in children
            for (TreeNode n : children) {
                treeItem.getChildren().add(n.createTreeItem());
            }
            
            return treeItem;
        }
        
        @Override public String toString() {
            if (servicios != null) {
                return " Sample [ sampleName: " + servicios.getServicioNombre()+ ", children: " + children + " ]";
            } else {
                return " Sample [ packageName: " + packageName + ", children: " + children + " ]";
            }
        }
    }
}