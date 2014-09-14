package util;

public interface Cell<T> {
	
	public void setTop(T top);
	
	public void setBottom(T bottom);
	
	public void setRight(T right);
	
	public void setLeft(T left);

	public float getTemp();
	
	public void setTemp(float temp);

}
