package logic;

public class Product {
	
	private String productName;
	private String productID;
	private String productDescription;
	private int productLimit;
	private int productAmount;
	private ProductStatus status;
	
	
	public Product(String productName, String productID, String productDescription, int productLimit,
			int productAmount) {
		this.productName = productName;
		this.productID = productID;
		this.productDescription = productDescription;
		this.productLimit = productLimit;
		this.productAmount = productAmount;
		this.status = status.available;
	}
	
	
	public boolean GetProduct(int amaunt) {
		if(amaunt >=productAmount)
			return false;
		productAmount -= amaunt;
		changeStatuse();
		return true;
			
	}


	private void changeStatuse() {
		// TODO Auto-generated method stub
		if(productAmount ==0)
		{
			status = status.unavailable;		}
		else if(productAmount <= productLimit) {
			status = status.Limit;	
		}
	}
	



	
	

}
