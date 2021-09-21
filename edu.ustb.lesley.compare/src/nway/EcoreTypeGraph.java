package nway;

import org.eclipse.emf.common.util.URI;

import edu.ustb.sei.mde.bxcore.SourceType;
import edu.ustb.sei.mde.bxcore.ViewType;
import edu.ustb.sei.mde.bxcore.XmuCore;
import edu.ustb.sei.mde.bxcore.exceptions.BidirectionalTransformationDefinitionException;
import edu.ustb.sei.mde.bxcore.exceptions.NothingReturnedException;
import edu.ustb.sei.mde.bxcore.structures.ContextGraph;
import edu.ustb.sei.mde.bxcore.util.XmuProgram;
import edu.ustb.sei.mde.graph.type.TypeGraph;
import edu.ustb.sei.mde.graph.typedGraph.TypedGraph;
import edu.ustb.sei.mde.structure.Tuple2;

@SuppressWarnings("all")
public class EcoreTypeGraph extends XmuProgram {
	private TypeGraph typeGraph_ecore;

	public TypeGraph getTypeGraph_Ecore() {
		if (typeGraph_ecore == null) {
			typeGraph_ecore = new edu.ustb.sei.mde.graph.type.TypeGraph();
			typeGraph_ecore.declare("@EModelElement");
			typeGraph_ecore.declare("@ENamedElement,EModelElement");
			typeGraph_ecore.declare("@ETypedElement,ENamedElement");
			typeGraph_ecore.declare("@EStructuralFeature,ETypedElement");
			typeGraph_ecore.declare("EAttribute,EStructuralFeature");
			typeGraph_ecore.declare("@EClassifier,ENamedElement");
			typeGraph_ecore.declare("EDataType,EClassifier");
			typeGraph_ecore.declare("EAnnotation,EModelElement");
			typeGraph_ecore.declare("EStringToStringMapEntry");
			typeGraph_ecore.declare("EObject");
			typeGraph_ecore.declare("EClass,EClassifier");
			typeGraph_ecore.declare("EOperation,ETypedElement");
			typeGraph_ecore.declare("EReference,EStructuralFeature");
			typeGraph_ecore.declare("EGenericType");
			typeGraph_ecore.declare("EPackage,ENamedElement");
			typeGraph_ecore.declare("ETypeParameter,ENamedElement");
			typeGraph_ecore.declare("EEnum,EDataType");
			typeGraph_ecore.declare("EEnumLiteral,ENamedElement");
			typeGraph_ecore.declare("EFactory,EModelElement");
			typeGraph_ecore.declare("EParameter,ETypedElement");
			typeGraph_ecore.declare("EBoolean:boolean");
			typeGraph_ecore.declare("EString:java.lang.String");
			typeGraph_ecore.declare("EJavaClass:java.lang.Class");
			typeGraph_ecore.declare("EJavaObject:java.lang.Object");
			typeGraph_ecore.declare("EInt:int");
			typeGraph_ecore.declare("EEnumerator:org.eclipse.emf.common.util.Enumerator");
			typeGraph_ecore.declare("EBigDecimal:java.math.BigDecimal");
			typeGraph_ecore.declare("EBigInteger:java.math.BigInteger");
			typeGraph_ecore.declare("EBooleanObject:java.lang.Boolean");
			typeGraph_ecore.declare("EByte:byte");
			typeGraph_ecore.declare("EByteArray:byte[]");
			typeGraph_ecore.declare("EByteObject:java.lang.Byte");
			typeGraph_ecore.declare("EChar:char");
			typeGraph_ecore.declare("ECharacterObject:java.lang.Character");
			typeGraph_ecore.declare("EDate:java.util.Date");
			typeGraph_ecore.declare("EDiagnosticChain:org.eclipse.emf.common.util.DiagnosticChain");
			typeGraph_ecore.declare("EDouble:double");
			typeGraph_ecore.declare("EDoubleObject:java.lang.Double");
			typeGraph_ecore.declare("EEList:org.eclipse.emf.common.util.EList");
			typeGraph_ecore.declare("EFeatureMap:org.eclipse.emf.ecore.util.FeatureMap");
			typeGraph_ecore.declare("EFeatureMapEntry:org.eclipse.emf.ecore.util.FeatureMap$Entry");
			typeGraph_ecore.declare("EFloat:float");
			typeGraph_ecore.declare("EFloatObject:java.lang.Float");
			typeGraph_ecore.declare("EIntegerObject:java.lang.Integer");
			typeGraph_ecore.declare("ELong:long");
			typeGraph_ecore.declare("ELongObject:java.lang.Long");
			typeGraph_ecore.declare("EMap:java.util.Map");
			typeGraph_ecore.declare("EResource:org.eclipse.emf.ecore.resource.Resource");
			typeGraph_ecore.declare("EResourceSet:org.eclipse.emf.ecore.resource.ResourceSet");
			typeGraph_ecore.declare("EShort:short");
			typeGraph_ecore.declare("EShortObject:java.lang.Short");
			typeGraph_ecore.declare("ETreeIterator:org.eclipse.emf.common.util.TreeIterator");
			typeGraph_ecore.declare("EInvocationTargetException:java.lang.reflect.InvocationTargetException");
			typeGraph_ecore.declare("iD:EAttribute->EBoolean");
			typeGraph_ecore.declare("source:EAnnotation->EString");
			typeGraph_ecore.declare("abstract:EClass->EBoolean");
			typeGraph_ecore.declare("interface:EClass->EBoolean");
			typeGraph_ecore.declare("instanceClassName:EClassifier->EString");
			typeGraph_ecore.declare("instanceClass:EClassifier->EJavaClass");
			typeGraph_ecore.declare("defaultValue:EClassifier->EJavaObject");
			typeGraph_ecore.declare("instanceTypeName:EClassifier->EString");
			typeGraph_ecore.declare("serializable:EDataType->EBoolean");
			typeGraph_ecore.declare("value:EEnumLiteral->EInt");
			typeGraph_ecore.declare("instance:EEnumLiteral->EEnumerator");
			typeGraph_ecore.declare("literal:EEnumLiteral->EString");
			typeGraph_ecore.declare("name:ENamedElement->EString");
			typeGraph_ecore.declare("nsURI:EPackage->EString");
			typeGraph_ecore.declare("nsPrefix:EPackage->EString");
			typeGraph_ecore.declare("containment:EReference->EBoolean");
			typeGraph_ecore.declare("container:EReference->EBoolean");
			typeGraph_ecore.declare("resolveProxies:EReference->EBoolean");
			typeGraph_ecore.declare("changeable:EStructuralFeature->EBoolean");
			typeGraph_ecore.declare("volatile:EStructuralFeature->EBoolean");
			typeGraph_ecore.declare("transient:EStructuralFeature->EBoolean");
			typeGraph_ecore.declare("defaultValueLiteral:EStructuralFeature->EString");
			typeGraph_ecore.declare("defaultValue:EStructuralFeature->EJavaObject");
			typeGraph_ecore.declare("unsettable:EStructuralFeature->EBoolean");
			typeGraph_ecore.declare("derived:EStructuralFeature->EBoolean");
			typeGraph_ecore.declare("ordered:ETypedElement->EBoolean");
			typeGraph_ecore.declare("unique:ETypedElement->EBoolean");
			typeGraph_ecore.declare("lowerBound:ETypedElement->EInt");
			typeGraph_ecore.declare("upperBound:ETypedElement->EInt");
			typeGraph_ecore.declare("many:ETypedElement->EBoolean");
			typeGraph_ecore.declare("required:ETypedElement->EBoolean");
			typeGraph_ecore.declare("key:EStringToStringMapEntry->EString");
			typeGraph_ecore.declare("value:EStringToStringMapEntry->EString");
			typeGraph_ecore.declare("eAttributeType:EAttribute->EDataType");
			typeGraph_ecore.declare("@details:EAnnotation->EStringToStringMapEntry*");
			typeGraph_ecore.declare("eModelElement:EAnnotation->EModelElement");
			typeGraph_ecore.declare("@contents:EAnnotation->EObject*");
			typeGraph_ecore.declare("references:EAnnotation->EObject*");
			typeGraph_ecore.declare("eSuperTypes:EClass->EClass*");
			typeGraph_ecore.declare("@eOperations:EClass->EOperation*");
			typeGraph_ecore.declare("eAllAttributes:EClass->EAttribute*");
			typeGraph_ecore.declare("eAllReferences:EClass->EReference*");
			typeGraph_ecore.declare("eReferences:EClass->EReference*");
			typeGraph_ecore.declare("eAttributes:EClass->EAttribute*");
			typeGraph_ecore.declare("eAllContainments:EClass->EReference*");
			typeGraph_ecore.declare("eAllOperations:EClass->EOperation*");
			typeGraph_ecore.declare("eAllStructuralFeatures:EClass->EStructuralFeature*");
			typeGraph_ecore.declare("eAllSuperTypes:EClass->EClass*");
			typeGraph_ecore.declare("eIDAttribute:EClass->EAttribute");
			typeGraph_ecore.declare("@eStructuralFeatures:EClass->EStructuralFeature*");
			typeGraph_ecore.declare("@eGenericSuperTypes:EClass->EGenericType*");
			typeGraph_ecore.declare("eAllGenericSuperTypes:EClass->EGenericType*");
			typeGraph_ecore.declare("ePackage:EClassifier->EPackage");
			typeGraph_ecore.declare("@eTypeParameters:EClassifier->ETypeParameter*");
			typeGraph_ecore.declare("@eLiterals:EEnum->EEnumLiteral*");
			typeGraph_ecore.declare("eEnum:EEnumLiteral->EEnum");
			typeGraph_ecore.declare("ePackage:EFactory->EPackage");
			typeGraph_ecore.declare("@eAnnotations:EModelElement->EAnnotation*");
			typeGraph_ecore.declare("eContainingClass:EOperation->EClass");
			typeGraph_ecore.declare("@eTypeParameters:EOperation->ETypeParameter*");
			typeGraph_ecore.declare("@eParameters:EOperation->EParameter*");
			typeGraph_ecore.declare("eExceptions:EOperation->EClassifier*");
			typeGraph_ecore.declare("@eGenericExceptions:EOperation->EGenericType*");
			typeGraph_ecore.declare("eFactoryInstance:EPackage->EFactory");
			typeGraph_ecore.declare("@eClassifiers:EPackage->EClassifier*");
			typeGraph_ecore.declare("@eSubpackages:EPackage->EPackage*");
			typeGraph_ecore.declare("eSuperPackage:EPackage->EPackage");
			typeGraph_ecore.declare("eOperation:EParameter->EOperation");
			typeGraph_ecore.declare("eOpposite:EReference->EReference");
			typeGraph_ecore.declare("eReferenceType:EReference->EClass");
			typeGraph_ecore.declare("eKeys:EReference->EAttribute*");
			typeGraph_ecore.declare("eContainingClass:EStructuralFeature->EClass");
			typeGraph_ecore.declare("eType:ETypedElement->EClassifier");	
			typeGraph_ecore.declare("@eGenericType:ETypedElement->EGenericType");
			typeGraph_ecore.declare("@eUpperBound:EGenericType->EGenericType");
			typeGraph_ecore.declare("@eTypeArguments:EGenericType->EGenericType*");
			typeGraph_ecore.declare("eRawType:EGenericType->EClassifier");
			typeGraph_ecore.declare("@eLowerBound:EGenericType->EGenericType");
			typeGraph_ecore.declare("eTypeParameter:EGenericType->ETypeParameter");
			typeGraph_ecore.declare("eClassifier:EGenericType->EClassifier");
			typeGraph_ecore.declare("@eBounds:ETypeParameter->EGenericType*");
		}
		return typeGraph_ecore;
	}

