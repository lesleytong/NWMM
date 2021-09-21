package edu.ustb.sei.mde.bxcore.util

import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathType
import edu.ustb.sei.mde.bxcore.dsl.bXCore.DashedPathTypeSegment
import org.eclipse.xtend2.lib.StringConcatenation

class PathTypeUtil {
	static def StringConcatenation getText(DashedPathType type) '''«type.segment.text»«IF type.next!==null».«type.next.text»«ENDIF»'''
	static def getText(DashedPathTypeSegment seg) '''(«FOR s : seg.types SEPARATOR '|'»«s.name»«ENDFOR»)«seg.repeat»'''
	
	static def boolean isEqual(DashedPathType left, Object right) {
		if(left===right) true
		else {
			if(right===null || !(right instanceof DashedPathType)) false
			else {
				val rt = right as DashedPathType;
				if(!left.segment.isEqual(rt.segment)) false
				else if(left.next!==null) left.next.isEqual(rt.next)
				else rt.next===null
			}
		}
	}
	
	static def isEqual(DashedPathTypeSegment left, DashedPathTypeSegment right) {
		if(!((left.repeat===null && right.repeat===null) 
			|| (left.repeat!==null && left.repeat.equals(right.repeat)))) false
		else {
			left.types.equals(right.types)
		}
	}
	
	static def int hash(DashedPathTypeSegment seg) {
		seg.types.map[t|t.hashCode].reduce[p1, p2| p1 + p2]
	}
	
	static def int hash(DashedPathType type) {
		type.segment.hash + (if(type.next===null) 0 else (type.next.hash << 2))
	}
}