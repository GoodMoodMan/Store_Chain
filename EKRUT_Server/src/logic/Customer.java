package logic;

public class Customer extends User {
	
	public enum CustomerStatus {
		Active,
		Frozen,
		Pending,
		Blocked,
	}
	
	private int customer_number;
	private String credit_card_number;
	private CustomerStatus customer_status;
	private int isSubscriber;
	
	public Customer(User user, Customer customer) {
		super(user.username, user.id, user.first_name, user.last_name, Role.Customer, user.email, user.phone_number, user.is_logged_in);
		this.customer_number = customer.customer_number;
		this.credit_card_number = customer.credit_card_number;
		this.customer_status = customer.customer_status;
		this.isSubscriber = customer.isSubscriber;
	}

	public void insertData() {
				
	}

	public void removeData() {
				
	}

	public void updateData() {
				
	}
}