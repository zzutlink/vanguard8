package com.vanguard8.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    //id：节点ID，对加载远程数据很重要。
    //text：显示节点文本。
    //state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
    //checked：表示该节点是否被选中。
    //attributes: 被添加到节点的自定义属性。
    //children: 一个节点数组声明了若干节点。

    private String id ;
    private String text ;
    private String dsCode ;
    private String _parentId;
    private String state ;
    private Object attributes;
    private List<TreeNode> children;
    private String iconCls;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDsCode() {
        return dsCode;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    //值传递
    public void setChildren(List<TreeNode> children) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for(int i=0;i<children.size();i++){
            nodes.add(children.get(i));
        }
        this.children = nodes;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append("id=").append(id);
        sb.append(", text=").append(text);
        sb.append(", dsCode=").append(dsCode);
        sb.append("]");
        return sb.toString();
    }
}
