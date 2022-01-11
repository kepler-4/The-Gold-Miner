package com.sxt;

import java.awt.*;

public class Bg {
    //关卡数
    static  int level=1;
    //目标得分
    int gold = level*15;
    //总分
    static int count=0;
    //药水数量
    static int waterNum=3;
    //药水状态 默认false true表示正在使用
    static boolean waterFlag = false;
    //开始时间
    long startTime;
    //结束时间
    long endTime;
    //药水价格
    int price = (int) Math.random(); //强制转换
    //是否购买 false不买
    boolean shop = false;
    //载入背景图片
    Image bg=Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
    Image bg1=Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
    Image peo=Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
    Image water=Toolkit.getDefaultToolkit().getImage("imgs/water.png");


    void paintSelf(Graphics g){  //绘制自己 参数传入画笔

        g.drawImage(bg1,0,0,null);
        g.drawImage(bg,0,200,null); //图片路径 Image类型 起始坐标
        switch (GameWin.state){
            case 0:
                drawWord(g,80,Color.yellow,"准备开始",200,400);

                break;
            case 1:
                g.drawImage(peo,310,50,null);
                drawWord(g,30,Color.black,"积分："+count,30,150);

                //绘制药水
                g.drawImage(water,450,40,null);
                drawWord(g,30,Color.black,"*"+waterNum,510,70);
                //关卡数
                drawWord(g,20,Color.black,"第"+level+"关",30,60);
                //目标积分
                drawWord(g,30,Color.black,"目标:"+gold,30,110);
                //时间组件
                endTime = System.currentTimeMillis();
                long time =20-(endTime-startTime)/1000; //将毫秒转换为秒 倒计时
                drawWord(g,30,Color.black,"时间:"+(time>0?time:0),520,150);
                break;
            case 2:
                g.drawImage(water,300,400,null);
                drawWord(g,30,Color.black,"价格"+price,300,500);
                drawWord(g,30,Color.black,"是否购买"+waterNum,300,550);
                if(shop){
                    count=count-price;
                    waterNum++;
                    shop=false;
                    GameWin.state=1;
                    startTime=System.currentTimeMillis();
                }
                break;
            case 3:
                drawWord(g,80,Color.black,"失败了",250,350);
                drawWord(g,80,Color.black,"积分："+count,200,450);

                break;
            case 4:
                drawWord(g,80,Color.red,"成功了",250,350);
                drawWord(g,80,Color.red,"积分："+count,200,450);
                break;
        }

    }
    //true倒计时完成 false正在倒计时
    boolean gameTime(){
        long tim = (endTime-startTime)/1000;
        if (tim>20){
            return true;
        }

        return false;
    }

    //重置元素的方法
    void reGame(){
        //关卡数
         level=1;
        //目标得分
         gold = level*15;
        //总分
        count=0;
        //药水数量
         waterNum=3;
        //药水状态 默认false true表示正在使用
         waterFlag = false;
    }

    //打印字符串的方法
    public static void drawWord(Graphics g,int size,Color color,String str,int x,int y){
        //打印积分
        g.setColor(color);
        g.setFont(new Font("仿宋",Font.BOLD,size));
        g.drawString(str,x,y);
    }
}
