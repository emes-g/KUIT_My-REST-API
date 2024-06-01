package kuit3.backend.dao;

import kuit3.backend.dto.user.GetUserResponse;
import kuit3.backend.dto.user.PostUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long createUser(PostUserRequest postUserRequest) {
        log.info("[UserDao.createUser]");
        String sql = "insert into user(nickname, phone_number, status)" +
                "values(:nickname, :phoneNumber, :status)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean hasDuplicateNickName(String nickname) {
        log.info("[UserDao.hasDuplicateNickName]");
        String sql = "select exists(select nickname from user where nickname=:nickname and status='ACTIVE')";
        Map<String, Object> param = Map.of("nickname", nickname);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public int updateNickname(long userId, String nickname) {
        log.info("[UserDao.updateNickname]");
        String sql = "update user set nickname=:nickname where id=:id";
        Map<String, Object> param = Map.of(
                "nickname", nickname,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updatePhoneNumber(long userId, String phoneNumber) {
        log.info("[UserDao.updatePhoneNumber]");
        String sql = "update user set phone_number=:phone_number where id=:id";
        Map<String, Object> param = Map.of(
                "phone_number", phoneNumber,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updateStatus(long userId, String status) {
        log.info("[UserDao.updateStatus]");
        String sql = "update user set status=:status where id=:id";
        Map<String, Object> param = Map.of(
                "status", status,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updateUserAllInfo(long userId, PostUserRequest postUserRequest) {
        log.info("[UserDao.updateUserAllInfo]");
        String sql = "update user set nickname=:nickname, phone_number=:phone_number, status=:status where id=:id";
        Map<String, Object> param = Map.of(
                "nickname", postUserRequest.getNickname(),
                "phone_number", postUserRequest.getPhoneNumber(),
                "status", postUserRequest.getStatus(),
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    @Description("오름차순 무한 스크롤")
    public List<GetUserResponse> getAllUsers(long lastId) {
        log.info("[UserDao.getAllUsers]");
        String sql = "select nickname, phone_number, status, lpad(id, 10, '0') as 'cursor' " +
                "from user " +
                "where lpad(id, 10, '0') > lpad(:last_id, 10, '0')" +
                "order by id asc " +
                "limit 5";
        Map<String, Object> param = Map.of("last_id", lastId);

        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserResponse(
                        rs.getString("nickname"),
                        rs.getString("phone_number"),
                        rs.getString("status"),
                        rs.getString("cursor")
                )
        );
    }

    public List<GetUserResponse> getUserByUserId(long userId) {
        log.info("[UserDao.getUserByUserId]");
        String sql = "select nickname, phone_number, status from user where id=:id";
        Map<String, Object> param = Map.of("id", userId);
        
        // 커서 관련하여 추후 수정 필요
        return jdbcTemplate.query(sql, param,
                (rs, rowNum) -> new GetUserResponse(
                        rs.getString("nickname"),
                        rs.getString("phone_number"),
                        rs.getString("status"),
                        rs.getString("cursor"))
        );
    }

    public boolean isExistedUserId(long userId) {
        log.info("[UserDao.isExistedUserId]");
        String sql = "select exists(select id from user where id=:id)";
        Map<String, Object> param = Map.of("id", userId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long getUserIdByNickname(String nickname) {
        String sql = "select id from user where nickname=:nickname and status='ACTIVE'";
        Map<String, Object> param = Map.of("nickname", nickname);
        return jdbcTemplate.queryForObject(sql, param, long.class);
    }
}
