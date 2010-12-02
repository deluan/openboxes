package org.pih.warehouse

import grails.converters.*;

import org.pih.warehouse.core.DialogForm;
import org.pih.warehouse.core.Person;
import org.pih.warehouse.inventory.Warehouse;
import org.pih.warehouse.product.Product;
import org.pih.warehouse.shipping.Container;
import org.pih.warehouse.shipping.ShipmentItem;
import org.pih.warehouse.shipping.Shipper;
import org.pih.warehouse.shipping.ShipperService;
import org.pih.warehouse.shipping.Shipment;



class JsonController {

	def findShipperServiceByName = {
		log.info params
		def items = new TreeSet();
		if (params.term) {
			items = ShipperService.withCriteria {
				or {
					ilike("name", "%" +  params.term + "%")					
					ilike("description", "%" +  params.term + "%")
					shipper { 
						ilike("name", "%" +  params.term + "%")
					}
				}
			}
			if (items) {
				items = items.collect() {
					[	value: it.id,
						label: it.shipper.name + " " + it.name,
						valueText: it.shipper.name + " " + it.name,
						desc: it.description,
						icon: "none"]
				}
			}
		}
		render items as JSON;
	}
	
	def findShipperByName = {
		log.info params
		def items = new TreeSet();
		if (params.term) {
			items = Shipper.withCriteria {
				or {
					ilike("name", "%" +  params.term + "%")
					ilike("description", "%" +  params.term + "%")
				}
			}
			if (items) {
				items = items.collect() {
					[	value: it.id,
						label: it.name,
						valueText: it.name,
						desc: it.description,
						icon: "none"]
				}
			}
		}
		render items as JSON;
	}
	
