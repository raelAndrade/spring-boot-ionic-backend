package com.iga.cursomc.services.validation;

import com.iga.cursomc.domain.Cliente;
import com.iga.cursomc.domain.enums.TipoCliente;
import com.iga.cursomc.dto.ClienteNewDTO;
import com.iga.cursomc.repositories.ClienteRepository;
import com.iga.cursomc.resources.exception.FieldMessage;
import com.iga.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClientInsert ann){ }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context){
        List<FieldMessage> list = new ArrayList<>();
        // Inclua os testes aqui, inserindo erros na lista
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null){
            list.add(new FieldMessage("email", "E-mail já existente"));
        }
        for (FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
