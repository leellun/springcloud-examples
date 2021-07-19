/*
 * Copyright 2017-2021 Dromara.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.newland.hmilytcc.account.controller;

import com.newland.hmilytccaccountservice.dto.AccountDTO;
import com.newland.hmilytccaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * AccountController.
 * @author xiaoyu
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private  AccountService accountService;

    @RequestMapping("/payment")
    public Boolean payment(@RequestBody AccountDTO accountDO) {
        return accountService.payment(accountDO);
    }
    
    @RequestMapping("/testPayment")
    public Boolean testPayment(@RequestBody AccountDTO accountDO) {
        return accountService.testPayment(accountDO);
    }
    
    @RequestMapping("/mockWithTryException")
    public Boolean mockWithTryException(@RequestBody AccountDTO accountDO) {
        return accountService.mockWithTryException(accountDO);
    }
    
    @RequestMapping("/mockWithTryTimeout")
    public Boolean mockWithTryTimeout(@RequestBody AccountDTO accountDO) {
        return accountService.mockWithTryTimeout(accountDO);
    }
    
    @RequestMapping("/paymentWithNested")
    public Boolean paymentWithNested(@RequestBody AccountNestedDTO nestedDTO) {
        return accountService.paymentWithNested(nestedDTO);
    }
    
    @RequestMapping("/paymentWithNestedException")
    public Boolean paymentWithNestedException(@RequestBody AccountNestedDTO nestedDTO) {
        return accountService.paymentWithNestedException(nestedDTO);
    }
    
    @RequestMapping("/findByUserId")
    public BigDecimal findByUserId(@RequestParam("userId") String userId) {
        return accountService.findByUserId(userId).getBalance();
    }
}
