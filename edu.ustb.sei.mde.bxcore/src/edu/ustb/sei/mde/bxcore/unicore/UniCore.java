package edu.ustb.sei.mde.bxcore.unicore;

import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.SourceType;
import edu.ustb.sei.mde.bxcore.ViewType;

abstract public class UniCore implements BiFunction<SourceType,ViewType,SourceType> {
	abstract public SourceType apply(SourceType source, ViewType view);
}
