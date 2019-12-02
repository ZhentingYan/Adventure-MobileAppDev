# Adventure-MobileAppDev
## 开发进度
表头|完成内容
---|:--:|---:
2019.12.02|完成项目的重构；修复了用户第一次进入应用后定位失败造成闪退的Bug；修复了距离显示的问题；增加了MyProfile界面的逻辑；增加了大部分函数的规范注释；抽象出了AMapHelper、CityPikerHelper、SessionManager；项目使用MVP架构，若业务逻辑触及Model则一个View对应一个Presenter

## 说明
可参照ScenicSpotFragment参考编写Story模块，如果需要调用用户当前登录信息，可以声明mSessionManager变量，在OnViewCreated中实例化。
在Presenter中声明Dao变量，进行业务逻辑处理，然后调用View的显示函数。

