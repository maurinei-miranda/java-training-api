package br.com.training.repositorys;

import br.com.training.models.UserAdmin;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserAdminRepository extends PagingAndSortingRepository<UserAdmin, Long> {
    UserAdmin findUserAdminByUsername(String username);
}
