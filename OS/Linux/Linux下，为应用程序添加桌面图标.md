#Linux为应用程序添加桌面图标

###一、桌面图标位置
Lniux下桌面图标储存路径为：/usr/share/applications

###二、桌面图标格式
所有桌面图标格式均为desktop，即名为XXX.desktop

###三、编辑内容（常用）
```vim
// 文件头（必须）
[Desktop Entry]

Name=XXX					  # 程序名（必须）
Exec=XXX                      # 执行脚本（若为应用程序桌面图标则必须）
Type = Application            # desktop的类型(必须),常见值有“Application”和“Link”.

Icon=XXX					  # 图标
Encoding=UTF-8                # 编码方式
Comment=comment               # 鼠标经过上面时的提示名称
GenericName = Web Browser     # 程序通用名称
Terminal = false              # 是否在终端中运行,当Type为Application,此项有效.
Categories = GNOME;Application;Network;   # 注明在菜单栏中显示的类别
```

###四、示例（以创建wps文字图标）
Alt+Ctrl+t打开终端，键入：cd /usr/share/applications 进入桌面图标文件夹。

键入：sudo vim ./wps-word.desktop 以管理员权限建立图标文件wps-word.desktop。键入i进入插入模式，输入以下内容：
```vim
[Desktop Entry]

Name = wps word
Exec = /usr/local/wps-office_10.1.0.6757_x86_64/wps
Icon = /usr/local/wps-office_10.1.0.6757_x86_64/resource/icons/hicolor/256x256/apps/wps-office-wpsmain.png
Encoding = UTF-8
Comment = wps word
Type = Application
```

ESC回到命令行模式，*:wq*保存文件并退出。此时，应用程序列表就多出了一个名为wpd word的图标，即完成。

**备注：**若启动项是带有欢迎界面（例如matlab），Exec = 路径/matlab 后要加-desktop，即：

Exec = 路径/matlab -desktop