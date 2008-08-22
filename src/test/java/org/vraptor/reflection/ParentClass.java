package org.vraptor.reflection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vraptor.annotations.Parameter;

@SuppressWarnings("unused")
public class ParentClass {

	@Parameter
	ChildClass child = new ChildClass();

	@Parameter
	ChildClass[] childArray = new ChildClass[2];

	@Parameter
	List<ChildClass> childList = new ArrayList<ChildClass>();

	@Parameter
	Set<ChildClass> childSet = new HashSet<ChildClass>();

	@Parameter(create = true)
	ChildClass lazyChild;

	@Parameter(create = true)
	ChildClass[] lazyChildArray;

	@Parameter(create = true)
	List<ChildClass> lazyChildList;

	@Parameter(create = true)
	Set<ChildClass> lazyChildSet;

	@Parameter(create = true)
	MotherClass lazyMother = new MotherClass();

	@Parameter
	private ChildClass mainChild = new ChildClass();

	@Parameter
	MotherClass mother = new MotherClass();

	@Parameter
	String[] values;

	ParentClass() {
		mother.setChilds(new ChildClass[2]);
		for (int i = 0; i < 2; i++) {
			childArray[i] = new ChildClass();
			childList.add(new ChildClass());
			childSet.add(new ChildClass());
			mother.getChilds()[i] = new ChildClass();
		}
	}
	
	public ChildClass getMainChild() {
		return mainChild;
	}

}
