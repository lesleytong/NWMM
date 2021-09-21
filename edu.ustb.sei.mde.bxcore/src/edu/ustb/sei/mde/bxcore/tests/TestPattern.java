package edu.ustb.sei.mde.bxcore.tests;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.GraphPath;
import edu.ustb.sei.mde.bxcore.structures.IndexPath;
import edu.ustb.sei.mde.graph.IEdge;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.pattern.PatternPathEdge;
import edu.ustb.sei.mde.graph.type.DashedPathType;
import edu.ustb.sei.mde.graph.type.DashedPathTypeSegment;
import edu.ustb.sei.mde.graph.type.IStructuralFeatureEdge;
import edu.ustb.sei.mde.graph.type.TypeEdge;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedEdge;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedNode;

class TestPattern {

	private TypeGraph typeGraph;
	private TypedGraph typedGraph;
	
	@BeforeEach
	void setUp() throws Exception {
		buildTypeGraph();
		buildTypedGraph();
	}

	public void buildTypeGraph() {
		typeGraph = new TypeGraph();
		
		// add type nodes
		typeGraph.declare("A");
		typeGraph.declare("B");
		typeGraph.declare("C");
		typeGraph.declare("D");
		
		// add data type nodes
		typeGraph.declare("String:java.lang.String");
		
		// add type edges
		typeGraph.declare("a2b:A->B*");
		typeGraph.declare("b2c:B->C");
		typeGraph.declare("c2d:C->D");
		
		// add property edges
		typeGraph.declare("a2S:A->String#");
	}

