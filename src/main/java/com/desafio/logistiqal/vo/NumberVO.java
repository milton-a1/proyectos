package com.desafio.logistiqal.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*Any class definition may be annotated with @EqualsAndHashCode to let lombok 
 *generate implementations of the equals(Object other) and hashCode() methods*/

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NumberVO extends GenericVO {
	int valor;

	public NumberVO(int valor, String mensaje, String codigo) {
		super(mensaje, codigo);
		this.valor = valor;

	}

}
