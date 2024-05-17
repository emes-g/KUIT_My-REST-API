package kuit.server.dao;

import kuit.server.dto.user.PostUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        String sql = "select exists(select nickname from user where nickname=:nickname and status='ACTIVE')";
        Map<String, Object> param = Map.of("nickname", nickname);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public int updateNickname(long userId, String nickname) {
        String sql = "update user set nickname=:nickname where id=:id";
        Map<String, Object> param = Map.of(
                "nickname", nickname,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updatePhoneNumber(long userId, String phoneNumber) {
        String sql = "update user set phone_number=:phone_number where id=:id";
        Map<String, Object> param = Map.of(
                "phone_number", phoneNumber,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updateStatus(long userId, String status) {
        String sql = "update user set status=:status where id=:id";
        Map<String, Object> param = Map.of(
                "status", status,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public int updateUserAllInfo(long userId, String nickname, String phoneNumber, String status) {
        String sql = "update user set nickname=:nickname, phone_number=:phone_number, status=:status where id=:id";
        Map<String, Object> param = Map.of(
                "nickname", nickname,
                "phone_number", phoneNumber,
                "status", status,
                "id", userId);
        return jdbcTemplate.update(sql, param);
    }
}
