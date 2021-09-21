package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXCoreFactory;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXFunctionDefinition;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.BXProgram;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.TypeLiteral;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.VarMapping;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreAlign;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreContextSource;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreDependencyView;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreDeriveSource;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreExpandSource;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreExpandView;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreForEachMatchSource;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreFork;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreFunctionCall;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreGraphReplace;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreIndex;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreMatchSource;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreMatchView;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreParallelComposition;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreStatement;
import edu.ustb.sei.mde.bxcore.dsl.bXCore.XmuCoreSwitch;
import edu.ustb.sei.mde.bxcore.dsl.structure.TupleType;
import edu.ustb.sei.mde.structure.Tuple2;

public class SourceTypeModel extends TypeModel {

	public SourceTypeModel(BXProgram program, Map<TypeLiteral, Tuple2<TupleType, Integer>> literalMap) {
		super(program, literalMap);
	}

	@Override
	protected void extractConstraint(XmuCoreStatement e) {
		if(e instanceof XmuCoreMatchSource) {
			TupleType st = getType(e);
			TupleType pt = getType(((XmuCoreMatchSource) e).getPattern());
			TupleType bt = getType(((XmuCoreMatchSource) e).getBody());
			TypeUnion c1 = TypeUnion.makeSubSet(pt, st);
			this.constraints.add(c1);
			TypeEqual c2 = TypeEqual.makeLeftAbstractEqual(pt, bt);
			this.constraints.add(c2);
			linkCause(c1, e);
			linkCause(c2, e);
		} else if(e instanceof XmuCoreMatchView) {
			TupleType st = getType(e);
			TupleType bt = getType(((XmuCoreMatchView) e).getBody());
			TypeEqual c = TypeEqual.makeEqual(st, bt);
			this.constraints.add(c);
			linkCause(c, e);
		} else if(e instanceof XmuCoreExpandSource) {
			TupleType st = getType(e);
			TupleType pt = getType(((XmuCoreExpandSource) e).getPattern());
			TupleType bt = getType(((XmuCoreExpandSource) e).getBody());

			TypeEqual c1 = TypeEqual.makeLeftAbstractEqual(st, pt);
			this.constraints.add(c1);
			linkCause(c1, e);
			
			TupleType it = new UnsolvedTupleType();
			this.types.add(it);

			TypeUnion c3 = TypeUnion.makeSubSet(pt, it);
			this.constraints.add(c3);
			linkCause(c3, e);
			
			TypeEqual c4 = TypeEqual.makeMapping(it, bt, ((XmuCoreExpandSource) e).getMappings());
			this.constraints.add(c4);
			linkCause(c4, e);
		} else if(e instanceof XmuCoreExpandView) {
			TupleType st = getType(e);
			TupleType bt = getType(((XmuCoreExpandView) e).getBody());
			TypeEqual c = TypeEqual.makeEqual(st, bt);
			this.constraints.add(c);
			linkCause(c, e);
		} else if(e instanceof XmuCoreSwitch) {
			TupleType st = getType(e);
			((XmuCoreSwitch) e).getBranches().forEach(b->{
				TupleType bt = getType(b.getAction());
				TypeEqual c = TypeEqual.makeLeftAbstractEqual(st, bt);
				this.constraints.add(c);
				linkCause(c, b);
			});
		} else if(e instanceof XmuCoreAlign) {
			TupleType st = getType(e);
			TupleType pt = getType(((XmuCoreAlign) e).getSourcePattern());
			TupleType bt = getType(((XmuCoreAlign) e).getMatch());
			
			TypeUnion c1 = TypeUnion.makeSubSet(pt, st);
			this.constraints.add(c1);
			TypeEqual c2 = TypeEqual.makeLeftAbstractEqual(pt, bt);
			this.constraints.add(c2);
			
			linkCause(c1, e);
			linkCause(c2, e);
		} else if(e instanceof XmuCoreParallelComposition) {
			TupleType st = getType(e);
			TypeUnion c = TypeUnion.makeUnion(st);
			((XmuCoreParallelComposition) e).getBodies().forEach(b->{
				TupleType bt = getType(b);
				c.types.add(bt);
			});
			this.constraints.add(c);
			linkCause(c, e);
		} else if(e instanceof XmuCoreFork) {
			TupleType st = getType(e);
			TypeUnion c = TypeUnion.makeUnion(st);
			((XmuCoreFork) e).getForks().forEach(b->{
				TupleType bt = getType(b.getBody());
				TupleType it = new UnsolvedTupleType();
				c.types.add(it);
				this.constraints.add(TypeEqual.makeLeftAbstractMapping(it, bt, b.getSourceMappings()));
			});
			this.constraints.add(c);
			linkCause(c, e);
		} else if(e instanceof XmuCoreGraphReplace) {
			TupleType st = getType(e);
			TupleType pt = getType(((XmuCoreGraphReplace) e).getSource());
			TypeEqual c1 = TypeEqual.makeEqual(st, pt);
			this.constraints.add(c1);
			linkCause(c1, e);
		} else if(e instanceof XmuCoreFunctionCall) {
			TupleType st = getType(e);
			TupleType bt = getType(((XmuCoreFunctionCall) e).getTarget());
			TypeEqual c1 = TypeEqual.makeRightAbstractMapping(st, bt, ((XmuCoreFunctionCall) e).getSourceMappings());
			this.constraints.add(c1);
			linkCause(c1, e);
		} else if(e instanceof XmuCoreIndex) {
			TupleType st = getType(e);
			TupleType bt = getType(((XmuCoreIndex) e).getBody());
			TypeEqual c1 = TypeEqual.makeEqual(st, bt);
			this.constraints.add(c1);
			linkCause(c1, e);
			
			((XmuCoreIndex) e).getParts().forEach(part->{
				TupleType pt = getType(part.getSignature().getSourceType());
				List<VarMapping> list = new ArrayList<>();
				for(int i=0;i<part.getSourceKeys().size();i++) {
					String sk = part.getSourceKeys().get(i);
					String tk = pt.tuples.get(i).first;
					VarMapping m = BXCoreFactory.eINSTANCE.createVarMapping();
					m.setFrom(sk);
					m.setTo(tk);
					list.add(m);
				}
				TupleType it = new UnsolvedTupleType();
				this.types.add(it);
				TypeUnion c2 = TypeUnion.makeSubSet(st, it);
				this.constraints.add(c2);
				TypeEqual c3 = TypeEqual.makeRightAbstractMapping(it, pt, list);
				this.constraints.add(c3);
				linkCause(c2, e);
				linkCause(c3, e);
			});
		} else if(e instanceof XmuCoreForEachMatchSource) {
			TupleType st = getType(e);
			TupleType pt = getType(((XmuCoreForEachMatchSource) e).getPattern());
			TupleType bt = getType(((XmuCoreForEachMatchSource) e).getBody());
			
			TypeUnion c1 = TypeUnion.makeSubSet(pt, st);
			this.constraints.add(c1);
			TypeEqual c2 = TypeEqual.makeLeftAbstractEqual(pt, bt);
			this.constraints.add(c2);
			
			linkCause(c1, e);
			linkCause(c2, e);
		} else if(e instanceof XmuCoreContextSource) {
			TupleType st = getType(e);
//			TupleType stm = getType(((XmuCoreContextSource) e).getMappingSource());
			TupleType bt = getType(((XmuCoreContextSource) e).getBody());
//			
//			TypeUnion c1 = TypeUnion.makeSubSet(st, stm);
//			this.constraints.add(c1);
			TypeEqual c2 = TypeEqual.makeEqual(st, bt);
			this.constraints.add(c2);

//			linkCause(c1, e);
			linkCause(c2, e);
		} else if(e instanceof XmuCoreDeriveSource) {
			TupleType st = getType(e);
			TupleType stm = getType(((XmuCoreDeriveSource) e).getDerivedType());
			TupleType bt = getType(((XmuCoreDeriveSource) e).getBody());
			
			TypeUnion c1 = TypeUnion.makeUnion(bt);
			c1.types.add(st);
			c1.types.add(stm);
			this.constraints.add(c1);
			
			TypeDisjoint c2 = TypeDisjoint.makeDisjoint(st, stm);
			this.constraints.add(c2);
			
			linkCause(c1, e);
			linkCause(c2, e);
		} else if(e instanceof XmuCoreDependencyView) {
			TupleType st = getType(e);
			TupleType bt = getType(((XmuCoreDependencyView) e).getBody());
			
			TypeEqual c1 = TypeEqual.makeEqual(st, bt);
			this.constraints.add(c1);
			
			linkCause(c1, e);
		}
	}

	@Override
	protected String getName() {
		return "source type infer";
	}

	@Override
	protected void handleTypeIndicator(XmuCoreStatement e) {
		TupleType st = getType(e);
		TupleType it = getType(e.getTypeIndicator().getSourceType());
		
		TypeEqual c = TypeEqual.makeEqual(st, it);
		this.constraints.add(c);
		linkCause(c, e);
	}

	@Override
	protected void handleTypeIndicator(BXFunctionDefinition e) {
		TupleType st = getType(e.getStatement());
		TupleType it = getType(e.getTypeIndicator().getSourceType());
		
		TypeEqual c = TypeEqual.makeEqual(st, it);
		this.constraints.add(c);
		linkCause(c, e);
	}

}
