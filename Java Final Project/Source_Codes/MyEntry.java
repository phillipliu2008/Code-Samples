import java.util.Map;


public class MyEntry<X, Y> implements Map.Entry<X, Y>{
	private X key;
	private Y value;
	
	public MyEntry(X key, Y value){
		this.key = key;
		this.value = value;
	}
	
	public boolean equals(MyEntry<X,Y> other){
		return key == other.key && value == other.value;
	}
	
	@Override
	public X getKey() {
		return key;
	}

	@Override
	public Y getValue() {
		return value;
	}

	@Override
	public Y setValue(Y value) {
		Y oldValue = this.value;
		this.value = value;
		return oldValue;
	}

}
