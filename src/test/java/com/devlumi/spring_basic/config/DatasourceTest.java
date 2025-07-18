package com.devlumi.spring_basic.config;

import com.devlumi.spring_basic.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class DatasourceTest {
  @Autowired
  private DataSource dataSource;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void test() {
    log.info("{}", dataSource);
    log.info("{}", jdbcTemplate);
  }

  @Test
  public void testGetMembers(){
    jdbcTemplate.queryForList("select * from tbl_member").forEach(System.out::println);
  }

  @Test
  public void testIter(){
    List<Integer> list = List.of(2,3,4,1);
    Iterator<Integer> iterator = list.iterator();
    while(iterator.hasNext()){
      System.out.println(iterator.next());
    }
  }


  @Test
  public void testCursor() throws SQLException {
    Connection conn = jdbcTemplate.getDataSource().getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from tbl_member where id = ?");
    ps.setString(1,"test");
    ResultSet rs = ps.executeQuery();
    while(rs.next()){ //커서를 움직인다 행을 구분하기 위한 커서 표= 결과 집합
//      log.info(rs.getCursorName());
      int no = rs.getInt(1);
      String name = rs.getString("name");
      log.info("{} {}",no, name);
    }
  }

  @Test
  public void testCallFunction() {
    int result = jdbcTemplate.queryForObject("select add_num(?,?)",int.class, 10,20);
    log.info("{}",result);
  }

  @Test
  public void testCallProcedure(){
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
    call.withCatalogName("pbl").withProcedureName("list_members");
    Map<String,Object> map = call.execute();
    log.info("{}",map);
  }

  @Test
  public void testCallProcedure2(){  // RowMapper 로 mybatis의 mapper처럼 사용가능
    List<Member> members = jdbcTemplate.query("call list_members()", new RowMapper<Member>() {
      @Override
      public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Member.builder().id(rs.getString("id")).name(rs.getString("name")).build();
      }
    });
    members.forEach(System.out::println);
  }

  @Test
  public void testCallProcedure3(){
     SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl").withProcedureName("find_member_by_id")
            .declareParameters(new SqlParameter("v_id",Types.VARCHAR));
     Map<String,Object>map = call.execute("test");
     log.info("{}",map);
  }

  @Test
  public void testCallProcedre4(){
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl")
            .withProcedureName("find_name_by_id")
            .declareParameters(new SqlParameter("v_id", Types.VARCHAR), new SqlOutParameter("m_name", Types.VARCHAR));
    Map<String, Object> map = call.execute("test");
    log.info("{}",map);
    log.info("{}",map.get("m_name"));
  }

  @Test
  public void testCallProcedre5(){
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl")
            .withProcedureName("add_five")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(new SqlInOutParameter("num", Types.INTEGER));
    Map<String, Object> map = call.execute(10);
    log.info("{}",map);
  }
}
