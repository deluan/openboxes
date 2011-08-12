
<%@ page import="org.pih.warehouse.shipping.Shipper" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="custom" />
        <g:set var="entityName" value="${warehouse.message(code: 'shippers.label')}" />
        <title><warehouse:message code="default.list.label" args="[entityName]" /></title>
        <!-- Specify content to overload like global navigation links, page titles, etc. -->
		<content tag="pageTitle"><warehouse:message code="default.list.label" args="[entityName]" /></content>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
            
				<div>            	
	            	<span class="menuButton">
	            		<g:link class="new" action="create"><warehouse:message code="default.add.label" args="['shipper']"/></g:link>
	            	</span>
            	</div>
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="${warehouse.message(code: 'default.name.label')}" />
                        
                            <g:sortableColumn property="description" title="${warehouse.message(code: 'default.description.label')}" />
                        
                            <g:sortableColumn property="trackingUrl" title="${warehouse.message(code: 'shipper.trackingUrl.label')}" />
                        
                            <g:sortableColumn property="trackingFormat" title="${warehouse.message(code: 'shipper.trackingFormat.label')}" />
                        
                            <g:sortableColumn property="parameterName" title="${warehouse.message(code: 'shipper.parameterName.label')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${shipperInstanceList}" status="i" var="shipperInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                          
                            <td><g:link action="show" id="${shipperInstance.id}">${fieldValue(bean: shipperInstance, field: "name")}</g:link></td>
                        
                            <td>${fieldValue(bean: shipperInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: shipperInstance, field: "trackingUrl")}</td>
                        
                            <td>${fieldValue(bean: shipperInstance, field: "trackingFormat")}</td>
                        
                            <td>${fieldValue(bean: shipperInstance, field: "parameterName")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${shipperInstanceTotal}" />
            </div>
        </div>
    </body>
</html>