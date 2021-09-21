package edu.ustb.sei.mde.bxcore.dsl.infer;

import java.util.logging.Level;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.exception.ContradictionException;
import org.eclipse.emf.ecore.EObject;

import edu.ustb.sei.mde.bxcore.XmuCoreUtils;

public class TypeInferenceException extends RuntimeException {
	public TypeInferenceException(EObject reason, ContradictionException e, Model model, TypeModel typeModel) {
		this("Error in type inference",reason, e, model, typeModel);
	}
	
	public TypeInferenceException(String message, EObject reason, ContradictionException e, Model model, TypeModel typeModel) {
		super(message,e);
		this.reason = reason;
		this.model = model;
		this.typeModel = typeModel;
	}

	private static final long serialVersionUID = -6560701499665265312L;
	private EObject reason;
	private Model model;
	private TypeModel typeModel;
	
	public EObject getReason() {
		return reason;
	}
	
	public void explain() {
		if(model!=null) {
			ContradictionException contradictionException = model.getSolver().getContradictionException();
			if(typeModel!=null) {
				XmuCoreUtils.log(Level.INFO, "Names: "+typeModel.nameList, contradictionException);
			}
			XmuCoreUtils.log(Level.INFO, contradictionException.c.toString(), contradictionException);
		} else {
			XmuCoreUtils.log(Level.INFO, "No more information", null);
		}
	}
}
