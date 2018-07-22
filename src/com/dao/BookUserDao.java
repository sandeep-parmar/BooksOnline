package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.bean.BookUser;
import com.connection.ConnectionHandler;
import com.utility.Errorcode;

public class BookUserDao implements IBaseDao {

	public BookUserDao() {
	}


	public String getSelectQueryForField(String field) {
		String sql = "select * from book_user where " + field + " = ?";
		return sql;
	}

	@Override
	public String getSelectQuery() {
		String sql = "select * from book_user where soldstatus = 0 order by ADDEDON DESC";
		return sql;
	}
	
	
	public String getSelectQueryForCategory() {
		String sql = "select * from book_user where bookid in (select bookid from books where category like ?) and soldstatus = 0";
		return sql;
	}
	
	@Override
	public String getInsertQuery() {
		String sql = "insert into book_user(uid, bookid, name, price, soldstatus, city, area) select * from (select ? as uid, ? as bid, ? as name, ? price, ? as st, ? as cty, ? as ar) as tmp"
				+ " where not exists ("
				+ " select uid,bookid from book_user where uid = ? and bookid = ?) limit 1";
		return sql;
	}
	
	public String getUpdateQuery() {
		String sql = "update book_user set soldstatus = 1 where uid = ? and bookid = ?";
		return sql;
	}
	public String getUpdatePriceQuery() {
		String sql = "update book_user set price = ? where uid = ? and bookid = ?";
		return sql;
	}
	
	public String getBooksAdCountQuery(){
		return "select count(*) AS BOOKCOUNT from book_user where soldstatus = 0";
	}

	public String getPaginatedHomeSelectQuery(int currPage, int recordPg) {
		return "select * from book_user where soldstatus = 0 order by ADDEDON DESC limit "+currPage+","+recordPg;
	}
	
	public int saveBookUser(BookUser bookUser) {
		
		/*PENDING*/
		/*Do not savre if already exists*/
		 
		int status = Errorcode.EC_SUCCESS.getValue();
		String sql = getInsertQuery();
		Connection conn = null;
		try {
			conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, bookUser.getUid());
			preparedStmt.setString(2, bookUser.getBookid());
			//preparedStmt.setInt(3, bookUser.getPin());
			preparedStmt.setString(3, bookUser.getName());
			//preparedStmt.setString(5, bookUser.getPhone());
			preparedStmt.setString(4, bookUser.getPrice());
			preparedStmt.setInt(5, bookUser.getSoldstatus());
			preparedStmt.setString(6, bookUser.getCity());
			preparedStmt.setString(7, bookUser.getArea());
			
			preparedStmt.setString(8, bookUser.getUid());
			preparedStmt.setString(9, bookUser.getBookid());
			
			preparedStmt.execute();
			
		}catch (SQLIntegrityConstraintViolationException e) {
			status = Errorcode.EC_FAILED_DB_UPDATE.getValue();
			System.out.println(e.toString());
		}
		catch(SQLException e) {			
			status = Errorcode.EC_FAILED_DB_UPDATE.getValue();
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return status;
	}
	
