import com.atlassian.jira.component.ComponentAccessor 
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.CustomFieldManager 
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.customfields.manager.OptionsManager 
import com.atlassian.jira.issue.fields.config.FieldConfig 
import com.atlassian.jira.issue.customfields.options.* 
import com.atlassian.jira.bc.issue.IssueService 
import com.atlassian.jira.issue.context.IssueContext

/*
Assessment #3
Write a script to copy the values of a given multi-select type field to another multi-select type
field.

This field will copy all the values in one multi-select list field to another multi-select list field.
This is assuming that the target custom field already exist.
*/

//This is the source custom field which we will be copying values from. Please replace "Role" with the correct field name.
String cf1 = "Role" 

//This is the target custom field which we will by copying values to. Please replace "Test Select Multi" with the correct field name 
String cf2 = "Test Select Multi"


CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager()
OptionsManager optionsManager = ComponentAccessor.getOptionsManager()
IssueService issueService = ComponentAccessor.getIssueService()

CustomField sourceCF = customFieldManager.getCustomFieldObjectsByName(cf1)[0] 
CustomField targetCF = customFieldManager.getCustomFieldObjectsByName(cf2)[0]

FieldConfig sourceCF_fieldConfig = sourceCF.getRelevantConfig(IssueContext.GLOBAL) 
FieldConfig targetCF_fieldConfig = targetCF.getRelevantConfig(IssueContext.GLOBAL)

List sourceCF_fieldOptions = optionsManager.getOptions (sourceCF_fieldConfig)
int seq = optionsManager.getOptions(sourceCF_fieldConfig).getRootOptions().size() + 1

for(int cnt = 0; cnt <sourceCF_fieldOptions.size(); cnt++){
	String temp_option = sourceCF_fieldOptions.get(cnt).toString()
	optionsManager.createOption(targetCF_fieldConfig, null, seq++, temp_option)
}