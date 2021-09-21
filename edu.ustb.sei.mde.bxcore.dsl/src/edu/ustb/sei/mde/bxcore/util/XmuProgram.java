package edu.ustb.sei.mde.bxcore.util;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.structures.Index;
import edu.ustb.sei.mde.graph.typedGraph.GraphvizExporterForTypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.IndexSystem;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;

abstract public class XmuProgram {
	private Map<String, EPackage> packageMap = new HashMap<>();
	private String dotPath = "/usr/local/bin/dot";
	
	final static public Index BEGIN = IndexSystem.INITIAL_INDEX; 
	final static public Index END = IndexSystem.TERMINAL_INDEX; 
	
	static public Object defaultValue(String name, ContextGraph con, Object value) {
		try {
			return con.getContext().getValue(name);
		} catch (Exception e) {
		}
		return value;
	}
	
	public String getDotPath() {
		return dotPath;
	}

	public void setDotPath(String dotPath) {
		this.dotPath = dotPath;
	}

	public void registerPackage(String name, URI uri) {
		EPackage pkg = EcoreModelUtil.loadPacakge(uri);
		if(pkg!=null)
			packageMap.put(name, pkg);
		else {
			java.util.logging.Logger.getLogger(XmuProgram.class.getName()).log(Level.SEVERE, "The package at "+uri+" is not found");
		}
	}
	
	public EPackage getPackage(String name) {
		return packageMap.get(name);
	}
	
	public void exportTypedGraph(TypedGraph graph, String uri,String type) {
		GraphvizExporterForTypedGraph exporter = new GraphvizExporterForTypedGraph();
		String content = exporter.export(graph, "TypedGraph");
		
		try {
			File file = new File(uri+"."+type+".gv"); 
			Writer writer = new java.io.BufferedWriter(new FileWriter(file));
			writer.append(content);
			writer.close();
			
			String[] args = {dotPath, "-T"+type, file.getAbsolutePath(), "-o", uri+"."+type};
			Process p = Runtime.getRuntime().exec(args);
	        p.waitFor();
		} catch (Exception e) {
			XmuCoreUtils.log(Level.WARNING, "Cannot save graphviz file! The content is printed below", e);
			System.out.println(content);
		}
	}
}
