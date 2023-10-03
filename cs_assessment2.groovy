import com.atlassian.jira.project.Project 
import com.atlassian.jira.project.ProjectManager 
import com.atlassian.jira.web.bean.PagerFilter 
import com.atlassian.jira.user.ApplicationUser 
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.bc.issue.search.SearchService 
import com.atlassian.jira.component.ComponentAccessor 
import com.atlassian.jira.issue.Issue

/*
Assessment #2
Write a script to generate a report that shows the number of times a Custom Field has been
used in total across all projects within the system.

This script will generate a report that shows for all projects in Jira, it will return any project
with issues using this custom field if any exist.
*/

//Please replace "Role" with the custom field name you want to search
String customfieldToSearch = "Role"

ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().loggedInUser 
ProjectManager projectManager = ComponentAccessor.getProjectManager()
IssueManager issueManager = ComponentAccessor.getIssueManager()
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class)

List<Project> listOfProjects = projectManager.getProjects()

String report = ""

for(Project p : listOfProjects){
	String searchjql = "'" + customfieldToSearch + "' is not empty and Project = '" + p.getName().toString() + "'"
	SearchService.ParseResult parseResult = searchService.parseQuery(user, searchjql)
	
	if (parseResult.isValid()){

		def results = searchService.search(user, parseResult.getQuery(), PagerFilter.getUnlimitedFilter())
		def total = results.getTotal()
		if(total > 0){
			report += p.getName() + ": " + total + ";"
		}
	}
}
return report