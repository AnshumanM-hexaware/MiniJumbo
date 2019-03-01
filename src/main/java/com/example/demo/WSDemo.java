package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	//service for getting query.
	@RequestMapping(value = "/getQuery", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getTableDesc(@RequestBody Map<String, String> requestBody) {

		String schema = requestBody.get("schema");
		String table_name = requestBody.get("table");
		String columns = requestBody.get("colmns");
		String keys = requestBody.get("keyss");
		List<String> query = Practice.getQuery(schema,table_name,columns,keys);
		return new ResponseEntity<List<String>>(query, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getPreview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,String>> getPreview(@RequestBody Map<String,String> requestBody) throws ClassNotFoundException, SQLException {
		//System.out.println("done project ....................");
		String query = requestBody.get("output");
		Map<String,String> pre = Practice.getPreview(query);
		//System.out.println("done project ....................");
		return new ResponseEntity<Map<String,String>>(pre, HttpStatus.OK);
	}
		
}
