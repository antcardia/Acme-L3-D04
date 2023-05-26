<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-1" action="http://www.youtube.com/"/>
      		<acme:menu-suboption code="master.menu.anonymous.favourite-link-2" action="https://es.wikipedia.org/wiki/Michael_Jackson"/>
      		<acme:menu-suboption code="master.menu.anonymous.favourite-link-3" action="https://store.steampowered.com/?l=spanish"/>
      		<acme:menu-suboption code="master.menu.anonymous.favourite-link-4" action="https://www.gorillaz.com/home/"/>
      		<acme:menu-suboption code="master.menu.anonymous.favourite-link-5" action="https://www.youtube.com/watch?v=dQw4w9WgXcQ"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.any">
			<acme:menu-suboption code="master.menu.any.course" action="/any/course/list"/>
			<acme:menu-suboption code="master.menu.any.peep" action="/any/peep/list"/>
			
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.practicum" action="/authenticated/practicum/list"/>
			<acme:menu-suboption code="master.menu.authenticated.offer.list" action="/authenticated/offer/list"/>
			<acme:menu-suboption code="master.menu.authenticated.tutorials" action="/authenticated/tutorial/list"/>
			<acme:menu-suboption code="master.menu.authenticated.note.list" action="/authenticated/note/list"/>
			<acme:menu-suboption code="master.menu.authenticated.bulletin" action="/authenticated/bulletin/list"/>

		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.administrator-dashboard" action="/administrator/administrator-dashboard/show"/>
			<acme:menu-suboption code="master.menu.administrator.bulletin" action="/administrator/bulletin/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.systemConfiguration" action="/administrator/system-configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-suboption code="master.menu.administrator.banner-list" action="/administrator/banner/list"/>
			<acme:menu-suboption code="master.menu.administrator.offer.list" action="/administrator/offer/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.lecturer" access="hasRole('Lecturer')">
			<acme:menu-suboption code="master.menu.lecturer.my-courses" action="/lecturer/course/list-mine"/>
			<acme:menu-suboption code="master.menu.lecturer.my-lectures" action="/lecturer/lecture/list-mine"/>
			<acme:menu-suboption code="master.menu.lecturer.my-dashboard" action="/lecturer/lecturer-dashboard/show"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.company" access="hasRole('Company')">
			<acme:menu-suboption code="master.menu.company.practicum" action="/company/practicum/list"/>
			<acme:menu-suboption code="master.menu.company.session-practicum" action="/company/session-practicum/list"/>
			<acme:menu-suboption code="master.menu.company.practicum.dashboard" action="/company/company-dashboard/show"/>
		</acme:menu-option>    
    
		<acme:menu-option code="master.menu.student" access="hasRole('Student')">
			<acme:menu-suboption code="master.menu.student.my-courses" action="/student/course/list"/>
			<acme:menu-suboption code="master.menu.student.my-enrolments" action="/student/enrolment/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.assistant" access="hasRole('Assistant')">
			<acme:menu-suboption code="master.menu.assistant.my-tutorials" action="/assistant/tutorial/list"/>
			<acme:menu-suboption code="master.menu.assistant.my-sessions" action="/assistant/session/list-mine"/>
		</acme:menu-option>

    
    <acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.audits" action="/auditor/audit/list"/>
	</acme:menu-option>
   
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-student" action="/authenticated/student/create" access="!hasRole('Student')"/>
		 	<acme:menu-suboption code="master.menu.user-account.student" action="/authenticated/student/update" access="hasRole('Student')"/>
			<acme:menu-suboption code="master.menu.user-account.become-lecturer" action="/authenticated/lecturer/create" access="!hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.lecturer" action="/authenticated/lecturer/update" access="hasRole('Lecturer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-assistant" action="/authenticated/assistant/create" access="!hasRole('Assistant')"/>
			<acme:menu-suboption code="master.menu.user-account.assistant" action="/authenticated/assistant/update" access="hasRole('Assistant')"/>
      <acme:menu-suboption code="master.menu.user-account.become-company" action="/authenticated/company/create" access="!hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.company" action="/authenticated/company/update" access="hasRole('Company')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

