package edu.ustb.sei.mde.bxcore.conditions;

import java.util.function.BiFunction;

import edu.ustb.sei.mde.bxcore.structures.Context;

@FunctionalInterface
public interface ContextCondition extends BiFunction<Context, Context, Boolean> {

}
