package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBアクセス時にインジェクションを引き起こすサンプルのクラス
 *
 * @author tnagai
 *
 */
public class DbAccessInjectionSample {
	/**
	 * 指定された値でDBの検索をおこなう
	 *
	 * @param args	第1引数：DB検索時の値
	 */
	public static void main(String[] args) {
		//	JDBC接続のオブジェクト
		Connection connection = null;
		//	DBクエリ実行用のオブジェクト
		PreparedStatement statement = null;
		//	クエリ実行結果のオブジェクト
		ResultSet result = null;
		try {
			// データベースに接続
			connection = DriverManager.getConnection(
					"jdbc:mysql://0.0.0.1/test_database?useSSL=false",
					"root",
					"root"
			);

			// ★★★引数の文字列をそのまま連結してクエリのSQL文を組み立てる（インジェクションの可能性がある方法）★★★
			//String sql = "select * from test_table where id = '" + args[0] + "'";
			String sql = "select * from test_table where id = ?";
			pstmt.setString(1,  args[0]);
			System.out.println(sql);
			statement = connection.prepareStatement(sql);

			//	SQLを実行
			result = statement.executeQuery();

			// 検索結果を表示
			while( result.next() ){
				System.out.println( "id:" +result.getInt("id") + "/" + "name:" + result.getString("name") );
			}
		} catch( SQLException e ) {
			e.printStackTrace();
		}
		finally {
			//	後始末
			try {
				if ( result != null ) {
					result.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
			try {
				if ( statement != null ) {
					statement.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
			try {
				if ( connection != null ) {
					connection.close();
				}
			} catch ( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
