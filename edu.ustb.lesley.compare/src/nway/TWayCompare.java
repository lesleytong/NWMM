package nway;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ConflictKind;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceSource;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.merge.BatchMerger;
import org.eclipse.emf.compare.merge.IBatchMerger;
import org.eclipse.emf.compare.merge.IMerger;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class TWayCompare {
	
	String NsURIName = null;
	EPackage ep = null;
	
	public TWayCompare(String NsURIName, EPackage ep) {
		this.NsURIName = NsURIName;
		this.ep = ep;
	}

	public void tWay(URI leftURI, URI rightURI, URI baseURI) {

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(NsURIName, ep);
		
		Resource baseResource = null;
		if(baseURI != null) {			
			baseResource = resourceSet.getResource(baseURI, true);
		}
		Resource leftResource = resourceSet.getResource(leftURI, true);
		Resource rightResource = resourceSet.getResource(rightURI, true);

		IComparisonScope scope = new DefaultComparisonScope(leftResource, rightResource, baseResource);
		Comparison comparison = EMFCompare.builder().build().compare(scope);
		final EList<Diff> differences = comparison.getDifferences();
		List<Diff> leftDiffs = filterDiffs(differences, DifferenceSource.LEFT, false);
		IBatchMerger merger = new BatchMerger(IMerger.RegistryImpl.createStandaloneInstance());
		merger.copyAllLeftToRight(leftDiffs, new BasicMonitor());
		
		TreeIterator<EObject> allContents = rightResource.getAllContents();
		while(allContents.hasNext()) {
			System.out.println(allContents.next());
		}
		
	}
	
	private List<Diff> filterDiffs(List<Diff> diffs, DifferenceSource source, boolean filterConflicting) {
		List<Diff> filteredDiffs = new ArrayList<Diff>();
		for (Diff diff : diffs) {
			if (source.equals(diff.getSource()) && (!isConflicting(diff) || !filterConflicting)) {
				filteredDiffs.add(diff);
			}
		}
		return filteredDiffs;
	}

	private boolean isConflicting(Diff diff) {
		return diff.getConflict() != null && ConflictKind.REAL.equals(diff.getConflict().getKind());
	}
	


}
