package edu.ustb.sei.mde.bxcore;

import java.util.HashSet;
import java.util.Set;

import edu.ustb.sei.mde.bxcore.bigul.BidirectionalTransformation;
import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.exceptions.UninitializedException;
import edu.ustb.sei.mde.bxcore.structures.Context;
import edu.ustb.sei.mde.bxcore.structures.ContextType;
import edu.ustb.sei.mde.bxcore.structures.FieldDef;
import edu.ustb.sei.mde.graph.pattern.Pattern;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint;
import edu.ustb.sei.mde.graph.typedGraph.constraint.GraphConstraint.ConstraintStatus;
import edu.ustb.sei.mde.structure.Tuple3;

public class GraphReplace extends XmuCore {
	
	public GraphReplace(Object key, 
			Pattern patS, Pattern patV,
			Tuple3<String[], String[], BidirectionalTransformation<Object[], Object[]>>[] bodyConversions) 
					throws BidirectionalTransformationDefinitionException {
		super(key, patS.getType(), patV.getType());
		
		this.patS = patS;
		this.patV = patV;
		this.bodyConversions = bodyConversions;
		
		checkWellDefinedness();
	}
	
	@Override
	protected void checkWellDefinedness() throws BidirectionalTransformationDefinitionException {
		ContextType sourceType = this.getSourceDef();
		ContextType viewType = this.getViewDef();
		
		Set<FieldDef<?>> views = new HashSet<>();
		
		for(Tuple3<String[], String[], BidirectionalTransformation<Object[], Object[]>> body : bodyConversions) {
			for(int i=0;i<body.first.length;i++) {
				for(int j=i+1;j<body.first.length;j++) {
					if(body.first[i].equals(body.first[j]))
						throw new BidirectionalTransformationDefinitionException("Duplicate keys");
				}
			}
			
			for(int i=0;i<body.second.length;i++) {
				for(int j=i+1;j<body.second.length;j++) {
					if(body.second[i].equals(body.second[j]))
						throw new BidirectionalTransformationDefinitionException("Duplicate keys");
				}
			}
			
			for(String sk : body.first) {
				FieldDef<?> field = sourceType.getField(sk);
				if(field==null)
					throw new BidirectionalTransformationDefinitionException("Invalid parameter ("+sk+")");
//				else if(field.isElementType()) 
//					throw new BidirectionalTransformationDefinitionException("The body conversion of a GraphReplace must convert primitive values only ("+sk+" is a "+field.getName()+")");
			}
			
			for(String vk : body.second) {
				FieldDef<?> field = viewType.getField(vk);
				if(field==null)
					throw new BidirectionalTransformationDefinitionException("Invalid parameter ("+vk+")");
//				else if(field.isElementType()) 
//					throw new BidirectionalTransformationDefinitionException("The body conversion of a GraphReplace must convert primitive values only ("+vk+" is a "+field.getName()+")");
				
				views.add(field);
			}
		}
		
		if(!viewType.fields().stream().allMatch(f->f.isElementType() || views.contains(f)))
			throw new BidirectionalTransformationDefinitionException("All primitive view values must be converted");
	}

	private Pattern patS;
	private Pattern patV;
	private Tuple3<String[],String[],BidirectionalTransformation<Object[], Object[]>>[] bodyConversions;
	
	

	@Override
	public ViewType forward(SourceType s)
			throws NothingReturnedException {
		
		// s.second must be a match of s.first
		// init view match
		// construct view
		
		ContextType sourceType = this.getSourceDef();
		
		Context upstreamSource = s.second;
		
		if(!checkSourceContextStructure(upstreamSource)) 
			return nothing();
		
		if(patS.isMatchOf(s.first, upstreamSource)) {
			Context viewMatch = createViewContext();
			patV.getType().initializeView(viewMatch, s.second, s.third);
			
			
			for(Tuple3<String[], String[], BidirectionalTransformation<Object[], Object[]>> b : bodyConversions) {
				String[] sourceKeys = b.first;
				String[] viewKeys = b.second;
				
				Object[] sources = new Object[sourceKeys.length];
				for(int i=0;i<sourceKeys.length;i++) {
					try {
						sources[i] = upstreamSource.getPrimitiveValue(sourceType.getField(sourceKeys[i]));
					} catch (UninitializedException e) {
						return nothing();
					}
				}
				
				Object[] views = b.third.forward(sources);
				for(int i=0;i<viewKeys.length;i++) {
					viewMatch.setValue(viewKeys[i], views[i]);
				}
			}
			
			
			try {
				TypedGraph view = patV.construct(null, viewMatch);
				
				view.setConstraint(getConsistencyConstraint());
				
				return ViewType.makeView(view, viewMatch);
			} catch (UninitializedException e) {
				return nothing();
			}
			
		} else 
			return nothing();
	}

