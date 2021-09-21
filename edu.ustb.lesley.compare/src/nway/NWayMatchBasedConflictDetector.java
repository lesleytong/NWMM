package nway;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ComparisonCanceledException;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompareMessages;
import org.eclipse.emf.compare.conflict.MatchBasedConflictDetector;
import org.eclipse.emf.compare.internal.conflict.AbstractConflictSearch;
import org.eclipse.emf.compare.internal.conflict.ConflictSearchFactory;

public class NWayMatchBasedConflictDetector extends MatchBasedConflictDetector {

	private static final Logger LOGGER = Logger.getLogger(MatchBasedConflictDetector.class);

	@Override
	public void detect(Comparison comparison, Monitor monitor) {
		long start = System.currentTimeMillis();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("detect conflicts - START"); //$NON-NLS-1$
		}
		final List<Diff> differences = comparison.getDifferences();
		final int diffCount = differences.size();

		ConflictSearchFactory conflictSearchFactory = new ConflictSearchFactory(comparison, monitor);
		for (int i = 0; i < diffCount; i++) {
			if (i % 100 == 0) {
				monitor.subTask(EMFCompareMessages.getString("DefaultConflictDetector.monitor.detect", //$NON-NLS-1$
						Integer.valueOf(i + 1), Integer.valueOf(diffCount)));
				if (monitor.isCanceled()) {
					throw new ComparisonCanceledException();
				}
			}
			final Diff diff = differences.get(i);

			// lyt: 执行前，此diff改成RIGHT
			diff.setSource(DifferenceSource.RIGHT);

			AbstractConflictSearch<? extends Diff> search = conflictSearchFactory.doSwitch(diff);
			search.detectConflicts();

			// lyt: 执行后，此diff改成LEFT，保证之后的冲突检测
			diff.setSource(DifferenceSource.LEFT);

		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("detect conflicts - END - Took %d ms", Long.valueOf(System //$NON-NLS-1$
					.currentTimeMillis() - start)));
		}
	}

}