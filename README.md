# phone2db
DAR's 第一個手機軟體

##架構
 * 使用 JAVA MySql 庫進行資料庫連接 版本：mysql-connector-java-5.1.49-bin.jar
 * AndroidManifest.xml 新增 USER INTERNET 連線權限
 * 建立 MysqlCon.java 做為資料連接用的函數庫
 * activity_main.xml 有四格輸入，但只有使用前二個，後面二個未有功能 (V0.0.1版時)
