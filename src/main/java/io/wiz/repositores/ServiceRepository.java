package io.wiz.repositores;

import io.wiz.models.ServiceEntity;

public interface ServiceRepository {
   ServiceEntity findServiceByDomain(String domain);
}
