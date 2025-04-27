package org.example.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="Transactions")
public class dbTransaction {

	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "txnId")
	private String txnId = "TXN-" + java.util.UUID.randomUUID().toString();
	@Column(name = "source")
	private String source;
	@Column(name = "destination")
	private String destination;
	@Column(name = "amount")
	@Min(value = 0, message = "Amount must be greater than 0")
	private float amount;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "dbTransaction [txnId=" + txnId + ", source=" + source + ", destination=" + destination + ", amount="
				+ amount + "]";
	}
	public dbTransaction(String source, String destination, float amount) {
		super();
		this.source = source;
		this.destination = destination;
		this.amount = amount;
	}
	public dbTransaction() {
		super();
	}
	
	
	
	
	
	
	
}