	/*Get list of books based on category*/
	public List<BookUser> getBookList(String category) {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;		
		String sql = getSelectQueryForCategory();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, "%"+category+"%");
			System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();			
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString(BookUser.uidStr),
												 rs.getString(BookUser.bookidStr),
												 rs.getString(BookUser.areaStr),
												 rs.getString(BookUser.cityStr),
												 rs.getString(BookUser.nameStr), 
												 //rs.getString(BookUser.phoneStr), 
												 rs.getString(BookUser.priceStr), 
												 rs.getInt(BookUser.soldstatusStr)
												 ); 
				list.add(bookUser);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}
	
	public List<BookUser> getBookList() {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString(BookUser.uidStr),
												 rs.getString(BookUser.bookidStr),
												 rs.getString(BookUser.areaStr),
												 rs.getString(BookUser.cityStr),
												 rs.getString(BookUser.nameStr), 
												 //rs.getString(BookUser.phoneStr), 
												 rs.getString(BookUser.priceStr), 
												 rs.getInt(BookUser.soldstatusStr)
												 ); 
				list.add(bookUser);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}

	public List<BookUser> getBookList(int currentHomePageStr, int recordsHomePerPageStr) {
		System.out.println("called getBookList :currentHomePageStr::"+currentHomePageStr+" recordsHomePerPageStr:: "+recordsHomePerPageStr);
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		int currPage = currentHomePageStr != 0 ? (currentHomePageStr-1)*10 : currentHomePageStr;
		int recordPerPg = recordsHomePerPageStr;
		String sql = getPaginatedHomeSelectQuery(currPage, recordPerPg);
		System.out.println("Query to get Books reocrds::"+sql);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString(BookUser.uidStr),
												 rs.getString(BookUser.bookidStr),
												 rs.getString(BookUser.areaStr),
												 rs.getString(BookUser.cityStr),
												 rs.getString(BookUser.nameStr), 
												 rs.getString(BookUser.priceStr), 
												 rs.getInt(BookUser.soldstatusStr)
												 ); 
				list.add(bookUser);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}

	
	@Override
	public String getSingleEntryQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BookUser> getBookListForUser(String email) {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQueryForField(BookUser.uidStr);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, email);			
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString(BookUser.uidStr),
						 rs.getString(BookUser.bookidStr),
						 rs.getString(BookUser.areaStr),
						 rs.getString(BookUser.cityStr), 
						 rs.getString(BookUser.nameStr), 
						// rs.getString(BookUser.phoneStr), 
						 rs.getString(BookUser.priceStr), 
						 rs.getInt(BookUser.soldstatusStr)
												 ); 
				list.add(bookUser);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}


	public int setSoldStatusTrue(String uid, String bookid) {
		String sql = getUpdateQuery();
		int status = Errorcode.EC_SUCCESS.getValue();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, uid);
			preparedStmt.setString(2, bookid);
			System.out.println(preparedStmt);
			preparedStmt.execute();
		}catch(SQLException e)
		{
			status = Errorcode.EC_FAILED_DB_UPDATE.getValue();
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return status;
	}
	
	public int updateNewOfferPrice(String uid, String bookid, String newprice) {
		int status = Errorcode.EC_SUCCESS.getValue();
		String sql = getUpdatePriceQuery();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, newprice);
			preparedStmt.setString(2, uid);
			preparedStmt.setString(3, bookid);
			System.out.println(preparedStmt);
			
			preparedStmt.execute();
		}catch(SQLException e)
		{
			status = Errorcode.EC_FAILED_DB_UPDATE.getValue();
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return status;
	}

	public List<BookUser> getBookListByCriteria(String city, String area, String criteria) {
		List<BookUser> list = new ArrayList<BookUser>(0);
		ResultSet rs = null;
		String sql = getSelectQueryByCriteria();
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, "%"+criteria+"%");
			preparedStmt.setString(2, "%"+criteria+"%");
			preparedStmt.setString(3, "%"+criteria+"%");
			preparedStmt.setString(4, "%"+city+"%");
			preparedStmt.setString(5, "%"+area+"%");
			
			//System.out.println(preparedStmt.toString());
			
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				BookUser bookUser = new BookUser(rs.getString(BookUser.uidStr),
						 rs.getString(BookUser.bookidStr),
						 rs.getString(BookUser.areaStr),
						 rs.getString(BookUser.cityStr),
						 rs.getString(BookUser.nameStr), 
						 //rs.getString(BookUser.phoneStr), 
						 rs.getString(BookUser.priceStr), 
						 rs.getInt(BookUser.soldstatusStr)
												 ); 
				list.add(bookUser);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			ConnectionHandler.closeConnection();
		}
		return list;
	}
	public List<String> getSuggestionList(String phrase, String str) {
		List<String> clist = new ArrayList<>();
		ResultSet rs = null;
		String sql = getStrListQuery(str);
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			preparedStmt.setString(1, "%" + phrase + "%");			
			//System.out.println(preparedStmt.toString());
			rs = preparedStmt.executeQuery();
			while(rs.next())
			{
				clist.add(rs.getString(str));
			}
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return clist;
	}
	public String getStrListQuery(String str) {
		String sql = "select distinct " + str + " from book_user where " + str +" like ?" ;
		return sql;
	}
	private String getSelectQueryByCriteria() {
		//String sql = "select * from book_user where pin in(select pin from locality where city like ? and area like ?)";
		
		String sql = "select * from book_user where bookid in "
				+ "(select bookid from books where booktitle like ? or bookauthor like ? or bookid like ?) "
				+ "and "
				+ "city like ? and area like ? "
				+ "and "
				+ "soldstatus != 1";
			
		
		/*Use below inner join in future if performance impact is bigger*/
		
		/*String sql2 = "select * from book_user bu inner join "
				+ "(select b.bookid from books b where b.booktitle like ? or b.bookauthor like ? or b.bookid like ?) d "
				+ "on bu.bookid = d.bookid inner join "
				+ "(select pin from locality where city like ? and area like ?) l "
				+ "on bu.pin=l.pin";*/
		return sql;
	}	
	

	public int getBookListCount() {
		ResultSet rs = null;
		String sql = getBooksAdCountQuery();
		int bookCount = 0;
		try {
			Connection conn = ConnectionHandler.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(sql);
			rs = preparedStmt.executeQuery();
			while(rs.next()){
				bookCount = rs.getInt("BOOKCOUNT");
			}
		}catch(SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			ConnectionHandler.closeConnection();
		}
		return bookCount;
	}
	
}
