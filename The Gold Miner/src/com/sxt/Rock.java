package com.sxt;

import java.awt.*;

public class Rock extends Object{
    Rock(){
        this.x=(int)(Math.random()*700); //防止金块生成到窗体上
        this.y=(int)((Math.random()*550+300)); //防止金块生成到天上
        this.width=71;
        this.height=71;
        this.flag=false;
        this.m=50;
        this.count=1;
        this.type=2;
        this.img= Toolkit.getDefaultToolkit().getImage("imgs/rock1.png");
    }

}
