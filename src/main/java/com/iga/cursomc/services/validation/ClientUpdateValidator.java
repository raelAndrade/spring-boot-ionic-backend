package com.iga.cursomc.services.validation;

import com.iga.cursomc.domain.Cliente;
import com.iga.cursomc.domain.enums.TipoCliente;
import com.iga.cursomc.dto.ClienteDTO;
import com.iga.cursomc.dto.ClienteNewDTO;
import com.iga.cursomc.repositories.ClienteRepository;
import com.iga.cursomc.resources.exception.FieldMessage;
import com.iga.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClientUpdate ann){ }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context){
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();
        // Inclua os testes aqui, inserindo erros na lista
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){
            list.add(new FieldMessage("email", "E-mail já existente"));
        }
        for (FieldMessage e : list){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
