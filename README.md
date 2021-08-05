# KikaApiRegressionTesting
一、主要类介绍

1.Util包

  1)CsvAction类：读写Csv文件的类，为单例模式
  
  2）EmptyUtil类：判断对象是否为空的工具类
  
  3）LogUtil类：日志打印的工具类
  
  4）RequestConstructer类：根据csv文件中数据构造okhttp request对象（也就是请求）的工具类，为单例模式。
  
  构造Get请求的方法为ConstructGetRequest，构造Post请求的方法ConstructPostRequest
  构造get请求时，会先检查字符串数组是否为空，
  
  然后调用CheckValidity方法检查请求头参数个数和请求头value个数是否匹配以及请求体参数个数和请求体value个数是否匹配
  
  然后将读取paramsArray字符串数组中请求头参数/请求头value/请求体参数/请求体value并分别存入对应的字符串数组，
  
  然后分别遍历请求头参数字符串数组和请求体参数字符串数组，并将参数和value构造成请求头和请求体的HashMap，
  
  然后用请求头和请求体的HashMap以及接口url作为参数调用httpGet方法来构造okhttp的request对象并返回
  
  5)SignGenerater类：接口签名生成工具
  
  6）GetParamsArray接口：每个测试类都必须继承这个接口并实现其getCsvParams方法读取用例对应的数据
  
2.AssertInterface包

  1）ParamExistCheck接口：检查response中data字段的value是否包含特定单个或多个字段的类型（字段使用字符串格式数组进行存储）
  
  2）ResourceArrayCheck接口：检查data字段的value的内容为json数组格式是否正确，数组大小是否不为0（适用于返回资源列表类型的接口）
  
  3）ResponseCheck接口：检查响应码和errorMesg是否正常（CheckResponseFormat），以及记录响应内容到csv文件中
  
3.AssertImpl包

  1）ParamExistCheckImpl类：ParamExistCheck接口的实现类，作用同ParamExistCheck接口
  
  2）ResourceArrayCheckImpl类：ResourceArrayCheck接口的实现类，作用同ResourceArrayCheck接口
  
  3）CommanResponseCheckImpl类：ResponseCheck接口的实现类，作用同ResponseCheck接口
  
4.ResponseBean包

  1）ResponseBean类：所有接口的response的实体对象类
  
5.TestCase包

  1）每一个类对应一个接口，每个类都实现了GetParamsArray接口和一个断言类型接口（ResourceArrayCheck或ParamExistCheck）


项目实现流程大致为：

1.TestCase类继承GetParamsArray并重写getCsvParamsArray方法和一个断言类（ResourceArrayCheck或ParamExistCheck）并重写Check方法

2.先调用getCsvParams方法获取用例数据

3.再调用RequestConstructer.getInstance().ConstructGetRequest(paramsArray)方法构造request对象

4.然后执行request并获取response对象，最后执行CommanResponseCheckImpl.getInstance().CheckResponseFormat和Check检查接口是否可用以及业务是否可用,CheckResponseFormat方法会调用RecordContent方法，将响应码/errorMes/errorCode和响应中的data字段的值保存到csv中当前用例类对应的那一行









