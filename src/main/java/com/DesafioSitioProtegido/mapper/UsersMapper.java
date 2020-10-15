package com.DesafioSitioProtegido.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.DesafioSitioProtegido.model.Users;

@Mapper
public interface UsersMapper {
	@Select("select * from users where email = #{email}")
	Users findByEmail(@Param("email") String email);
}
