package com.misiontic.compras_ms.repositories;

import com.misiontic.compras_ms.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository <Account, String> { }