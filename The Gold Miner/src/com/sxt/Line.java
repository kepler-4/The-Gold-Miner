package com.sxt;

import java.awt.*;

public class Line {
    //线的起点坐标
    int x=380;
    int y=180;
    //终点坐标
    int endx=500;
    int endy=500;
    //线长
    double lenght = 100;
    //线长最小值
    double MIn_lenght =100;
    //最大值
    double MAX_lenght = 750;
    double n = 0;
    //方向
    int dir = 1;
    //状态 0摇摆 1向下抓取 2收回 3抓取返回
    int state;
    //钩子图片
    Image hook = Toolkit.getDefaultToolkit().getImage(("imgs/hook.png"));

    //红线类要接收主窗口的元素
    GameWin frame;
    Line (GameWin frame){ //有参构造 参数是此窗口
        this.frame=frame;
    }

    //判断方法 碰撞检测 检查物体是否被抓取
    void logic(){
        for(Object obj:this.frame.objectList){
            if(endx>obj.x && endx<obj.x+obj.width
                    && endy>obj.y && endy<obj.y+obj.height){
                state=3;
                obj.flag=true;
            }
        }

    }
    //绘制方法
    void lines(Graphics g){
        endx = (int) (x +lenght*Math.cos(n*Math.PI)); //强制类型转换(int)
        endy = (int) (y +lenght*Math.sin(n*Math.PI));
        g.setColor(Color.red);
        g.drawLine(x-1,y,endx-1,endy);
        g.drawLine(x,y,endx,endy);
        g.drawLine(x+1,y,endx+1,endy); //画线 需要传入4个参数
        g.drawImage(hook,endx-36,endy-2,null); //红线的终点坐标
    }

    void paintSelf(Graphics g){  //绘制方法
        logic();
        switch (state){
            case 0:  //如果是0就左右摇摆
                if(n<0.1){ dir =1;}  //线的顺时针旋转
                else if(n>0.9){ dir=-1;} //逆时针
                n=n+0.005*dir;
                lines(g);
                break;
            case 1:  //不停延长线的长度
                if(lenght<750){ //设置线的长度 小于500则可以延长
                    lenght=lenght+5;
                    lines(g);
                }else {
                    state=2;  //左右摇摆
                }
                break;
            case 2:  //收回线长
                if(lenght>MIn_lenght){
                    lenght=lenght-5;
                    lines(g);
                }else {
                    state=0;
                }
                break;
            case 3: //抓取返回
                int m=1;
                if(lenght>MIn_lenght){
                    lenght=lenght-5;
                    lines(g);
                    for (Object obj:this.frame.objectList){
                        if(obj.flag){
                            m=obj.m;
                            obj.x=endx-obj.getWidth()/2; //偏移量 金块的一半
                            obj.y=endy;
                            if(lenght<=MIn_lenght){
                                obj.x=-150;  //移除金块
                                obj.y=-150;
                                obj.flag=false;
                                Bg.waterFlag=false;
                                //加积分
                                Bg.count+=obj.count;
                                state=0;
                        }
                            if(Bg.waterFlag){
                                if(obj.type==1){
                                    m=1;
                                }
                               if(obj.type==2){
                                   obj.x=-150;  //移除石块
                                   obj.y=-150;
                                   obj.flag=false;
                                   Bg.waterFlag=false;
                                   state=2;
                               }
                            }
                        }
                    }

                }
                //添加延时效果，抓取石块时更慢
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {   //抛出异常
                    e.printStackTrace();
                }
                break;
            default:
        }




    }
    //重置线中的元素
    void reGame(){
        n=0; //线的角度 百分比
        lenght=100;
    }
}
