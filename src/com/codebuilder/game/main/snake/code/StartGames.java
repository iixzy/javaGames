package com.codebuilder.game.main.snake.code;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
//        绘制一个静态窗口
        JFrame jFrame = new JFrame("CodeBuilder的贪吃蛇2020年12月27日");
//        设置界面大小
        jFrame.setBounds(10,10,900,720);
//        窗口大小不可改变
        jFrame.setResizable(false);
//        窗口关闭事件
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new GamePanel());
//        窗口可见性
        jFrame.setVisible(true);







    }
}