	public void buildTypedGraph() {
		typedGraph = new TypedGraph(typeGraph);
		
		// add nodes
		typedGraph.declare(
				"a1:A;"
				+"a2:A;"
				+"b1:B;"
				+"b2:B;"
				+"b3:B;"
				+"b4:B;"
				+"b5:B;"
				+"b6:B;"
				+"c1:C;"
				+"d1:D;"
				+"a1-a2b->b1;"
				+"a1-a2b->b2;"
				+"a1-a2b->b3;"
				+"a2-a2b->b4;"
				+"a2-a2b->b5;"
				+"a2-a2b->b6;"
				+"b1-b2c->c1;"
				+"b2-b2c->c1;"
				+"b3-b2c->c1;"
				+"b4-b2c->c1;"
				+"b5-b2c->c1;"
				+"b6-b2c->c1;"
				+"c1-c2d->d1;"
				+"a1.a2S=\"str1\";"
				+"a1.a2S=\"str2\";"
				+"a1.a2S=\"str1\";");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	

	@Test
	void testPlainPatternConstruction() {
		Pattern plainPattern = new Pattern(typeGraph);
		plainPattern.declare("pa:A");
		plainPattern.declare("pb:B");
		plainPattern.declare("pc:C");
		plainPattern.declare("pa2b:pa-a2b->pb");
		plainPattern.declare("pb2c:pb-b2c->pc");
		
		System.out.println(plainPattern.toString());
	}
	
	@Test
	void testSetPatternConstruction() {
		Pattern setPattern = new Pattern(typeGraph);
		setPattern.declare("pa:A");
		setPattern.declare("pb:B*");
		setPattern.declare("pc:C");
		setPattern.declare("pa2b:pa-a2b->pb");
		setPattern.declare("pb2c:pb-b2c->pc");
		
		System.out.println(setPattern.toString());
	}
	
	@Test
	public void testPlainPatternMatching() throws Exception {
		Pattern plainPattern = new Pattern(typeGraph);
		plainPattern.declare("pa:A");
		plainPattern.declare("pb:B");
//		plainPattern.declare("pc:C");
		plainPattern.declare("pa2b:pa-a2b->pb");
//		plainPattern.declare("pb2c:pb-b2c->pc");
		
		Context base = Context.emptyContext();
		
		List<Context> matches = plainPattern.match(typedGraph, base);
		
		Assert.assertTrue(matches.size()+" matches are found", matches.size()==6);
		Assert.assertTrue(matches.stream().allMatch(match->plainPattern.isMatchOf(typedGraph, match)));
	}
	
	@Test
	public void testSetPatternMatching() throws Exception {
		Pattern setPattern = new Pattern(typeGraph);
		setPattern.declare("pa:A");
		setPattern.declare("pb:B*");
		setPattern.declare("pc:C");
		setPattern.declare("pa2b:pa-a2b->pb");
		setPattern.declare("pb2c:pb-b2c->pc");
		
		Context base = Context.emptyContext();
		
		List<Context> matches = setPattern.match(typedGraph, base);
		
		Assert.assertTrue(matches.size()+" matches are found", matches.size()==2);
		Assert.assertTrue(matches.stream().allMatch(match->setPattern.isMatchOf(typedGraph, match)));
	}
	
	@Test
	public void testSetPatternPathMatching() throws Exception {
		Pattern setPattern = new Pattern(typeGraph);
		setPattern.declare("pa:A");
//		setPattern.declare("pb:B");
		setPattern.declare("pc:C");
//		setPattern.declare("pa2b:pa-a2b->pb");
//		setPattern.declare("pb2c:pb-b2c->pc");
		
		DashedPathType dpt = new DashedPathType(
				DashedPathTypeSegment.createOne(typeGraph.getTypeEdge(typeGraph.getTypeNode("A"), "a2b")),
				DashedPathTypeSegment.createOne(typeGraph.getTypeEdge(typeGraph.getTypeNode("B"), "b2c")));
		
		setPattern.appendPatternEdge("pabc", "pa", "pc", dpt);
		
		
		System.out.println(setPattern);
		
		Context base = Context.emptyContext();
		
		List<Context> matches = setPattern.match(typedGraph, base);
		
		Assert.assertTrue(matches.size()+" matches are found", matches.size()==6);
		Assert.assertTrue(matches.stream().allMatch(match->setPattern.isMatchOf(typedGraph, match)));
	}
	
	@Test
	public void testPath() throws Exception {

	 Pattern pattern = new Pattern(typeGraph);
	 
	 //define
	 pattern.declare("pa:A");
	 pattern.declare("pd:D");

	 IStructuralFeatureEdge e1 = typeGraph.getTypeEdge(typeGraph.getTypeNode("A"), "a2b");
	 IStructuralFeatureEdge e2 = typeGraph.getTypeEdge(typeGraph.getTypeNode("B"), "b2c");
	 IStructuralFeatureEdge e3 = typeGraph.getTypeEdge(typeGraph.getTypeNode("C"), "c2d");
	 
	 DashedPathType dPathType = new DashedPathType(new DashedPathTypeSegment(0, -1, e1), 
			 										new DashedPathTypeSegment(0, -1, e2),
			 										 new DashedPathTypeSegment(0, 5, e3));

	 pattern.appendPatternEdge("PathPattern", "pa", "pd", dPathType);

	 // match typedGraph
	 Context base = Context.emptyContext();
	 List<Context> matches = pattern.match(typedGraph, base);
	 System.out.println(matches.size());
	 
	 
	 Context newMatch = matches.get(0).getCopy();
	 
	 
	 
	 DashedPathType newPathType = (DashedPathType) typeGraph.resolvePathType("A::(a2b){0,-1}.(b2c){0,-1}.(c2d){0,5}"); 
			 
//			 new DashedPathType(new DashedPathTypeSegment(0, -1, e1), 
//				new DashedPathTypeSegment(0, -1, e2),
//				 new DashedPathTypeSegment(0, 5, e3));
	 
	 TypedNode a = typedGraph.getElementByIndexObject(newMatch.getValue("pa"));
	 TypedNode nb = new TypedNode(typeGraph.getTypeNode("B"));
	 TypedNode nc = new TypedNode(typeGraph.getTypeNode("C"));
	 TypedNode d = typedGraph.getElementByIndexObject(newMatch.getValue("pd"));
	 
	 TypedEdge a2b = new TypedEdge(a, nb, (TypeEdge) e1);
	 TypedEdge b2c = new TypedEdge(nb, nc, (TypeEdge) e2);
	 TypedEdge c2d = new TypedEdge(nc, d, (TypeEdge) e3);
	 
	 GraphPath p = new GraphPath(new IEdge[] {a2b, b2c, c2d}, newPathType);
	 newMatch.setValue("PathPattern", p);
	 
	 
	 TypedGraph newGraph = pattern.construct(typedGraph, newMatch);
	 
	 System.out.println(newGraph.printGraph());
	 
	 //Assert.assertTrue(matches.size()+" matches are found", matches.size()==6);
     //Assert.assertTrue(matches.stream().allMatch(match->pattern.isMatchOf(typedGraph, match)));
	}

	@Test
	public void testName() throws Exception {
		Object[] left = new Object[] {1,2,3};
		Object[] right = new Object[] {1,2,3};
		
		
		Assert.assertTrue(Arrays.equals(left,right));
	}
	
	
	
	
	
}
