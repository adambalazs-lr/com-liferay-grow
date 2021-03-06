<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>
<%@ page import="com.liferay.document.library.kernel.exception.DuplicateFileEntryException" %><%@
page import="com.liferay.document.library.kernel.service.DLAppLocalServiceUtil" %><%@
page import="com.liferay.document.library.kernel.util.DLUtil" %><%@
page import="com.liferay.grow.gamification.badges.editor.constants.BadgeTypeEditorPortletKeys" %><%@
page import="com.liferay.grow.gamification.model.Badge" %><%@
page import="com.liferay.grow.gamification.model.BadgeGroup" %><%@
page import="com.liferay.grow.gamification.service.BadgeGroupLocalServiceUtil" %><%@
page import="com.liferay.grow.gamification.service.BadgeTypeLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.dao.orm.QueryUtil" %><%@
page import="com.liferay.portal.kernel.repository.model.FileEntry" %><%@
page import="com.liferay.portal.kernel.util.DateUtil" %>

<%@ page import="java.util.List" %><%@
page import="java.util.Locale" %>

<%
	List<BadgeGroup> badgeGroups = BadgeGroupLocalServiceUtil.getBadgeGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	pageContext.setAttribute("badgeGroups", badgeGroups);
%>

<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="badge-image-file-already-exists" />

<div class="container">
	<div class="row">
		<div class="col-sm">
			<liferay-ui:search-container total="<%= BadgeTypeLocalServiceUtil.getBadgeTypesCount() %>">
				<liferay-ui:search-container-results
					results="<%= BadgeTypeLocalServiceUtil.getBadgeTypes(
						searchContainer.getStart(), searchContainer.getEnd()) %>"
					/>

					<liferay-ui:search-container-row className="com.liferay.grow.gamification.model.BadgeType" modelVar="badgeType">
						<liferay-ui:search-container-column-text name="Badge">

							<%
								FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(badgeType.getFileEntryId());

								String downloadUrl = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, "", false, true);
							%>

							<p class="badge-icon">
								<img class="badge-image" src="<%= downloadUrl %>" />
							</p>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text property="type" />

						<liferay-ui:search-container-column-text name="User Created">
							<c:out value="${badgeType.getUserName()}"></c:out>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text name="Available Since">
							<c:if test="${badgeType.getAssignableFrom() != null}">
								<%= DateUtil.getDate(badgeType.getAssignableFrom(), "yyyy-MM-dd", Locale.US) %>
							</c:if>

							<c:if test="${badgeType.getAssignableFrom() == null}">
								Start of GROW
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text name="Available Until">
							<c:if test="${badgeType.getAssignableTo() != null}">
								<%= DateUtil.getDate(badgeType.getAssignableTo(), "yyyy-MM-dd", Locale.US) %>
							</c:if>

							<c:if test="${badgeType.getAssignableTo() == null}">
								Forever
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text property="system" />
					</liferay-ui:search-container-row>
				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</div>
	</div>

	<div class="row">
		<div class="col-sm">
			<button class="btn btn-primary float-right" data-target="#badgeTypeModal" data-toggle="modal" type="button">Add Badge Type</button>
			<button class="btn btn-primary float-right" data-target="#badgeGroupModal" data-toggle="modal" type="button">Add Badge Group</button>
		</div>
	</div>
</div>

<portlet:actionURL name="addBadgeType" var="addBadgeTypeURL">
	<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
</portlet:actionURL>

<portlet:actionURL name="addBadgeGroup" var="addBadgeGroupURL">
	<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
</portlet:actionURL>

<c:if test="${themeDisplay.isSignedIn() == true}">
	<div aria-hidden="true" class="modal" id="badgeTypeModal" role="dialog" style="display:none; z-index" tabindex="-1">
		<div class="flex">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<aui:form action="<%= addBadgeTypeURL %>" enctype="multipart/form-data" id="badgeForm" method="post" name="badgeForm">
					<aui:input id="userId" name="userId" type="hidden" value="" />

					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Add Badge Type</h5>

							<button aria-label="Close" class="close" data-dismiss="modal" type="button">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>

						<div class="modal-body">
							<div class="form-group">
								<input class="form-control" id="_badgetypeeditor_type" name="_badgetypeeditor_type" required="required" type="input" value="" />
							</div>

							<div class="form-group">
								<aui:input class="form-control" label="Email Template" name="templateHTML" type="textarea" value="" />
							</div>

							<div class="form-group">
								<aui:input class="form-control" name="availableFrom" type="date" value="" />
							</div>

							<div class="form-group">
								<aui:input class="form-control" name="availableTo" type="date" value="" />
							</div>

							<div class="form-group">
								<aui:input class="form-control" name="system" type="checkbox" />
							</div>

							<div class="form-group">
								<select class="form-control" name="group">
									<c:out escapeXml="false" value="<option value='0'>Not Groupped</option>" />

									<c:forEach items="${badgeGroups}" var="g">
										<c:out escapeXml="false" value="<option value='${g.badgeGroupId}'>${g.groupName}</option>" />
									</c:forEach>
								</select>
							</div>

							<div class="form-group">
								<label for="fileEntry">Upload a transparent PNG file with 200x200 pixel size.</label>

								<input class="form-control" name="fileEntry" required="required" type="file" value="" />
							</div>
						</div>

						<div class="modal-footer">
							<button class="btn btn-secondary" data-dismiss="modal" type="button">Cancel</button>
							<button class="btn btn-primary" onclick="addBadgeType()" type="submit">Add Badge Type</button>
						</div>
					</div>
				</aui:form>
			</div>
		</div>
	</div>

	<div aria-hidden="true" class="modal" id="badgeGroupModal" role="dialog" style="display:none; z-index" tabindex="-1">
		<div class="flex">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<aui:form action="<%= addBadgeGroupURL %>" enctype="multipart/form-data" id="badgeGroupForm" method="post" name="badgeGroupForm">
					<aui:input id="userId" name="userId" type="hidden" value="" />

					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Add Badge Group</h5>

							<button aria-label="Close" class="close" data-dismiss="modal" type="button">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>

						<div class="modal-body">
							<div class="form-group">
								<aui:input class="form-control" id="<%= BadgeTypeEditorPortletKeys.BADGE_GROUP_NAME %>" name="<%= BadgeTypeEditorPortletKeys.BADGE_GROUP_NAME %>" required="required" type="input" value="" />
							</div>

						<div class="modal-footer">
							<button class="btn btn-secondary" data-dismiss="modal" type="button">Cancel</button>
							<button class="btn btn-primary" onclick="addBadgeGroup()" type="submit">Add Badge Group</button>
						</div>
					</div>
				</aui:form>
			</div>
		</div>
	</div>
</c:if>