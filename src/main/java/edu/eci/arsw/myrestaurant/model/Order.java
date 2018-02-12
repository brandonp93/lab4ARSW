package edu.eci.arsw.myrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.eci.arsw.myrestaurant.services.OrderServicesException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Order {


    private Map<String, Integer> orderAmountsMap;

    public Map<String, Integer> getOrderAmountsMap() {
        return orderAmountsMap;
    }

    public void setOrderAmountsMap(Map<String, Integer> orderAmountsMap) {
        this.orderAmountsMap = orderAmountsMap;
    }
    private int tableNumber;

    public Order() {
    }    
    
    public Order(int tableNumber) {
        orderAmountsMap = new ConcurrentHashMap<>();
        this.tableNumber = tableNumber;
    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void addDish(String p, int amount) {
        if (!orderAmountsMap.containsKey(p)){
            orderAmountsMap.put(p, amount);
        }
        else{
            int previousAmount=orderAmountsMap.get(p);
            orderAmountsMap.put(p, previousAmount+amount);
        }
    } 
    
    public void removeDish(String p, int amount) throws OrderServicesException {
        if (!orderAmountsMap.containsKey(p)){
            throw new OrderServicesException("La mesa no ha pedido este plato");
        }
        else{
            int previousAmount=orderAmountsMap.get(p);
            if(previousAmount-amount>0){
                orderAmountsMap.put(p, previousAmount-amount);
            }
            else{
                orderAmountsMap.remove(p);
            }
        }
    }
        
    @JsonIgnore
    public Set<String> getOrderedDishes() {
        return orderAmountsMap.keySet();
    }

    public int getDishOrderedAmount(String p) {
        if (!orderAmountsMap.containsKey(p)) {
            return 0;
        } else {
            return orderAmountsMap.get(p);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Table " + tableNumber+"\n");
        getOrderedDishes().forEach((p) -> {
            sb.append(p).append(" x ").append(orderAmountsMap.get(p)).append("\n");
        });
        return sb.toString();

    }

}
