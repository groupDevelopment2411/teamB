package update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
	@Autowired
	private UpdateMapper mapper;
		
		public void Update(update.Update employee) {
			mapper.update(employee);
		}
	}



