package com.codebuilder.game.main.snake.code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * 面板类 继承JPanel
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    /**
     * 蛇的长度
     */
    private int length;

    /**
     * 蛇的坐标XY
     */
    private int[] snakeX = new int[600];
    private int[] snakeY = new int[500];

    /**
     * 蛇的方向
     */
    private String fx;

    /**
     * 判断游戏是否开始
     */
    private boolean isStart = false;

    /**
     * 100是速度
     */
    Timer timer;

    /**
     * food
     */
    private int foodX;
    private int foodY;
    Random random = new Random();

    /**
     * 失败
     */
    boolean isDie = false;

    /**
     * 积分
     */
    private int score;

    /**
     * 速度
     */
    private int speed;

    /**
     * 初始化
     */
    public void init() {
        speed = 200;
        timer = new Timer(speed, this);
        fx = Data.RIGHT;
        length = 3;
        score = 0;
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;

        // 生成食物
        foodX = 25 + 25 * random.nextInt(34);
        foodY = 75 + 25 * random.nextInt(24);
    }

    public GamePanel() {
        this.init();
        // 获取键盘监听事件
        // 开启监听事件
        this.setFocusable(true);
        // 添加监听事件的类 -- 当前类
        this.addKeyListener(this);
        timer.start();
    }

    /**
     * 面板
     *
     * @param g 画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 清屏
//        设置背景颜色
        this.setBackground(Color.WHITE);
//        绘制头部广告栏
        Data.header.paintIcon(this, g, 25, 11);
//        绘制游戏区域
        g.fillRect(26, 75, 850, 600);
//      画一条静态小蛇
        if (fx.equals(Data.RIGHT)) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals(Data.LEFT)) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals(Data.UP)) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals(Data.DOWN)) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        // 画食物
        Data.food.paintIcon(this, g, foodX, foodY);

        //画积分
        g.setColor(Color.YELLOW);
        g.setFont(new Font("微软雅黑", Font.BOLD, 15));
        g.drawString("长度: " + length, 800, 35);
        g.drawString("积分: " + score, 800, 50);

        for (int i = 1; i < length; i++) {
            //蛇的长度通过数组打印出来
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        // 游戏提示: 是否开始
        if (!isStart) {
            // 画一个文字
            g.setColor(Color.blue);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏", 300, 300);
        }

        // 游戏提示: 失败
        if (isDie) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("失败!!!! 按下空格重新开始", 300, 300);
        }

    }

    //        接受键盘的输入:监听

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 键盘按下 未释放
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            // 按下空格
            if (isDie) {
                isDie = false;
                init();
            } else {
                // 反向选择 不错的方法 启动游戏
                isStart = !isStart;
            }


            repaint(); // 刷新界面
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            fx = Data.LEFT;
        } else if (keyCode == KeyEvent.VK_UP) {
            fx = Data.UP;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            fx = Data.DOWN;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            fx = Data.RIGHT;
        }
    }

    /**
     * 时间监听实现
     * 定时器, 监听时间 帧 执行定时操作
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && !isDie) {
            //右移
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }

            // 通过控制方向让头部移动
            if (fx.equals(Data.RIGHT)) {
                // 头部移动
                snakeX[0] = snakeX[0] + 25;
                // 边界判断
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (fx.equals(Data.LEFT)) {
                // 头部移动
                snakeX[0] = snakeX[0] - 25;
                // 边界判断
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (fx.equals(Data.UP)) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if (fx.equals(Data.DOWN)) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }

            // 如果小蛇头和食物重合
            if (snakeX[0] == foodX && snakeY[0] == foodY) {
                length++;
                score += 10;
                if (speed > 50) {
                    timer.setDelay(speed -= 50);
                }

                // 重新生成食物
                foodX = 25 + 25 * random.nextInt(34);
                foodY = 75 + 25 * random.nextInt(24);
            }

            // 游戏失败事件
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isDie = true;
                }
            }

            // 刷新界面
            repaint();
        }
        timer.start();
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // 键盘按下弹起: 敲击键盘
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // 释放某个键
    }


}
