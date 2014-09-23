package com.orangeandbronze.schoolreg.domain;

import java.util.HashSet;
import java.util.Set;

public class Subject extends Entity {
	
	private final String subjectId;
	private final Set<Subject> prerequisites = new HashSet<>();

	public Subject(String subjectId) {
		this.subjectId = subjectId;
	}
	
	public Subject(String subjectId, Set<Subject> prerequisites) {
		this.subjectId = subjectId;
		this.prerequisites.addAll(prerequisites);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((subjectId == null) ? 0 : subjectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return subjectId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public Set<Subject> getPrerequisites() {
		return new HashSet<>(prerequisites);
	}
	

}
