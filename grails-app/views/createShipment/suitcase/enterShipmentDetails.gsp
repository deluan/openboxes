  
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="custom" />
         <title>Enter Shipment Details</title>         
    </head>
    <body>
        <div class="body">
           <g:if test="${message}">
                 <div class="message">${message}</div>
           </g:if>   
			<g:hasErrors bean="${shipmentInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${shipmentInstance}" as="list" />
	            </div>				
			</g:hasErrors>

			<g:render template="flowHeader" model="['currentState':'Details']"/>
			
           	<g:form action="suitcase" method="post">
				<g:hiddenField name="id" value="${shipmentInstance?.id}"/>
				<fieldset>
					<legend>Step 1&nbsp; Enter shipment details</legend>
					
					<g:render template="../shipment/summary" />	
					
               		<div class="dialog">
		                <table>
		                    <tbody>
								<tr class="prop">
									<td valign="top" class="name"><label><g:message code="shipment.type.label" default="Type" /></label></td>
									<td valign="top"
										class="value ${hasErrors(bean: shipmentInstance, field: 'shipmentType', 'errors')}">
										<g:if test="${shipmentInstance?.shipmentType}">
											<g:hiddenField name="shipmentType.id" value="${shipmentInstance?.shipmentType?.id}" />
											${shipmentInstance?.shipmentType?.name }																	
										</g:if>
										<g:else>
											<g:select
												name="shipmentType.id"
												from="${org.pih.warehouse.shipping.ShipmentType.list()}"
												optionKey="id" optionValue="name" value="${shipmentInstance?.shipmentType?.id}" />								
										</g:else>
									</td>
								</tr>
								<tr class='prop'>
									<td valign='top' class='name'>
										<label for='name'><label><g:message code="shipment.name.label" default="Name" /></label>
									</td>
									<td valign='top' class='value ${hasErrors(bean:shipmentInstance,field:'name','errors')}'>
										<input type="text" name='name' value="${shipmentInstance?.name?.encodeAsHTML()}" size="40"/>
									</td>
								</tr>  
								<tr class="prop">
									<td valign="top" class="name"><label><label><g:message code="shipment.origin.label" default="Origin" /></label></td>
									<td valign="top"
										class="value ${hasErrors(bean: shipmentInstance, field: 'origin', 'errors')}">								
										<g:select name="origin.id" from="${org.pih.warehouse.inventory.Warehouse.list()}" optionKey="id" value="${shipmentInstance?.origin?.id}" style="width: 180px" />							
									</td>
								</tr>
								<tr class="prop">
									<td valign="top" class="name"><label><g:message code="shipment.destination.label" default="Destination" /></td>
									<td valign="top" class="value ${hasErrors(bean: shipmentInstance, field: 'destination', 'errors')}">
										<g:select name="destination.id" from="${org.pih.warehouse.inventory.Warehouse.list()}" optionKey="id" value="${shipmentInstance?.destination?.id}" style="width: 180px" />	
									</td>
								</tr>
								<tr class="prop">
									<td valign="top" class="name"><label><g:message code="shipment.expectedShippingDate.label" default="Expected to ship on" /></td>
									<td class="value ${hasErrors(bean: shipmentInstance, field: 'expectedShippingDate', 'errors')}"> 
										<g:jqueryDatePicker id="expectedShippingDate" name="expectedShippingDate"
											value="${shipmentInstance?.expectedShippingDate}" format="MM/dd/yyyy"/>							
									
									</td>
								</tr>
								<tr class="prop">
									<td valign="top" class="name"><label><g:message code="shipment.expectedDeliveryDate.label" default="Expected to arrive on" /></td>
									<td class="value ${hasErrors(bean: shipmentInstance, field: 'expectedDeliveryDate', 'errors')}"> 
										<g:jqueryDatePicker id="expectedDeliveryDate" name="expectedDeliveryDate"
											value="${shipmentInstance?.expectedDeliveryDate}" format="MM/dd/yyyy"/>
									</td>
									
								</tr>
		                    </tbody>
		               </table>
					</div>
					<div class="buttons">
						<table>
							<tr>
								<td style="text-align: center;">
									<g:submitButton name="back" value="Back" disabled="true"></g:submitButton>	
									<g:submitButton name="submit" value="Next"></g:submitButton> 
								</td>
								<td style="text-align: right;">
									<g:submitButton name="done" value="Exit"></g:submitButton>						
								</td>
							</tr>
						</table>
					</div>
				</fieldset>
            </g:form>
        </div>
    </body>
</html>