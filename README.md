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
AnyCost.end(key)
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

### Kotlin扩展
使用kotlin，可以更简单

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