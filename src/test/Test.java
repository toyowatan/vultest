package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Test {
  public static void main(String[] args) {
	  try {
	    // データベースに接続
	    Connection con = DriverManager.getConnection(
	      "jdbc:mysql://192.168.100.4/test_database?useSSL=false",
	      "root",
	      "root"
	    );
	    // SQL文の実行
	    String sql = "select * from test_table where id = ?";
	    System.out.println(sql);
	    PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,  args[0]);
	    ResultSet rs = pstmt.executeQuery();
	    // 検索結果を表示
	    while (rs.next()) {
		      System.out.println("id:" +rs.getInt("id") + "/" + "name:" + rs.getString("name"));
	    }
	    // 後処理（リソースのクローズ）
	    rs.close();
	    pstmt.close();
	    con.close();
	  }
	  catch ( Exception e ) {
		  e.printStackTrace();
	  }
  }
}
