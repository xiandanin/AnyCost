## 安装
```
implementation 'in.xiandan:anycost:1.0.1'
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
AnyCost.getInstance().setOnTimingEndListener(object : AnyCost.OnTimingListener() {
    override fun onTimingBegin(key: String?, threadName: String?) {

    }

    override fun onTimingEnd(key: String?, threadName: String?, time: Long) {
        
    }
})
```
