package com.sxt;

import java.awt.*;

public class Object {
    //坐标
    int x;
    int y;
    //宽高
    int width;
    int height;
    //图片
    Image img;
    //标记死否能移动
    boolean flag;
    //质量
    int m;
    //积分
    int count;
    //类型1金块 2石块
    int type;

    //绘制图片
    void paintSelf(Graphics g){ g.drawImage(img,x,y,null);}

    //获取他们的宽度，这样在线中可以直接调用
    public int getWidth() {
        return width;
    }
    //获取矩形 解决物体堆叠问题
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
    }

}
