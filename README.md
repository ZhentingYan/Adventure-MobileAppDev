# Adventure-MobileAppDev
## 任务清单
1. Story修改存在Bug，如果点进去修改Story界面，再点击图片想更新图片会闪退
2. 修改用户信息以后，MyAdventure界面还是原来的名字
3. 需要添加头像功能，建议放在PhoneNum的前一个Fragment操作，和发布故事不同的是希望能够有一个大的图像预览，布局文件叫做fragment_add_avatar.xml

## 重构、代码审查要求

1. 我写的代码都加上注释了，麻烦把剩下代码加上注释
2. 参考PPT使用Android Lint和SonarQube进行代码审查
3. View接口中把方法写的详细一些，不要就show，写清楚是成功还是失败，show的什么东西

## 说明

可参照ScenicSpotFragment参考编写Story模块，如果需要调用用户当前登录信息，可以声明mSessionManager变量，在OnViewCreated中实例化。
在Presenter中声明Dao变量，进行业务逻辑处理，然后调用View的显示函数。

