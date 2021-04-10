## 安装
```
implementation 'in.xiandan:anycost:1.0.3'
```

## 使用
### 开始计时
```
AnyCost.begin(key)
```

### 结束计时
```
long end = AnyCost.end(key)
```

### 启用或禁用
```
AnyCost.getInstance().enable(BuildConfig.DEBUG)
```

### 计时监听
```
// 添加监听
AnyCost.getInstance().addOnTimingListener(new OnTimingListener() {
    @Override
    public void onTimingBegin(String key, String threadName) {
        
    }

    @Override
    public void onTimingEnd(String key, String threadName, long time, Object extras) {
        
    }
});

//移除监听
AnyCost.getInstance().removeOnTimingEndListener(listener)

//移除所有监听
AnyCost.getInstance().removeOnTimingEndListener()
```

### 注解计时
如果需要通过注解自动计时，还需要启用插件
```
buildscript {
    dependencies {
        classpath 'in.xiandan:anycost-gradle-plugin:1.0.1'
    }
}
```

`app`-`build.gradle`
```
plugins {
    id 'anycost'
}
```
在需要计算耗时的方法加上`AnyCostMark`注解
```
@AnyCostMark(key = "test")
private void test() {
}
```

### Kotlin扩展
#### 计算代码块耗时
```
cost(key) {
    // 自动计算此代码块所耗的时间
}
```

#### 添加计时结束监听
```
AnyCost.getInstance().addOnTimingListener { key, threadName, time, extras ->

}
```
