package com.clog.fballoon.app;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static com.clog.fballoon.util.Constant.FRAME_HEIGHT;
import static com.clog.fballoon.util.Constant.FRAME_WIDTH;
import static com.clog.fballoon.util.Constant.FRAME_X;
import static com.clog.fballoon.util.Constant.FRAME_Y;
import static com.clog.fballoon.util.Constant.FPS;
import static com.clog.fballoon.util.Constant.GAME_TITLE;

public class App extends Frame {
    private static final long serialVersionUID = 1L; // 保持版本的兼容性

    private static int gameState; // 游戏状态
    public static final int GAME_READY = 0; // 游戏未开始
    public static final int GAME_START = 1; // 游戏开始
    public static final int STATE_OVER = 2; // 游戏结束

    private GameBackground background; // 游戏背景对象
    private GameForeground foreground; // 游戏前景对象
    private Bird bird; // 小鸟对象
    private GameElementLayer gameElement; // 游戏元素对象
    private WelcomeAnimation welcomeAnimation; // 游戏未开始时对象

    public App() {
        initFrame();


    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // 设置窗口大小
        setTitle(GAME_TITLE); // 设置窗口标题
        setLocation(FRAME_X, FRAME_Y); // 窗口初始位置
        setResizable(false); // 设置窗口大小不可变
        // 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 结束程序
            }
        });
        addKeyListener(new BirdKeyListener()); // 添加按键监听
    }
}
