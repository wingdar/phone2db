package com.wingdar.phone2db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MysqlCon {
    // 資料庫定義
    String mysql_ip = "192.168.1.218";
    int mysql_port = 3306; // Port 預設為 3306
    String db_name = "mydb";
    String url = "jdbc:mysql://"+mysql_ip+":"+mysql_port+"/"+db_name;
    String db_user = "dar";
    String db_password = "16584326";

    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v("DB","加載驅動成功");
        }catch( ClassNotFoundException e) {
            Log.e("DB","加載驅動失敗");
            return;
        }

        // 連接資料庫
        try {
            Connection con = DriverManager.getConnection(url,db_user,db_password);
            Log.v("DB","遠端連接成功");
        }catch(SQLException e) {
            Log.e("DB","遠端連接失敗");
            Log.e("DB", e.toString());
        }
    }

    public String getData() {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT * FROM TempX ORDER BY datetime DESC";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String id = rs.getString("userid");
                String temp = rs.getString("tempx");
                String date = rs.getString("datetime");
                data += String.format("%8s, %6s, 時間：%22s %n", id, temp, date);    //Log 格式化輸出很漂亮，但 TextView 不行
                //data += id + ", " + temp + ", 時間："+ date +"\n";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void insertData(float data,String id){
        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String t = simpleDate.format(new Date());
            Log.v("時間：",t);

            Connection con = DriverManager.getConnection(url, db_user, db_password);
//            String sql = "INSERT INTO TempX (datetime, tempx, userid) VALUES ('"+ t +","+ data +","+ id +"')";
            String sql = "INSERT INTO TempX (datetime, tempx, userid) VALUES ('"+ t +"','"+ data + "','"+ id +"')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            Log.v("DB", "寫入資料完成：" + data);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB", "寫入資料失敗");
            Log.e("DB", e.toString());
        }
    }

}