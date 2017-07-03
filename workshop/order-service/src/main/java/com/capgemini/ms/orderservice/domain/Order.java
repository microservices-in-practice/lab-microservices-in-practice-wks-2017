package com.capgemini.ms.orderservice.domain;

import javax.persistence.*;
import java.io.Serializable;

import com.capgemini.ms.orderservice.domain.Address;
import com.capgemini.ms.orderservice.domain.OrderCustomer;
import javax.persistence.ManyToOne;
import com.capgemini.ms.orderservice.domain.OrderLine;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

@Entity
@Table(name = "ORDERTABLE")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "street", column = @Column(name = "SHIPPING_STREET")),
			@AttributeOverride(name = "zip", column = @Column(name = "SHIPPING_ZIP")),
			@AttributeOverride(name = "city", column = @Column(name = "SHIPPING_CITY"))})
	private Address shippingAddress;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "street", column = @Column(name = "BILLING_STREET")),
			@AttributeOverride(name = "zip", column = @Column(name = "BILLING_ZIP")),
			@AttributeOverride(name = "city", column = @Column(name = "BILLING_CITY"))})
	private Address billingAddress;

	@ManyToOne
	private OrderCustomer customer;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<OrderLine> orderLine = new HashSet<OrderLine>();

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public OrderCustomer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final OrderCustomer customer) {
		this.customer = customer;
	}

	public Set<OrderLine> getOrderLine() {
		return this.orderLine;
	}

	public void setOrderLine(final Set<OrderLine> orderLine) {
		this.orderLine = orderLine;
	}
}