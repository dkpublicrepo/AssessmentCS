import com.atlassian.jira.user.ApplicationUser 
import com.atlassian.jira.component.ComponentAccessor 
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.bc.issue.search.SearchService

/*
Assessment #1
Write a script to generate a report that shows the number of times a Custom Field has been
used in total across the system.

This script will take a customfield Id which will have to be replaced by the end user and will run
a JQL search within scriptrunner to retrieve all issues using the customfield.
I went with the customfield id approach because there can be custom fields with the same name
and using the id will specify which field you want to search for.
*/

//Please replace the customfield_10600 with the correct customfield field Id
String customfieldToSearch = "customfield_10600"

ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().loggedInUser

IssueManager issueManager = ComponentAccessor.getIssueManager();
SearchService searchService = ComponentAccessor.getComponentOfType(SearchService.class)


String[] split_cf_text = customfieldToSearch.split("_")
String search_cf = "cf[" + split_cf_text[1] + "]"

String searchjql = "'" + search_cf +"' is not EMPTY"

SearchService.ParseResult parseResult = searchService.parseQuery (user, searchjql)
if (parseResult.isValid()){
	def results = searchService.search(user, parseResult.getQuery(), PagerFilter.getUnlimitedFilter()); 
	def total = results.getTotal()

	return "The system has a total of: " + total + " issues using the custom field."
}