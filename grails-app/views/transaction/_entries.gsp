<g:if test="${transactionInstance?.id }">
    <div class="box">
        <h2>${warehouse.message(code: 'transaction.transactionEntries.label')}</h2>
        <table id="prodEntryTable">
            <thead>
            <tr class="odd">
                <th></th>
                <th><warehouse:message code="product.label"/></th>
                <th style="text-align: center"><warehouse:message code="product.lotNumber.label"/></th>
                <th style="text-align: center"><warehouse:message code="product.expirationDate.label"/></th>
                <th style="text-align: center"><warehouse:message code="default.qty.label"/></th>
            </tr>
            </thead>
            <tbody>
            <g:set var="transactionSum" value="${0 }"/>
            <g:set var="transactionCount" value="${0 }"/>

            <g:if test="${transactionInstance?.transactionEntries }">
                <g:each in="${transactionInstance?.transactionEntries.sort { it?.inventoryItem?.product?.name } }" var="transactionEntry" status="status">
                    <g:set var="selected" value="${transactionEntry?.inventoryItem?.product?.id == params?.product?.id}"/>
                    <g:set var="transactionSum" value="${transactionSum + transactionEntry?.quantity}"/>
                    <g:set var="transactionCount" value="${transactionCount+1 }"/>
                    <tr class="${status%2?'odd':'even' } ${selected?'selected':''}">
                        <td>
                            <g:link controller="transactionEntry" action="edit" id="${transactionEntry?.id}">
                                <img src="${createLinkTo(dir:'images/icons/silk',file:'pencil.png')}" style="vertical-align: middle"/>
                            </g:link>

                        </td>
                        <td style="text-align: left;">
                            <%--
                            <g:if test="${params?.showAll || !params.product }">
                                <g:link controller="inventory" action="showTransaction" id="${transactionInstance.id}" params="['product.id':transactionEntry?.inventoryItem?.product?.id]">
                                    <img src="${createLinkTo(dir:'images/icons/silk',file:'zoom.png')}" alt="${warehouse.message(code: 'transaction.showSingleProduct.label') }" style="vertical-align: middle"/>
                                </g:link>
                            </g:if>
                            <g:else>
                                <g:if test="${transactionInstance?.transactionEntries?.size() > transactionCount || params?.product?.id }">
                                    <a href="?showAll=true">
                                        <img src="${createLinkTo(dir:'images/icons/silk',file:'decline.png')}" alt="${warehouse.message(code: 'transaction.showAllProducts.label') }" style="vertical-align: middle"/>
                                    </a>
                                </g:if>
                            </g:else>
                            --%>
                            <g:link controller="inventoryItem" action="showStockCard" params="['product.id':transactionEntry?.inventoryItem?.product?.id]">
                                <format:product product="${transactionEntry?.inventoryItem?.product}"/>
                            </g:link>
                        </td>
                        <td class="center">
                            ${transactionEntry?.inventoryItem?.lotNumber }
                        </td>
                        <td class="center">
                            <format:expirationDate obj="${transactionEntry?.inventoryItem?.expirationDate}"/>
                        </td>
                        <td class="center">
                            ${transactionEntry?.quantity}
                        </td>
                    </tr>
                </g:each>
            <%--
            <g:if test="${transactionInstance?.transactionEntries?.size() > transactionCount || params?.product?.id }">
                <tr>
                    <td>
                        <a href="?showAll=true">show all</a>
                    </td>
                </tr>
            </g:if>
            --%>
            </g:if>
            <g:else>
                <tr>
                    <td colspan="4">
                        <div class="empty center">
                            <warehouse:message code="transaction.noEntries.message"/>
                        </div>
                    </td>
                </tr>
            </g:else>
            </tbody>
        </table>
    </div>
</g:if>