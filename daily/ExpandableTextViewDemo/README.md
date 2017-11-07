### 自定义属性
* 可展开的最大行数
* 内容文字的大小
* 内容文字的颜色
* 展开的图
* 展开的文字
* 折叠的图
* 展开文字的位置
* 展开动画的间隔时长
* 内容文字行间距

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="ExpandableTextView">
        <!--内容文字大小-->
        <attr name="contentTextSize" format="dimension"/>
        <!--内容文字颜色-->
        <attr name="contentTextColor" format="color"/>
        <!--最大可折叠行数-->
        <attr name="maxCollapseLines" format="integer"/>
        <!--内容行间距乘数-->
        <attr name="contentLineSpacingMultiplier" format="float"/>
        <!--展开的图片-->
        <attr name="expandDrawable" format="reference"/>
        <!--展开的文字-->
        <attr name="expandText" format="string"/>
        <!--折叠的图片-->
        <attr name="collapseDrawable" format="reference"/>
        <!--折叠的文字-->
        <attr name="collapseText" format="string"/>
        <!--展开折叠的文字颜色-->
        <attr name="ExpandCollapseTextColor" format="color"/>
        <!--动画时长-->
        <attr name="animationDuration" format="integer"/>
        <!--位置-->
        <attr name="DrawableAndTextGravity">
            <enum name="left" value="0"/>
            <enum name="center" value="1"/>
            <enum name="right" value="2"/>
        </attr>
    </declare-styleable>
</resources>
```

### 使用
```
<com.sx.expandabletextview.ExpandableTextView
    android:id="@+id/tv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:DrawableAndTextGravity="center"
    app:animationDuration="500"
    app:collapseText="less"
    app:expandText="more"
    app:maxCollapseLines="4"/>

```