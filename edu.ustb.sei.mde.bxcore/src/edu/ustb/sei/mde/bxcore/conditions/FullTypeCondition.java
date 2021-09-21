package edu.ustb.sei.mde.bxcore.conditions;

import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.SourceType;
import edu.ustb.sei.mde.bxcore.ViewType;

@FunctionalInterface
public interface FullTypeCondition extends BiFunction<SourceType, ViewType, Boolean> {

}