	public void registerEcorePackage(final URI metamodelUri) {
		registerPackage("ecore", metamodelUri);
	}

	public TypedGraph loadEcoreModel(final URI modelUri) {
		org.eclipse.emf.ecore.EPackage pack = getPackage("ecore");
		java.util.List<org.eclipse.emf.ecore.EObject> roots = edu.ustb.sei.mde.bxcore.util.EcoreModelUtil
				.load(modelUri);
		edu.ustb.sei.mde.graph.typedGraph.TypedGraph graph = edu.ustb.sei.mde.bxcore.util.EcoreModelUtil.load(roots,
				getTypeGraph_Ecore());
		return graph;
	}

	public void saveEcoreModel(final URI uri, final ContextGraph data, final TypedGraph originalGraph)
			throws NothingReturnedException, BidirectionalTransformationDefinitionException {
		edu.ustb.sei.mde.bxcore.util.EcoreModelUtil.save(uri, data.getGraph(), originalGraph, getPackage("ecore"));
	}

	public void saveEcoreModel(final URI uri, final ContextGraph data)
			throws NothingReturnedException, BidirectionalTransformationDefinitionException {
		edu.ustb.sei.mde.bxcore.util.EcoreModelUtil.save(uri, data.getGraph(), null, getPackage("ecore"));
	}

