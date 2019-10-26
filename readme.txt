keytool -genkeypair -alias alias -keyalg RSA -keystore key.keystore -storepass storepass
一个keystore可以存储很多的公私钥key。
keytool -list -keystore key.keystore 查看keystore
keytool -delete -alias alias2 -keystore key.keystore

下载安装openssl，配置环境变量，执行如下代码导出publicKey。
keytool -list -rfc --keystore key.keystore | openssl x509 -inform pem -pubkey



关于spring 注解的一些解释
@Import，这个注解相当于扫描特定的配置类(因为某些配置类可能不在当前扫描包中)。
@ImportResource 类似于import,只不过是用来导入xml格式的bean定义。
@ConfigurationProperties(prefix = "spring.datasource"),这个注解表明将程序从配置文件中获取到的属性绑定到类属性上。
@PropertySource，指定配置文件。此注解指定的配置文件最好使用.properties后缀。用yaml后缀会解析不出来。


