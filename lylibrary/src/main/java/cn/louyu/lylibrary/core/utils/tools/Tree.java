package cn.louyu.lylibrary.core.utils.tools;

import java.util.*;

/**
 * 树数据结构
 */

public class Tree<T> {
    private T data; //携带的数据
    private List<Tree<T>> childs; //孩子节点
    private Tree<T> parent; //此节点的父节点

    private Tree(T data){
        this(data,null);
    }

    public Tree(T data,Tree<T> parent){
        this(data,null,parent);
    }

    public Tree(T data,List<Tree<T>> childs,Tree<T> parent){
        this.data = data;
        this.parent = parent;
        if(childs == null) {
            this.childs = new ArrayList<Tree<T>>();
        }else {
            this.childs = childs;
        }
        if(parent!=null) parent.add(this);
    }

    /**
     * 添加子树
     * */
    public void add(Tree<T> tree){
        childs.add(tree);
    }

    /**
     * 清空树
     * */
    public void clear(){
        childs.clear();
    }

    /**
     * 在它的双亲中移除自己包括后代
     * */
    public void remove(){
        if(this.parent!=null){
            this.parent.getChilds().remove(this);
        }
        clear();
    }

    /**
     * 递归求树的深度
     */
    public int dept(){
        return dept(this);
    }

    /**
     * 指定树的深度
     * */
    private int dept(Tree<T> tree){
        if(!tree.hasChilds()) return 0;
        int [] array=new int[childs.size()];
        for(int i=0;i<childs.size();i++){
            array[i]=childs.get(i).dept(childs.get(i));
        }
        //升序排序
        Arrays.sort(array);
        return array[array.length-1]+1;
    }

    /**
     * 这颗树的总节点数
     * */
    public int size(){
        return size(this)+1;
    }

    /**
     * 指定树的子节点数
     * */
    private int size(Tree<T> tree){
        int count=childs.size();
        for(int i=0;i<childs.size();i++){
            count+=childs.get(i).size(childs.get(i));
        }
        return count;
    }

    /**
     * 递归获取根节点
     * */
    public Tree<T> getRoot(){
        if(parent!=null){
            return parent.getRoot();
        }
        return this;
    }

    /**
     * 判断树是否为空
     * */
    public boolean isEmpty(){
        if(childs.isEmpty() && data == null)
            return true;
        return false;
    }

    /**
     * 判断此节点是否有孩子节点
     * */
    public boolean hasChilds() {
        return !childs.isEmpty();
    }

    /**
     * 获取当前节点的数据
     * */
    public T getData() {
        return data;
    }

    /**
     * 设置当前节点的数据
     * */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取指定的子节点
     * */
    public Tree<T> getChild(int index){
        return childs.get(index);
    }

    /**
     * 获取兄弟节点
     * */
    public List<Tree<T>> getChilds() {
        return childs;
    }

    /**
     * 前根遍历
     * （1）.访问根结点；
     * （2）.按照从左到右的次序行根遍历根结点的第一棵子树；
     * */
    public List<Tree<T>> preVisit(Tree<T> tree){
        List<Tree<T>> values=new ArrayList<Tree<T>>();
        if (tree.isEmpty()) return values;
        values.add(tree);
        for(int i=0;i<childs.size();i++){
            values.addAll(childs.get(i).preVisit(childs.get(i)));
        }
        return values;
    }

    /**
     * 前根遍历（深度遍历）
     * */
    public List<T> preVisitValue(){
        List<Tree<T>> values=preVisit(this);
        List<T> vals=new ArrayList<T>();
        for (Tree<T> t : values) {
            vals.add(t.getData());
        }
        return vals;
    }

    /**
     * 前根遍历（深度遍历）
     * */
    public List<Tree<T>> preVisit(){
        return preVisit(this);
    }

    /**
     * 后根遍历
     * （1）.按照从左到右的次序行根遍历根结点的第一棵子树；
     * （2）.访问根结点；
     * */
    public List<Tree<T>> sufVisit(Tree<T> tree){
        List<Tree<T>> values=new ArrayList<Tree<T>>();
        if (tree.isEmpty()) return values;
        for(int i=0;i<childs.size();i++){
            values.addAll(childs.get(i).sufVisit(childs.get(i)));
        }
        values.add(tree);
        return values;
    }

    /**
     * 后根遍历（深度遍历）
     * */
    public List<T> sufVisitValue(){
        List<Tree<T>> values=sufVisit(this);
        List<T> vals=new ArrayList<T>();
        for (Tree<T> t : values) {
            vals.add(t.getData());
        }
        return vals;
    }

    /**
     * 后根遍历（深度遍历）
     * */
    public List<Tree<T>> sufVisit(){
        return sufVisit(this);
    }

    /**
     * 层次遍历（广度遍历）
     * (1) 先访问根节点
     * (2) 访问第n层时，将第n+1层的节点放入队列
     * (3) 进入下一层
     * (4) 重复上面的操作
     * */
    public List<Tree<T>> levelVisit(Tree<T> tree){
        List<Tree<T>> values=new ArrayList<Tree<T>>();
        if(tree==null) return values;
        Queue<Tree<T>> queue=new LinkedList<Tree<T>>();
        ((LinkedList<Tree<T>>) queue).add(tree);
        while (!queue.isEmpty()){
            for (int i=0;i<queue.size();i++){
                Tree<T> temp=queue.poll();
                if(temp.isEmpty()) continue;
                //Do Something
                values.add(temp);
                if(!temp.hasChilds()) continue;
                for (int j=0;j<temp.getChilds().size();j++){
                    if(temp.getChild(j).isEmpty()) continue;
                    queue.add(temp.getChild(j));
                }
            }
        }
        return values;
    }

    /**
     * 层次遍历（广度遍历）
     * */
    public List<T> levelVisitValue(){
        List<Tree<T>> values=levelVisit(this);
        List<T> vals=new ArrayList<T>();
        for (Tree<T> t : values) {
            vals.add(t.getData());
        }
        return vals;
    }

    /**
     * 层次遍历（广度遍历）
     * */
    public List<Tree<T>> levelVisit(){
        return levelVisit(this);
    }

    /**
     * 树中的节点是否包含某个值
     * */
    public boolean containsValue(T val){
        List<T> values=preVisitValue();
        return values.contains(val);
    }

    /**
     * 获取当前的父类节点
     * */
    public Tree<T> getParent(){
        return this.parent;
    }

    /**
     * 判断是不是当前节点的祖先节点
     * */
    public boolean isAncestor(Tree<T> ancestor){
        if (this.parent == null||ancestor==null) return false;
        if (this.parent==ancestor) return true;
        return this.parent.isAncestor(ancestor);
    }

    /**
     * 获取节点所在的层级（相对于根,从0开始）
     * */
    public int selfTierNum(){
        if(this.parent==null){
            return 0;
        }
        return this.parent.selfTierNum()+1;
    }

}