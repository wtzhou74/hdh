package com.healthcurrent.data_management.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Test {

	public static void main(String[] args) throws Exception{
		
		String str1 = "wentao";
		String str2 = "zhou";
		List<String> list = new ArrayList<String>();
		list.add(str1);
		list.add(str2);
		
		String str3 = "wentao";
		System.out.println(list.contains(str3));
	}
}
