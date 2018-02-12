/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.Order;
import edu.eci.arsw.myrestaurant.model.ProductType;
import edu.eci.arsw.myrestaurant.model.RestaurantProduct;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServicesStub;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/orders")
public class OrdersAPIController {

        @Autowired
        RestaurantOrderServicesStub orders;

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<?> manejadorGetOrders() throws OrderServicesException{
            try {
                    //obtener datos que se enviarán a través del API
                    return new ResponseEntity<>(orders.getTablesWithOrders(),HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                    Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                    return new ResponseEntity<>("Error " + ex,HttpStatus.NOT_FOUND);
            }  
        } 

        @RequestMapping(path = "/{idmesa}",method = RequestMethod.GET)
        public ResponseEntity<?> manejadorGetOrders(@PathVariable int idmesa) throws OrderServicesException{
            try {
                if(orders.getTableOrder(idmesa)==null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity<>(orders.getTableOrder(idmesa),HttpStatus.ACCEPTED);
                }
            } catch (Exception ex) {
                Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>("Error " + ex,HttpStatus.NOT_FOUND);
            }  
        } 

        @RequestMapping(method = RequestMethod.POST)	
        public ResponseEntity<?> manejadorPostNewOrden(@RequestBody Order o){
            try {
                    //registrar dato
                    orders.addNewOrderToTable(o);
                    return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (OrderServicesException ex) {
                    Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                    return new ResponseEntity<>("Error " + ex,HttpStatus.FORBIDDEN);            
            }        

        }
        
        @RequestMapping(path = "/{idmesa}/total",method = RequestMethod.GET)
        public ResponseEntity<?> manejadorGetOrdersTotal(@PathVariable int idmesa) throws OrderServicesException{
            try {
                if(orders.getTableOrder(idmesa)==null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                else{
                    return new ResponseEntity<>(orders.calculateTableBill(idmesa),HttpStatus.ACCEPTED);
                }
            } catch (Exception ex) {
                Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>("Error " + ex,HttpStatus.NOT_FOUND);
            }  
        } 
        
        @RequestMapping(path = "/{idmesa}",method = RequestMethod.PUT)	
        public ResponseEntity<?> manejadorPostModifyOrden(@PathVariable int idmesa,@RequestBody Order o){
            try {
                    //registrar dato                   
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } catch (Exception ex) {
                    Logger.getLogger(OrdersAPIController.class.getName()).log(Level.SEVERE, null, ex);
                    return new ResponseEntity<>("Error " + ex,HttpStatus.FORBIDDEN);            
            }        

        }
}
