## 安装
```
implementation 'in.xiandan:anycost:1.0.2'
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
AnyCost.getInstance().enable(true)
```

### 计时监听
```
// 添加监听
AnyCost.getInstance().addOnTimingEndListener(object : AnyCost.OnTimingListener() {
    override fun onTimingBegin(key: String?, threadName: String?) {

    }

    override fun onTimingEnd(key: String?, threadName: String?, time: Long) {
        
    }
})
//移除监听
AnyCost.getInstance().removeOnTimingEndListener(listener)
```
