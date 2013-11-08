/**
* Copyright (c) 2012 Partners In Health.  All rights reserved.
* The use and distribution terms for this software are covered by the
* Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
* which can be found in the file epl-v10.html at the root of this distribution.
* By using this software in any fashion, you are agreeing to be bound by
* the terms of this license.
* You must not remove this notice, or any other, from this software.
**/
package org.pih.warehouse.core

import grails.test.mixin.Mock
import org.junit.Before
import org.junit.Test
import org.pih.warehouse.inventory.Inventory
import org.pih.warehouse.inventory.InventoryItem
import org.pih.warehouse.inventory.TransactionCode
import org.pih.warehouse.inventory.TransactionType
import org.pih.warehouse.product.Category

// import org.pih.warehouse.core.Location
import org.pih.warehouse.product.Product

@Mock([LocationType, Location, TransactionType, Category, Product, InventoryItem, Inventory])
class BaseUnitTest {

    def consumptionTransactionType
    def inventoryTransactionType
    def productInventoryTransactionType
    def transferInTransactionType
    def transferOutTransactionType

    @Before
	void setUp() {
          // create some default location types
        def warehouseLocationType = new LocationType(name: "Location", description: "Location").save()
        def supplierLocationType= new LocationType(name: "Supplier", description: "Supplier").save()

        // create a default location
        new Location(name: "Acme Supply Company", locationType: supplierLocationType).save()

        // create some default warehouses and inventories
        def bostonLocation = new Location(name: "Boston Location", locationType: warehouseLocationType).save()
        def haitiLocation = new Location(name: "Haiti Location", locationType: warehouseLocationType).save()

        def bostonLocationInventory = new Inventory(warehouse: bostonLocation).save()
        def haitiLocationInventory = new Inventory(warehouse: haitiLocation).save()

        bostonLocation.inventory = bostonLocationInventory
        haitiLocation.inventory = haitiLocationInventory

        // create some default transaction types
        consumptionTransactionType = new TransactionType(name: "Consumption", transactionCode: TransactionCode.DEBIT).save()
        inventoryTransactionType = new TransactionType(name: "Inventory", transactionCode: TransactionCode.INVENTORY).save()
        productInventoryTransactionType = new TransactionType(name: "Product Inventory", transactionCode: TransactionCode.PRODUCT_INVENTORY).save()
        transferInTransactionType = new TransactionType(name: "Transfer In", transactionCode: TransactionCode.CREDIT).save()
        transferOutTransactionType = new TransactionType(name: "Transfer Out", transactionCode: TransactionCode.DEBIT).save()


		def category = new Category(name: "Pain Medication").save()

        // create some products
        def aspirin = new Product(name: "Aspirin", category: category).save()
        def tylenol = new Product(name:"Tylenol", category: category).save()

        // create some inventory items
        new InventoryItem(product: aspirin, lotNumber: "1").save()
        new InventoryItem(product: aspirin, lotNumber: "2").save()
        new InventoryItem(product: tylenol, lotNumber: "1").save()

    }

    @Test
	void testDataHasBeenInitialized() {
		assertEquals 2, LocationType.list().size()
		assertEquals 3, Location.list().size()
		assertEquals 5, TransactionType.list().size()
		assertEquals 2, Product.list().size()
		assertEquals 3, InventoryItem.list().size()
	}

}
