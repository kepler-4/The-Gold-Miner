package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//绘制窗口
public class GameWin extends JFrame { //继承JFrame类才有监听鼠标键盘事件的功能

    //游戏状态 0未开始 1运行中 2商店 3失败 4胜利
    static int state;
    //创建集合存储金块,石块
    List<Object> objectList = new ArrayList<>();
    Bg bg = new Bg();
    Line line = new Line(this); //需要传参 不然报错


    //用代码块来添加金块
    {
        boolean isPlace = true;  //是否可以放置
        for(int i = 0;i <11;i++){
            double random = Math.random();

            Gold gold;  //添加成员变量 存放当前生成的金块
            if(random<0.3){
                gold=new GoldMini();
            }else if(random<0.7){
                gold=new Gold();
            }else {gold=new GoldPlus();
            }
            for (Object obj:objectList){
                if(gold.getRec().intersects(obj.getRec())){ //判断金块是否与金块重叠
                    //若重叠 不能放置需重新生成
                    isPlace = false;
                }
            }
            if(isPlace){ //判断是否可放置
                objectList.add(gold); //可以就添加
            }else {
                isPlace=true;
                i--;
            }
        }
        for(int i = 0;i <8;i++){
            Rock rock = new Rock();
            for(Object obj:objectList){
                if(rock.getRec().intersects(obj.getRec())){
                    isPlace = false;
                }
            }
            if(isPlace){
                objectList.add(rock);
            }else {
                isPlace=true;
                i--;
            }
        }
    }
    //绘制画布解决画面闪动问题 (双缓存技术)
    Image offScreenImage; //绘制画布


    //launch方法用来初始化窗口信息
    void launch(){
        this.setVisible(true);  //设置窗口是否可见
        this.setSize(768,1000);  //设置窗口大小
        this.setLocationRelativeTo(null);   //设置窗口位置(居中)
        this.setTitle("猫猫的黄金矿工");      //设置窗口标题
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //设置窗口关闭的方法(点击右上角X号可以关闭程序)

        //鼠标事件来改变函数

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);     //点击事件
                switch (state){
                    case 0:
                        if(e.getButton()==3){state=1;}
                        bg.startTime = System.currentTimeMillis();
                        break;
                    case 1:
                        //左右摇摆
                        if(e.getButton()==1&&line.state==0)
                        {line.state=1;}//点击了鼠标左键 左键是1 右键是3
                        //抓取返回 点击右键 药水数量减1
                        if(e.getButton()==3&&line.state==3&&Bg.waterNum>0)
                        {
                            Bg.waterFlag=true;
                            Bg.waterNum--;
                        }
                        break;
                    case 2:
                        if(e.getButton()==1){
                            bg.shop=true;
                        }
                        if(e.getButton()==3){
                            state=1;
                            bg.startTime=System.currentTimeMillis();
                        }
                        break;
                    case 3:      //要删除这里的break
                    case 4:
                        if(e.getButton()==1){
                            state=0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:
                }

            }
        });

        while (true){ //红线的左右摆动 死循环
            repaint(); //重新绘制
            nextlevel();
            try {
                Thread.sleep(10); //延时效果 10毫秒刷新一次
            } catch (InterruptedException e) {   //抛出异常
                e.printStackTrace();
            }
        }

    }

    //过关 下一关
    public void nextlevel(){
        if(bg.gameTime() && state==1){
            if(Bg.count>=bg.gold){
                if(Bg.level==5){
                    state=4;
                }else {
                    state=2;
                    Bg.level++;
                }
            }else {
                state = 3;
            } dispose();
            GameWin gameWin = new GameWin();
            gameWin.launch();
        }

    }


    //绘制图片的方法
    @Override
    public void paint(Graphics g) { //参数传入画笔
        offScreenImage = this.createImage(768,1000); //画布的大小
        Graphics gImage = offScreenImage.getGraphics();
        bg.paintSelf(gImage);             //调用类中的方法即可

        if(state==1){
            //增强for循环 绘制物体
            for (Object obj:objectList){
                obj.paintSelf(gImage);
            }
            line.paintSelf(gImage);
        }


        g.drawImage(offScreenImage,0,0,null); //把画布绘制到窗口中
    }

    public static void main(String[] args) {
        GameWin gameWin=new GameWin();
        gameWin.launch();  //调用方法
    }


}
