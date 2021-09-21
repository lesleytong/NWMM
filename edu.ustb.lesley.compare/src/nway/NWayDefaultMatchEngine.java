package nway;

import static java.util.Collections.emptyIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompareMessages;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProvider;
import org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProviderDescriptorRegistryImpl;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.WeightProvider;
import org.eclipse.emf.compare.match.eobject.WeightProviderDescriptorRegistryImpl;
import org.eclipse.emf.compare.match.resource.IResourceMatcher;
import org.eclipse.emf.compare.match.resource.IResourceMatchingStrategy;
import org.eclipse.emf.compare.match.resource.StrategyResourceMatcher;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class NWayDefaultMatchEngine extends DefaultMatchEngine {

	private static final Logger LOGGER = Logger.getLogger(DefaultMatchEngine.class);

	private final IComparisonFactory comparisonFactory;

	public NWayDefaultMatchEngine(IEObjectMatcher eObjectMatcher, IResourceMatcher resourceMatcher,
			IComparisonFactory comparisonFactory) {
		super(eObjectMatcher, resourceMatcher, comparisonFactory);
		this.comparisonFactory = comparisonFactory;
		// TODO Auto-generated constructor stub
	}

	public NWayDefaultMatchEngine(IEObjectMatcher matcher, IComparisonFactory comparisonFactory) {
		super(matcher, comparisonFactory);
		this.comparisonFactory = comparisonFactory;
		// TODO Auto-generated constructor stub
	}

	// lyt: 新加的方法
	public Comparison matchN(IComparisonScope scope, List<Match> preMatches, Map<EObject, Diff> addDiffsMap,
			Monitor monitor) {
		long start = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("detect matches - START")); //$NON-NLS-1$
		}

		Comparison comparison = comparisonFactory.createComparison();

		comparison.getMatches().addAll(preMatches); // lyt: 实际上就添加了这行

		final Notifier left = scope.getLeft();
		final Notifier right = scope.getRight();
		final Notifier origin = scope.getOrigin();

		comparison.setThreeWay(origin != null);

		if (left instanceof Resource || right instanceof Resource) {
			matchADD(comparison, scope, (Resource) left, (Resource) right, (Resource) origin, monitor, addDiffsMap);
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("detect matches - END - Took %d ms", Long.valueOf(System //$NON-NLS-1$
					.currentTimeMillis() - start)));
		}
		return comparison;
	}

	protected void matchADD(Comparison comparison, IComparisonScope scope, Resource left, Resource right,
			Resource origin, Monitor monitor, Map<EObject, Diff> addDiffsMap) {
		monitor.subTask(EMFCompareMessages.getString("DefaultMatchEngine.monitor.match.resource")); //$NON-NLS-1$
		// Our "roots" are Resources. Consider them matched
		final MatchResource match = CompareFactory.eINSTANCE.createMatchResource();

		match.setLeft(left);
		match.setRight(right);
		match.setOrigin(origin);

		if (left != null) {
			URI uri = left.getURI();
			if (uri != null) {
				match.setLeftURI(uri.toString());
			}
		}

		if (right != null) {
			URI uri = right.getURI();
			if (uri != null) {
				match.setRightURI(uri.toString());
			}
		}

		if (origin != null) {
			URI uri = origin.getURI();
			if (uri != null) {
				match.setOriginURI(uri.toString());
			}
		}

		comparison.getMatchedResources().add(match);

		// We need at least two resources to match them
		if (atLeastTwo(left == null, right == null, origin == null)) {
			/*
			 * TODO But if we have only one resource, which is then unmatched, should we not
			 * still do something with it?
			 */
			return;
		}

		final Iterator<? extends EObject> leftEObjectsUnModifiable;
		EList<EObject> leftEList = new BasicEList<>();
		if (left != null) {
			leftEObjectsUnModifiable = scope.getCoveredEObjects(left);
			while (leftEObjectsUnModifiable.hasNext()) {
				EObject next = leftEObjectsUnModifiable.next();
				if (comparison.getMatch(next) == null && addDiffsMap.get(next) != null) { // 说明是新加元素需要进行匹配
					leftEList.add(next);
				}
			}

		} else {
			leftEObjectsUnModifiable = emptyIterator();
		}

		final Iterator<? extends EObject> rightEObjectsUnModifiable;
		EList<EObject> rightEList = new BasicEList<>();
		if (right != null) {
			rightEObjectsUnModifiable = scope.getCoveredEObjects(right);
			while (rightEObjectsUnModifiable.hasNext()) {
				EObject next = rightEObjectsUnModifiable.next();
				if (comparison.getMatch(next) == null && addDiffsMap.get(next) != null) { // 说明是新加元素需要进行匹配
					rightEList.add(next);
				}
			}

		} else {
			rightEObjectsUnModifiable = emptyIterator();
		}

		// 官方原来的代码
		final Iterator<? extends EObject> originEObjectsUnModifiable;
		if (origin != null) {
			originEObjectsUnModifiable = scope.getCoveredEObjects(origin);
		} else {
			originEObjectsUnModifiable = emptyIterator();
		}

		getEObjectMatcher().createMatches(comparison, leftEList.iterator(), rightEList.iterator(),
				originEObjectsUnModifiable, monitor);

	}

	private static boolean atLeastTwo(boolean condition1, boolean condition2, boolean condition3) {
		// CHECKSTYLE:OFF This expression is alone in its method, and documented.
		return condition1 && (condition2 || condition3) || (condition2 && condition3);
		// CHECKSTYLE:ON
	}

	public static IMatchEngine create(UseIdentifiers useIDs) {
		return create(useIDs, WeightProviderDescriptorRegistryImpl.createStandaloneInstance(),
				EqualityHelperExtensionProviderDescriptorRegistryImpl.createStandaloneInstance(), null);
	}

	public static IMatchEngine create(UseIdentifiers useIDs, WeightProvider.Descriptor.Registry weightProviderRegistry,
			EqualityHelperExtensionProvider.Descriptor.Registry equalityHelperExtensionProviderRegistry,
			Collection<IResourceMatchingStrategy> strategies) {
		final IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());
		final IEObjectMatcher eObjectMatcher = createDefaultEObjectMatcher(useIDs, weightProviderRegistry,
				equalityHelperExtensionProviderRegistry);

		final IResourceMatcher resourceMatcher;
		if (strategies == null || strategies.isEmpty()) {
			resourceMatcher = new StrategyResourceMatcher();
		} else {
			resourceMatcher = new StrategyResourceMatcher(strategies);
		}

		final IMatchEngine matchEngine = new NWayDefaultMatchEngine(eObjectMatcher, resourceMatcher, comparisonFactory);
		return matchEngine;
	}
}