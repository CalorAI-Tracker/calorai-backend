package ru.calorai.user.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.calorai.user.jpa.entity.RoleEntity;
import ru.calorai.user.jpa.entity.UserEntity;
import ru.calorai.users.model.User;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToIds")
    User toDomain(UserEntity userEntity);

    @Named("rolesToIds")
    default Set<Long> rolesToIds(Set<RoleEntity> roles) {
        if (roles == null) {
            return Collections.emptySet();
        }
        return roles.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toSet());
    }
}
