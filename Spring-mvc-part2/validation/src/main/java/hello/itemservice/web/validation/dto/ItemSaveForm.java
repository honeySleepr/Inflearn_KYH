package hello.itemservice.web.validation.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ItemSaveForm {

	@NotBlank
	private String itemName;

	@NotNull
	@Range(min = 1_000, max = 1_000_000)
	private Integer price;

	@NotNull
	@Max(value = 9999)
	private Integer quantity;

}
