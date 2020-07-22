package cn.edu.bjtu.ebosuser.dto.base;

import org.springframework.lang.NonNull;
import static cn.edu.bjtu.ebosuser.util.BeanUtils.updateProperties;

public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {
    default <T extends DTO> T convertForm(@NonNull DOMAIN domain) {
        updateProperties(domain, this);
        return (T) this;
    }
}
