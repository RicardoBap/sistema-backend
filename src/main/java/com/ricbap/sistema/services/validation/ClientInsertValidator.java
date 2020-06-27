package com.ricbap.sistema.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ricbap.sistema.domain.enums.TipoCliente;
import com.ricbap.sistema.dto.ClienteNewDTO;
import com.ricbap.sistema.resources.exception.FieldMessage;
import com.ricbap.sistema.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid( ClienteNewDTO clienteNewDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (clienteNewDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(clienteNewDto.getCpfOuCnpj() )) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (clienteNewDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(clienteNewDto.getCpfOuCnpj() )) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				.addPropertyNode(e.getFieldMessage()) //<-------e.getFieldName()
				.addConstraintViolation();
		}
		return list.isEmpty();
	}
}