package com.kadirkara.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kadirkara.enums.CurrencyType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {
	   
	    @Column(name = "balance", nullable = false)
	    private BigDecimal balance;

	    @Column(name = "account_no")
		private String accountNo;
	    
	    @Column(name = "iban")
		private String iban;
	    
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "currency_type", nullable = false)
	    private CurrencyType currencyType;
	    
	    @Column(name = "last_update_time")
	    private Date lastUpdateTime;

	    /*@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Transaction> transactions;*/

}
