package com.example.demo;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Practice;
@Controller
@CrossOrigin
@RestController
@RequestMapping("/MiniJumbo")
public class WSDemo {
	
	//service for getting schema names.
	@RequestMapping("/getSchema")
	public @ResponseBody List<String> getSchemasService() throws ClassNotFoundException, SQLException {
		return Practice.getSchema();
	}
	
	//service for getting table names.
	@RequestMapping(path = "/getTables/{schema}", method = RequestMethod.GET)
	public @ResponseBody List<String> getTablesService(@PathVariable String schema) {
		return Practice.getTables(schema);
	}
	
	//service for getting column names.
	@RequestMapping(path = "/getColumns/{schema}/{table_name}", method = RequestMethod.GET)
	public @ResponseBody List<String> getColumnsService(@PathVariable String schema, @PathVariable String table_name) {
		return Practice.getColumns(schema,table_name);
	}
	
	
}
