package com.abhijith.dream_books.service;

import com.abhijith.dream_books.dto.AddressDTO;
import com.abhijith.dream_books.entity.Address;
import com.abhijith.dream_books.entity.Orders;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public Address createAddressFromDTO(AddressDTO addressDTO, Orders orders){

        Address address = new Address();
        address.setOrder(orders);
        address.setName(addressDTO.getName());
        address.setMobile(addressDTO.getMobile());
        address.setAddressline(addressDTO.getAddress());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipcode(addressDTO.getZipcode());

        return address;
    }
}
