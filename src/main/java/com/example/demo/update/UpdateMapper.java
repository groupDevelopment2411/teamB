package com.example.demo.update;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UpdateMapper {
	@Update("UPDATE employee SET name= #{name}, age= #{age}, start_date= #{start_date}, end_date= #{end_date}, password= #{password} where id= #{id}")
	void update(UpDate employee);

}
