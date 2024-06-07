package com.rich.AccountApplicationTest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getCustomerByCustomerNumber(Long customerNumber);
}