	@Override
	public SourceType backward(SourceType s,
			ViewType v) throws NothingReturnedException {
		
		Context upstreamSource = s.second;
		upstreamSource.setWillCreateElement(true);
		Context upstreamView = v.second;
		upstreamView.setWillCreateElement(true);
		
		if(upstreamView.verticalCorrectness(s.third)==false) {
			XmuCoreUtils.warning("Shared node issue in view detected");
		}
		
		// v.second must be a match of v.first
		// body conversion
		// construct source
		
		if(!(checkSourceContextStructure(upstreamSource) && checkViewContextStructure(upstreamView)))
			return nothing();
		
		if(patV.isMatchOf(v.first, upstreamView)) {
			Context sourceMatchNew = computeSourcePost(upstreamSource, upstreamView);
			
			
			TypedGraph source;
			try {
				source = patS.construct(s.first, sourceMatchNew);
				TypedGraph mergedSource = s.first.additiveMerge(source);
				if(patS.isMatchOf(mergedSource,sourceMatchNew)) {
					TraceSystem ts = s.third.getCopy();
					ts.trace(this.getSerializationKey(), upstreamSource, upstreamView, sourceMatchNew);
					
					mergedSource.setConstraint(getConsistencyConstraint());
					
					return SourceType.makeSource(mergedSource, sourceMatchNew, ts);
				}
				else
					return nothing();
			} catch (UninitializedException e) {
				return nothing();
			}
		}
		return nothing();
	}

	public Context computeSourcePost(Context upstreamSource, Context upstreamView) throws NothingReturnedException {
		Context sourceMatchNew = createSourceContext();
		sourceMatchNew.initWith(upstreamSource);
		
		
		ContextType sourceType = this.getSourceDef();
		ContextType viewType = this.getViewDef();
		
		for(Tuple3<String[],String[],BidirectionalTransformation<Object[], Object[]>> b : bodyConversions) {
			String[] sourceKeys = b.first;
			String[] viewKeys = b.second;
			
			Object[] sources = new Object[sourceKeys.length];
			for(int i=0;i<sourceKeys.length;i++) {
				try {
					sources[i] = upstreamSource.getPrimitiveValue(sourceType.getField(sourceKeys[i]));
				} catch (UninitializedException e) {
					sources[i] = null;
				}
			}
			
			Object[] views = new Object[viewKeys.length];
			for(int i=0;i<viewKeys.length;i++) {
				try {
					views[i] = upstreamView.getPrimitiveValue(viewType.getField(viewKeys[i]));
				} catch (UninitializedException e) {
					nothing(e);
				}
			}
			
			Object[] sourcesNew = b.third.backward(sources, views);
			for(int i=0;i<sourceKeys.length;i++) {
				sourceMatchNew.setValue(sourceKeys[i], sourcesNew[i]);
			}
		}
		return sourceMatchNew;
	}

	
	@Override
	protected GraphConstraint generateConsistencyConstraint() {
		return (gs,cs, gv, cv)->{
			if(patS.isMatchOf(gs, cs)==false) return ConstraintStatus.unenforceable;
			if(patV.isMatchOf(gv, cv)==false) return ConstraintStatus.unenforceable;
			try {
				Context sp = computeSourcePost(cs, cv);
				if(Context.isIdentifical(sp, cs)==false) return ConstraintStatus.enforceable;
				else return ConstraintStatus.sat;
			} catch (Exception e) {
				return ConstraintStatus.unenforceable;
			}
		};
	}
}