	public ViewType execute(final XmuCore bx, final TypedGraph graph, final Tuple2<String, Object>[] inits)
			throws NothingReturnedException {
		edu.ustb.sei.mde.bxcore.structures.Context sourceContext = bx.getSourceDef().createInstance();
		for (edu.ustb.sei.mde.structure.Tuple2<java.lang.String, java.lang.Object> tuple : inits) {
			sourceContext.setValue(tuple.first, tuple.second);
		}
		return bx.forward(edu.ustb.sei.mde.bxcore.SourceType.makeSource(graph, sourceContext,
				new edu.ustb.sei.mde.bxcore.TraceSystem()));
	}

	public SourceType execute(final XmuCore bx, final TypedGraph source, final Tuple2<String, Object>[] sourceInits,
			final TypedGraph view, final Tuple2<String, Object>[] viewInits) throws NothingReturnedException {
		edu.ustb.sei.mde.bxcore.structures.Context sourceContext = bx.getSourceDef().createInstance();
		for (edu.ustb.sei.mde.structure.Tuple2<java.lang.String, java.lang.Object> tuple : sourceInits) {
			sourceContext.setValue(tuple.first, tuple.second);
		}
		edu.ustb.sei.mde.bxcore.structures.Context viewContext = bx.getViewDef().createInstance();
		for (edu.ustb.sei.mde.structure.Tuple2<java.lang.String, java.lang.Object> tuple : viewInits) {
			viewContext.setValue(tuple.first, tuple.second);
		}

		return bx.backward(
				edu.ustb.sei.mde.bxcore.SourceType.makeSource(source, sourceContext,
						new edu.ustb.sei.mde.bxcore.TraceSystem()),
				edu.ustb.sei.mde.bxcore.ViewType.makeView(view, viewContext));
	}
}
