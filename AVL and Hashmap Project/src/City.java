
import java.io.File;

public class City {
	private String cityName;
	private String cityFile;

	public City(String s ,String f) {
		cityName = s;
		cityFile = f;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityFile() {
		return cityFile;
	}

	public void setCityFile(String cityFile) {
		this.cityFile = cityFile;
	}

	

	
	public int compareTo(City o) {
		
		if(this.cityName.compareTo(o.getCityName()) > 0) {
			return 1;
		}
		else if(this.cityName.compareTo(o.getCityName()) < 0)
		{
			return -1;
			
		}
		
		else
		return 0;
	}
	
	public String toFile() {
		
		return cityName+"/"+cityFile;
		
	}
	
}
