package com.sxt;

import java.awt.*;

public class Gold extends Object{



    Gold(){ //无参构造
        this.x=(int)(Math.random()*700); //防止金块生成到窗体上
        this.y=(int)((Math.random()*550+300)); //防止金块生成到天上
        this.width=52;
        this.height=52;
        this.flag=false;
        this.m=30;
        this.count=4;
        this.type=1;
        this.img= Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
    }

}
    //让其他金块继承金块类
    class GoldMini extends Gold{
        GoldMini(){
            this.width=36;
            this.height=36;
            this.m=15;
            this.count=2;
            this.img= Toolkit.getDefaultToolkit().getImage("imgs/gold0.gif");

        }
}
    class GoldPlus extends Gold{
    GoldPlus(){
        this.x=(int)(Math.random()*650);
        this.width=105;
        this.height=105;
        this.m=60;
        this.count=8;
        this.img= Toolkit.getDefaultToolkit().getImage("imgs/gold2.gif");

    }
}