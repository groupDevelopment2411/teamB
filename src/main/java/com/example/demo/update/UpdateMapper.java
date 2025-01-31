package com.example.demo.update;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UpdateMapper {
	@Update("UPDATE employee SET id= #{id}, name= #{name}, age= #{age}, start_date= #{start_date}, end_date= #{end_date}, password= #{password}")
	void update(UpDate employee);

}
