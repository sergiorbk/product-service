package com.sergosoft.productservice.repository.faker.impl;

import com.sergosoft.productservice.domain.CosmoCat;
import com.sergosoft.productservice.repository.CosmoCatRepository;
import com.sergosoft.productservice.repository.faker.FakeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("fakeRepository")
public class CosmoCatFakeRepository extends FakeRepository<CosmoCat, UUID> implements CosmoCatRepository {

    public CosmoCatFakeRepository() {

        CosmoCat cosmoCatBarsik = CosmoCat.builder()
                .id(UUID.randomUUID())
                .email("barsik@cosmocats.com")
                .password("notStrongPass123%")
                .name("Barsik")
                .registrationDate(LocalDate.now())
                .build();

        CosmoCat cosmoCatMurzik = CosmoCat.builder()
                .id(UUID.randomUUID())
                .email("murzik@cosmocats.com")
                .password("notStrongPass123%")
                .name("Murzik")
                .registrationDate(LocalDate.now())
                .build();

        CosmoCat cosmoCatVasil = CosmoCat.builder()
                .id(UUID.randomUUID())
                .email("vasil@cosmocats.com")
                .password("notStrongPass123%")
                .name("Vasil")
                .registrationDate(LocalDate.now())
                .build();

        save(cosmoCatBarsik);
        save(cosmoCatMurzik);
        save(cosmoCatVasil);
    }

    @Override
    protected UUID nextId() {
        return UUID.randomUUID();
    }

    @Override
    public CosmoCat save(CosmoCat entity) {
        UUID id = nextId();
        database.put(id, entity);
        return database.get(id);
    }

    @Override
    public List<CosmoCat> findAll() {
        return database.values().stream().toList();
    }
}
