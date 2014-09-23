package com.orangeandbronze.schoolreg.service;

import java.util.ArrayList;
import java.util.List;

import com.orangeandbronze.schoolreg.domain.Faculty;
import com.orangeandbronze.schoolreg.domain.Section;
import com.orangeandbronze.schoolreg.domain.Subject;

public class SectionCreationService {
	
	public List<Faculty> fetchFacultyList(){
		List<Faculty> facultyList = new ArrayList<>();
		
		//Remove 2 lines below because these are test purpose
		Faculty faculty = new Faculty(new Integer(1));
		facultyList.add(faculty);
		
		return facultyList;
	};
	
	public List<Subject> fetchSubjectList(){
		List<Subject> subjectList = new ArrayList<>();
		return subjectList;
	};
	
	
	public boolean checkTeacherScheduleAvailability(){
		return true;
	}
	
	public void createSection(){
		//Implement insert statement later
	}

}