	def findPersonByName = {
		log.info "findPersonByName: " + params
		def items = new TreeSet();
		try {
			
			if (params.term) {
						
				def terms = params.term.split(" ")				
				for (term in terms) { 						
					items = Person.withCriteria {
						or {
							ilike("firstName", "%" + term + "%")
							ilike("lastName", "%" + term + "%")
							ilike("email", "%" + term + "%")
						}
					}
				}
							
				if (items) {
					items.unique();
					items = items.collect() {						
						
						[	value: it.id,
							valueText: it.name,
							label:  "" + it.firstName + " " + it.lastName + "&nbsp;&lt;" +  it.email + "&gt;",
							desc: (it?.email) ? it.email : "no email",
						]
					}
				}
				/*
				else {
					def item =  [
						value: null,
						valueText : params.term,
						label: "Add new person '" + params.term + "'?",
						desc: params.term,
						icon: "none"
					];
					items.add(item)
				}
				*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		render items as JSON;
	}
	
	
	
	def findProductByName = {
		log.info params
		def items = new TreeSet();
		
		if (params.term) {			
			
			// Match full name
			items = Product.withCriteria { 
				ilike("name", "%" + params.term + "%")				
			}
			
			// If no items found, we search by category, product type, name, upc
			if (!items) { 
				def terms = params.term.split(" ")
				for (term in terms) {
					items = Product.withCriteria {
						or {
							ilike("name", "%" + term + "%")
							ilike("upc", "%" + term + "%")
							categories { 
								ilike("name", "%" + term + "%")
							}
							productType { 							
								ilike("name", "%" + term + "%")
							}
						}
					}
				}
			}
		}

		if (!items) { 
			items.add(new Product(name: "No matching products"))
			//items.addAll(Product.getAll());		
		}
		
		
		if (items) {
			// Make sure items are unique
			items.unique();
			items = items.collect() {
				[	value: it.id,
					valueText: it.name,
					label: it.name,
					desc: it.description,
					icon: "none"]
			}
		}
		else {
			def item =  [
				value: null,
				valueText : params.term,
				label: "Add a new product '" + params.term + "'?",
				desc: params.term,
				icon: "none"
			];
			items.add(item)
		}
		render items as JSON;
	}
	

	def findWarehouseByName = {
		log.info params
		def items = new TreeSet();
		if (params.term) {
			items = Warehouse.withCriteria {
				or {
					ilike("name", "%" +  params.term + "%")
				}
			}
			if (items) {
				items = items.collect() {
					[	value: it.id,
						valueText: it.name,
						label: "<img src=\"/warehouse/warehouse/viewLogo/" + it.id + "\" width=\"24\" height=\"24\" style=\"vertical-align: bottom;\"\"/>&nbsp;" + it.name,
						desc: it.name,
						icon: "<img src=\"/warehouse/warehouse/viewLogo/" + it.id + "\" width=\"24\" height=\"24\" style=\"vertical-align: bottom;\"\"/>"]
				}
			}
			/*
			else {
				def item =  [
					value: 0,
					valueText : params.term,
					label: "Add a new warehouse for '" + params.term + "'?",
					desc: params.term,
					icon: "none"
				];
				items.add(item)
			}*/
		}
		render items as JSON;
	}


	def availableItems = {
		log.debug params;
		def items = null;
		if (params.query) {
			
			//String [] parts = params.query.split(" ");
			
			//items = Product.findAllByNameLike("%${params.query}%", [max:10, offset:0, "ignore-case":true]);
			items = Product.withCriteria {
				or {
					ilike("name", "%${params.query}%")
					ilike("description", "%${params.query}%")
				}
			}
			
			items = items.collect() {
				[id:it.id, name:it.name]
			}
		}
		def jsonItems = [result: items]
		render jsonItems as JSON;
	}
		
	
	def availableContacts = {
		def contacts = null;
		if (params.query) {
			contacts = Contact.withCriteria {
				or {
					ilike("name", "%${params.query}%")
					ilike("email", "%${params.query}%")
					ilike("phone", "%${params.query}%")
					ilike("firstName", "%${params.query}%")
					ilike("lastName", "%${params.query}%")
				}
			}
			
			contacts = contacts.collect() {
				[id : it.id, name : it.name]
			}
		}
		def jsonItems = [result: contacts]
		render jsonItems as JSON;
	}

	def availableShipments = {
		log.debug params;
		def items = null;
		if (params.query) {
			items = Shipment.findAllByNameLike("%${params.query}%", [max:10, offset:0, "ignore-case":true]);
			items = items.collect() {
				[id:it.id, name:it.name]
			}
		}
		def jsonItems = [result: items]
		render jsonItems as JSON;
	}
	
	/*
	def savePerson = {	
		log.info("save person" + params)	
		def personInstance = new Person(params)
		if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
			log.info("saved")
			render personInstance as JSON;
		}
		else {
			log.info("errors")
			render("there was an error");
		}
	}*/
	def savePerson = {
		log.info("save person" + params)	
		def personInstance = new Person(params)
		personInstance.save(flush: true)	
		render prepareDialogForm(personInstance) as JSON
	}

	
	def saveBox = {
		log.info("save box " + params)
		def shipmentInstance = Shipment.get(params["shipmentId"]);
		def containerInstance = new Container(params);
		if (containerInstance) {
			log.info("add container");
			shipmentInstance.addToContainers(containerInstance).save(flush:true);
			log.info("added container");
		}
		else {
			log.info("could not add container")
		}
		render prepareDialogForm(containerInstance) as JSON
	}
	
	def saveItem = {
		log.info("save item" + params)		
		//def itemInstance = null;
		def itemInstance = new ShipmentItem(params);	
		def shipmentInstance = Shipment.get(params["shipmentId"]);
		
		if (shipmentInstance) { 
			log.info("shipment is not null")
			shipmentInstance.expectedDeliveryDate = new Date();
			shipmentInstance.save(flush:true);
		}
		
		def containerInstance = Container.get(params["containerId"]);
		def productInstance = Product.get(params["productId"])
		def recipientInstance = Person.get(params["recipientId"]);
		def quantity = (params.quantity) ? Integer.parseInt(params.quantity.trim()) : 1;		
		if (containerInstance) { 
			itemInstance = new ShipmentItem(product: productInstance, quantity: quantity, 
				serialNumber: params.lotNumber, recipient: recipientInstance, lotNumber: params.lotNumber);
			
			if (itemInstance) { 
				log.info("add item to container");
				containerInstance.shipment.addToShipmentItems(itemInstance).save(flush:true);
				shipmentInstance.save(flush:true);
				log.info("added item to container");
			}
			else { 
				log.info("could not create shipment item");
			}
		} 
		else { 
			log.info("could not find container")
		}
		
		
		// Create a new unverified product
		/*
		if (!productInstance) {
			productInstance = new Product(name: params.selectedItem.name, unverified: true);
			if (!productInstance.hasErrors() && productInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'container.label', default: 'Product'), product.id])}"
				log.info("saved product")
			}
			else {
				log.info("error saving product")
				// Encountered an error with saving the product
				//redirect(action: "editContents", id: shipment.id, params: ["container.id": container?.id])
				return;
			}
		}*/
		
		
		
		
		/*
		// Add item to container if product doesn't already exist
		if (containerInstance) {
			def oldQuantity = 0;
			def newQuantity = 0;
			boolean found = false;
			containerInstance.shipmentItems.each {
				if (it.product == productInstance) {
					oldQuantity = it.quantity;
					it.quantity += quantity;
					newQuantity = it.quantity;
					it.save();
					found = true;
				}
			}
			if (!found) {
				itemInstance = new ShipmentItem(product: productInstance, quantity: quantity, serialNumber: params.lotNumber, recipient: recipientInstance);
				containerInstance.shipment.addToShipmentItems(itemInstance).save(flush:true);
			}
			else {
				flash.message = "Modified quantity of existing shipment item ${productInstance.name} from ${oldQuantity} to ${newQuantity}"
			}
		}*/
		
		render prepareDialogForm(itemInstance) as JSON
	}

	
	def prepareDialogForm(domainInstance) {
		//def g = grailsApplication.mainContext.getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
		def dialogForm = new DialogForm()
		if (domainInstance.hasErrors()) {
			g.eachError(bean: domainInstance) {
				log.info("error: " + it.field + it)
				dialogForm.errors."${it.field}" = g.message(error: it)
			}
			dialogForm.success = false
			dialogForm.message = "There was an error"
		} else {
			dialogForm.success = true
			dialogForm.message = "Success"
		}
		dialogForm.domainInstance = domainInstance
		return dialogForm
	}
	
	
	
}