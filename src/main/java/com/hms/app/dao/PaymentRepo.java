package com.hms.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.app.model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {


}
