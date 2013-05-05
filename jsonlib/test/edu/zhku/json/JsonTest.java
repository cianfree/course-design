package edu.zhku.json;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import edu.zhku.bean.User;
import edu.zhku.bean.UserBean;

public class JsonTest {

	@Test
	public void testToJsonPrimitive() {
		String json = Json.toJson(12);
		assertEquals("12", json);
		
		json = Json.toJson(12.5F);
		assertEquals("12.5", json);
		
		json = Json.toJson(12.6);
		assertEquals("12.6", json);
		
		json = Json.toJson(12L);
		assertEquals("12", json);
		json = Json.toJson("http://192.168.1.103:8080/skh/authir.jpeg");
		System.out.println(json);
		assertEquals("\"http://192.168.1.103:8080/skh/authir.jpeg\"", json);
	}
	
	@Test
	public void testToJsonCollection() {
		Set<User> friends = new HashSet<User>();
		for(int i=5; i<7; ++i) {
			User u2 = new User();
			u2.setId(2L);
			u2.setName("friend");
			friends.add(u2);
		}
		
		String json = Json.toJson(friends);
		System.out.println(json);
		assertEquals("[{name:\"friend\",id:2},{name:\"friend\",id:2}]", json);
	}
	
	@Test
	public void testToJsonBean() {
		
		User user = new User();
		user.setId(1L);
		user.setName("cian");
		
		User wife = new User();
		wife.setId(2L);
		wife.setName("cian wife");
		user.setWife(wife);
		
		List<User> family = new ArrayList<User>();
		for(int i=1; i<2; ++i) {
			User u = new User();
			u.setId((long)(i+2));
			u.setName("family_" + (i+2));
			family.add(u);
		}
		user.setFamilys(family);
		Set<User> friends = new HashSet<User>();
		for(int i=2; i<3; ++i) {
			User u2 = new User();
			u2.setId((long)i);
			u2.setName("friends_" + i);
			friends.add(u2);
		}
		user.setFriends(friends);
		
		Map<String, User> others = new HashMap<String, User>();
		for(int i=3; i<4; ++i) {
			User u3 = new User();
			u3.setId((long)i);
			u3.setName("others_" + i);
			others.put("map_" + i, u3);
		}
		user.setOthers(others);
		
		String json = Json.toJson(user);
		System.out.println(json);
		assertEquals("{name:\"cian\",wife:{name:\"cian wife\",id:2},friends:[{name:\"friends_2\",id:2}],familys:[{name:\"family_3\",id:3}],others:{\"map_3\":{name:\"others_3\",id:3}},id:1}", json);
	}

	@Test
	public void testFromJsonPrimitive() {
		String json = "21";
		int intv = Json.fromJson(json, int.class);
		assertEquals(21, intv);
		
		Integer intvo = Json.fromJson(json, Integer.class);
		assertEquals(new Integer(21), intvo);
		
		long longv = Json.fromJson(json, long.class);
		assertEquals(21L, longv);
		
		Long longvo = Json.fromJson(json, Long.class);
		assertEquals(new Long(21), longvo);
		
		json = "21.1";
		float fv = Json.fromJson(json, float.class);
		assertEquals(21.1, fv, 0.1);
		
		Float fvo = Json.fromJson(json, Float.class);
		assertEquals(new Float(21.1), fvo);
		
		double dv = Json.fromJson(json, double.class);
		assertEquals(21.1, dv, 0.1);
		
		Double dvo = Json.fromJson(json, Double.class);
		assertEquals(new Double(21.1), dvo);
		
		String s = Json.fromJson(json, String.class);
		assertEquals("21.1", s);
	}
	
	@Test
	public void tesJsonArray() {
		String[] strArray = new String[]{"arvin", "cian", "free"};
		String json = Json.toJson(strArray);
		System.out.println(json);
		
		int[] intArray = new int[]{1, 2,3,4};
		json = Json.toJson(intArray);
		System.out.println(json);
	}
	
	@Test
	public void testFromJsonBean() {
		String json = "{id: 2}";
		UserBean user = Json.fromJson(json, UserBean.class);
		System.out.println(user.getId());
	}
	
	@Test
	public void testFromJsonArray() {
		String array = "[1,2,3,4,5]";
		int[] intArray = Json.fromJson(array, int[].class);
		System.out.println(Arrays.toString(intArray));
	}
}
